package com.example.roombbdd_gonzalez_adrian.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roombbdd_gonzalez_adrian.entities.Tasques;
import com.example.roombbdd_gonzalez_adrian.entities.TasqueWithTags;

import java.util.List;

@Dao
public interface TasquesDao {
    @Insert
    long insertTasques(Tasques tasques);
    
    @Update
    void updateTasques(Tasques tasques);
    
    @Delete
    void deleteTasques(Tasques tasques);
    
    @Query("SELECT * FROM tasques")
    List<Tasques> getAllTasques();
    
    @Query("SELECT * FROM tasques WHERE id = :id")
    Tasques getTasquesById(int id);
    
    @Query("SELECT * FROM tasques")
    List<TasqueWithTags> getAllTasquesWithTags();
    
    @Query("SELECT t.* FROM tasques t INNER JOIN tasca_tag tt ON t.id = tt.tascaId WHERE tt.tagId = :tagId")
    List<Tasques> getTasquesByTag(int tagId);
    
    @Query("SELECT t.* FROM tasques t INNER JOIN tasca_tag tt ON t.id = tt.tascaId INNER JOIN tags ta ON tt.tagId = ta.id WHERE ta.nom = :tagNom")
    List<Tasques> getTasquesByTagName(String tagNom);
}
