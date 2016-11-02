package com.endergeek.rookie.asqlitedatabasedemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * 通过内容提供其具有跨程序共享数据的能力: AndroidManifest.xml + DatabaseProvider
 * Created by wangsenhui on 11/1/16.
 */
public class DatabaseProvider extends ContentProvider{

    public static final int BOOK_DIR = 0;

    public static final int BOOK_ITEM = 1;

    public static final int CATEGORY_DIR = 2;

    public static final int CATEGORY_ITEM = 3;

    public static final String AUTHORITY = "com.endergeek.rookie.asqlitedatabasedemo.provider";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir";
    public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item";

    private static UriMatcher uriMatcher;

    private MyDatabaseHelper myDatabaseHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        myDatabaseHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 2);
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return CONTENT_TYPE + "/vnd." +  AUTHORITY + ".book";
            case BOOK_ITEM:
                return CONTENT_TYPE_ITEM + "/vnd." +  AUTHORITY + ".book";
            case CATEGORY_DIR:
                return CONTENT_TYPE + "/vnd." +  AUTHORITY + ".category";
            case CATEGORY_ITEM:
                return CONTENT_TYPE_ITEM + "/vnd." +  AUTHORITY + ".category";
        }
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // 查询数据
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = sqLiteDatabase.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = sqLiteDatabase.query("Book", projection, "id = ?", new String[]{ bookId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = sqLiteDatabase.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId= uri.getPathSegments().get(1);
                cursor = sqLiteDatabase.query("Category", projection, "id = ?", new String[] {categoryId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // 添加数据
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = sqLiteDatabase.insert("Book", null, contentValues);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book/" + newBookId);
                break;
            case CATEGORY_DIR:
                break;
            case CATEGORY_ITEM:
                long newCategoryId = sqLiteDatabase.insert("Category", null, contentValues);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/category/" + newCategoryId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // 删除数据
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deletedRows = sqLiteDatabase.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deletedRows = sqLiteDatabase.delete("Book", "id = ?", new String[] {bookId});
                break;
            case CATEGORY_DIR:
                deletedRows = sqLiteDatabase.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deletedRows = sqLiteDatabase.delete("Category", "id = ?", new String[] {categoryId});
                break;
        }
        return deletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selction, String[] selectionArgs) {
        // 更新数据
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updatedRows = sqLiteDatabase.update("Book", contentValues, selction, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = sqLiteDatabase.update("Book", contentValues, "id = ?", new String[] {bookId});
                break;
            case CATEGORY_DIR:
                updatedRows = sqLiteDatabase.update("Category", contentValues, selction, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updatedRows = sqLiteDatabase.update("Category", contentValues, "id = ?", new String[] {categoryId});
                break;
        }
        return updatedRows;
    }
}
