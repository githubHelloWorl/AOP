package com.aop.controller;

import com.aop.annotation.AuthCheck;
import com.aop.model.eitity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import cn.hutool.json.JSONUtil;

@ResponseBody
@RestController
@RequestMapping("/a")
@Slf4j
public class AContorller {

    @GetMapping("/A")
    public String aaa(HttpServletRequest request){
        log.info("GetMapping A = {} ",request);

        request.getSession().setAttribute("user","user");

        return "aaa";
    }

    @PostMapping("/AA")
    @AuthCheck(mustRole = "aaa")
    public String a1(HttpServletRequest request,@RequestBody User user){
        log.info("AA user = {}",user);

        request.getSession().removeAttribute("user");

        return JSONUtil.toJsonStr(user);
    }

}
