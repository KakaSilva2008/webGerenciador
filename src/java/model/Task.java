package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Task {

    private int id;
    private String title;
    private String description;
    //private Date dueDate;
    //private Date createdAt;
    private LocalDate dueDate;
    private LocalDateTime createdAt;

    public Task() {
    }

    public Task(int id, String title, String description, LocalDate dueDate, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
