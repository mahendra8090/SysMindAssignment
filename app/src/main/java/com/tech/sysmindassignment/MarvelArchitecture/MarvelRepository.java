package com.tech.sysmindassignment.MarvelArchitecture;

import android.app.Application;
import android.os.AsyncTask;

import com.tech.sysmindassignment.NoteDataBase.MyDataBase;

import java.util.List;

import androidx.lifecycle.LiveData;


public class MarvelRepository {

        private MarvelDao marvelDao;
        private LiveData<List<MarvelModelEntity>> allMarvel;

        public MarvelRepository(Application application) {
            MyDataBase myDataBase = MyDataBase.getInstance(application);
            marvelDao = myDataBase.marvelDao();
            allMarvel = marvelDao.getAllMarvel();
        }

        public void insert(MarvelModelEntity marvelModelEntity) {
            new InsertMarvelAsyncTask(marvelDao).execute(marvelModelEntity);
        }

        public void update(MarvelModelEntity marvelModelEntity) {
            new UpdateMarvelAsyncTask(marvelDao).execute(marvelModelEntity);
        }

        public void delete(MarvelModelEntity marvelModelEntity) {
            new DeleteMarvelAsyncTask(marvelDao).execute(marvelModelEntity);
        }

        public void deleteAllMarvel() {
            new DeleteAllMarvelAsyncTask(marvelDao).execute();
        }

        public LiveData<List<MarvelModelEntity>> getAllMarvel() {
            return allMarvel;
        }

        private static class InsertMarvelAsyncTask extends AsyncTask<MarvelModelEntity, Void, Void> {
            private MarvelDao marvelDao;

            private InsertMarvelAsyncTask(MarvelDao marvelDao) {
                this.marvelDao = marvelDao;
            }

            @Override
            protected Void doInBackground(MarvelModelEntity... marvelModelEntities) {
                marvelDao.insert(marvelModelEntities[0]);
                return null;
            }
        }

        private static class UpdateMarvelAsyncTask extends AsyncTask<MarvelModelEntity, Void, Void> {
            private MarvelDao marvelDao;

            private UpdateMarvelAsyncTask(MarvelDao marvelDao) {
                this.marvelDao = marvelDao;
            }

            @Override
            protected Void doInBackground(MarvelModelEntity... marvelModelEntities) {
                marvelDao.update(marvelModelEntities[0]);
                return null;
            }
        }

        private static class DeleteMarvelAsyncTask extends AsyncTask<MarvelModelEntity, Void, Void> {
            private MarvelDao marvelDao;

            private DeleteMarvelAsyncTask(MarvelDao marvelDao) {
                this.marvelDao = marvelDao;
            }

            @Override
            protected Void doInBackground(MarvelModelEntity... marvelModelEntities) {
                marvelDao.delete(marvelModelEntities[0]);
                return null;
            }
        }

        private static class DeleteAllMarvelAsyncTask extends AsyncTask<Void, Void, Void> {
            private MarvelDao marvelDao;

            private DeleteAllMarvelAsyncTask(MarvelDao marvelDao) {
                this.marvelDao = marvelDao;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                marvelDao.deleteAllMarvel();
                return null;
            }
        }
}
