package com.example.team.service;

import com.example.team.dao.TeamTodoDAO;
import com.example.team.dao.TeamTodoSetDAO;
import com.example.team.pojo.TeamTodo;
import com.example.team.pojo.TeamTodoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("TeamTodoSetService")
@Transactional(rollbackFor = Exception.class)
public class TeamTodoSetServiceImpl implements TeamTodoSetService {
    @Autowired
    private TeamTodoSetDAO teamTodoSetDAO;

    @Autowired
    private TeamTodoDAO teamTodoDAO;

    @Override
    public boolean createTeamTodoSet(TeamTodoSet teamTodoSet) {
        TeamTodoSet teamTodoSet1 = teamTodoSetDAO.getByName(teamTodoSet.getName(),teamTodoSet.getTeamId());
        if (teamTodoSet1 == null) {
            teamTodoSetDAO.add(teamTodoSet);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTeamTodoSet(TeamTodoSet teamTodoSet) {
        TeamTodoSet teamTodoSet1 = teamTodoSetDAO.getByName(teamTodoSet.getName(),teamTodoSet.getTeamId());
        if (teamTodoSet1 == null || teamTodoSet1.getTeamTodoSetId() == teamTodoSet.getTeamTodoSetId()) {
            teamTodoSetDAO.update(teamTodoSet);
            return true;
        }
        return false;
    }

    @Override
    public void deleteTeamTodoSet(int teamTodoSetId) {
        int teamId = teamTodoSetDAO.get(TeamTodoSet.class, teamTodoSetId).getTeamId();
        List<TeamTodo> list = teamTodoDAO.list(teamId, teamTodoSetId);
        for (int i = 0; i < list.size(); i++) {
            TeamTodo teamTodo = list.get(i);
            teamTodoDAO.delete(teamTodo.getTeamTodoId());
        }
        teamTodoSetDAO.delete(teamTodoSetId);
    }

    @Override
    public TeamTodoSet getById(int teamTodoSetId) {
        return teamTodoSetDAO.get(TeamTodoSet.class, teamTodoSetId);
    }

    @Override
    public TeamTodoSet getByName(String name,int teamId) {
        return teamTodoSetDAO.getByName(name,teamId);
    }

    @Override
    public List<TeamTodoSet> listByTeamId(int teamId) {
        return teamTodoSetDAO.list(teamId);
    }
}
