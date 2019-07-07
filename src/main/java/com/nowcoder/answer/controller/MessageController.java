package com.nowcoder.answer.controller;

import com.nowcoder.answer.model.HostHolder;
import com.nowcoder.answer.model.Message;
import com.nowcoder.answer.model.User;
import com.nowcoder.answer.model.ViewObject;
import com.nowcoder.answer.service.MessageService;
import com.nowcoder.answer.service.UserService;
import com.nowcoder.answer.util.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {

    private static final Logger logger= LoggerFactory.getLogger(HomeController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @RequestMapping(path={"/msg/list"},method = {RequestMethod.GET})
    public String getConversationList(Model model){
        if(hostHolder.getUser()==null)
            return "redirect:/reglogin";
        int localUserId=hostHolder.getUser().getId();
        List<Message>conversationList=messageService.getConversationList(localUserId,0,10);
        List<ViewObject>conversations=new ArrayList<>();
        for(Message message:conversationList){
            ViewObject vo=new ViewObject();
            vo.set("conversation",message);
            int targetId=message.getFromId()==localUserId?message.getToId():message.getFromId();
            vo.set("user",userService.getUser(targetId));
            //vo.set("unread",messageService.getConversationUnreadCount(localUserId,message.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations",conversations);
        return "letter";
    }

    @RequestMapping(path={"/msg/detail"},method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @Param("conversationId")String conversationId){
        try{
            List<Message>conversationList=messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject>messages=new ArrayList<>();
            for(Message msg:conversationList){
                ViewObject vo=new ViewObject();
                vo.set("message",msg);
                User user=userService.getUser(msg.getFromId());
                if(user==null)
                    continue;
                vo.set("headUrl",user.getHeadUrl());
                vo.set("userId",user.getId());
                messages.add(vo);
            }
            model.addAttribute("messages",messages);
        }catch (Exception e){
            logger.error("获取详情失败",e.getMessage());
        }
        return "letterDetail";
    }


    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName")String toName,
                             @RequestParam("content")String content){
        try{
            if(hostHolder.getUser()==null)
                return WendaUtil.getJsonString(999,"未登录");
            User user=userService.selectByName(toName);
            if(user ==null){
                return WendaUtil.getJsonString(1,"用户不存在");
            }
            Message msg=new Message();
            msg.setContent(content);
            msg.setFromId(hostHolder.getUser().getId());
            msg.setToId(user.getId());
            msg.setCreatedDate(new Date());
            messageService.addMessage(msg);
            return WendaUtil.getJsonString(0);
        }catch(Exception e){
            logger.error("发送信息失败"+e.getMessage());
            return WendaUtil.getJsonString(1,"发信失败");
        }
    }

}
