package com.firefly.cloud.repository.dao;

import com.firefly.cloud.repository.entity.StorageDirEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StorageDirDao {
    
    @Insert("INSERT INTO storage_dir (id, user_id, parent_dir, dir_name, source, create_time, update_time) " +
            "VALUES (#{id}, #{userId}, #{parentDir}, #{dirName}, #{source}, #{createTime}, #{updateTime})")
    int insert(StorageDirEntity storageDir);
    
    @Update("UPDATE storage_dir SET user_id = #{userId}, parent_dir = #{parentDir}, dir_name = #{dirName}, " +
            "source = #{source}, update_time = #{updateTime} WHERE id = #{id}")
    int update(StorageDirEntity storageDir);
    
    @Delete("DELETE FROM storage_dir WHERE id = #{id}")
    int deleteById(Long id);
    
    @Select("SELECT * FROM storage_dir WHERE id = #{id}")
    StorageDirEntity findById(Long id);
    
    @Select("SELECT * FROM storage_dir WHERE user_id = #{userId} AND parent_dir = #{parentDir} AND dir_name = #{dirName}")
    StorageDirEntity findByUserIdAndParentDirAndDirName(Long userId, String parentDir, String dirName);
    
    @Select("SELECT * FROM storage_dir WHERE user_id = #{userId}")
    List<StorageDirEntity> findByUserId(Long userId);
} 