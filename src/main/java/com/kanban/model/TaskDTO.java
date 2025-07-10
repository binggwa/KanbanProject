package com.kanban.model;

import java.time.LocalDate;

public class TaskDTO {

    public String id;
    public String title;
    public String description;
    public LocalDate dueDate;
    public String priority;
    public String tag;

    /**
     * Jackson 직렬화를 위한 기본 생성자
     */
    public TaskDTO() {}

    /**
     * Task → DTO 변환
     */
    public TaskDTO(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.dueDate = task.getDueDate();
        this.priority = task.getPriority();
        this.tag = task.getTag();
    }

    /**
     * DTO → Task 변환
     */
    public Task toTask() {
        Task task = new Task(title, description, dueDate, priority, tag);
        task.setId(id); // 기존 ID 유지
        return task;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", dueDate=" + dueDate +
                ", priority='" + priority + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
