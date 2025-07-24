package com.kanban.controller;

import com.kanban.model.Column;
import com.kanban.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.scene.input.*;

import java.io.IOException;

public class ColumnController {

    @FXML
    private Label columnTitleLabel;     // 컬럼의 제목을 표시하는 <Label fx:id="columnTitleLabel"> 과 연결

    @FXML
    private VBox taskListContainer;     // TaskCard 배치 컨테이너. <VBox fx:id="taskListContainer"> 와 연결

    @FXML
    private Button addTaskButton;

    private BoardController boardController; // 상위 보드 컨트롤러
    private Column column; // 이 컬럼에 대한 모델 객체

    public void setBoardController(BoardController controller) {
        this.boardController = controller;

        taskListContainer.setPickOnBounds(true);                          // 비어 있어도 드롭 이벤트를 받을 수 있도록 설정 추가
        taskListContainer.setStyle("-fx-background-color: transparent;"); // 이벤트 처리를 위한 투명 배경 추가

        // 드롭 대상 이벤트 등록
        // 드래그한 TaskCard가 이 Column 위에 있을 때 Drop 허용 조건 처리
        taskListContainer.setOnDragOver(event -> {
            if (event.getGestureSource() != taskListContainer && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE); // 드래그 오버 허용
            }
            event.consume();
        });

        // TaskCard가 실제로 Drop 되었을 때의 처리
        taskListContainer.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();        // 드래그 과정에서 주고받는 데이터를 담는 객체인 Dragboard, 드래그 출발지에서 설정한 문자열(Task ID) 이 들어 있음
            boolean success = false;                    // 드롭 성공 여부를 저장할 변수

            if (db.hasString()) {                                        // TaskID가 문자열로 들어오므로 체크
                String taskId = db.getString();                          // 드래그한 TaskCard의 ID를 문자열로 꺼냄
                Task movedTask = boardController.findTaskById(taskId);   // Task ID로 Board 전체에서 해당 Task를 탐색
                if (movedTask != null) {                                 // Task가 실제로 존재하는지 체크
                    boardController.moveTaskToColumn(movedTask, column);
                    boardController.refreshUIFromBoard(); // UI 갱신
                    success = true;                       // 드롭 작업 성공으로 처리
                }
            }
            event.setDropCompleted(success);              // 드롭 처리가 성공적으로 끝났음을 JavaFX에 알림
            event.consume();                              // 이벤트가 더 이상 다른 핸들러로 전달되지 않도록 소모 처리
        });
    }

    // 모델 객체인 Column을 설정하고, UI에 표시 및 Task 목록 새로고침
    public void setColumn(Column column) {
        this.column = column;
        columnTitleLabel.setText(column.getName());
        refreshTasks();
    }

    public Column getColumn() {
        return column;
    }

    /**
     * Column 모델의 Task 목록으로 UI 갱신
     */
    private void refreshTasks() {
        taskListContainer.getChildren().clear();        // 기존 TaskCard UI 모두 제거

        for (Task task : column.getTasks()) {
            try {
                // Task 객체를 기반으로 각 TaskCard FXML을 로딩
                FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/com/kanban/view/TaskCard.fxml"));
                AnchorPane taskCard = cardLoader.load();

                TaskCardController cardController = cardLoader.getController();
                cardController.setTask(task);

                // Delete 버튼 클릭 시 Column의 Task에서도 제거, UI 갱신
                cardController.setOnDeleteCallback(() -> {
                    column.removeTask(task);
                    refreshTasks();
                });

                // 새로 생성된 TaskCard를 VBox에 추가
                taskListContainer.getChildren().add(taskCard);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add Task 버튼 클릭 시 호출
     */
    @FXML
    private void onAddTaskClicked() {
        try {
            // TaskInputForm.fxml 로딩
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kanban/view/TaskInputForm.fxml"));
            AnchorPane formPane = loader.load();

            Stage dialog = new Stage();                         // 모달 창 형태로 입력창 생성
            dialog.initModality(Modality.APPLICATION_MODAL);    // 현재 앱을 멈추고 이 창에 집중하도록 설정
            dialog.setTitle("Add New Task");                    // 다이얼로그 창의 제목 설정

            // 로드된 폼을 포함하는 Scene 설정
            // JavaFX가 Stage에 직접 AnchorPane을 붙일 수 없으며, Scene을 통해서만 붙일 수 있음
            Scene scene = new Scene(formPane);
            dialog.setScene(scene);

            // TaskInputFormController 인스턴스를 가져와서 Stage를 주입
            // 폼 입력을 받은 후 창을 닫아야 하는데, 컨트롤러는 Stage 객체를 기본적으로 알 수 없기 때문에, Stage를 전달해줘야 함
            TaskInputFormController formController = loader.getController();
            formController.setDialogStage(dialog);

            // 다이얼로그 창을 띄우고 사용자의 입력을 기다림
            dialog.showAndWait();

            // Task추가 UI가 닫힌 뒤, 입력한 Task가 유효하면 모델에 추가하고 UI 업데이트
            Task newTask = formController.getCreatedTask();
            if (newTask != null) {
                column.addTask(newTask);
                refreshTasks();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 외부에서 TaskCard를 직접 추가하고 싶을 때
     */
    public void addTaskCard(AnchorPane taskCard) {
        taskListContainer.getChildren().add(taskCard);
    }
}
