package com.example.team.controller;

import com.example.team.pojo.*;
import com.example.team.service.TeamService;
import com.example.team.service.TeamTodoService;
import com.example.team.service.TeamTodoSetService;
import com.example.team.service.UserService;
import com.example.team.util.DataVo;
import com.example.team.util.DateUtil;

import com.example.team.util.ExcelWr;

import com.example.team.util.ExcelWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/team")
public class TeamController extends BaseController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamTodoService teamTodoService;
    @Autowired
    private TeamTodoSetService teamTodoSetService;
    
    @RequestMapping(value = "/createTeam", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 创建团队
     * @Param: [param]
     * @return: com.example.team.pojo.Team
     * @update: time: 2020/6/3 9:27
     */
    public Team createTeam(@RequestBody Map<String, Object> param) {
        String name = param.get("name").toString();
        int userId = Integer.parseInt(request.getHeader("id"));
        Team team = new Team();
        User user = userService.getById(userId);
        team.setName(name);
        team.setCreateDate(DateUtil.getCurrentTime());
        team.setLeader(user);
        return teamService.createTeam(user, team);
    }
    
    @RequestMapping(value = "/deleteTeam", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 删除团队
     * @Param: [param]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:27
     */
    public String deleteTeam(@RequestBody Map<String, Object> param) {
        int teamId = Integer.parseInt(param.get("teamId").toString());
        teamService.deleteTeam(teamId);
        return "delete-success";
    }
    
    @RequestMapping(value = "/joinTeam", method = RequestMethod.POST)
    @ResponseBody
   /**
    * @description: 加入团队
    * @Param: [param]
    * @return: com.example.team.pojo.Team
    * @update: time: 2020/6/3 9:27
    */
    public Team joinTeam(@RequestBody Map<String, Object> param) {
        int teamId = Integer.parseInt(param.get("teamId").toString());
        int userId = Integer.parseInt(request.getHeader("id"));
        return userService.joinTeam(teamId, userId);
    }
    
    @RequestMapping(value = "/quitTeam", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 退出团队
     * @Param: [param]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:27
     */
    public String quitTeam(@RequestBody Map<String, Object> param) {
        int teamId = Integer.parseInt(param.get("teamId").toString());
        int userId = Integer.parseInt(request.getHeader("id"));
        if (userService.quitTeam(teamId, userId)) {
            return "quit-success";
        }
        return "quit-fail";
    }
    
    @RequestMapping(value = "/inviteMember", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 邀请队员
     * @Param: [param]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:27
     */
    public String inviteMember(@RequestBody Map<String, Object> param) {
        int teamId = Integer.parseInt(param.get("teamId").toString());
        String email = param.get("email").toString();
        int userId = userService.getUserId("", email);
        Team team=userService.joinTeam(teamId, userId);
        if (team != null) {
            if(team.getTeamId()==-1){
                return "invited";
            }
            return "invite-success";
        }
        return "invite-fail";
    }
    
    @RequestMapping(value = "/outMember", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 踢出队员
     * @Param: [param]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:27
     */
    public String outMember(@RequestBody Map<String, Object> param) {
        int teamId = Integer.parseInt(param.get("teamId").toString());
        String email = param.get("email").toString();
        int userId = userService.getUserId("", email);
        if (userService.quitTeam(teamId, userId)) {
            return "out-success";
        }
        return "out-fail";
    }
    
    @RequestMapping(value = "/getMembers", method = RequestMethod.GET)
    @ResponseBody
    /**
     * @description: 获取用户集合
     * @Param: [teamId]
     * @return: java.util.Set<com.example.team.pojo.User>
     * @update: time: 2020/6/3 9:27
     */
    public Set<User> getMembers(@RequestParam String teamId) {
        int teamId1 = Integer.parseInt(teamId);
        return userService.getMembers(teamId1);
    }
    
    @RequestMapping(value = "/getTeams", method = RequestMethod.GET)
    @ResponseBody
    /**
     * @description: 获取团队集合
     * @Param: []
     * @return: java.util.Set<com.example.team.pojo.Team>
     * @update: time: 2020/6/3 9:27
     */
    public Set<Team> getTeams() {
        int userId = Integer.parseInt(request.getHeader("id"));
        return userService.getTeams(userId);
    }
    
    @RequestMapping(value = "/updateTeam", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 更新团队名
     * @Param: [param]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:27
     */
    public String updateTeam(@RequestBody Map<String, Object> param) {
        String name = param.get("name").toString();
        int teamId = Integer.parseInt(param.get("teamId").toString());
        Team team = new Team();
        team.setName(name);
        team.setTeamId(teamId);
        teamService.updateTeam(team);
        return "update-success";
    }

    @RequestMapping(value = "/getRecords", method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    /**
     * @description: 获取团队使用情况记录
     * @Param: [param, userId]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:27
     */
    public String getRecords( @RequestParam String option, @RequestParam String teamId,HttpServletResponse response){
        int todoNum = 0;
        int todoSetNum = 0;
        String records = "";
        int option1 = Integer.parseInt(option);
        int teamId1 = Integer.parseInt(teamId);
        Set<User> setUser = userService.getMembers(teamId1);
        List<TeamTodoSet> teamTodoSetList = teamTodoSetService.listByTeamId(teamId1);
        List<DataVo> dataVOList = new ArrayList<>();
        if (option1 == 1){
            for (User user : setUser){
                todoNum = 0;
                todoSetNum = 0;
                List<TeamTodo> teamTodoList = teamTodoService.listByUser(teamId1,user.getUserId());
                DataVo dataV = new DataVo();
                dataV.setUserName(user.getName());
                DataVo dataVO = new DataVo();
                for (TeamTodo list : teamTodoList){
                    if (list.getTodoStatusId()==2){
                        todoNum++;
                    }
                }
                dataVO.setName("Todo");
                dataVO.setCompletion(todoNum + "/" + teamTodoList.size());
                DataVo dataVO2 = new DataVo();
                for (TeamTodoSet set : teamTodoSetList){
                    int i = 0;
                    List<TeamTodo> todoList = teamTodoService.listTeamTodo(set.getTeamTodoSetId(),teamId1,user.getUserId());
                    for (TeamTodo list : todoList){
                        if (list.getTodoStatusId()==2){
                            i++;
                        }
                    }
                    if (i == todoList.size()){
                        todoSetNum++;
                    }
                }
                dataVO2.setSName("TodoSet");
                dataVO2.setCompletion(todoSetNum + "/" + teamTodoSetList.size());
                dataVOList.add(dataV);
                dataVOList.add(dataVO2);
                dataVOList.add(dataVO);
            }
            records = "操作1";
            // 写入数据到工作簿对象内
            HSSFWorkbook workbook = ExcelWr.exportData(dataVOList);
            // 以文件的形式输出工作簿对象
            OutputStream fos = null;
            try {
                fos = response.getOutputStream();
                String userAgent = request.getHeader("USER-AGENT");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date create = new java.util.Date();
                String fileName = "output-" + df.format(create);
                try{
                    if(StringUtils.contains(userAgent, "Mozilla")){
                        fileName = new String(fileName.getBytes(), "ISO8859-1");
                    }
                    else {
                        fileName = URLEncoder.encode(fileName, "utf8");
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                response.setCharacterEncoding("UTF-8");
                // 设置contentType为excel格式
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
                workbook.write(fos);
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fos) {
                        fos.close();
                    }
                    if (null != workbook) {
                        workbook.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (option1 == 2){
            for (User user : setUser){
                DataVo dataV = new DataVo();
                dataV.setUserName(user.getName());
                dataVOList.add(dataV);
                for (TeamTodoSet set : teamTodoSetList){
                    DataVo dataVO = new DataVo();
                    int i = 0;
                    List<TeamTodo> todoList = teamTodoService.listTeamTodo(set.getTeamTodoSetId(),teamId1,user.getUserId());
                    for (TeamTodo list : todoList){
                        if (list.getTodoStatusId()==2){
                            i++;
                        }
                    }
                    if (i == todoList.size()){
                        dataVO.setSName(set.getName());
                        dataVO.setSetRecord("完成");
                    }
                    else {
                        dataVO.setSName(set.getName());
                        dataVO.setSetRecord("未完成");
                    }
                    dataVOList.add(dataVO);
                    for (TeamTodo list : todoList){
                        DataVo dataVO1 = new DataVo();
                        if (list.getTodoStatusId()==2){
                            dataVO1.setName(list.getName());
                            dataVO1.setRecord("完成");
                        }
                        else {
                            dataVO1.setName(list.getName());
                            dataVO1.setRecord("未完成");
                        }
                        dataVOList.add(dataVO1);
                    }
                }
            }
            records = "操作2";
            // 写入数据到工作簿对象内
            HSSFWorkbook workbook = ExcelWriter.exportData(dataVOList);
            // 以文件的形式输出工作簿对象
            OutputStream fos = null;
            try {
                fos = response.getOutputStream();
                String userAgent = request.getHeader("USER-AGENT");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date create = new java.util.Date();
                String fileName = "output-" + df.format(create);
                try{
                    if(StringUtils.contains(userAgent, "Mozilla")){
                        fileName = new String(fileName.getBytes(), "ISO8859-1");
                    }
                    else {
                        fileName = URLEncoder.encode(fileName, "utf8");
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                response.setCharacterEncoding("UTF-8");
                // 设置contentType为excel格式
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
                workbook.write(fos);
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fos) {
                        fos.close();
                    }
                    if (null != workbook) {
                        workbook.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            records = "option输入错误";
        }
        return records;
    }
}
