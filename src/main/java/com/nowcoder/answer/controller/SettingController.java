package com.nowcoder.answer.controller;

import com.nowcoder.answer.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

//@Controller
public class SettingController {
    @Autowired
    AnswerService as;

    @RequestMapping(path={"setting"})
    @ResponseBody
    public String index(HttpSession httpSession){
        as.setAge(2);
        return as.getMessage(1)+"hello NowCoder"+ httpSession.getAttribute("msg");
    }
}
