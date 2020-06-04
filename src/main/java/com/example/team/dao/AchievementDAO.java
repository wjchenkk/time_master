package com.example.team.dao;

import com.example.team.pojo.AccRecord;
import com.example.team.pojo.Achievement;

import java.util.List;


public interface AchievementDAO extends BaseDAO<Achievement>{
    void update(int userId,int achievementTypeId);
    List<Achievement> getByUserId(int userId);
    List<Achievement> getUnAchievement(int userId);
}
