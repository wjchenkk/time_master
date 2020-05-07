package com.example.team.controller;

import com.example.team.pojo.UserTodo;
import com.example.team.pojo.UserTodoSet;
import com.example.team.service.UserTodoService;
import com.example.team.service.UserTodoSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userTodo")
public class UserTodoController {

    @Autowired
    private UserTodoService userTodoService;

    @RequestMapping(value = "/listById",method = RequestMethod.POST)
    @ResponseBody
    public List<UserTodo> listById(@RequestBody Map<String,Object> param) {
        Integer userId=Integer.valueOf(param.get("userId").toString());
        Integer userTodoSetId=Integer.valueOf(param.get("userTodoSetId").toString());
        return userTodoService.listUserTodo(userTodoSetId,userId);
    }

    @RequestMapping(value = "/listByUserId",method = RequestMethod.POST)
    @ResponseBody
    public List<UserTodo> listByUserId(@RequestBody Map<String,Object> param) {
        Integer userId=Integer.valueOf(param.get("userId").toString());
        return userTodoService.listUserTodo(userId);
    }

    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ResponseBody
    public UserTodo get(@RequestBody Map<String,Object> param){
        Integer userTodoId = Integer.valueOf(param.get("userTodoId").toString());
        return userTodoService.getById(userTodoId);
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody Map<String,Object> param) {
        UserTodo userTodo = new UserTodo();
        userTodo.setName(param.get("name").toString());
        userTodo.setUserTodoSetId(Integer.valueOf(param.get("userTodoSetId").toString()));
        userTodo.setUserId(Integer.valueOf(param.get("userId").toString()));
        userTodo.setTime(Long.valueOf(param.get("time").toString()));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date create = new Date();
        userTodo.setCreate(java.sql.Date.valueOf(df.format(create)));
        userTodo.setTodoStatusId(1);
        userTodo.setTypeId(2);
        if (userTodoService.createUserTodo(userTodo)){
            return "create-success";
        }
        return "create-fail";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestBody Map<String,Object> param){
        Integer userTodoId = Integer.valueOf(param.get("userTodoId").toString());
        userTodoService.deleteUserTodo(userTodoId);
        return "delete-success";
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestBody Map<String,Object> param) {
        UserTodo userTodo = new UserTodo();
        userTodo.setUserTodoId(Integer.valueOf(param.get("userTodoId").toString()));
        userTodo.setName(param.get("name").toString());
        userTodo.setUserTodoSetId(Integer.valueOf(param.get("userTodoSetId").toString()));
        userTodo.setUserId(Integer.valueOf(param.get("userId").toString()));
        userTodo.setTime(Long.valueOf(param.get("time").toString()));
        userTodo.setCreate(java.sql.Date.valueOf(param.get("create").toString()));
        userTodo.setTodoStatusId(Integer.valueOf(param.get("todoStatusId").toString()));
        userTodo.setTypeId(Integer.valueOf(param.get("typeId").toString()));
        if (userTodoService.updateUserTodo(userTodo)){
            return "update-success";
        }
        return "update-fail";
    }
}
