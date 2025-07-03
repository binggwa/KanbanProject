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
    }
}
