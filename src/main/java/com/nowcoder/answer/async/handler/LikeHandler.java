package com.nowcoder.answer.async.handler;

import com.nowcoder.answer.async.EventHandler;
import com.nowcoder.answer.async.EventModel;
import com.nowcoder.answer.async.EventType;
import com.nowcoder.answer.model.Message;
import com.nowcoder.answer.model.User;
import com.nowcoder.answer.service.MessageService;
import com.nowcoder.answer.service.UserService;
import com.nowcoder.answer.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message=new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user=userService.getUser(model.getActorId());
        message.setContent("用户"+user.getName()+"赞了你的评论,"+
                "http://127.0.0.1:8080/question/"+model.getExt("questionId"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
