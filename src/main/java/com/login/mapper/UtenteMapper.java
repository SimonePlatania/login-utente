package com.login.mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.login.entity.Utente;

@Mapper
public interface UtenteMapper {
    
    @Results(id = "utenteResultMap", value = {
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "email", column = "email"),
        @Result(property = "password", column = "password"),
        @Result(property = "ruolo", column = "role"),
        @Result(property = "dataCreazione", column = "created_at")
    })
    @Select("SELECT * FROM users WHERE username = #{username}")
    Utente findByUsername(String username);
    
    @Select("SELECT * FROM users WHERE email = #{email}")
    @ResultMap("utenteResultMap")
    Utente findByEmail(String email);
    
    @Insert("INSERT INTO users (username, email, password, role, created_at) " +
           "VALUES (#{username}, #{email}, #{password}, #{ruolo}, NOW())")
    void insert(Utente utente);
    
    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE username = #{username})")
    boolean existsByUsername(String username);
    
    @Select("SELECT * from users WHERE id = #{id}")
    Utente findById(Long id);
    
    @Update("UPDATE users SET email = #{email}, username = #{username} " + "WHERE id = #{id}")
    void updateUtente(Utente utente);
}