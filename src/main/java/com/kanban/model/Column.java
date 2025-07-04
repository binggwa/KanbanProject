package com.kanban.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.UUID;

public class Column {

    private StringProperty id;
    private StringProperty name;
    private ObservableList<Task> tasks;

    // 기본 생성자 (Jackson 직렬화용)
    public Column() {
        this(UUID.randomUUID().toString(), "New Column");
    }

    public Column(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Column(String id, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.tasks = FXCollections.observableArrayList();
    }

    // 게터/세터
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
}