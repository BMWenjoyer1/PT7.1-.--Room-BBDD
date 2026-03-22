package com.example.roombbdd_gonzalez_adrian.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasques")
public class Tasques {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String titol;
    public String descripcio;
    public String estat; // pendent, en_proces, completada
    public long dataCreacio;
    public long dataCanvi;
    
    public Tasques() {}
    
    public Tasques(String titol, String descripcio, String estat, long dataCreacio, long dataCanvi) {
        this.titol = titol;
        this.descripcio = descripcio;
        this.estat = estat;
        this.dataCreacio = dataCreacio;
        this.dataCanvi = dataCanvi;
    }
    
    public Tasques(int id, String titol, String descripcio, String estat, long dataCreacio, long dataCanvi) {
        this.id = id;
        this.titol = titol;
        this.descripcio = descripcio;
        this.estat = estat;
        this.dataCreacio = dataCreacio;
        this.dataCanvi = dataCanvi;
    }
}
