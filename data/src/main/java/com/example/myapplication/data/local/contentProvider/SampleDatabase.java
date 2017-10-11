package com.example.myapplication.data.local.contentProvider;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.myapplication.util.Constant;

/**
 * Created by CPU11112-local on 10/11/2017.
 */
@Database(entities = {Cheese.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase {

    public static SampleDatabase getSampleDatabase(Context context) {
        SampleDatabase sampleDatabase = Room
                .databaseBuilder(context, SampleDatabase.class, "ex")
                .build();

        sampleDatabase.populateInitialData();
        return sampleDatabase;
    }

    // used to control the database
    public abstract CheeseDao cheese();


    /**
     * Inserts the dummy data into the database if it is currently empty.
     */
    private void populateInitialData() {
        // if not have any cheese in the table
        if (cheese().count() == 0) {
            Cheese cheese = new Cheese();
            beginTransaction();
            try {
                for (int i = 0; i < Constant.CHEESES.length; i++) {
                    cheese.name = Constant.CHEESES[i];
                    cheese().insert(cheese);
                }

                setTransactionSuccessful();
            } finally {
                endTransaction();
            }
        }
    }
}
