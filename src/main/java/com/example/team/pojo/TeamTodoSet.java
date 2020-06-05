package com.example.team.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "teamtodoset")
public class TeamTodoSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_todo_set_id")
    private int teamTodoSetId;
    private String name;
    @Column(name = "create_date")
    @JsonIgnore
    private Date create;
    @Column(name = "team")
    private int teamId;

    public int getTeamTodoSetId() {
        return teamTodoSetId;
    }

    public void setTeamTodoSetId(int teamTodoSetId) {
        this.teamTodoSetId = teamTodoSetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

}
