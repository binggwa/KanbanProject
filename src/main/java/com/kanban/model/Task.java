package com.kanban.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.UUID;

public class Task {

    private StringProperty id;
    private StringProperty title;
    private StringProperty description;
    private ObjectProperty<LocalDate> dueDate;
    private StringProperty priority;
    private StringProperty tag;

    // 기본 생성자 (JSON 직렬화용)
    public Task() {
        this("","", LocalDate.now(), "보통","");
    }

    public Task(String title, String description, LocalDate dueDate, String priority, String tag) {
        this.id = new SimpleStringProperty(UUID.randomUUID().toString());
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.priority = new SimpleStringProperty(priority);
        this.tag = new SimpleStringProperty(tag);
    }

    // 게터와 세터
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

    @Override
    public String toString() {
        return "Task(" +
                "title = " + getTitle() +
                ", dueDate = " + getDueDate() +
                ", priority = " + getPriority() +
                ", tag = " + getTag() +
                '}';
    }

}
