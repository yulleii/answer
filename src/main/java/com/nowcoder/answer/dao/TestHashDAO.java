package com.nowcoder.answer.dao;

import com.nowcoder.answer.model.LoginTicket;
import com.nowcoder.answer.model.UserName;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestHashDAO {
    String TABLE_NAME=" TestDefault ";
    String INSERT_FILEDS=" fname,lname ";
    String SELECT_FILEDS=INSERT_FILEDS;
    @Insert({"insert",TABLE_NAME,"(",INSERT_FILEDS,
            ") values (#{fname},#{lname})"})
    int addName(UserName userName);
}
