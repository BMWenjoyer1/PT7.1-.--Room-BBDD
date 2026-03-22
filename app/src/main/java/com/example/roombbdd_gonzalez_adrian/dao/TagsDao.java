package com.example.roombbdd_gonzalez_adrian.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roombbdd_gonzalez_adrian.entities.Tags;

import java.util.List;

@Dao
public interface TagsDao {
    @Insert
    long insertTag(Tags tags);
    
    @Update
    void updateTag(Tags tags);
    
    @Delete
    void deleteTag(Tags tags);
    
    @Query("SELECT * FROM tags")
    List<Tags> getAllTags();
    
    @Query("SELECT * FROM tags WHERE id = :id")
    Tags getTagById(int id);
    
    @Query("SELECT * FROM tags WHERE nom = :nom")
    Tags getTagByNom(String nom);
}
