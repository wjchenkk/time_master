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
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    //@RequestMapping(value = "/getRecords", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @RequestMapping("/getRecords")
    @ResponseBody
    /**
     * @description: 获取团队使用情况记录
     * @Param: [param, userId]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:27
     */
    //public void getRecords(@RequestBody Map<String, Object> param,@RequestHeader("id") int userId) throws IOException
    public void getRecords() throws IOException {
        int todoNum = 0;
        int todoSetNum = 0;
        String records = "";
        /*int option = Integer.parseInt(param.get("option").toString());
        int teamId = Integer.parseInt(param.get("teamId").toString());
        String path = param.get("path").toString();*/
        int option=1;
        int teamId=9;
        String path="D:";
        Set<User> setUser = userService.getMembers(teamId);
        List<TeamTodoSet> teamTodoSetList = teamTodoSetService.listByTeamId(teamId);
        List<DataVo> dataVOList = new ArrayList<>();
        Workbook workbook = null;
        if (option == 1){
            for (User user : setUser){
                todoNum = 0;
                todoSetNum = 0;
                List<TeamTodo> teamTodoList = teamTodoService.listByUser(teamId,user.getUserId());
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
                    List<TeamTodo> todoList = teamTodoService.listTeamTodo(set.getTeamTodoSetId(),teamId,user.getUserId());
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
            //Workbook workbook = ExcelWr.exportData(dataVOList);
            workbook = ExcelWr.exportData(dataVOList);
            // 以文件的形式输出工作簿对象
            FileOutputStream fileOut = null;
            try {
                //文件不用创建，会自动生成的
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date create = new java.util.Date();
                String exportFilePath = path + "\\output-" + df.format(create) + ".xlsx";
                File exportFile = new File(exportFilePath);
                if (!exportFile.exists()) {
                    exportFile.createNewFile();
                }
                /*fileOut = new FileOutputStream(exportFilePath);
                workbook.write(fileOut);
                fileOut.flush();*/
                //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-disposition", "attachment;filename=" + "output-" + df.format(DateUtil.getCurrentTime()) +
                        ".xlsx");
                response.flushBuffer();
                OutputStream outputStream = response.getOutputStream();
                workbook.write(response.getOutputStream());
                workbook.close();
                outputStream.flush();
                outputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fileOut) {
                        fileOut.close();
                    }
                    if (null != workbook) {
                        workbook.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (option == 2){
            for (User user : setUser){
                DataVo dataV = new DataVo();
                dataV.setUserName(user.getName());
                dataVOList.add(dataV);
                for (TeamTodoSet set : teamTodoSetList){
                    DataVo dataVO = new DataVo();
                    int i = 0;
                    List<TeamTodo> todoList = teamTodoService.listTeamTodo(set.getTeamTodoSetId(),teamId,user.getUserId());
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
            //Workbook workbook = ExcelWriter.exportData(dataVOList);
            workbook = ExcelWriter.exportData(dataVOList);
            // 以文件的形式输出工作簿对象
            FileOutputStream fileOut = null;
            try {
                //文件不用创建，会自动生成的
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date create = new java.util.Date();
                String exportFilePath = path + "\\output-" + df.format(create) + ".xlsx";
                File exportFile = new File(exportFilePath);
                if (!exportFile.exists()) {
                    exportFile.createNewFile();
                }
                fileOut = new FileOutputStream(exportFilePath);
                workbook.write(fileOut);
                fileOut.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fileOut) {
                        fileOut.close();
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

        //return records;
    }
}
