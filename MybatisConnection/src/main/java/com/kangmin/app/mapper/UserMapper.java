package com.kangmin.app.mapper;

import java.util.List;

import com.kangmin.app.model.User;
import com.kangmin.app.model.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("SELECT * FROM users")
    @Results({
        @Result(property = "userRole", column = "user_role", javaType = UserRole.class),
        @Result(property = "nickName", column = "nick_name")
    })
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
        @Result(property = "userRole", column = "user_role", javaType = UserRole.class),
        @Result(property = "nickName", column = "nick_name")
    })
    User getOne(Long id);

    @Insert("INSERT INTO users(username,password,user_role,nick_name) VALUES(#{username}, #{password}, #{userRole}, #{nickName})")
    void insert(User user);

    @Update("UPDATE users SET username=#{username},nick_name=#{nickName} WHERE id =#{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);

}
