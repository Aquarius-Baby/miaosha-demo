package com.demo.miaoshademo.dao;

import com.demo.miaoshademo.entity.MiaoshaUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from miaosha_user where nickname = #{nickname}")
    public MiaoshaUser getByNickname(@Param("nickname") String nickname);

    @Select("select * from miaosha_user where id = #{id}")
    public MiaoshaUser getById(@Param("id") long id);

    @Update("update miaosha_user set password = #{password} where id = #{id}")
    public void update(MiaoshaUser toBeUpdate);

    @Insert("insert into miaosha_user (id , nickname ,password , salt ,head,register_date,last_login_date)value (#{id},#{nickname},#{password},#{salt},#{head},#{registerDate},#{lastLoginDate}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insertMiaoShaUser(MiaoshaUser miaoshaUser);

    @Select("select * from miaosha_user where id = #{mobile}")
    MiaoshaUser getByMobile(String mobile);
}
