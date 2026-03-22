package com.example.roombbdd_gonzalez_adrian.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roombbdd_gonzalez_adrian.entities.TascaTag;

import java.util.List;

@Dao
public interface TascaTagDao {
    @Insert
    long insertTascaTag(TascaTag tascaTag);
    
    @Delete
    void deleteTascaTag(TascaTag tascaTag);
    
    @Query("SELECT * FROM tasca_tag")
    List<TascaTag> getAllTascaTags();
    
    @Query("SELECT * FROM tasca_tag WHERE tascaId = :tascaId")
    List<TascaTag> getTascaTagsByTascaId(int tascaId);
    
    @Query("DELETE FROM tasca_tag WHERE tascaId = :tascaId AND tagId = :tagId")
    void deleteTascaTag(int tascaId, int tagId);
    
    @Query("SELECT * FROM tasca_tag WHERE tascaId = :tascaId AND tagId = :tagId LIMIT 1")
    TascaTag getTascaTag(int tascaId, int tagId);
}
