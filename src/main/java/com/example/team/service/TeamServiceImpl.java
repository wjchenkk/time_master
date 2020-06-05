package com.example.team.service;

import com.example.team.dao.TeamDAO;
import com.example.team.dao.TeamTodoDAO;
import com.example.team.dao.TeamTodoSetDAO;
import com.example.team.dao.UserDAO;
import com.example.team.pojo.Team;
import com.example.team.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("teamService")
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamDAO teamDAO;
    @Autowired
    private TeamTodoDAO teamTodoDAO;
    @Autowired
    private TeamTodoSetDAO teamTodoSetDAO;

    @Override
    /**
     * @description: 
     * @Param: [user, team] 
     * @return: com.example.team.pojo.Team 
     * @update: time: 2020/6/3 9:36 
     */
    public Team createTeam(User user, Team team) {
        teamDAO.add(team);
        team.getUsers().add(user);
        teamDAO.update(team);
        return team;
    }

    @Override
    /**
     * @description: 
     * @Param: [team] 
     * @return: void 
     * @update: time: 2020/6/3 9:37 
     */
    public void updateTeam(Team team) {
        Team team1 = teamDAO.get(Team.class,team.getTeamId());
        team1.setName(team.getName());
        teamDAO.update(team1);
    }

    @Override
    /**
     * @description: 
     * @Param: [teamId] 
     * @return: void 
     * @update: time: 2020/6/3 10:22
     */
    public void deleteTeam(int teamId) {
        teamDAO.delete(teamId);
        teamTodoSetDAO.deleteList(teamId);
        teamTodoDAO.deleteList(teamId);
    }

    @Override
    public Team get(int teamId) {
        return teamDAO.get(Team.class,teamId);
    }

}
