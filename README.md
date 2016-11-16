
# The First Line of Android Code

``Android``开发的一些代码示例，由易到难，由简单到复杂的实现，《第一行代码》中的实现。测试环境：``Android Studio 2.0``，``API 23``及更高

### Demo List
 1. [An Activity LifeCycle Demo](https://github.com/sennhviwang/Android-Learn-Journey#an-activity-lifecycle-demo)
 - [An UI Widget Demo](https://github.com/sennhviwang/Android-Learn-Journey#an-ui-widget-demo)
 - [An UI Custom View Demo](https://github.com/sennhviwang/Android-Learn-Journey#an-ui-custom-view-demo)
 - [A List View Demo](https://github.com/sennhviwang/Android-Learn-Journey#a-listview-demo)
 - [An UI Best Practice](https://github.com/sennhviwang/Android-Learn-Journey#an-ui-best-practice)
 - [A Fragment Test Demo](https://github.com/sennhviwang/Android-Learn-Journey#a-fragment-test-demo)
 - [A Fragment Best Practice](https://github.com/sennhviwang/Android-Learn-Journey#a-fragment-best-practice)
 - [A Broadcast Test Demo](https://github.com/sennhviwang/Android-Learn-Journey#a-broadcast-test-demo)
 - [A Broadcast Best Practice](https://github.com/sennhviwang/Android-Learn-Journey#a-broadcast-best-practice)
 - [A Data Persistence 4 File Demo](https://github.com/sennhviwang/Android-Learn-Journey#a-data-persistence-4-file-demo)
 - [A Data Persistence 4 SharedPreferences](https://github.com/sennhviwang/Android-Learn-Journey#a-data-persistence-4-sharedpreferences)
 - [A SQLite Database Demo(medium)](https://github.com/sennhviwang/Android-Learn-Journey#a-sqlite-database-demomedium)


###### [An Activity LifeCycle Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/AnActivityLifeCycleDemo)
 - 使用普通活动和对话框活动以及4种不同的启动模式加深对活动的生命周期理解，四种启动模式为：``standard``,``singleTop``,``singleTask``和``singleInstance``.为了更好理解四种模式，项目根目录中添加了相应的截图以及可能有帮助的文字说明
 - ``Key Words``: requestWindowFeature, savedInstanceState, onSaveInstanceState,  setOnClickListener,onCreate, onStart, onResume, onRestart, onPause, onStop, onDestory, @android:style/Theme.Dialog, launchMode
 - [Managing the Activity Lifecycle](https://developer.android.com/training/basics/activity-lifecycle/starting.html)
 - [First Code of Android-LifeCycle](https://book.douban.com/subject/25942191/)
 
###### [An UI Widget Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/AnUIWidgetDemo)
 - 演示一些常见组件和布局的UI示例，在``onClick``中使用``switch``集中处理点击事件，``NOTE``：务必在``AndroidManifest.xml``文件中注册你的``activity``，显式``Intent``并不需要在``intent-filter``中声明太多东西
 - ``Key Words``: onClick, ProgressBar, setProgress, setImageResource, setVisibility, getVisibility, AlertDialog.Builder, setTitle, setMessage, setCancelable, setPositiveButton, setNegativeButton, show, ProgressDialog, layout_alignParentLeft|Bottom|Right|Top, layout_toRight|Left|Bottom|TopOf, layout_weight, FrameLayout, TableLayout, android:stretchColumns, layout_span
 - [First Code of Android-UI](https://book.douban.com/subject/25942191/)

###### [An UI Custom View Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/AnUICustomViewDemo)
 - 简单实现自定义``View``，演示如何构造和调用基于``LinearLayout``的自定义布局
 - ``Key Words``: include, custom view, extends LinearLayout, LayoutInflater.from(context).inflate(sub_layout, this), ((Activity) getContext())
 - [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html)
 - [Difference between getContext(), getApplicationContext(), getBaseContext(), this](http://stackoverflow.com/questions/10641144/difference-between-getcontext-getapplicationcontext-getbasecontext-and)

###### [A ListView Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/AListViewDemo)
 - ``Adapter``第一例子，使用自定义和原始``adapter``的范例，实体类为``Fruit``
 - ``Key Words``: Abstract Class, List, Entity Class, ArrayAdapter, setAdapter, getItem, getView, LayoutInflater.from(getContext()).inflate(id, null), ViewHolder
 - [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html)

###### [An UI Best Practice](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/AnUIBestPractice)
 - ``Adapter``的第二例子，使用``draw9patch``制作``Nine-Patch``图片，左&上边框表示拉伸区域，右&下边框表示内容放置区域。
 - ``Key Words``: Abstract Class, List, Entity Class, ArrayAdapter, setAdapter, getItem, getView, LayoutInflater.from(getContext()).inflate(id, null), ViewHolder, setTag, getTag, notifyDataSetChanged, setSelection
 - [BaseAdapter](https://developer.android.com/reference/android/widget/BaseAdapter.html)

###### [A Fragment Test Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/AFragmentTestDemo)
 - 替换fragment的例子，简述fragment生命周期，fragment与activity之间通信
 - ``Key Words``: onAttach, onCreate, onCreateView, onActivityCreated, onStart, onResume, onPause, onStop, onDestroyView, onDestroy, onDetach, FragmentTransaction, getSupportFragmentManager, getActivity
 - [Fragment Lifecycle](https://developer.android.com/guide/components/fragments.html)

###### [A Fragment Best Practice](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/AFragmentBestPractice)
 - listview+fragment+adapter结合使用，手机与平板适配demo
 - ``Key Words``: activity_main.xml(平板适配), onAttach, onCreateView, onActivityCreated, onItemClick, actionStart, android:name, tools:layout, android:scrollbars, tv.setMovementMethod(new ScrollingMovementMethod()), standard broadcast receiver
 - [Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/fragment-ui.html)

###### [A Broadcast Test Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/ABroadcastTestDemo)
 - 全局广播与本地广播，标准广播与有序广播
 - ``Key Words``: IntentFilter, NetworkChangeReceiver, LocalBroadcastManager, sendBroadcast, sendOrderedBroadcast, addAction, registerReceiver, unregisterReceiver, ConnectivityManager, NetworkInfo, BroadReceiver, Custom Toast, abortBroadcast, receiver, android.intent.action.BOOT_COMPLETED, anroid:priority
 - [sendBroadcast](https://developer.android.com/reference/android/content/Context.html#sendBroadcast(android.content.Intent, java.lang.String))
 - [Manifest.permission](https://developer.android.com/reference/android/Manifest.permission.html)

###### [A Broadcast Best Practice](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/ABroadcastBestPractice)
 - 广播机制的最佳实践-强制下线功能，此demo突然涉及Android 6.0 Draw over other apps高级权限，不能像一般权限那样在运行时请求获取，需要引导至开关处打开
 - ``Key Words``: Intent("CUSTOM_STRING_IN_MANIFEST_BROADCAST_RECEIVER), BaseActivity, ActivityCollector, Build.VERSION.SDK_INT, Settings.ACTION_MANAGE_OVERLAY_PERMISSION, ``<receiver>``, ``List<Activity>``, AlertDialog.Builder, intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), dialogBuilder.create, alertDialog.getWindow().setType, TYPE_SYSTEM_ALERT
 - [AlertDialog](https://developer.android.com/reference/android/app/AlertDialog.html)
 - [Dialog](https://developer.android.com/reference/android/app/Dialog.html)

###### [A Data Persistence 4 File Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/ADataPersistence4FileDemo)
 - 向Internal Storage中写入文件，快速请求权限（DEMO专用）
 - ``Key Words``:TextUtils.isEmpty, setSelection, FileOutputStream, openFileOutput, OutputStreamWriter, BufferedWriter, BufferedWriter.write, BufferedWriter.close, FileInputStream, StringBuilder, StringBuilder.append, openFileInput, InputStreamReader, BufferedReader, BufferedReader.readLine, BufferedReader.close, ActivityCompat.requestPermissions(activity, PERMISSION_ARRAY, REQUEST_CODE)
 - [Saving files](https://developer.android.com/training/basics/data-storage/files.html)
 - [FileOutputStream](https://developer.android.com/reference/java/io/FileOutputStream.html)
 - [FileInputStream](https://developer.android.com/reference/java/io/FileInputStream.html)

###### [A Data Persistence 4 SharedPreferences](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/ADataPersistence4SharedPreferences)
 - 使用SharedPreferences存储，读取数据：三种获取SharedPreferences对象的方法，存储数据的三步，读取数据的两步，结合广播最佳实践，实现登录保存密码例子,详见项目内
 - ``Key Words``:getSharedPreferences, getPreferences, getDefaultSharedPreferences, edit(), putString, putInt, putBoolean, commit, getSharedPreferences(FILE_NAME, MODE), SharedPreferences, getString(KEY, DEFAULT_VALUE),
CheckBox, setChecked(true), isChecked() 
 - [Shared preferences](https://developer.android.com/training/basics/data-storage/shared-preferences.html)

###### [A SQLite Database Demo(medium)](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/ASQLiteDatabaseDemo)
 - ``SQLite``数据库实例，增删查改，以及使用事务，数据库的创建和升级方式，使用``adb shell``打开并进入数据库目录:``/data/data/PACKAGE_NAME/databases/``，``sqlite3 BookStore.db``查看数据库，``.table``查看表， ``.schema``查看建表语句， ``.exit``退出， 此例子不是最好实践，建议参考[SQLite OpenHelper Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/SQLiteOpenHelperDemo)
 - ``Key Words``:SQLiteOpenHelper构造函数(context, name, factory, version), onCreate, onUpgrade, getWritableDatabase, SQLiteDatabase, myDatabaseHelper.getWritableDatabase();, ContentValues, contentValues.put(KEY, VALUE);, sqliteDatabase.insert(TABLE, NULLABLECOLUMN, MAP_VALUE);, sqliteDatabase.update(TABLE, MAP_VALUE, SELECTION, SELECTION_ARGS);, sqliteDatabase.delete(TABLE, SELECTION, SELECTION_ARGS);, sqliteDatabase.query(TABLE, COLUMN, WHERE_COLUMN, WHERE_VALUE, NO_GROUP_ROW, NO_FILTER_ROW, SORTED);, cursor.moveToFirst, cursor.getString(cursor.getColumnIndex(VALUE));, cursor.moveToNext
cursor.close, sqliteDatabase.beginTransaction, sqliteDatabase.setTransactionSuccessful, sqliteDatabase.endTransaction
 - [Databases](https://developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow)
 - [SQLiteDatabase](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html)


时间赶，快速编辑，待整理

[Content Provider详解](http://codingnow.cn/android/1078.html)

[通知 Guide](https://developer.android.com/guide/topics/ui/notifiers/notifications.html)
[Notification API](https://developer.android.com/reference/android/app/Notification.html)
[Notification.Builder](https://developer.android.com/reference/android/app/Notification.Builder.html)
[PendingIntent](https://developer.android.com/reference/android/app/PendingIntent.html)
[NotificationManager](https://developer.android.com/reference/android/app/NotificationManager.html)

[SMSMessage](https://developer.android.com/reference/android/telephony/SmsMessage.html)
[BroadcastReceiver](https://developer.android.com/reference/android/content/BroadcastReceiver.html) getResultCode()

java.lang.NullPointerException: Attempt to get length of null array sendTextMessage时，要检查每个值存在不为null

[ContentResolver](https://developer.android.com/reference/android/content/ContentResolver.html)This class provides applications access to the content model.

[MediaPlayer Guide](https://developer.android.com/guide/topics/media/mediaplayer.html)
[MediaPlayer Reference](https://developer.android.com/reference/android/media/MediaPlayer.html)

[java.lang.IllegalStateException](http://stackoverflow.com/questions/7816551/java-lang-illegalstateexception-what-does-it-mean)

before calling player.start(), you have to run setDataSource() and prepare(), according to the State Diagram of the MediaPlayer reference.

mediaplayer state machine

[videoview](https://developer.android.com/reference/android/widget/VideoView.html)

Thread

[Service](https://developer.android.com/reference/android/app/Service.html),
[IntentService](https://developer.android.com/reference/android/app/IntentService.html)

[AlarmManager StackOverflow](http://stackoverflow.com/questions/38094420/android-alarmmanager-setexact-is-not-exact)

getBroadcast
[进程和线程](https://developer.android.com/guide/components/processes-and-threads.html#Processes)

[HttpClient移除](https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html#behavior-apache-http-client)

[NGINX测试环境配置]

XML解析 Pull/SAX
JSON解析 JSONObject/Gson

回调接口

[FileNotFound Exception:setDoOutput 405](http://stackoverflow.com/questions/9365829/filenotfoundexception-for-httpurlconnection-in-ice-cream-sandwich)

LocationManager未测试，更多LBS查看相应API

Sensor,GPS,Light,Magnetic,Accelerator

RotateAnimation

[Vibrator](https://developer.android.com/reference/android/os/Vibrator.html)


[BaseBundle](https://developer.android.com/reference/android/os/BaseBundle.html)

[Parcelable & Serializable](http://www.cnblogs.com/renqingping/archive/2012/10/25/Parcelable.html)

[android:label](https://developer.android.com/guide/topics/manifest/activity-element.html#label)

[java.lang.NullPointerException: println needs a message](http://stackoverflow.com/questions/11449589/java-lang-nullpointerexception-println-needs-a-message)

Attempt to invoke virtual method 'java.lang.String java.lang.Object.toString()' on a null object reference List``<String>``中元素为null

[打包签名](http://blog.csdn.net/linghu_java/article/details/6701666)未签名会安装包解析失败

[Manifest.xml合并](https://developer.android.com/studio/build/manifest-merge.html)