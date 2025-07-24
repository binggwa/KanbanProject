package com.kanban.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Task {

    private StringProperty id;
    private StringProperty title;
    private StringProperty description;
    private ObjectProperty<LocalDate> dueDate;
    private StringProperty priority;
    private StringProperty tag;

    public Task() {
        this("", "", LocalDate.now(), "MEDIUM", "");
    }

    public Task(String title, String description, LocalDate dueDate, String priority, String tag) {
        this.id = new SimpleStringProperty(UUID.randomUUID().toString());
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.priority = new SimpleStringProperty(priority);
        this.tag = new SimpleStringProperty(tag);
    }

    public Task(String title, String description, LocalDate dueDate, String priority, List<String> tags) {
        this(title, description, dueDate, priority, String.join(",", tags));
    }

    public Task(String title, LocalDate dueDate, String priority, List<String> tags) {
        this(title, "", dueDate, priority, String.join(",", tags));
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public String getPriority() {
        return priority.get();
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public String getTag() {
        return tag.get();
    }

    public void setTag(String tag) {
        this.tag.set(tag);
    }

    public StringProperty tagProperty() {
        return tag;
    }

    public Priority getPriorityEnum() {
        return Priority.fromString(getPriority());
    }

    public void setPriorityEnum(Priority priorityEnum) {
        setPriority(priorityEnum.name());
    }

    public TaskDTO toDTO() {
        return new TaskDTO(this);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + getId() +
                ", title=" + getTitle() +
                ", dueDate=" + getDueDate() +
                ", priority=" + getPriority() +
                ", tag=" + getTag() +
                '}';
    }

    // 열거형 Priority 정의: 작업의 우선순위를 Enum으로 선언함
    public enum Priority {
        // 각 Enum 값에 표시용 한글 이름(String) 표기
        LOW("낮음"), MEDIUM("보통"), HIGH("높음");

        // Enum 상수마다 한글로 표시될 이름을 저장하는 필드. 불변(final)이며 생성자에서 초기화
        private final String displayName;

        Priority(String displayName) {
            this.displayName = displayName;
        }

        // 문자열 입력을 받아 Enum 값으로 변환하는 정적 유틸리티 메서드. 사용자 입력이나 JSON 파싱에 사용
        public static Priority fromString(String str) {
            if (str == null) return MEDIUM;
            return switch (str.toUpperCase()) {
                case "LOW", "낮음" -> LOW;
                case "HIGH", "높음" -> HIGH;
                case "MEDIUM", "보통" -> MEDIUM;
                default -> MEDIUM;
            };
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return name(); // JSON 직렬화 시에는 영문을 사용
        }
    }
}
