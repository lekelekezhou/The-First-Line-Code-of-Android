
# Android Learn

This repository implements some code sample in the official android site which may work fine under Android Studio 2.0 and Android  API 23 or higher.

###Demo List
 1. MyFirstApp
 - AndroidLifeCycleDemo
 - FragmentBasics
 - FragmentBasicLearn
 - SQLiteOpenHelperDemo
 - AnInteractiveIntentDemo
 - APermissionDemo
 - AContentSharingDemo
 - AnActivityLifeCycleDemo
 - AnUIWidgetDemo
 - AnUICustomViewDemo
 - AListViewDemo


###### MyFirstApp
 - An Intent Demo, Launch another activity and Display text inputted in last activity.
 - ``Key Words``: Intent, onClick, putExtra, startActivity, parentActivityName, getIntent.
 - [Starting Another Activity](https://developer.android.com/training/basics/firstapp/starting-activity.html)


###### AndroidLifeCycleDemo
 - A Demo without any effects, Use DDMS to inspect Log.
 - ``Key Words``: onCreate, onStart, onResume, onRestart, onPause, onStop, onDestory, onSaveInstanceState, onRestoreInstanceState.
 - [Managing the Activity Lifecycle](https://developer.android.com/training/basics/activity-lifecycle/starting.html)


###### FragmentBasics
 - Fragment Basic Demo, Display Headlines and Articles Based on Fragments, From Official Repo.
 - ``Key Words``: Fragment, FrameLayout, FragmentTransaction, getSupportFragmentManager, addToBackStack.
 - [Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/index.html)


###### FragmentBasicLearn
 - Fragment Basic Demo, Display Headlines and Articles Based on Fragments, Test Edition.
 - ``Key Words``: Fragment, FrameLayout, FragmentTransaction, getSupportFragmentManager, addToBackStack.
 - [Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/index.html)


###### SQLiteOpenHelperDemo
 - SQLiteOpenHelper Demo, Works with SQLite DataBase, other than Preferences or Files. with some improvement 
 - ``Key Words``: BaseColumns, SQLiteOpenHelper, ContentValues, moveToFirst, getColumnIndex, getReadableDatabase, getWritableDatabase, setEnabled.
 - [Saving Data in SQL Databases](https://developer.android.com/training/basics/data-storage/databases.html)


###### An Interactive Intent Demo
 - An Interactive Intent Demo between apps, Use Intent to Dial number inputted in first activity.
 - ``Key Words``: Intent, ACTION_DIAL, packageManager, startActivityForResult, RESULT_OK, RESULT_CANCELLED, intent-filter, Uri, setResult.
 - [Interacting with Other Apps](https://developer.android.com/training/basics/intents/index.html)


###### A Permission Demo
 - A Permission Demo, Works with System Permissions, declare and request permissions at runtime, partially from Official Repo.
 - ``Key Words``: uses-permission, checkSelfPermission, shouldShowRequestPermissionRationale, RequestPermissions, onRequestPermissionsResult.
 - [Working with System Permissions](https://developer.android.com/training/permissions/index.html)


###### A Content Sharing Demo
 - A Content Sharing Demo, Works with System Permissions, declare and request permissions at runtime, which is necessary when read/write external memory.Able to share text, set Compressed Image and share it later via apps.Query IP Location, learn more about intent as well as intent-filter,action,category,data:scheme,mimeType...Also a Packing Demo of Android Studio.
 - ``Key Words``: uses-permission, checkSelfPermission, shouldShowRequestPermissionRationale, RequestPermissions, onRequestPermissionsResult, getPickImageIntent, getImageResized, decodeBitmap, getRotationFromGallery, getRotationFromCamera, startActivityForResult, requestCode, resultCode, onBackPressed, setData, putExtra, addCategory, finish
 - [Apps with Content Sharing](https://developer.android.com/training/building-content-sharing.html)


###### An Activity LifeCycle Demo
 - An Activity LifeCycle Demo for understanding the lifecycle of Normal and Dialog activites as well as four different launch modes:standard,singleTop,singleTask and singleInstance.For a better understandable experience, some screenshots were added to the root directory of corresponding project which may be helpful in the procedure of learning activity lifecyle and four launch modes.
 - ``Key Words``: requestWindowFeature, savedInstanceState, onSaveInstanceState,  setOnClickListener,onCreate, onStart, onResume, onRestart, onPause, onStop, onDestory, @android:style/Theme.Dialog, launchMode
 - [Managing the Activity Lifecycle](https://developer.android.com/training/basics/activity-lifecycle/starting.html)
 - [First Code of Android-LifeCycle](https://book.douban.com/subject/25942191/)
 
###### AnUIWidgetDemo
 - An UI Widget Demo for demonstrating a list of widgets and common used layouts such as RelativeLayout.An improvement has been made to respond all clickable events in onClick method.DOOONot forget to register your activities in AndroidManifest.xml file.Also note that an explicit Intent don't need declare more about intent-filter.
 - ``Key Words``: onClick, ProgressBar, setProgress, setImageResource, setVisibility, getVisibility, AlertDialog.Builder, setTitle, setMessage, setCancelable, setPositiveButton, setNegativeButton, show, ProgressDialog, layout_alignParentLeft|Bottom|Right|Top, layout_toRight|Left|Bottom|TopOf, layout_weight, FrameLayout, TableLayout, android:stretchColumns, layout_span
 - [First Code of Android-UI](https://book.douban.com/subject/25942191/)

###### AnUICustomViewDemo
 - A simply implemented custom View demo demonstrates how to construct and invoke custom layout based on LinearLayout
 - ``Key Words``: include, custom view, extends LinearLayout, LayoutInflater.from(context).inflate(sub_layout, this), ((Activity) getContext())
 - [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html)
 - [Difference between getContext(), getApplicationContext(), getBaseContext(), this](http://stackoverflow.com/questions/10641144/difference-between-getcontext-getapplicationcontext-getbasecontext-and)

###### AListViewDemo
 - A Demo of ListView with custom and native adapter for entity class(Fruit in this case).
 - ``Key Words``: Abstract Class, List, Entity Class, ArrayAdapter, setAdapter, getItem, getView, LayoutInflater.from(getContext()).inflate(id, null), 
 - [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html)