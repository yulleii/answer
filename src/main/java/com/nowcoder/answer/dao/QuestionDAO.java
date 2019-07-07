package com.nowcoder.answer.dao;

import com.nowcoder.answer.model.Question;
import com.nowcoder.answer.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nowcoder.answer.dao.UserDAO.SELECT_FILEDS;


@Mapper
@Repository
public interface QuestionDAO {
    String TABLE_NAME=" question ";
    String INSERT_FILEDS=" title,content,created_date,user_id,comment_count ";
    String SELECT_FILEDS=" id,"+INSERT_FILEDS;

    @Insert({"insert ",
            TABLE_NAME ,"(",INSERT_FILEDS,
            ") values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId")int userId,
                                         @Param("offset")int offset,
                                         @Param("limit")int limit);

    @Select({"select ",SELECT_FILEDS,"from ",TABLE_NAME,"where id = #{id}"})
    Question selectById(int id);

    @Update({"update ",TABLE_NAME,"set comment_count=#{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id")int id,@Param("commentCount")int commentCount);
}
