# A SQLite Database Demo

### Overview 

``SQLite``数据库实例，增删查改，以及使用事务，数据库的创建和升级方式，使用``adb shell``打开并进入数据库目录:``/data/data/PACKAGE_NAME/databases/``，``sqlite3 BookStore.db``查看数据库，``.table``查看表， ``.schema``查看建表语句， ``.exit``退出， 此例子不是最好实践，建议参考[SQLite OpenHelper Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/SQLiteOpenHelperDemo)

### Table of contents

 1. SQLiteOpenHelper
    - 构造函数(context, name, factory, version)
    - onCreate创建数据库
    - onUpgrade升级数据库，使用switch case以增量更改
 - getWritableDatabase，若不存在，会调用onCreate方法，存在则不调用
 - ``SQLiteDatabase sqliteDatabase = myDatabaseHelper.getWritableDatabase();``
 - ``ContentValues contentValues = new ContentValues();``
 - ``contentValues.put(KEY, VALUE);``
 - ``sqliteDatabase.insert(TABLE, NULLABLECOLUMN, MAP_VALUE);``
 - ``sqliteDatabase.update(TABLE, MAP_VALUE, SELECTION, SELECTION_ARGS);``
 - ``sqliteDatabase.delete(TABLE, SELECTION, SELECTION_ARGS);``
 - ``sqliteDatabase.query(TABLE, COLUMN, WHERE_COLUMN, WHERE_VALUE, NO_GROUP_ROW, NO_FILTER_ROW, SORTED);``
 - ``cursor.moveToFirst``
 - ``cursor.getString(cursor.getColumnIndex(VALUE));``
 - ``cursor.moveToNext``
 - ``cursor.close``
 - ``sqliteDatabase.beginTransaction``
 - ``sqliteDatabase.setTransactionSuccessful``
 - ``sqliteDatabase.endTransaction``
 