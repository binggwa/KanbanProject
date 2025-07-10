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

import java.io.IOException;

public class ColumnController {

    @FXML
    private Label columnTitleLabel;

    @FXML
    private VBox taskListContainer;

    @FXML
    private Button addTaskButton;

    private BoardController boardController; // 상위 보드 컨트롤러
    private Column column; // 이 컬럼에 대한 모델 객체

    public void setBoardController(BoardController controller) {
        this.boardController = controller;
    }

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
        taskListContainer.getChildren().clear();

        for (Task task : column.getTasks()) {
            try {
                FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/com/kanban/view/TaskCard.fxml"));
                AnchorPane taskCard = cardLoader.load();

                TaskCardController cardController = cardLoader.getController();
                cardController.setTask(task);

                // 삭제 시 Column의 Task에서도 제거
                cardController.setOnDeleteCallback(() -> {
                    column.removeTask(task);
                    refreshTasks();
                });

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kanban/view/TaskInputForm.fxml"));
            AnchorPane formPane = loader.load();

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Add New Task");

            Scene scene = new Scene(formPane);
            dialog.setScene(scene);

            TaskInputFormController formController = loader.getController();
            formController.setDialogStage(dialog);

            dialog.showAndWait();

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
