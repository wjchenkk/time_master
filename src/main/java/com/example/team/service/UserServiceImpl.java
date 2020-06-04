package com.example.team.service;

import com.example.team.dao.PetDAO;
import com.example.team.dao.TeamDAO;
import com.example.team.dao.UserDAO;
import com.example.team.mail.MailSenderInfo;
import com.example.team.mail.SimpleMailSender;
import com.example.team.pojo.Pet;
import com.example.team.pojo.Team;
import com.example.team.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Service(value = "userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private int id;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PetDAO petDAO;
    @Autowired
    private TeamDAO teamDAO;

    /**
     * @description: 验证登录
     * @Param:  
     * @return:  
     * @update: time: 2020/6/3 9:31 
     */
    public boolean verify(String userName, String password) {
        User user = userDAO.getByEmail(userName);
        if (user != null && password.equals(user.getPassword())) {
            id = user.getUserId();
            return true;
        }
        return false;
    }

    @Override
    /**
     * @description: 注册
     * @Param: [userId] 
     * @return: void 
     * @update: time: 2020/6/3 9:31 
     */
    public void quit(int userId) {
        redisService.delete(String.valueOf(userId));
    }

    public boolean sign(User user, Pet pet) {
        String email = user.getEmail();
        String tel = user.getTel();
        User user_1 = userDAO.getByEmail(email);
        User user_2 = userDAO.getByTel(tel);
        if (user_1 == null && user_2 == null) {
            petDAO.add(pet);
            user.setPet(pet);
            userDAO.add(user);
            return true;
        }
        return false;
    }

    @Override
    /**
     * @description: 获取用户Id
     * @Param: [tel, email, name] 
     * @return: int 
     * @update: time: 2020/6/3 9:31 
     */
    public int getUserId(String tel, String email) {
        if (tel != null) {
            if (userDAO.getByTel(tel) != null) {
                return userDAO.getByTel(tel).getUserId();
            }
        }
        if (email != null) {
            if (userDAO.getByEmail(email) != null) {
                return userDAO.getByEmail(email).getUserId();
            }
        }
        return 0;
    }

    public int getId() {
        return id;
    }
    
    @Override
    /**
     * @description: 更新邮箱
     * @Param: [userId, email] 
     * @return: boolean 
     * @update: time: 2020/6/3 9:31 
     */
    public boolean updateEmail(int userId, String email) {
        User user = userDAO.get(User.class, userId);
        User user1 = userDAO.getByEmail(email);
        if (user1 != null && !email.equals(user.getEmail())) {
            return false;
        }
        if (!email.equals("")) {
            user.setEmail(email);
        }
        userDAO.update(user);
        return true;
    }

    @Override
    /**
     * @description: 更新手机
     * @Param: [userId, tel] 
     * @return: boolean 
     * @update: time: 2020/6/3 9:31 
     */
    public boolean updateTel(int userId, String tel) {
        User user = userDAO.get(User.class, userId);
        User user1 = userDAO.getByTel(tel);
        if (user1 != null && !tel.equals(user.getTel())) {
            return false;
        }
        if (!tel.equals("")) {
            user.setTel(tel);
        }
        userDAO.update(user);
        return true;
    }

    @Override
    /**
     * @description: 更新用户名
     * @Param: [userId, userName] 
     * @return: boolean 
     * @update: time: 2020/6/3 9:31 
     */
    public boolean updateUserName(int userId, String userName) {
        User user = userDAO.get(User.class, userId);
        if (!userName.equals("")) {
            user.setName(userName);
            userDAO.update(user);
            return true;
        }
        return false;
    }

    @Override
    /**
     * @description: 更新密码
     * @Param: [userId, password] 
     * @return: boolean 
     * @update: time: 2020/6/3 9:31 
     */
    public boolean updateUserPassword(int userId, String password) {
        User user = userDAO.get(User.class, userId);
        if (password.length() >= 6 && password.length() <= 20) {
            user.setPassword(password);
            userDAO.update(user);
            return true;
        }
        return false;
    }
    
    @Override
    /**
     * @description: 获取用户
     * @Param: [userId] 
     * @return: com.example.team.pojo.User 
     * @update: time: 2020/6/3 9:31 
     */
    public User getById(int userId) {
        return userDAO.get(User.class, userId);
    }

    @Override
    /**
     * @description: 获取团队队员集合
     * @Param: [teamId] 
     * @return: java.util.Set<com.example.team.pojo.User> 
     * @update: time: 2020/6/3 9:31 
     */
    public Set<User> getMembers(int teamId) {
        Team team = teamDAO.get(Team.class, teamId);
        if (team != null) {
            return team.getUsers();
        }
        return null;
    }

    @Override
    /**
     * @description: 获取团队集合
     * @Param: [userId] 
     * @return: java.util.Set<com.example.team.pojo.Team> 
     * @update: time: 2020/6/3 9:31 
     */
    public Set<Team> getTeams(int userId) {
        User user = userDAO.get(User.class, userId);
        if (user != null) {
            return user.getTeams();
        }
        return null;
    }

    @Override
    /**
     * @description: 加入团队
     * @Param: [teamId, userId] 
     * @return: com.example.team.pojo.Team 
     * @update: time: 2020/6/3 9:31 
     */
    public Team joinTeam(int teamId, int userId) {
        Team team = teamDAO.get(Team.class, teamId);
        User user = userDAO.get(User.class, userId);
        if (team != null && user != null) {
            for (User user1 : team.getUsers()) {
                if (user1.getUserId() == userId) {
                    Team temp=new Team();
                    temp.setTeamId(-1);
                    return temp;
                }
            }
            team.getUsers().add(user);
            teamDAO.update(team);
            return team;
        }
        return null;
    }

    @Override
    /**
     * @description: 退出团队
     * @Param: [teamId, userId]
     * @return: boolean 
     * @update: time: 2020/6/3 9:31 
     */
    public boolean quitTeam(int teamId, int userId) {
        boolean flag = false;
        Team team = teamDAO.get(Team.class, teamId);
        if (team != null) {
            for (User user : team.getUsers()) {
                if (user.getUserId() == userId) {
                    team.getUsers().remove(user);
                    flag = true;
                    break;
                }
            }
            teamDAO.update(team);
            if (team.getUsers().size() == 0) {
                teamDAO.delete(teamId);
            }
        }
        return flag;
    }

}
