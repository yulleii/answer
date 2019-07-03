package com.nowcoder.answer.controller;

import com.nowcoder.answer.model.Comment;
import com.nowcoder.answer.model.EntityType;
import com.nowcoder.answer.model.HostHolder;
import com.nowcoder.answer.service.CommentService;
import com.nowcoder.answer.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger= LoggerFactory.getLogger(HomeController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @RequestMapping(path={"/addComment"},method={RequestMethod.POST})
    public String addComment(@RequestParam("questionId")int questionId,
                             @RequestParam("content")String content){
        try{
            Comment comment=new Comment();
            comment.setContent(content);
            if(hostHolder.getUser()!=null)
                comment.setUserId(hostHolder.getUser().getId());
            else{
                comment.setUserId(WendaUtil.ANONYMITY_USERID);
            }
            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_COMMENT);
            commentService.addComment(comment);

        }catch(Exception e){
            logger.error("增加评论失败"+e.getMessage());
        }
        return "redirect:/question/"+questionId;

    }
}
