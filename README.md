
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


###### MyFirstApp
 - An Intent Demo, Launch another activity and Display text inputted in last activity.
 - Key Words: Intent, onClick, putExtra, startActivity, parentActivityName, getIntent.
 - [Starting Another Activity](https://developer.android.com/training/basics/firstapp/starting-activity.html)


###### AndroidLifeCycleDemo
 - A Demo without any effects, Use DDMS to inspect Log.
 - Key Words: onCreate, onStart, onResume, onRestart, onPause, onStop, onDestory, onSaveInstanceState, onRestoreInstanceState.
 - [Managing the Activity Lifecycle](https://developer.android.com/training/basics/activity-lifecycle/starting.html)


###### FragmentBasics
 - Fragment Basic Demo, Display Headlines and Articles Based on Fragments, From Official Repo.
 - Key Words: Fragment, FrameLayout, FragmentTransaction, getSupportFragmentManager, addToBackStack.
 - [Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/index.html)


###### FragmentBasicLearn
 - Fragment Basic Demo, Display Headlines and Articles Based on Fragments, Test Edition.
 - Key Words: Fragment, FrameLayout, FragmentTransaction, getSupportFragmentManager, addToBackStack.
 - [Building a Dynamic UI with Fragments](https://developer.android.com/training/basics/fragments/index.html)


###### SQLiteOpenHelperDemo
 - SQLiteOpenHelper Demo, Works with SQLite DataBase, other than Preferences or Files. with some improvement 
 - Key Words: BaseColumns, SQLiteOpenHelper, ContentValues, moveToFirst, getColumnIndex, getReadableDatabase, getWritableDatabase, setEnabled.
 - [Saving Data in SQL Databases](https://developer.android.com/training/basics/data-storage/databases.html)


###### An Interactive Intent Demo
 - An Interactive Intent Demo between apps, Use Intent to Dial number inputted in first activity.
 - Key Words: Intent, ACTION_DIAL, packageManager, startActivityForResult, RESULT_OK, RESULT_CANCELLED, intent-filter, Uri, setResult.
 - [Interacting with Other Apps](https://developer.android.com/training/basics/intents/index.html)


###### A Permission Demo
 - A Permission Demo, Works with System Permissions, declare and request permissions at runtime, partially from Official Repo.
 - Key Words: uses-permission, checkSelfPermission, shouldShowRequestPermissionRationale, RequestPermissions, onRequestPermissionsResult.
 - [Working with System Permissions](https://developer.android.com/training/permissions/index.html)


###### A Content Sharing Demo
 - A Content Sharing Demo, Works with System Permissions, declare and request permissions at runtime, which is necessary when read/write external memory.Able to share text, set Compressed Image and share it later via apps.Query IP Location, learn more about intent as well as intent-filter,action,category,data:scheme,mimeType...Also a Packing Demo of Android Studio.
 - Key Words: uses-permission, checkSelfPermission, shouldShowRequestPermissionRationale, RequestPermissions, onRequestPermissionsResult, getPickImageIntent, getImageResized, decodeBitmap, getRotationFromGallery, getRotationFromCamera, startActivityForResult, requestCode, resultCode, onBackPressed, setData, putExtra, addCategory, finish
 - [Apps with Content Sharing](https://developer.android.com/training/building-content-sharing.html)
