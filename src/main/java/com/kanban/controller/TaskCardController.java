package com.kanban.controller;

import com.kanban.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TaskCardController {

    @FXML
    private Label taskTitleLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Circle priorityIndicator;

    @FXML
    private HBox tagContainer;

    @FXML
    private Button deleteButton;

    private Task task;

    // 외부에서 등록하는 삭제 콜백
    private Runnable onDeleteCallback;

    /**
     * Task 설정 및 UI 표시
     */
    public void setTask(Task task) {
        this.task = task;

        taskTitleLabel.setText(task.getTitle());
        dueDateLabel.setText("Due: " + task.getDueDate());

        // 우선순위 표시
        Task.Priority priorityEnum = task.getPriorityEnum();
        priorityLabel.setText(priorityEnum.toString());

        switch (priorityEnum) {
            case LOW -> priorityIndicator.setFill(Color.GREEN);
            case MEDIUM -> priorityIndicator.setFill(Color.ORANGE);
            case HIGH -> priorityIndicator.setFill(Color.RED);
        }

        // 태그 표시
        tagContainer.getChildren().clear();
        String[] tags = task.getTag().split(",");
        for (String tag : tags) {
            Label tagLabel = new Label(tag.trim());
            tagLabel.setStyle("-fx-background-color: #d9d9d9; -fx-padding: 2 5 2 5; -fx-border-radius: 3; -fx-background-radius: 3;");
            tagContainer.getChildren().add(tagLabel);
        }
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
     * 외부에서 삭제 콜백 설정
     */
    public void setOnDeleteCallback(Runnable callback) {
        this.onDeleteCallback = callback;
    }

    /**
     * 현재 Task 반환
     */
    public Task getTask() {
        return task;
    }
}
