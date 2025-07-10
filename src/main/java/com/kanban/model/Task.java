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

    public enum Priority {
        LOW("낮음"), MEDIUM("보통"), HIGH("높음");

        private final String displayName;

        Priority(String displayName) {
            this.displayName = displayName;
        }

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
