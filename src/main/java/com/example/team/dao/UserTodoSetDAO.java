package com.example.team.dao;

import com.example.team.pojo.UserTodoSet;

import java.util.List;

public interface UserTodoSetDAO extends BaseDAO<UserTodoSet>{

    UserTodoSet getByName(String name,int userId);

    List<UserTodoSet> listById(int userId);
}
