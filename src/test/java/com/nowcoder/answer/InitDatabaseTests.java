package com.nowcoder.answer;

import com.nowcoder.answer.dao.QuestionDAO;
import com.nowcoder.answer.dao.UserDAO;
import com.nowcoder.answer.model.Question;
import com.nowcoder.answer.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql")
public class InitDatabaseTests {
	@Autowired
	UserDAO userDAO;
	@Autowired
	QuestionDAO questionDAO;
	@Test
	public void contextLoads() {
		Random random=new Random();
		for (int i=0;i<11;i++){
			User user=new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			user.setName(String.format("USER%d",i));
			user.setPassword("xxxx");
			user.setSalt("");
			userDAO.addUser(user);

			userDAO.updatePassword(user);

			Question question=new Question();
			question.setCommentCount(i);
			Date date=new Date();
			date.setTime(date.getTime()+1000*3600*i);
			question.setCreatedDate(date);
			question.setUserId(i+1);
			question.setTitle(String.format("TITLE{%d}",i));
			question.setContent(String.format("Balalalaal content %d",i));
			questionDAO.addQuestion(question);

		}
		Assert.assertEquals("xxxx",userDAO.selectById(1).getPassword());
		userDAO.deleteById(1);
		Assert.assertNull(userDAO.selectById(1));

		//List<Question> ql=questionDAO.selectLatestQuestions(0,0,10);

	}

}
