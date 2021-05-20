package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.redis.RedisService;
import com.example.demo.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * UserController
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Controller
public class UserController {

    private static final String KEY_USER_ID = "__userid__";
    private static final String KEY_USERS = "__users__";

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RedisService redisService;
    @Autowired
    IUserService userService;

    /**
     * 把User写入Redis:
     */
    private void putUserIntoRedis(User user) throws Exception {
        redisService.hset(KEY_USERS, String.valueOf(user.getId()), objectMapper.writeValueAsString(user));
    }

    /**
     * 从Redis读取User:
     */
    private User getUserFromRedis(HttpSession session) throws Exception {
        Long id = (Long) session.getAttribute(KEY_USER_ID);
        if (id != null) {
            String s = redisService.hget(KEY_USERS, id.toString());
            if (s != null) {
                return objectMapper.readValue(s, User.class);
            }
        }
        return null;
    }

    @PostMapping("/signin")
    public ModelAndView doSignin(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) throws Exception {
        try {
            User user = userService.signin(email, password);
            session.setAttribute(KEY_USER_ID, user.getId());
            putUserIntoRedis(user);
        } catch (RuntimeException e) {
            Map<String, String> map = new HashMap<>(2);
            map.put("email", email);
            map.put("error", "Signin failed");

            return new ModelAndView("signin.html", map);
        }
        return new ModelAndView("redirect:/profile");
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session) throws Exception {
        User user = getUserFromRedis(session);
        if (user == null) {
            return new ModelAndView("redirect:/signin");
        }
        Map<String, User> map = new HashMap<>(1);
        map.put("user", user);
        return new ModelAndView("profile.html", map);
    }
}
