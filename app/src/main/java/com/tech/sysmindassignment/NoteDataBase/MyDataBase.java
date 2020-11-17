package com.tech.sysmindassignment.NoteDataBase;

import android.content.Context;

import com.tech.sysmindassignment.MarvelArchitecture.MarvelDao;
import com.tech.sysmindassignment.MarvelArchitecture.MarvelModelEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MarvelModelEntity.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {



        private static MyDataBase instance;
          public abstract MarvelDao marvelDao();

        public static synchronized MyDataBase getInstance(Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        MyDataBase.class, "my_database")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return instance;
        }
    }

