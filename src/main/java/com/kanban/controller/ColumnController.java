package com.kanban.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import java.time.LocalDate;
import java.util.List;
import com.kanban.model.Task;
import com.kanban.controller.TaskCardController;


import java.io.IOException;

public class ColumnController {
    @FXML
    private Label columnTitleLabel;

    @FXML
    private VBox taskListContainer;

    @FXML
    private Button addTaskButton;

    /**
     * Add Task 버튼 클릭 시 호출되는 메서드
     */
    @FXML
    private void onAddTaskClicked() {
        try {
            // TaskCard.fxml 파일을 로드하여 taskListContainer에 추가
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TaskCard.fxml"));
            AnchorPane taskCard = loader.load();

            // TaskCardController와 연동
            // 1. 컨트롤러 가져오기
            TaskCardController controller = loader.getController();

            // 2. Task 객체 생성 (샘플 값. 실제론 사용자 입력 받아 생성하는 게 좋음)
            Task newTask = new Task("New Task", LocalDate.now().plusDays(3), Task.Priority.MEDIUM, List.of("sample"));

            // 3. TaskCard UI에 Task 내용 세팅
            controller.setTask(newTask);

            // 4. 삭제 시 동작 설정 (자기 자신을 taskListContainer에서 제거)
            controller.setOnDeleteCallback(() -> taskListContainer.getChildren().remove(taskCard));

            // 5. 화면에 추가
            taskListContainer.getChildren().add(taskCard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 외부에서 컬럼 제목 설정할 수 있게 하는 메서드
     */
    public void setColumnTitle(String title) {
        columnTitleLabel.setText(title);
    }

    /**
     * TaskCard를 외부에서 추가하고 싶을 때 사용
     */
    public void addTaskCard(AnchorPane taskCard) {
        taskListContainer.getChildren().add(taskCard);
    }
}
