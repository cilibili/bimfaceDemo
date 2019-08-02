package com.example.bimfacedemo.mapper;

import com.example.bimfacedemo.entity.BimfileMessage;
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

    @Insert("insert into bimfile_message (fileId ) values (#{fileId} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveFileId(BimfileMessage bimfileMessage);

    @Select("select * from bimfile_message ")
    List<BimfileMessage> getAllBimfileMessage();

    @Select("select * from bimfile_message where id = #{id }")
    BimfileMessage getBimfileMessageById(Integer id);

    @Select("select * from bimfile_message where fileId = #{fileId }")
    BimfileMessage getBimfileMessageByFileId(String fileId);

    @Delete("delete from bimfile_message where id = #{id}")
    void deleteBimfileMessageById(Integer id);

    @Update("update bimfile_message set fileId = #{fileId}, viewToken = #{viewToken} where id = #{id}")
    void updateBimfileMessage(BimfileMessage fileMessage);

    // --------------------------上为bimfile_message表---下为file_message表--------------------------

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
