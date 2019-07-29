package com.example.bimfacedemo.mapper;

import com.example.bimfacedemo.entity.FileMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/18
 */
@Repository
@Mapper
public interface FileMapper {

    @Select("select * from file_message where id = #{id }")
    FileMessage getFileMessageById(Integer id);

    @Select("select * from file_message")
    List<FileMessage> getAllFileMessage();

    @Insert("insert into file_message (path, file_name, user_name) values (#{path}, #{fileName}, #{userName})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void fileMessageSave(FileMessage fileMessage);

    @Delete("delete from file_message where id = #{id}")
    void deleteFileMessageById(Integer id);

    @Update("update file_message set path = #{path}, file_name = #{fileName}, user_name = #{userName} where id = #{id}")
    void updateFileMessage(FileMessage fileMessage);
}
