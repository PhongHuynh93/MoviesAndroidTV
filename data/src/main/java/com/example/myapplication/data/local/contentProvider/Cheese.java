package com.example.myapplication.data.local.contentProvider;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

import static com.example.myapplication.util.Constant.COLUMN_ID;
import static com.example.myapplication.util.Constant.COLUMN_NAME;
import static com.example.myapplication.util.Constant.TABLE_NAME;

/**
 * Created by CPU11112-local on 10/11/2017.
 */
@Entity(tableName = TABLE_NAME)
public class Cheese {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_NAME)
    public String name;

    /**
     * Create a new {@link Cheese} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain {@link #COLUMN_NAME}.
     * @return A newly created {@link Cheese} instance.
     */
    public static Cheese fromContentValues(ContentValues values) {
        final Cheese cheese = new Cheese();
        if (values.containsKey(COLUMN_ID)) {
            cheese.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            cheese.name = values.getAsString(COLUMN_NAME);
        }
        return cheese;
    }
}
