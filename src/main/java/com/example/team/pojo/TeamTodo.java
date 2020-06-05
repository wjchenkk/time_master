package com.example.team.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "teamtodo")
public class TeamTodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_todo_id")
    private int teamTodoId;
    private String name;
    private long time;
    @Column(name = "create_date")
    @JsonIgnore
    private Date create;
    @JsonIgnore
    @Column(name = "status")
    private int todoStatusId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", insertable = false, updatable = false)
    private TodoStatus todoStatus;
    @Column(name = "todoset")
    private int teamTodoSetId;

    @Column(name = "team")
    private int teamId;

    @Column(name = "user")
    @JsonIgnore
    private int userId;


    public int getTeamTodoId() {
        return teamTodoId;
    }

    public void setTeamTodoId(int teamTodoId) {
        this.teamTodoId = teamTodoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public int getTodoStatusId() {
        return todoStatusId;
    }

    public void setTodoStatusId(int todoStatusId) {
        this.todoStatusId = todoStatusId;
    }

    public TodoStatus getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(TodoStatus todoStatus) {
        this.todoStatus = todoStatus;
    }

    public int getTeamTodoSetId() {
        return teamTodoSetId;
    }

    public void setTeamTodoSetId(int teamTodoSetId) {
        this.teamTodoSetId = teamTodoSetId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
