package com.example.team.service;

import com.example.team.pojo.Achievement;
import com.example.team.pojo.AchievementType;
import com.example.team.pojo.Pet;

import java.util.List;

public interface AchievementService{
    void addAchievement(int userId);
    void updateAchievement(int userId);
    List<Achievement> getByUserId(int userId);
    List<Achievement> getUnAchievement(int userId);
}
