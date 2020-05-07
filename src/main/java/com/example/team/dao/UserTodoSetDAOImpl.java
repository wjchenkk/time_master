package com.example.team.dao;

import com.example.team.pojo.UserTodo;
import com.example.team.pojo.UserTodoSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository(value = "UserTodoSetDAO")
@Transactional(rollbackFor = Exception.class)
public class UserTodoSetDAOImpl implements UserTodoSetDAO {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }

    @Override
    public void add(UserTodoSet userTodoSet) {
        getSession().save(userTodoSet);
    }

    @Override
    public void update(UserTodoSet userTodoSet) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.update(userTodoSet);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(int userTodoSetId) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        String hql = "from UserTodoSet where userTodoSetId=:userTodoSetId";
        UserTodoSet userTodoSet = (UserTodoSet) session.createQuery(hql).setParameter("userTodoSetId",userTodoSetId).uniqueResult();
        session.delete(userTodoSet);
        tx.commit();
        session.close();
    }

    @Override
    public UserTodoSet getById(int userTodoSetId) {
        String hql = "from UserTodoSet where userTodoSetId=:userTodoSetId";
        return (UserTodoSet) getSession().createQuery(hql).setParameter("userTodoSetId",userTodoSetId).uniqueResult();
    }

    @Override
    public UserTodoSet getByName(String name) {
        String hql = "from UserTodoSet where name=:name";
        return (UserTodoSet) getSession().createQuery(hql).setParameter("name",name).uniqueResult();
    }

    @Override
    public List<UserTodoSet> listById(int userId) {
        String hql = "from UserTodoSet where userId=:userId";
        return (List<UserTodoSet>) getSession().createQuery(hql).setParameter("userId",userId).list();
    }
}