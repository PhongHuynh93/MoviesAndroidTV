package com.example.myapplication.data.local.contentProvider;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.myapplication.util.Constant;

/**
 * Created by CPU11112-local on 10/11/2017.
 */
@Entity(tableName = Constant.TABLE_NAME)
public class Cheese {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = Constant.COLUMN_ID)
    public long id;

    @ColumnInfo(name = Constant.COLUMN_NAME)
    public String name;
}
