package com.example.team.service;

import com.example.team.dao.UserTodoDAO;
import com.example.team.dao.UserTodoSetDAO;
import com.example.team.pojo.User;
import com.example.team.pojo.UserTodo;
import com.example.team.pojo.UserTodoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userTodoSetService")
@Transactional(rollbackFor = Exception.class)
public class UserTodoSetServiceImpl implements UserTodoSetService {

    @Autowired
    private UserTodoSetDAO userTodoSetDAO;
    @Autowired
    private UserTodoDAO userTodoDAO;

    @Override
    public boolean createUserTodoSet(UserTodoSet userTodoSet) {
        UserTodoSet userTodoSet1 = userTodoSetDAO.getByName(userTodoSet.getName(),userTodoSet.getUserId());
        if (userTodoSet1 == null) {
            userTodoSetDAO.add(userTodoSet);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserTodoSet(UserTodoSet userTodoSet) {
        UserTodoSet userTodoSet1 = userTodoSetDAO.getByName(userTodoSet.getName(),userTodoSet.getUserId());
        if (userTodoSet1 == null || userTodoSet1.getUserTodoSetId() == userTodoSet.getUserTodoSetId()) {
            userTodoSetDAO.update(userTodoSet);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUserTodoSet(int userTodoSetId) {
        int userId = userTodoSetDAO.get(UserTodoSet.class, userTodoSetId).getUserId();
        List<UserTodo> list = userTodoDAO.listByUser(userId, userTodoSetId);
        for (int i = 0; i < list.size(); i++) {
            UserTodo userTodo = list.get(i);
            userTodoDAO.delete(userTodo.getUserTodoId());
        }
        userTodoSetDAO.delete(userTodoSetId);
    }

    @Override
    public UserTodoSet getById(int userTodoSetId) {
        return userTodoSetDAO.get(UserTodoSet.class, userTodoSetId);
    }

    @Override
    public UserTodoSet getByName(String name,int userId) {
        return userTodoSetDAO.getByName(name,userId);
    }

    @Override
    public List<UserTodoSet> listByUserId(int userId) {
        return userTodoSetDAO.listById(userId);
    }
}
