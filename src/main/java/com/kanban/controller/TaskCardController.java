package com.kanban.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import com.kanban.model.Task;

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

    // 필요시 외부에서 삭제 처리 콜백 등록 가능
    private Runnable onDeleteCallback;


    public void setTask(Task task) {
        this.task = task;

        taskTitleLabel.setText(task.getTitle());
        dueDateLabel.setText("Due: " + task.getDueDate());
        priorityLabel.setText(task.getPriority().toString());

        // 우선순위 색상 설정
        switch (task.getPriority()) {
            case LOW -> priorityIndicator.setFill(Color.GREEN);
            case MEDIUM -> priorityIndicator.setFill(Color.ORANGE);
            case HIGH -> priorityIndicator.setFill(Color.RED);
        }

        // 태그 설정 (간단하게 Label로 표시)
        tagContainer.getChildren().clear();
        for (String tag : task.getTags()) {
            Label tagLabel = new Label(tag);
            tagLabel.setStyle("-fx-background-color: #d9d9d9; -fx-padding: 2 5 2 5; -fx-border-radius: 3; -fx-background-radius: 3;");
            tagContainer.getChildren().add(tagLabel);
        }
    }

    /**
     * Delete 버튼 클릭 시 호출되는 메서드
     */
    @FXML
    private void onDeleteClicked() {
        // 삭제 콜백이 등록되어 있다면 실행
        if (onDeleteCallback != null) {
            onDeleteCallback.run();
        }
    }

    /**
     * 외부에서 삭제 이벤트 처리를 위임하고 싶을 때 사용
     */
    public void setOnDeleteCallback(Runnable callback) {
        this.onDeleteCallback = callback;
    }

    /**
     * 현재 Task 객체 반환
     */
    public Task getTask() {
        return task;
    }
}