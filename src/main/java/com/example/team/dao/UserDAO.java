package com.example.team.dao;


import com.example.team.pojo.User;


public interface UserDAO extends BaseDAO<User>{
    User getByTel(String tel);

    User getByEmail(String email);
}
