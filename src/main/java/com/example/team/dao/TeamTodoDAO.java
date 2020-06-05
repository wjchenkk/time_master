package com.example.team.dao;

import com.example.team.pojo.TeamTodo;

import java.util.List;

public interface TeamTodoDAO extends BaseDAO<TeamTodo>{
    void saveList(List<TeamTodo> list);
    void deleteByUser(String name,int userId,int teamId);
    void deleteList(int userId,int teamId);
    void deleteBySet(int teamId,int todoSetId);
    void deleteList(int teamId);
    void updateState(int teamTodoId,int todoStatusId,int userId);
    TeamTodo getByUser(String name,int userId);
    List<TeamTodo> listByUser(int teamId,int userId);
    List<TeamTodo> list(int teamId,int teamTodoSetId);
    List<TeamTodo> list(int teamId,int teamTodoSetId,int userId);
    void updateSchedule();
}
