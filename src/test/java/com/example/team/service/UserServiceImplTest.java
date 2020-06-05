package com.example.team.service;

import com.example.team.pojo.Pet;
import com.example.team.pojo.User;
import com.example.team.util.RandomNameUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private AchievementService achievementService;

    /*@Test
    @Transactional
    @Rollback(true)
    void verify() {
        Assert.assertTrue(userService.verify("wj@163.com","123456"));
        String a= RandomNameUtil.getRandomJName();
        System.out.println(a);
        //achievementService.addAchievement(1);
        //achievementService.updateAchievement(1);
    }

    /*@Test
    void quit() {
    }

    @Test
    void sign() {
        User user = new User();
        user.setEmail("wj@163.com");
        user.setName("123");
        user.setTel("12345678901");
        user.setPassword("123456");
        user.setSex("1");
        user.setCreate(new Date(new java.util.Date().getTime()));
        Pet pet = new Pet();
        pet.setBirth(new Date(new java.util.Date().getTime()));
        pet.setSex(1);
        pet.setName("简时");
        pet.setPetStatusId(1);
        pet.setSkinId(1);
        pet.setLevel(1);
        //wj@163.com存在false
        Assert.assertTrue(!userService.sign(user,pet));
        //user.setEmail("111@163.com");
       // Assert.assertTrue(userService.sign(user,pet));
    }

    @Test
    void getUserId() {
        int id1=userService.getUserId("","wj@163.com","");
        int id2=userService.getUserId("","","wjchen");
        int id3=userService.getUserId("13215077020","","");
        Assert.assertEquals(1,id1);
        Assert.assertEquals(2,id2);
        Assert.assertEquals(1,id3);
    }

   /* @Test
    void updateUserInfo() {
        //已存在用户名、email、tel 返回false
        Assert.assertTrue(!userService.updateUserInfo(1,"wjchen","",""));
        Assert.assertTrue(!userService.updateUserInfo(1,"","","15606013061"));
        Assert.assertTrue(!userService.updateUserInfo(1,"","1158802650@qq.com",""));
        //不存在
        Assert.assertTrue(userService.updateUserInfo(1,"admin1","",""));
    }*/

    /*@Test
    void updateUserPassword() {
        //太短太长
        Assert.assertTrue(!userService.updateUserPassword(1,"12345"));
        Assert.assertTrue(!userService.updateUserPassword(1,"12345678901234567890123"));
        //6位
        Assert.assertTrue(userService.updateUserPassword(1,"123456"));
    }

    @Test
    void getById() {
        User user=userService.getById(1);
        Assert.assertEquals(1,user.getUserId());
    }*/
}