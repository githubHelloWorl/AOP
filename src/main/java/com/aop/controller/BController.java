package com.aop.controller;

import cn.hutool.json.JSONUtil;
import com.aop.annotation.AuthCheck;
import com.aop.model.eitity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@RestController
@RequestMapping("/b")
@Slf4j
public class BController {


    @GetMapping("/B")
    public String bbb(HttpServletRequest request){
        log.info("GetMapping B = {} ",request);

        request.getSession().setAttribute("user","user");

        return "bbb";
    }

    @PostMapping("/BB")
    @AuthCheck(mustRole = "bbb")
    public String a1(HttpServletRequest request,@RequestBody User user){
        log.info("BB user = {}",user);

        request.getSession().removeAttribute("user");

        return JSONUtil.toJsonStr(user);
    }
}
