package com.kanban.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Column {

    private StringProperty id;
    private StringProperty name;
    private ObservableList<Task> tasks;

    /**
     * 기본 생성자 (Jackson 직렬화용)
     */
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

    /**
     * JSON 직렬화를 위해 List 형태로 반환
     */
    public List<Task> getTaskList() {
        return new ArrayList<>(tasks);
    }

    /**
     * JSON 역직렬화를 위해 List를 ObservableList로 변환
     */
    public void setTaskList(List<Task> taskList) {
        this.tasks = FXCollections.observableArrayList(taskList);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public String toString() {
        return "Column{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
