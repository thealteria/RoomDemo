package com.thealteria.roomdemo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Note.class, version = 1)
//this DB class has to includes list of entities associate with it
public abstract class NoteRoomDatabase extends RoomDatabase {
    //the requirement of the DB class for Room is that it's need to be abstract

    public abstract NoteDao noteDao();

    private static volatile NoteRoomDatabase noteRoomInstance;
    /*another thing we need to take care of is that we should have this a single instance of DB and to ensure that
    our DB should be singleton*/

    static NoteRoomDatabase getNoteRoomDatabase(final Context context) {
        if (noteRoomInstance == null) {
            synchronized (NoteRoomDatabase.class) {
                if (noteRoomInstance == null) {
                    noteRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_database").build();
                }
            }
        }
        return noteRoomInstance;
    }
}
