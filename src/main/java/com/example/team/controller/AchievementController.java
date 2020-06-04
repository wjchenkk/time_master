package com.example.team.controller;


import com.example.team.pojo.AccRecord;
import com.example.team.pojo.Achievement;
import com.example.team.pojo.AchievementType;
import com.example.team.pojo.DailyRecord;
import com.example.team.service.AchievementService;
import com.example.team.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/achievement")
public class AchievementController extends BaseController{
    @Autowired
    private AchievementService achievementService;
    @Autowired
    private RecordService recordService;


    /**
     * getAchievement 获取用户成就并判断成就是否达成
     *
     * @param userId 用户id
     * @return List<Achievement> 用户的成就列表
     */
    @RequestMapping(value = "/getAchievement", method = RequestMethod.POST)
    @ResponseBody
    List<Achievement> getAchievement(@RequestParam int userId){
       return achievementService.getByUserId(userId);
    }

}
