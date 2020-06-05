package com.example.team.dao;

import com.example.team.pojo.TeamTodoSet;

import java.util.List;

public interface TeamTodoSetDAO extends BaseDAO<TeamTodoSet>{

    TeamTodoSet getByName(String name,int teamId);

    List<TeamTodoSet> list(int teamId);

    void deleteList(int teamId);
}
