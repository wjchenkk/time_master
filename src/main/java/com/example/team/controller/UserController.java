package com.example.team.controller;

import com.example.team.pojo.*;
import com.example.team.service.*;
import com.example.team.util.DateUtil;
import com.example.team.util.MailUtil;
import com.example.team.util.RandomNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AchievementService achievementService;
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 登录
     * @Param: [param]
     * @return: java.lang.String 登录结果
     * @update: time: 2020/6/3 9:01
     */
    public String login(@RequestBody Map<String, Object> param) {
        String userName = param.get("userName").toString();
        String password = param.get("password").toString();
        if (userService.verify(userName, password)) {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            int id = userService.getId();
            redisService.set(String.valueOf(id), token);
            redisService.setExpire(String.valueOf(id), 21600);
            response.setHeader("Access-Control-Expose-Headers", "token");
            response.setHeader("Access-Control-Expose-Headers", "id");
            response.setHeader("token", token);
            response.setHeader("id", String.valueOf(id));
            return "login-success";
        }
        return "login-fail";
    }

    @RequestMapping("/quit")
    @ResponseBody
    /**
     * @description: 退出
     * @Param: []
     * @return: java.lang.String 退出结果
     * @update: time: 2020/6/3 9:01
     */
    public String quit() {
        int userId = Integer.parseInt(request.getHeader("id"));
        userService.quit(userId);
        return "quit-success";
    }

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 注册
     * @Param: [param]
     * @return: java.lang.String 注册结果
     * @update: time: 2020/6/3 9:01
     */
    public String sign(@RequestBody Map<String, Object> param) {
        User user = new User();
        user.setEmail(param.get("email").toString());
        user.setName(param.get("userName").toString());
        user.setTel(param.get("tel").toString());
        user.setPassword(param.get("password").toString());
        user.setSex(param.get("sex").toString());
        user.setCreate(DateUtil.getCurrentTime());
        Pet pet = new Pet();
        pet.setBirth(DateUtil.getCurrentTime());
        pet.setSex(new Random(System.currentTimeMillis()).nextInt(2));
        pet.setName(RandomNameUtil.getRandomJName());
        pet.setLevel(1);
        String verification = redisService.get(user.getEmail());
        if (verification != null) {
            if (verification.equals(param.get("verification").toString())) {
                if (userService.sign(user, pet)) {
                    achievementService.addAchievement(user.getUserId());
                    return "sign-succees";
                }
                return "sign-fail";
            }
            return "verify-fail";
        }
        return "overtime";
    }

    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 更新邮箱
     * @Param: [param]
     * @return: java.lang.String 更新结果
     * @update: time: 2020/6/3 9:00
     */
    public String updateEmail(@RequestBody Map<String, Object> param) {
        int userId = Integer.parseInt(request.getHeader("id"));
        String email = param.get("email").toString();
        String verification = redisService.get(email);
        if (verification != null) {
            if (verification.equals(param.get("verification").toString())) {
                if (userService.updateEmail(userId, email)) {
                    return "update-success";
                }
                return "update-fail";
            }
            return "verify-fail";
        }
        return "overtime";
    }

    @RequestMapping(value = "/updateUserName", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 更新用户名
     * @Param: [param]
     * @return: java.lang.String 更新结果
     * @update: time: 2020/6/3 9:00
     */
    public String updateUserName(@RequestBody Map<String, Object> param) {
        int userId = Integer.parseInt(request.getHeader("id"));
        String userName = param.get("userName").toString();
        if (userService.updateUserName(userId, userName)) {
            return "update-success";
        }
        return "update-fail";

    }

    @RequestMapping(value = "/updateTel", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 更新手机
     * @Param: [param]
     * @return: java.lang.String 更新结果
     * @update: time: 2020/6/3 9:00
     */
    public String updateTel(@RequestBody Map<String, Object> param) {
        int userId = Integer.parseInt(request.getHeader("id"));
        String tel = param.get("tel").toString();
        if (userService.updateTel(userId, tel)) {
            return "update-success";
        }
        return "update-fail";

    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    /**
     * @description: 更新密码
     * @Param: [param]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:00
     */
    public String updatePassword(@RequestBody Map<String, Object> param) {
        int userId = Integer.parseInt(request.getHeader("id"));
        String newPassword = param.get("newPassword").toString();
        String oldPassword = param.get("oldPassword").toString();
        User user = userService.getById(userId);
        if (userService.verify(user.getEmail(), oldPassword)) {
            if (userService.updateUserPassword(userId, newPassword)) {
                return "update-success";
            }
        }
        return "update-fail";
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    /**
     * @description: 获取用户
     * @Param: []
     * @return: com.example.team.pojo.User
     * @update: time: 2020/6/3 9:00
     */
    public User getUserInfo() {
        int userId = Integer.parseInt(request.getHeader("id"));
        return userService.getById(userId);
    }

    @RequestMapping("/findPassword")
    @ResponseBody
    /**
     * @description: 发送找回链接
     * @Param: [email]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:00
     */
    public String findPassword(@RequestParam String email) {
        int userId = userService.getUserId("", email);
        String address = "http://114.116.232.199:8080";
        String content = "点击下方重置链接重置密码<br><a href = \"" + address + "/user/gotoReset?key="
                + userId + "@" + new java.util.Date().getTime() + "\">重置链接</a><br>有效时长10分钟。";
        if (userId != 0) {
            if (MailUtil.sendEmail(email, "重置密码", content)) {
                return "send-success";
            }
        }
        return "send-fail";
    }

    @RequestMapping(value = "/gotoReset")
    /**
     * @description: 验证跳转
     * @Param: [key, model]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:00
     */
    public String gotoReset(@RequestParam String key, Model model) {
        String[] keys = key.split("@");
        int userId = Integer.parseInt(keys[0]);
        Long time = Long.parseLong(keys[1]);
        java.util.Date date = new java.util.Date();
        User user = userService.getById(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("email", user.getEmail());
        if (date.getTime() - time <= 600000) {
            model.addAttribute("flag", "true");
            return "resetPassword";
        }
        model.addAttribute("flag", "false");
        return "resetPassword";
    }

    @RequestMapping(value = "/resetPassword")
    /**
     * @description: 重置密码
     * @Param: [userId, password1, model]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:00
     */
    public String resetPassword(@RequestParam String userId, @RequestParam String password1, Model model) {
        int userId1 = Integer.parseInt(userId);
        User user = userService.getById(userId1);
        model.addAttribute("email", user.getEmail());
        if (userService.updateUserPassword(userId1, password1)) {
            model.addAttribute("flag", "true");
            return "result";
        }
        model.addAttribute("flag", "false");
        return "result";
    }

    @RequestMapping("/sendVerification")
    @ResponseBody
    /**
     * @description: 发送验证码
     * @Param: [email]
     * @return: java.lang.String
     * @update: time: 2020/6/3 9:00
     */
    public String sendVerification(@RequestParam String email) {
        StringBuilder content = new StringBuilder();
        StringBuilder code = new StringBuilder();
        content.append("有效时长：5分钟<br>");
        String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
        Random rand = new Random();
        for (int j = 0; j < 6; j++) {
            code.append(sources.charAt(rand.nextInt(9)));
        }
        content.append(code);
        if (MailUtil.sendEmail(email, "验证码", content.toString())) {
            redisService.set(email, code.toString());
            redisService.setExpire(email, 300);
            return "send-success";
        }
        return "send-fail";
    }

    @RequestMapping("/test")
    @ResponseBody
    /**
     * @description: 测试登录
     * @Param: []
     * @return: java.lang.String
     * @update: time: 2020/6/4 23:55
     */
    public String test() {
        return "test-success";
    }
}
