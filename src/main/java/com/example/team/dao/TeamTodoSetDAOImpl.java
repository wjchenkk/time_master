package com.example.team.dao;

import com.example.team.pojo.TeamTodoSet;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("teamTodoSetDAO")
@Transactional(rollbackFor = Exception.class)
public class TeamTodoSetDAOImpl extends BaseDAOImpl<TeamTodoSet> implements TeamTodoSetDAO {

    @Override
    public TeamTodoSet getByName(String name,int teamId) {
        String hql = "from TeamTodoSet where name=:name and teamId=:teamId";
        return (TeamTodoSet) getSession().createQuery(hql).setParameter("name", name).setParameter("teamId",teamId).uniqueResult();
    }

    @Override
    public List<TeamTodoSet> list(int teamId) {
        String hql = "from TeamTodoSet where teamId=:teamId";
        return (List<TeamTodoSet>) getSession().createQuery(hql).setParameter("teamId", teamId).list();
    }

    @Override
    public void deleteList(int teamId) {
        Session session = getSession();
        String hqlUpdate = "delete from TeamTodoSet  where teamId= :teamId";
        int updatedEntities = session.createQuery(hqlUpdate)
                .setParameter("teamId", teamId)
                .executeUpdate();
    }
}
