package com.example.team.controller;


import com.example.team.pojo.*;
import com.example.team.service.AchievementService;
import com.example.team.service.PetService;
import com.example.team.service.RecordService;
import com.example.team.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Map;
import java.sql.Date;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private PetService petService;
    @Autowired
    private AchievementService achievementService;



    @RequestMapping("/getDailyRecord")
    @ResponseBody
    /**
     * @description:
     * @Param: []
     * @return: com.example.team.pojo.DailyRecord
     * @update: time: 2020/6/3 8:52
     */
    DailyRecord getDailyRecord(){
        int userId = Integer.parseInt(request.getHeader("id"));
        return recordService.getDailyRecord(userId, DateUtil.getCurrentTime());
    }

    @RequestMapping("/getAccRecord")
    @ResponseBody
    /**
     * @description: 获取累计记录
     * @Param: []
     * @return: com.example.team.pojo.AccRecord
     * @update: time: 2020/6/3 8:52
     */
    AccRecord getAccRecord() {
        int userId = Integer.parseInt(request.getHeader("id"));
        return recordService.getAccRecord(userId);
    }

    @RequestMapping("/getMonthRecord")
    @ResponseBody
    /**
     * @description: 获取月记录
     * @Param: []
     * @return: com.example.team.pojo.MonthRecord
     * @update: time: 2020/6/3 8:52
     */
    MonthRecord getMonthRecord() {
        int userId = Integer.parseInt(request.getHeader("id"));
        return recordService.getMonthRecord(userId,DateUtil.getMonthDate());
    }


    @RequestMapping("/getDailyRecordByMonth")
    @ResponseBody
    /**
     * @description: 获取月的日记录集合
     * @Param: [date_1, date_2]
     * @return: java.util.Map<java.lang.Integer,java.lang.Long>
     * @update: time: 2020/6/3 8:51
     */
    Map<Integer, Long> getDailyRecordByMonth(@RequestParam String date_1, @RequestParam String date_2) throws ParseException {
        int userId = Integer.parseInt(request.getHeader("id"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date litleMonthDate = Date.valueOf(date_1);
        Date bigMonthDate = Date.valueOf(date_2);
        return recordService.listDailyRecordByMonth(userId, litleMonthDate, bigMonthDate);
    }


    @RequestMapping(value = "/setRecord",method = RequestMethod.POST)
    /**
     * @description: 设置记录
     * @Param: [param]
     * @return: java.lang.String
     * @update: time: 2020/6/3 8:52
     */
    public String setRecord(@RequestBody Map<String, Object> param) {
        String userId = request.getHeader("id"); //获取用户id
        String todoId = param.get("todoId").toString();//获取待办id
        String todoStatusId = param.get("statusId").toString();//获取待办状态
        String todoTime = param.get("time").toString();//获取待办时间
        String flag=param.get("flag").toString();
        
        long tTime = Integer.parseInt(todoTime);
        int tsId = Integer.parseInt(todoStatusId);
        int uId = Integer.parseInt(userId);

        //日记录
        if (recordService.isExistDailyRecord(uId,DateUtil.getCurrentTime())) {
            recordService.updateDailyRecord(uId, tTime, tsId);
        } else {
            recordService.addDailyRecord(uId, tTime, tsId);
        }

        //月记录
        if(recordService.isExistMonthRecord(uId,DateUtil.getMonthDate())){
            recordService.updateMonthRecord(uId, tTime, tsId);
        } else {
            recordService.addMonthRecord(uId, tTime, tsId);
        }


        //总记录
        if (recordService.isExistAccRecord(uId)){
            recordService.updateAccRecord(uId, tTime, tsId);
        } else {
            recordService.addAccRecord(uId, tTime, tsId);
        }

        achievementService.updateAchievement(uId);
        //宠物等级
        petService.updateLevel(uId);
        //跳转修改待办状态
        if(flag.equals("0")){
            return "forward:/userTodo/updateState?userTodoId=" + todoId + "&todoStatusId=" + tsId + "";
        }
        else{
            return "forward:/teamTodo/updateState?teamTodoId=" + todoId + "&todoStatusId=" + tsId + "";
        }
    }







}
