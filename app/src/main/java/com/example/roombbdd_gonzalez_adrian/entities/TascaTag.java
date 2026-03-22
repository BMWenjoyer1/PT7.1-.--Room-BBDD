package com.example.roombbdd_gonzalez_adrian.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasca_tag",
    foreignKeys = {
        @ForeignKey(entity = Tasques.class, parentColumns = "id", childColumns = "tascaId", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Tags.class, parentColumns = "id", childColumns = "tagId", onDelete = ForeignKey.CASCADE)
    })
public class TascaTag {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public int tascaId;
    public int tagId;
    
    public TascaTag() {}
    
    public TascaTag(int tascaId, int tagId) {
        this.tascaId = tascaId;
        this.tagId = tagId;
    }
    
    public TascaTag(int id, int tascaId, int tagId) {
        this.id = id;
        this.tascaId = tascaId;
        this.tagId = tagId;
    }
}
