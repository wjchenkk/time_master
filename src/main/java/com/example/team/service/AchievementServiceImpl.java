package com.example.team.service;

import com.example.team.dao.*;
import com.example.team.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("achievementService")
@Transactional(rollbackFor = Exception.class)
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    private AchievementDAO achievementDAO;
    @Autowired
    private AchievementTypeDAO achievementTypeDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private AccRecordDAO accRecordDAO;

    @Override
    public void addAchievement(int userId) {
        List<AchievementType> aList=achievementTypeDAO.getAchievementType();
        for (AchievementType a:aList){
            Achievement achievement = new Achievement();
            achievement.setUser(userDAO.get(User.class,userId));
            achievement.setStatus(0);
            achievement.setAchievementType(a);
            achievementDAO.add(achievement);
        }
    }

    @Override
    public void updateAchievement(int userId){
        AccRecord accRecord =accRecordDAO.getByUserId(userId);
        int id=achievementTypeDAO.getAchievementId((int)accRecord.getAcctime(),userId);
        achievementDAO.update(userId,id);
    }

    @Override
    public List<Achievement> getByUserId(int userId) {
        return achievementDAO.getByUserId(userId);
    }

    @Override
    public List<Achievement> getUnAchievement(int userId){
        return achievementDAO.getUnAchievement(userId);
    }

    @Override
    public List<AchievementType> getAchievement(int userId) {
        List<Achievement> aList=getByUserId(userId);
        List<AchievementType> atList=achievementTypeDAO.getAchievementType();
        for (AchievementType at:atList){
            at.setAstatus(achievementDAO.getAchievement(userId,at.getAId()).getStatus());
        }

        return null;
    }
}
