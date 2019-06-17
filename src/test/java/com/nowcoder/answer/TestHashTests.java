package com.nowcoder.answer;

import com.nowcoder.answer.dao.TestHashDAO;
import com.nowcoder.answer.model.UserName;
import com.nowcoder.answer.util.WendaUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHashTests {
    @Autowired
    TestHashDAO testHashDAO;
    @Test
    public void addUserName(){
        for(int i=0;i<100000;i++){
            UserName userName=new UserName(WendaUtil.getRandomString(15), WendaUtil.getRandomString(15));
            testHashDAO.addName(userName);
        }
    }
}
