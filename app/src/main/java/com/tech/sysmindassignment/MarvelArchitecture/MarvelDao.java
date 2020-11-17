package com.tech.sysmindassignment.MarvelArchitecture;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MarvelDao {


        @Insert
        void insert(MarvelModelEntity marvelModelEntity);

        @Update
        void update(MarvelModelEntity marvelModelEntity);

        @Delete
        void delete(MarvelModelEntity courseModelEntity);

        @Query("DELETE FROM marvel_table")
        void deleteAllMarvel();

        @Query("SELECT * FROM marvel_table")
        LiveData<List<MarvelModelEntity>> getAllMarvel();


    }

