package com.example.team.dao;

import com.example.team.pojo.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository(value = "userDAO")
@Transactional(rollbackFor = Exception.class)
public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO {


    @Override
    public User getByTel(String tel) {
        String hql = "from User where tel=:tel";
        return (User) getSession().createQuery(hql).setParameter("tel", tel).uniqueResult();
    }

    @Override
    public User getByEmail(String email) {
        String hql = "from User where email=:email";
        return (User) getSession().createQuery(hql).setParameter("email", email).uniqueResult();
    }
}
