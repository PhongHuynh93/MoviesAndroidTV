package com.gabilheri.moviestmdb.data.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myapplication.data.local.contentProvider.Cheese;
import com.example.myapplication.data.local.contentProvider.CheeseDao;
import com.gabilheri.moviestmdb.App;
import com.gabilheri.moviestmdb.util.Constant;

import javax.inject.Inject;

import static com.example.myapplication.util.Constant.TABLE_NAME;
import static com.gabilheri.moviestmdb.util.Constant.AUTHORITY;
import static com.gabilheri.moviestmdb.util.Constant.CODE_CHEESE_DIR;
import static com.gabilheri.moviestmdb.util.Constant.CODE_CHEESE_ITEM;

/**
 * Created by CPU11112-local on 10/11/2017.
 */

public class SampleContentProvider extends ContentProvider {
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, TABLE_NAME, CODE_CHEESE_DIR);
        MATCHER.addURI(AUTHORITY, TABLE_NAME + "/*", CODE_CHEESE_ITEM);
    }

    @Inject
    Context mContext;
    @Inject
    CheeseDao mCheeseDao;

    // step - this methdd used to create the sqlite database
    @Override
    public boolean onCreate() {
        App.instance().appComponent().inject(this);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        @Constant.ContentProviderCode final int code = MATCHER.match(uri);
        switch (code) {
            case Constant.CODE_CHEESE_DIR:
            case Constant.CODE_CHEESE_ITEM:
                if (mContext == null) {
                    return null;
                }
                final Cursor cursor;
                if (code == CODE_CHEESE_DIR) {
                    cursor = mCheeseDao.selectAll();
                } else {
                    cursor = mCheeseDao.selectById(ContentUris.parseId(uri));
                }
                cursor.setNotificationUri(mContext.getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CODE_CHEESE_DIR:
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;
            case CODE_CHEESE_ITEM:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (MATCHER.match(uri)) {
            case CODE_CHEESE_DIR:
                if (mContext == null) {
                    return null;
                }
                final long id = mCheeseDao
                        .insert(Cheese.fromContentValues(contentValues));
                mContext.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CODE_CHEESE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case CODE_CHEESE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_CHEESE_ITEM:
                if (mContext == null) {
                    return 0;
                }
                final int count = mCheeseDao
                        .deleteById(ContentUris.parseId(uri));
                mContext.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case CODE_CHEESE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_CHEESE_ITEM:
                if (mContext == null) {
                    return 0;
                }
                final Cheese cheese = Cheese.fromContentValues(contentValues);
                cheese.id = ContentUris.parseId(uri);
                final int count = mCheeseDao
                        .update(cheese);
                mContext.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
