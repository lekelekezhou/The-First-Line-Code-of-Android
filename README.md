
# Android Learn

``Android``开发的一些代码示例，由易到难，由简单到复杂的实现，来自官方文档以及《第一行代码》中的实现。测试环境：``Android Studio 2.0``，``API 23``及更高

### Demo List
 1. My First App
 - Android LifeCycle Demo
 - Fragment Basic Learn
 - SQLite OpenHelper Demo
 - An Interactive Intent Demo
 - A Permission Demo
 - A Content Sharing Demo
 - An Activity LifeCycle Demo
 - An UI Widget Demo
 - An UI Custom View Demo
 - A List View Demo
 - An UI Best Practice
 - A Fragment Test Demo
 - A Fragment Best Practice
 - A Broadcast Test Demo


###### My First App
 - ``Intent``示例，启动另一个``activity``并在其中显示之前输入的文本
 - ``Key Words``: Intent, onClick, putExtra, startActivity, parentActivityName, getIntent.
 - [Starting Another Activity](https://developer.android.com/training/basics/firstapp/starting-activity.html)


###### Android LifeCycle Demo
 - 简单的生命周期示例，需要使用``DDMS``来查看日志
 - ``Key Words``: onCreate, onStart, onResume, onRestart, onPause, onStop, onDestory, onSaveInstanceState, onRestoreInstanceState.
 - [Managing the Activity Lifecycle](https://developer.android.com/training/basics/activity-lifecycle/starting.html)


###### Fragment Basic Learn
 - ``Fragment``基础示例，使用``Fragment``显示标题和正文
 - ``Key Words``: Fragment, FrameLayout, FragmentTransaction, getSupportFragmentManager, addToBackStack.
 - [Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/index.html)


###### SQLite OpenHelper Demo
 - 使用``SQLite``数据库的``SQLiteOpenHelper``示例，不包括使用``Preferences``和``Files``，说明``Dao``，``Bean``，``DBHelper``较好的例子
 - ``Key Words``: BaseColumns, SQLiteOpenHelper, ContentValues, moveToFirst, getColumnIndex, getReadableDatabase, getWritableDatabase, setEnabled.
 - [Saving Data in SQL Databases](https://developer.android.com/training/basics/data-storage/databases.html)


###### An Interactive Intent Demo
 - 使用``Intent``与应用交互：拨号
 - ``Key Words``: Intent, ACTION_DIAL, packageManager, startActivityForResult, RESULT_OK, RESULT_CANCELLED, intent-filter, Uri, setResult.
 - [Interacting with Other Apps](https://developer.android.com/training/basics/intents/index.html)


###### A Permission Demo
 - 系统权限使用示例，由于``Android 6.0``要求，需在运行时请求权限，部分参考官方文档代码
 - ``Key Words``: uses-permission, checkSelfPermission, shouldShowRequestPermissionRationale, RequestPermissions, onRequestPermissionsResult.
 - [Working with System Permissions](https://developer.android.com/training/permissions/index.html)


###### A Content Sharing Demo
 - 内容分享示例，在读写存储空间时有必要在运行时声明和请求系统权限，功能包括输入和分享文本，设置压缩图片结果并支持分享，通过调用浏览器查询``IP``地址位置，学习更多关于``Intent``内容如``intent-filter``,``action``,``category``,``data:scheme``,``mimeType``等，同时也是使用``Android Studio``分渠道打包示例
 - ``Key Words``: uses-permission, checkSelfPermission, shouldShowRequestPermissionRationale, RequestPermissions, onRequestPermissionsResult, getPickImageIntent, getImageResized, decodeBitmap, getRotationFromGallery, getRotationFromCamera, startActivityForResult, requestCode, resultCode, onBackPressed, setData, putExtra, addCategory, finish
 - [Apps with Content Sharing](https://developer.android.com/training/building-content-sharing.html)


###### An Activity LifeCycle Demo
 - 使用普通活动和对话框活动以及4种不同的启动模式加深对活动的生命周期理解，四种启动模式为：``standard``,``singleTop``,``singleTask``和``singleInstance``.为了更好理解四种模式，项目根目录中添加了相应的截图以及可能有帮助的文字说明
 - ``Key Words``: requestWindowFeature, savedInstanceState, onSaveInstanceState,  setOnClickListener,onCreate, onStart, onResume, onRestart, onPause, onStop, onDestory, @android:style/Theme.Dialog, launchMode
 - [Managing the Activity Lifecycle](https://developer.android.com/training/basics/activity-lifecycle/starting.html)
 - [First Code of Android-LifeCycle](https://book.douban.com/subject/25942191/)
 
###### An UI Widget Demo
 - 演示一些常见组件和布局的UI示例，在``onClick``中使用``switch``集中处理点击事件，``NOTE``：务必在``AndroidManifest.xml``文件中注册你的``activity``，显式``Intent``并不需要在``intent-filter``中声明太多东西
 - ``Key Words``: onClick, ProgressBar, setProgress, setImageResource, setVisibility, getVisibility, AlertDialog.Builder, setTitle, setMessage, setCancelable, setPositiveButton, setNegativeButton, show, ProgressDialog, layout_alignParentLeft|Bottom|Right|Top, layout_toRight|Left|Bottom|TopOf, layout_weight, FrameLayout, TableLayout, android:stretchColumns, layout_span
 - [First Code of Android-UI](https://book.douban.com/subject/25942191/)

###### An UI Custom View Demo
 - 简单实现自定义``View``，演示如何构造和调用基于``LinearLayout``的自定义布局
 - ``Key Words``: include, custom view, extends LinearLayout, LayoutInflater.from(context).inflate(sub_layout, this), ((Activity) getContext())
 - [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html)
 - [Difference between getContext(), getApplicationContext(), getBaseContext(), this](http://stackoverflow.com/questions/10641144/difference-between-getcontext-getapplicationcontext-getbasecontext-and)

###### A ListView Demo
 - ``Adapter``第一例子，使用自定义和原始``adapter``的范例，实体类为``Fruit``
 - ``Key Words``: Abstract Class, List, Entity Class, ArrayAdapter, setAdapter, getItem, getView, LayoutInflater.from(getContext()).inflate(id, null), ViewHolder
 - [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html)

###### An UI Best Practice
 - ``Adapter``的第二例子，使用``draw9patch``制作``Nine-Patch``图片，左&上边框表示拉伸区域，右&下边框表示内容放置区域。
 - ``Key Words``: Abstract Class, List, Entity Class, ArrayAdapter, setAdapter, getItem, getView, LayoutInflater.from(getContext()).inflate(id, null), ViewHolder, setTag, getTag, notifyDataSetChanged, setSelection
 - [BaseAdapter](https://developer.android.com/reference/android/widget/BaseAdapter.html)

###### A Fragment Test Demo
 - 替换fragment的例子，简述fragment生命周期，fragment与activity之间通信
 - ``Key Words``: onAttach, onCreate, onCreateView, onActivityCreated, onStart, onResume, onPause, onStop, onDestroyView, onDestroy, onDetach, FragmentTransaction, getSupportFragmentManager, getActivity
 - [Fragment Lifecycle](https://developer.android.com/guide/components/fragments.html)

###### A Fragment Best Practice
 - listview+fragment+adapter结合使用，手机与平板适配demo
 - ``Key Words``: activity_main.xml(平板适配), onAttach, onCreateView, onActivityCreated, onItemClick, actionStart, android:name, tools:layout, android:scrollbars, tv.setMovementMethod(new ScrollingMovementMethod()), standard broadcast receiver
 - [Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/fragment-ui.html)

###### A Broadcast Test Demo
 - 全局广播与本地广播，标准广播与有序广播
 - ``Key Words``: IntentFilter, NetworkChangeReceiver, LocalBroadcastManager, sendBroadcast, sendOrderedBroadcast, addAction, registerReceiver, unregisterReceiver, ConnectivityManager, NetworkInfo, BroadReceiver, Custom Toast, abortBroadcast, receiver, android.intent.action.BOOT_COMPLETED, anroid:priority
 - [sendBroadcast](https://developer.android.com/reference/android/content/Context.html#sendBroadcast(android.content.Intent, java.lang.String))
 - [Manifest.permission](https://developer.android.com/reference/android/Manifest.permission.html)