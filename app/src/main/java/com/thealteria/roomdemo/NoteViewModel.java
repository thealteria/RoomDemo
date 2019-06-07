package com.thealteria.roomdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

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
    void insert(Note note) {
        //we need to perform these operations in non-UI thread we use AsyncTask
        new InsertAsyncTask(noteDao).execute(note);
    }

    LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel destroyed");
    }

    private static class InsertAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        UpdateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
}
