package com.kanban.controller;

import com.kanban.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TaskInputFormController {

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private ChoiceBox<String> priorityChoiceBox;

    @FXML
    private TextField tagsField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addButton;

    private Stage dialogStage;
    private Task createdTask;

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public Task getCreatedTask() {
        return createdTask;
    }

    @FXML
    private void initialize() {
        priorityChoiceBox.getItems().addAll("LOW", "MEDIUM", "HIGH");
        priorityChoiceBox.setValue("MEDIUM"); // 기본값
        dueDatePicker.setValue(LocalDate.now()); // 오늘 날짜 기본값
    }

    @FXML
    private void onAdd() {
        String title = titleField.getText();
        LocalDate dueDate = dueDatePicker.getValue();
        String priority = priorityChoiceBox.getValue();
        String tagsText = tagsField.getText();

        if (title == null || title.isBlank()) {
            showWarning("Task 제목을 입력해주세요.");
            return;
        }
        if (dueDate == null) {
            showWarning("마감일을 선택해주세요.");
            return;
        }

        List<String> tags = Arrays.asList(tagsText.split(",\\s*"));
        createdTask = new Task(title, dueDate, priority, tags);

        dialogStage.close();
    }

    @FXML
    private void onCancel() {
        createdTask = null;
        dialogStage.close();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("입력 오류");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
