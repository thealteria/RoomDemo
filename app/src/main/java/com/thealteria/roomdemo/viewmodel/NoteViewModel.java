package com.thealteria.roomdemo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.thealteria.roomdemo.model.Note;
import com.thealteria.roomdemo.interfaces.NoteDao;
import com.thealteria.roomdemo.database.NoteRoomDatabase;

import java.util.List;

public class NoteViewModel extends AndroidViewModel { //using AndroidViewModel so that we can get the application context
    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        NoteRoomDatabase noteRoomDatabase = NoteRoomDatabase.getNoteRoomDatabase(application);
        /*fetching the instance of DB which uses context.
        That's why we used AndroidViewModel*/
        noteDao = noteRoomDatabase.noteDao();
        mAllNotes = noteDao.getAllNotes();
    }

    //wrapper for insert operation
    public void insert(Note note) {
        //we need to perform these operations in non-UI thread we use AsyncTask
        new InsertAsyncTask(noteDao).execute(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void update(Note note) {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteAsyncTask(noteDao).execute(note);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel destroyed");
    }


    private static class OperationsAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao mAsyncTaskDao;

        OperationsAsyncTask(NoteDao noteDao) {
            this.mAsyncTaskDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            return null;
        }
    }

    private static class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends OperationsAsyncTask {

        DeleteAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }
}
