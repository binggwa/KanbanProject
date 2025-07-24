package com.kanban.model;

import java.time.LocalDate;

// Task 객체를 JSON으로 저장하거나 불러오기 위한 Data Transfer Object (DTO)
public class TaskDTO {

    public String id;
    public String title;
    public String description;
    public LocalDate dueDate;
    public String priority;
    public String tag;

    /**
     * Jackson에서 역직렬화 시 반드시 기본 생성자가 필요
     * JSON → TaskDTO 변환 시 내부적으로 new TaskDTO() 후 setter 또는 필드 주입을 사용
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
