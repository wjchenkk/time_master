package com.example.team.service;

import com.example.team.pojo.Pet;
import com.example.team.pojo.Team;
import com.example.team.pojo.User;

import java.util.Set;

public interface UserService {
    public boolean verify(String userName, String password);

    public void quit(int userId);

    public boolean sign(User user, Pet pet);

    public int getUserId(String tel, String email);

    public int getId();

    boolean updateEmail(int userId, String email);

    boolean updateTel(int userId, String tel);

    boolean updateUserName(int userId, String userName);

    public boolean updateUserPassword(int userId, String password);

    public User getById(int userId);

    Set<User> getMembers(int teamId);

    Set<Team> getTeams(int userId);

    Team joinTeam(int teamId, int userId);

    boolean quitTeam(int teamId, int userId);
}
