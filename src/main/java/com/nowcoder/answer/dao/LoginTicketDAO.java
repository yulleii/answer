package com.nowcoder.answer.dao;

import com.nowcoder.answer.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME=" login_ticket ";
    String INSERT_FILEDS=" user_id,expired,status,ticket ";
    String SELECT_FILEDS=" id, "+INSERT_FILEDS;

    @Insert({"insert",TABLE_NAME,"(",INSERT_FILEDS,
            ") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ",SELECT_FILEDS,"from",TABLE_NAME,"where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ",TABLE_NAME," set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket")String ticket,@Param("status")int status);
}
