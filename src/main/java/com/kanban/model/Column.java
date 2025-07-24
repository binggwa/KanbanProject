package com.kanban.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Column {

    private StringProperty name;
    private ObservableList<Task> tasks;

    // Jackson 직렬화를 위한 기본 생성자
    public Column() {
        this("New Column", FXCollections.observableArrayList());
    }

    // 이름만 지정하는 생성자
    public Column(String name) {
        this(name, FXCollections.observableArrayList());
    }

    // 내부적으로 사용하는 모든 필드 지정 생성자
    public Column(String name, ObservableList<Task> tasks) {
        this.name = new SimpleStringProperty(name);
        this.tasks = tasks;
    }

    // Jackson 역직렬화를 위한 생성자
    @JsonCreator
    public Column(
            @JsonProperty("name") String name,
            @JsonProperty("tasks") List<Task> tasks
    ) {
        this.name = new SimpleStringProperty(name);
        this.tasks = FXCollections.observableArrayList(tasks);
    }

    // 게터/세터
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
