package com.endergeek.rookie.acontact4contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by wangsenhui on 11/1/16.
 * 此应用为纯粹 Content Provider实例，无数据，具体实例参见 A SQLite Database Demo 和 A Provider4Database Test
 */
public class MyProvider extends ContentProvider{

    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;

    public static final String AUTHORITY = "com.endergeek.rookie.acontact4contentproviderdemo";
    private static UriMatcher uriMatcher;

    // 创建UriMatcher实例
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "table1", TABLE1_DIR);
        uriMatcher.addURI(AUTHORITY, "table1/#", TABLE1_ITEM);
        uriMatcher.addURI(AUTHORITY, "table2", TABLE2_DIR);
        uriMatcher.addURI(AUTHORITY, "table2/#", TABLE2_ITEM);
    }

    /**
     * NOTE: 只有当存在ContentResolver尝试访问程序中的数据时，才会初始化
     * 初始化时调用，完成对数据库的创建和升级
     * @return true初始化成功
     */
    @Override
    public boolean onCreate() {
        return false;
    }

    /**
     * 根据传入的URI返回响应的MIME类型
     * URI: content://com.package.name/table_name/[id_number|*|#]
     * 对应MIME组成（三部分）:
     * vnd.android.cursor.dir/vnd.<authority>.<path>    // 内容URI以路径结尾
     * vnd.android.cursor.item/vnd.<authority>.<path>   // 内容URI以id结尾
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.endergeek.rookie.acontact4contentproviderdemo.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.endergeek.rookie.acontact4contentproviderdemo.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.endergeek.rookie.acontact4contentproviderdemo.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.com.endergeek.rookie.acontact4contentproviderdemo.table2";
            default:
                break;
        }
        return null;
    }

    /**
     * 详细实践参考 A SQLiteDatabase Demo
     * String bookId = uri.getPathSegments().get(1); 将内容URI权限（AUTHORITY）之后的部分以“/”划分，1为id,0为路径
     */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // 对传入的uri对象匹配
        switch (uriMatcher.match(uri)) {
            // 查询表1中的所有数据
            case TABLE1_DIR:
                break;
            // 查询表1中的单条数据
            case TABLE1_ITEM:
                break;
            // 查询表2中的所有数据
            case TABLE2_DIR:
                break;
            // 查询表2中的单条数据
            case TABLE2_ITEM:
                break;
            default:
                break;
        }
        return null;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }
}
