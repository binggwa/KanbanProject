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
    private TextField titleField;       // 제목

    @FXML
    private DatePicker dueDatePicker;   // 날짜 선택

    @FXML
    private ChoiceBox<String> priorityChoiceBox;    // 우선순위 선택 드롭다운 메뉴

    @FXML
    private TextField tagsField;    // 태그 입력 필드

    @FXML
    private Button cancelButton;    // 취소 버튼

    @FXML
    private Button addButton;       // 추가 버튼

    private Stage dialogStage;      // 입력 폼이 띄워지는 Stage 저장. 종료를 위해 필요
    private Task createdTask;       // 생성된 Task 객체를 저장할 필드

    // 외부에서 이 컨트롤러의 Stage를 주입해주기 위한 메서드
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    // TaskInputForm을 닫은 후 직접 작성한 Task 객체를 가져오기 위한 getter
    public Task getCreatedTask() {
        return createdTask;
    }

    @FXML
    private void initialize() {
        priorityChoiceBox.getItems().addAll("낮음", "보통", "높음");       // 우선순위 선택 메뉴에 항목을 추가
        priorityChoiceBox.setValue("보통"); // 우선순위 기본값 MEDIUM
        dueDatePicker.setValue(LocalDate.now()); // 마감일 기본값 오늘 날짜로 설정
    }

    // 추가 버튼 클릭 시 실행되는 메서드
    // 각 입력 필드로부터 값들을 읽어오고, 비어있을 경우 경고 표시 및 종료
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

        // 콤마로 구분된 태그 문자열을 리스트로 분리
        List<String> tags = Arrays.asList(tagsText.split(",\\s*"));     // \\s*로 스페이스 허용
        createdTask = new Task(title, dueDate, priority, tags);               // 입력받은 값으로 새 객체 생성

        dialogStage.close();    // 입력 폼 창 close
    }

    // 취소 버튼 클릭 시 아무것도 하지 않고 창 close
    @FXML
    private void onCancel() {
        createdTask = null;
        dialogStage.close();
    }

    // 입력 창이 비었을 때 보여줄 경고창 메서드
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);       // JavaFX에서 제공하는 Alert 클래스 인스턴스 생성, 경고 유형의 다이얼로그 창이 생성
        alert.setTitle("입력 오류");                              // 알림 창 상단 제목 텍스트 설정
        alert.setHeaderText(null);                              // 경고창 헤더 텍스트 영역 제거
        alert.setContentText(message);                          // 경고창 본문 메시지 설정
        alert.showAndWait();                                    // 경고창을 모달 방식으로 보여주고, 확인 버튼을 누를 때까지 실행을 일시 중지
    }
}
