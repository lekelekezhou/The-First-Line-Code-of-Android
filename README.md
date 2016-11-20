
# The First Line of Android Code

``Android``开发的28个代码示例，由简到繁，依次实现《第一行代码》中代码示例。

代码开发测试环境：``Android Studio 2.1``，``API 14-23``

---

### 示例列表
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
 - [A SQLite Database Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-sqlite-database-demo)
 - [A Contact 4 Content Provider Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-contact-4-content-provider-demo)
 - [A Provider 4 Database Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-provider-4-database-test)
 - [A Notification Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-notification-test)
 - [A SMS Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-sms-test)
 - [A Choose Pic Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-choose-pic-test)
 - [A Audio Player Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-audio-player-demo)
 - [A Video Player Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-video-player-demo)
 - [A Thread Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-thread-demo)
 - [A Service Test Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-service-test-demo)
 - [A Service Best Practice](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-service-best-practice)
 - [A WebView Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-webview-test)
 - [A Network Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-network-test)
 - [A Location Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-location-test)
 - [A Sensor Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-sensor-test)
 - [A Skillful Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-skillful-demo)
 - [A Weather Final Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code#a-weather-final-demo)

---

### 示例介绍

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

###### [A SQLite Database Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/ASQLiteDatabaseDemo)
 - ``SQLite``数据库实例，增删查改，以及使用事务，数据库的创建和升级方式，使用``adb shell``打开并进入数据库目录:``/data/data/PACKAGE_NAME/databases/``，``sqlite3 BookStore.db``查看数据库，``.table``查看表， ``.schema``查看建表语句， ``.exit``退出，并通过``content provider``提供数据访问接口给``A Provider 4 Database Test``操作, 此例子不是最好实践，建议参考[SQLite OpenHelper Demo](https://github.com/sennhviwang/Android-Learn-Journey/tree/master/SQLiteOpenHelperDemo)
 - ``Key Words``:SQLiteOpenHelper构造函数(context, name, factory, version), onCreate, onUpgrade, getWritableDatabase, SQLiteDatabase, myDatabaseHelper.getWritableDatabase();, ContentValues, contentValues.put(KEY, VALUE);, sqliteDatabase.insert(TABLE, NULLABLECOLUMN, MAP_VALUE);, sqliteDatabase.update(TABLE, MAP_VALUE, SELECTION, SELECTION_ARGS);, sqliteDatabase.delete(TABLE, SELECTION, SELECTION_ARGS);, sqliteDatabase.query(TABLE, COLUMN, WHERE_COLUMN, WHERE_VALUE, NO_GROUP_ROW, NO_FILTER_ROW, SORTED);, cursor.moveToFirst, cursor.getString(cursor.getColumnIndex(VALUE));, cursor.moveToNext
cursor.close, sqliteDatabase.beginTransaction, sqliteDatabase.setTransactionSuccessful, sqliteDatabase.endTransaction
 - [Databases](https://developer.android.com/training/basics/data-storage/databases.html#UpdateDbRow)
 - [SQLiteDatabase](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html)


###### [A Contact 4 Content Provider Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AContact4ContentProviderDemo)
 - 内容提供器示例，能使用现有的内容提供器读取和操作响应的程序中的数据，也可以通过创建内容提供器为程序自身数据提供外部访问接口。本例通过内置接口，访问联系人数据并以listview显示，内容提供器作为代码实例并不参与执行
 - ``Key Words``: Context.getContentResolver(), URI(scheme://authority/table/path), Uri.parse, CRUD操作, UriMatcher, getType, getPathSegments().get(1)(1为id，0为路径), ``vnd.<authority>.<path>``
 - [Content Provider详解](http://codingnow.cn/android/1078.html)
 - [ContactsContract容器](https://developer.android.com/reference/android/provider/ContactsContract.CommonDataKinds.html)

###### [A Provider 4 Database Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AProvider4DatabaseTest)
 - 此应用为 A SQLite Database Demo 关于ContentProvider 的搭配测试应用，测试为数据提供外部访问接口
 - ``Key Words``: Context.getContentResolver, CRUD, ContentValues, getPathSegments, ``<provider>``, ``android:exported``, 
 - [Uri](https://developer.android.com/reference/android/net/Uri.html)
 - [UriMatcher](https://developer.android.com/reference/android/content/UriMatcher.html)
 - [ContentResolver](https://developer.android.com/reference/android/content/ContentResolver.html)This class provides applications access to the content model.

###### [A Notification Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/ANotificationTest)
 - 通知消息示例，通过按钮触发可取消通知，并振动手机。通知既可以在活动、广播接收器、服务中创建。书上此例使用方法已经过时，另由于此例中对通知的使用比较麻烦，不易理解，更好参考``A Service Test Demo``
 - ``Key Words``: NotificationManager, Context.getSystemService, Uri.fromFile, Context.NOTIFICATION_SERVICE, NotificationCompat.Builder, TaskStackBuilder, PendingIntent.getActivity, addParentStack, addNextIntent, setContentIntent
 - [通知 Guide](https://developer.android.com/guide/topics/ui/notifiers/notifications.html)
 - [Notification API](https://developer.android.com/reference/android/app/Notification.html)
 - [Notification.Builder](https://developer.android.com/reference/android/app/Notification.Builder.html)
 - [PendingIntent](https://developer.android.com/reference/android/app/PendingIntent.html)
 - [NotificationManager](https://developer.android.com/reference/android/app/NotificationManager.html)

###### [A SMS Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/ASMSTest)
 - 发送短信示例，需要请求相关权限， 通过IntentFilter添加Action以接收短信发送完毕广播以及获得短信接收广播：定义内部广播接收器，获取发送状态和接收状态广播。短信pdu相关在代码注释已解释。友情提醒：本例已经测试成功，但由于某方面原因，延迟了30分钟之后才确认收到，因此不建议尝试
 - ``Key Words``: IntentFilter, BroadcastReceiver, SendStatusReceiver, MessageReceiver, PendingIntent, sendTextMessage, Bundle对象， pdus, getOriginatingAddress, getMessageBody, getResultCode(), registerReceiver, unregisterReceiver
 - [SMSMessage](https://developer.android.com/reference/android/telephony/SmsMessage.html)
 - [BroadcastReceiver.getResultCode()](https://developer.android.com/reference/android/content/BroadcastReceiver.html) 
 - ``Error``:java.lang.NullPointerException: Attempt to get length of null array sendTextMessage 时，要检查每个值存在不为null

###### [A Choose Pic Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AChoosePicTest)
 - 选择照片实例，也是解析外存文件路径的实例，功能包含拍照、裁剪、从图片库中选择，并显示结果在ImageView中，需要获取写外存权限，已打必要log。由于照片裁剪可能导致内存泄露，因此建议根据需求先对照片压缩再加载，更复杂例子参见： ``A content sharing demo``
 - ``Key Words``: File, Environment.getExternalStorageDirectory, exists, delete, createNewFile, startActivityForResult, onActivityResult, intent.setDataAndType, Bitmap, BitmapFactory.decodeStream, getContentResolver().openInputStream(uri), cursor.moveToFirst, MediaStore.Images.Media.DATA, (cursor != null && !cursor.isClosed), intent.getData, DocumentsContract.isDocumentUri, DocumentsContract.getDocumentId(uri),
 - [BitmapFactory](https://developer.android.com/reference/android/graphics/BitmapFactory.html)
 - [MediaStore](https://developer.android.com/reference/android/provider/MediaStore.html)
 - [DocumentsContract](https://developer.android.com/reference/android/provider/DocumentsContract.html)

###### [A Audio Player Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AChoosePicTest)
 - 音频播放实例，参考书中代码会报错，经过诊断，在资源prepare时授权操作还未进行，当授权完毕时，onPrepareListener由于得不到文件因此不会执行onPrepared，重写onRequestPermissionsResult获取权限请求结果状态码，不允许访问则抛出异常，允许即可播放音频。需要读取外存权限，因此会再三确认是否具有读取权限（权限请求部分未使用最直接方式，在onCreate时并没有来得及赋予权限，直到onResume才会再次确认并初始化播放器）
 - ``Key Words``: ``Start called in state 1``, File, getExternalStorageDirectory,  AudioManager.STREAM_MUSIC, MediaPlayer, setDataSource, prepare, start, pause, reset, stop, release, OnPreparedListener, setAudioStreamType
 - [MediaPlayer Guide](https://developer.android.com/guide/topics/media/mediaplayer.html)
 - [MediaPlayer Reference](https://developer.android.com/reference/android/media/MediaPlayer.html)
 - [java.lang.IllegalStateException](http://stackoverflow.com/questions/7816551/java-lang-illegalstateexception-what-does-it-mean)
 - [MediaPlayer state machine](https://developer.android.com/reference/android/media/MediaPlayer.html)
 - before calling player.start(), you have to run setDataSource() and prepare(), according to the State Diagram of the MediaPlayer reference.

###### [A Video Player Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AVideoPlayerDemo)
 - 视频播放示例，使用videoview，由于权限限制因此设置在跳转页播放，需要读取外存权限
 - ``Key Words``: OnPreparedListener, OnCompletionListener, OnErrorListener, SetMediaController, File, getExternalStorageDirectory, Uri.fromFile, setVideoURI, setOnPreparedListener, setOnCompletionListener, setOnErrorListener, requestFocus, start, pause, resume
 - [videoview](https://developer.android.com/reference/android/widget/VideoView.html)

###### [A Thread Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AThreadDemo)
 - 线程示例，更新UI元素应在主线程中进行，本例使用handler处理异步消息，也可以使用``AsyncTask<Params, Progress, Result>``，并在doInBackground()中执行耗时任务，onProgressUpdate()中执行UI操作，onPostExecute()中执行任务结束工作。相关：Message, Handler, MessageQueue, Looper, AsyncTask(不作引用)
 - ``Key Words``: Message, handler.sendMessage, handleMessage
 - [Communicating with UI Thread](https://developer.android.com/training/multiple-threads/communicate-ui.html)

###### [A Service Test Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AServiceTestDemo)
 - 服务示例，通过启动/停止，绑定/解绑服务实现前台通知（锁定通知），此例对于绑定/解绑服务，增加flag以判断服务是否存在，否则将出现Service not registered异常，由于服务代码默认在主线程中运行，因此如果在其中处理耗时逻辑，容易ANR，本应在Service的onStartCommand中开启子线程执行耗时逻辑，但服务一旦启动之后就会处于运行状态，必须调用stopService()或stopSelf()，由于容易忘记在run()方法中调用stopSelf()，因此可以创建一个异步的、会在运行结束后**自动停止**的服务IntentService, 通知的startForeground 属于Service，需要通过调用Service才可使用。一个服务一旦被启动或绑定了之后，会一直处于运行状态，必须要让以上俩个条件都不满足才能销毁服务。噢，别忘了注册～
 - ``Key Words``: ServiceConnection, onServiceConnected, onServiceDisconnected, startService, stopService, bindService, unbindService, Ｔhread.currentThread, IBinder, onBind, PendingIntent, getActivity, Notification, onStartCommand, onHandleIntent
 - [Service](https://developer.android.com/reference/android/app/Service.html)
 - [IntentService](https://developer.android.com/reference/android/app/IntentService.html)

###### [A Service Best Practice](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AServiceBestPractice)
 - 使用服务和广播接收器制作循环定时执行任务(此处是闹钟)示例，启动服务后延时发送广播，广播中再次启动服务。依然别忘了注册
 - ``Key Words``:　onBind, onStartCommand, AlarmManager, PendingIntent.getBroadcast=Context.sendBroadcast,
 - [AlarmManager StackOverflow](http://stackoverflow.com/questions/38094420/android-alarmmanager-setexact-is-not-exact)
 - [进程和线程](https://developer.android.com/guide/components/processes-and-threads.html#Processes)

###### [A WebView Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AWebViewTest)
 - WebView示例，需要INTERNET权限，简单获取webview布局并设置允许js以及webviewclient即可加载指定url，本例不作深入
 - ``Key Words``: WebView, getSettings, setJavaScriptEnabled, setWebViewClient, WebViewClient, loadUrl
 - [WebView](https://developer.android.com/reference/android/webkit/WebView.html)

###### [A Network Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/ANetworkTest)
 - 网络、XML、JSON解析、回调接口示例,通过主机搭建nginx服务器返回请求xml或json以供应用解析，发送Http请求两种方式: HttpClient和HttpURLConnection，从 Android 6.0开始 HttpClient被移除，若要使用需要在build.gradle引入``android { useLibrary 'org.apache.http.legacy'}``，解析XML: Pull 与 SAX 方式， 解析JSON: JSONObject 与 GSON，需要在app的build.gradle中引入``compile 'com.google.code.gson:gson:2.8.0'``， HttpURLConnection请求方式: 子线程处理(MainActivity中直接实现) 与 回调接口处理(HttpCallbackListener + HttpUtil)，注释对Handler等进行了工程方面解释
 - ``Key Words``: Handler, Message.obj, sendMessage, handleMessage, HttpURLConnection, setRequestMethod, setConnectTimeout, setReadTimeout, setDoInput(GET方法),setDoOutput(POST方法), getInputStream, InputStreamReader, BufferReader, StringBuilder, readline, Gson, JSONArray, SAXParserFactory, XMLReader, DefaultHandler(SAX解析方式，用法复杂，语义清晰，具体操作在:startDocument, startElement, characters, endElement, endDocument步骤中定义), XmlPullParserFactory, getEventType, XmlPullParser.START_TAG, XmlPullParser.END_TAG, nextText, next,
 - [HttpClient移除](https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html#behavior-apache-http-client)
 - [HttpURLConnection](https://developer.android.com/reference/java/net/HttpURLConnection.html)
 - [JSONArray](https://developer.android.com/reference/org/json/JSONArray.html)
 - [Gson](https://github.com/google/gson)
 - [XmlPullParserFactory](https://developer.android.com/reference/org/xmlpull/v1/XmlPullParserFactory.html)
 - [SAXParserFactory](https://developer.android.com/reference/javax/xml/parsers/SAXParserFactory.html)
 - [FileNotFound Exception:setDoOutput 405](http://stackoverflow.com/questions/9365829/filenotfoundexception-for-httpurlconnection-in-ice-cream-sandwich)
 - [Nginx测试环境配置](http://nginx.org/en/docs/beginners_guide.html)本例只需源码下载配置安装，指定index目录文件即可

###### [A Location Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/ALocationTest)
 - 地理位置示例，未做出测试，在``A Sensor Test``中也有给出简洁代码片段。当然，需要获取对应权限。
 - ``Key Words``: LocationListener, onLocationChanged, onStatusChanged, onProviderEnabled, onProviderDisabled, LocationManager, removeUpdates, requestLocationUpdates, getLastKnownLocation, getProviders, getSystemService
 - [LocationManager](https://developer.android.com/reference/android/location/LocationManager.html)
 - [LocationListener](https://developer.android.com/reference/android/location/LocationListener.html)

###### [A Sensor Test](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/ASensorTest)
 - 传感器示例（光照强度，加速度传感器，地磁传感器），包含地理位置获取，摇一摇振动（需要权限）
 - ``Key Words``: getSystemService, SensorManager, getRotationMatrix, getOrientation, RotationAnimation, setFillAfter, startAnimation, getDefaultSensor, Sensor.TYPE_LIGHT|ACCELEROMETER|MAGNETIC_FIELD, SensorEventListener, onSensorChanged, onAccuracyChanged, registerListener, SensorEvent.sensor.getType, Vibrator, LocationManager, onLocationChanged, onStatusChanged, onProviderEnabled, onProviderDisabled
 - [Vibrator](https://developer.android.com/reference/android/os/Vibrator.html)
 - [Sensor](https://developer.android.com/reference/android/hardware/Sensor.html)
 - [SensorEvent](https://developer.android.com/reference/android/hardware/SensorEvent.html)
 - [SensorEventListener](https://developer.android.com/reference/android/hardware/SensorEventListener.html)
 - [SensorManager](https://developer.android.com/reference/android/hardware/SensorManager.html)
 - [RotateAnimation](https://developer.android.com/reference/android/view/animation/RotateAnimation.html)

###### [A Skillful Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/ASkillfulDemo)
 - 实用技巧（据说，工程中应用未知）示例，Intent支持其他传递对象方式，包含数据的Serializable, Parcelable，全局获取context（需要在manifest中指定完整初始化类包名）， 自定义log工具类, getSerializableExtra获取通过参数传递过来的序列化对象，需要再向下转型成实例对象，Parcelable方式不需要，俩种方式使用方法完全相同，定义Serializable对象类需要通过Serializable接口，实现一系列getter和setter。定义Parcelable对象类较复杂，通过实现Parcelable接口，将一个完整的对象进行分解，分解后的每一个部分都是Intent所支持的数据类型，效率相对较高，除了实现一系列getter和setter之外，还需要实现``Creater<Person>``接口，且内部读顺序(createFromParcel)与写顺序(writeToParcel)需要一致。
 - ``Key Words``: Serializable, Parcelable, Intent, putExtra, Creator, createFromParcel, newArray, describeContents, writeToParcel, getIntent, getSerializableExtra, getParcelableExtra, Application, getApplicationContext, Log
 - [Serializable接口](https://developer.android.com/reference/java/io/Serializable.html)
 - [Parcelable接口](https://developer.android.com/reference/android/os/Parcelable.html)
 - [Parcelable & Serializable](http://www.cnblogs.com/renqingping/archive/2012/10/25/Parcelable.html)
 - [Context.getApplicationContext](https://developer.android.com/reference/android/content/Context.html)
 - [Log](https://developer.android.com/reference/android/util/Log.html)
 - [``<application>``](https://developer.android.com/guide/topics/manifest/application-element.html)

###### [A Weather Final Demo](https://github.com/sennhviwang/The-First-Line-of-Android-Code/tree/master/AWeatherFinalDemo)
 - 天气应用示例，内容较多涉及较全：数据库存取，SharedPreferences存取，网络请求，json解析，自定义log，自动更新服务，ListView，ProgressDialog，并注释了开发中产生的一些BUG产生原因
 - ``Key Words``: getIntent, getBooleanExtra, SharedPreferences, PreferenceManager, getDefaultsharedPreferences, ListView, ArrayAdapter, setOnItemClickListener, notifyDataSetChanged, setSelection, runOnUiThread, ProgressDialog, setMessage, setCanceledOnTouchOutside, show, dismiss, onBackPressed, TextUtils, isEmpty, SQLiteOpenHelper, ContentValues, JSONObject, HttpURLConnection, setRequestMethod, setConnectTimeout, setReadTimeout, InputStreamReader, BufferedReader, disconnect, Cursor, IBinder, AlarmManager, PendingIntent
 - [java.lang.NullPointerException: println needs a message](http://stackoverflow.com/questions/11449589/java-lang-nullpointerexception-println-needs-a-message)
 - [Error:Attempt to invoke virtual method 'java.lang.String java.lang.Object.toString()' on a null object reference List``<String>``中元素为null]
 - [打包签名](http://blog.csdn.net/linghu_java/article/details/6701666)未签名会安装包解析失败
 - [Manifest.xml合并](https://developer.android.com/studio/build/manifest-merge.html)


---

###### ``License: MIT license``