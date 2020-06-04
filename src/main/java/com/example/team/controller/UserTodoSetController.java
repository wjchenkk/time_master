package com.example.team.controller;

import com.example.team.pojo.UserTodoSet;
import com.example.team.service.UserTodoSetService;
import com.example.team.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userTodoSet")
public class UserTodoSetController extends BaseController {

    @Autowired
    private UserTodoSetService userTodoSetService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    /**
     * @description: 获取待办集集合
     * @Param: [userId]
     * @return java.util.List<com.example.team.pojo.UserTodoSet> 待办集集合
     * @update time: 2020/6/3 9:02
     */
    public List<UserTodoSet> list(@RequestHeader("id") int userId) {
        return userTodoSetService.listByUserId(userId);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 获取待办集
     * @Param: [param]
     * @return com.example.team.pojo.UserTodoSet 待办集
     * @update time: 2020/6/3 9:02
     */
    public UserTodoSet get(@RequestBody Map<String, Object> param) {
        int userTodoSetId = Integer.parseInt(param.get("userTodoSetId").toString());
        return userTodoSetService.getById(userTodoSetId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 创建待办集
     * @Param: [param, userId]
     * @return java.lang.String 创建结果
     * @update time: 2020/6/3 9:02
     */
    public Map<String, Integer> create(@RequestBody Map<String, Object> param, @RequestHeader("id") int userId) {
        UserTodoSet userTodoSet = new UserTodoSet();
        userTodoSet.setName(param.get("name").toString());
        userTodoSet.setUserId(userId);
        userTodoSet.setCreate(DateUtil.getCurrentTime());
        Map<String, Integer> result = new HashMap<>();
        if (userTodoSetService.createUserTodoSet(userTodoSet)) {
            UserTodoSet userTodoSet1 = userTodoSetService.getByName(userTodoSet.getName(),userId);
            result.put("userTodoSetId", userTodoSet1.getUserTodoSetId());
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 更新待办集
     * @Param: [param, userId]
     * @return java.lang.String 更新结果
     * @update time: 2020/6/3 9:02
     */
    public Map<String, Integer> update(@RequestBody Map<String, Object> param, @RequestHeader("id") int userId) {
        UserTodoSet userTodoSet = new UserTodoSet();
        userTodoSet.setUserTodoSetId(Integer.parseInt(param.get("userTodoSetId").toString()));
        userTodoSet.setName(param.get("name").toString());
        userTodoSet.setUserId(userId);
        userTodoSet.setCreate(java.sql.Date.valueOf(param.get("create").toString()));
        Map<String, Integer> result = new HashMap<>();
        if (userTodoSetService.updateUserTodoSet(userTodoSet)) {
            UserTodoSet userTodoSet1 = userTodoSetService.getByName(userTodoSet.getName(),userId);
            result.put("userTodoSetId", userTodoSet1.getUserTodoSetId());
        }
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 删除用户待办集
     * @Param: [param]
     * @return java.lang.String 删除结果
     * @update time: 2020/6/3 9:02
     */
    public String delete(@RequestBody Map<String, Object> param) {
        int userTodoSetId = Integer.parseInt(param.get("userTodoSetId").toString());
        userTodoSetService.deleteUserTodoSet(userTodoSetId);
        return "delete-success";
    }
}
