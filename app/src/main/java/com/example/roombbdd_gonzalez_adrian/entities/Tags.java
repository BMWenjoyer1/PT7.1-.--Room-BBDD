package com.example.roombbdd_gonzalez_adrian.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags")
public class Tags {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String nom;
    
    public Tags() {}
    
    public Tags(String nom) {
        this.nom = nom;
    }
    
    public Tags(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
}
