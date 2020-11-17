package com.tech.sysmindassignment.MarvelArchitecture;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MarvelViewModel extends AndroidViewModel {
        private MarvelRepository repository;
        private LiveData<List<MarvelModelEntity>> allMarvel;

        public MarvelViewModel(@NonNull Application application) {
            super(application);
            repository = new MarvelRepository(application);
            allMarvel = repository.getAllMarvel();
        }

        public void insert(MarvelModelEntity marvelModelEntity) {
            repository.insert(marvelModelEntity);
        }

        public void update(MarvelModelEntity marvelModelEntity) {
            repository.update(marvelModelEntity);
        }

        public void delete(MarvelModelEntity marvelModelEntity) {
            repository.delete(marvelModelEntity);
        }

        public void deleteAllMarvels() {
            repository.deleteAllMarvel();
        }

        public LiveData<List<MarvelModelEntity>> getAllMarvels() {
            return allMarvel;
        }
    }

