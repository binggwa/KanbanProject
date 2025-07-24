package com.kanban.controller;

import com.kanban.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.*;

public class TaskCardController {

    @FXML
    private AnchorPane rootPane;    // 드래그 시작 이벤트를 걸기 위한 루트 패널 추가, TaskCard 전체를 감싸는 루트 패널

    @FXML
    private Label taskTitleLabel;   // 제목

    @FXML
    private Label dueDateLabel;     // 마감일

    @FXML
    private Label priorityLabel;    // 우선순위

    @FXML
    private Circle priorityIndicator;   // 우선순위를 시각적으로 표시하는 원 UI

    @FXML
    private HBox tagContainer;      // 태그 보관 HBox 컨테이너

    @FXML
    private Button deleteButton;    // Task를 삭제버튼

    private Task task;

    // 삭제 버튼 클릭 시 외부에서 지정한 동작을 실행할 수 있도록 콜백 등록
    private Runnable onDeleteCallback;

    /**
     * Task 설정 및 UI 표시
     */
    public void setTask(Task task) {
        this.task = task;

        // 제목과 마감일을 Label에 표시
        taskTitleLabel.setText(task.getTitle());
        dueDateLabel.setText("Due: " + task.getDueDate());

        // 우선순위 값을 Enum으로 변환해 텍스트로 표시
        Task.Priority priorityEnum = task.getPriorityEnum();
        priorityLabel.setText(priorityEnum.getDisplayName());

        // 우선순위에 따라 원 색깔 변경
        switch (priorityEnum) {
            case LOW -> priorityIndicator.setFill(Color.GREEN);
            case MEDIUM -> priorityIndicator.setFill(Color.ORANGE);
            case HIGH -> priorityIndicator.setFill(Color.RED);
        }

        tagContainer.getChildren().clear();               // 태그 표시
        String[] tags = task.getTag().split(",");   // 태그 문자열(콤마로 구분된)을 배열로 분리

        // 각 태그마다 Label을 만들고 스타일을 지정해 tagContainer에 추가
        for (String tag : tags) {
            Label tagLabel = new Label(tag.trim());
            tagLabel.setStyle("-fx-background-color: #d9d9d9; -fx-padding: 2 5 2 5; -fx-border-radius: 3; -fx-background-radius: 3;");
            tagContainer.getChildren().add(tagLabel);
        }
    }

    @FXML
    private void initialize() {
        // 드래그 시작 시 ID를 Dragboard에 등록
        rootPane.setOnDragDetected(event -> {
            Dragboard db = rootPane.startDragAndDrop(TransferMode.MOVE);        // 드래그 보드 시작, MOVE 모드로 원본을 옮김
            ClipboardContent content = new ClipboardContent();                  // 드래그 앤 드롭 시 전송할 데이터를 담는 컨테이너 JavaFX 클래스
            content.putString(task.getId());                                    // 드래그 중에 식별자로 사용할 Task ID 문자열 데이터를 저장
            db.setContent(content);                                             // 드래그 보드에 이 데이터를 등록
            event.consume();                                                    // 드래그 보드에 콘텐츠 적용 후 이벤트 소비
        });
    }

    /**
     * 삭제 버튼 클릭 시 호출
     */
    @FXML
    private void onDeleteClicked() {
        if (onDeleteCallback != null) {
            onDeleteCallback.run();
        }
    }

    /**
     * 외부에서 삭제 콜백 함수를 주입받는 메서드
     */
    public void setOnDeleteCallback(Runnable callback) {
        this.onDeleteCallback = callback;
    }

    public Task getTask() {
        return task;
    }
}
