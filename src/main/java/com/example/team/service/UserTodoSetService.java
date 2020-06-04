package com.example.team.service;

import com.example.team.pojo.UserTodoSet;

import java.util.List;

public interface UserTodoSetService {
    boolean createUserTodoSet(UserTodoSet userTodoSet);

    boolean updateUserTodoSet(UserTodoSet userTodoSet);

    void deleteUserTodoSet(int userTodoSetId);

    UserTodoSet getById(int userTodoSetId);

    UserTodoSet getByName(String name,int userId);

    List<UserTodoSet> listByUserId(int userId);
}
