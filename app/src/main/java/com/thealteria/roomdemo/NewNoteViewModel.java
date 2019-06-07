package com.thealteria.roomdemo;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

public class NewNoteViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase database;

    public NewNoteViewModel(@NonNull Application application) {
        super(application);

        database = NoteRoomDatabase.getNoteRoomDatabase(application);
        noteDao = database.noteDao();
    }

    LiveData<Note> getNote(String noteId) {
        return noteDao.getNote(noteId);
    }
}
