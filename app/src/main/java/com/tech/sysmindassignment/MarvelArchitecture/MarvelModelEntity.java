package com.tech.sysmindassignment.MarvelArchitecture;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "marvel_table")
public class MarvelModelEntity {

    @PrimaryKey(autoGenerate = true)
        private int id;




    private String marvel_name;
    private String description;
    private String marvel_image;



    public MarvelModelEntity( String marvel_name, String description, String marvel_image) {
        this.marvel_name = marvel_name;

        this.description = description;
        this.marvel_image = marvel_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarvel_name() {
        return marvel_name;
    }

    public void setMarvel_name(String marvel_name) {
        this.marvel_name = marvel_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMarvel_image() {
        return marvel_image;
    }

    public void setMarvel_image(String marvel_image) {
        this.marvel_image = marvel_image;
    }
}
