# A Data Persistence 4 SharedPreferences

### Overview

使用SharedPreferences存储，读取数据，结合广播最佳实践，实现登录保存密码例子

 1. 三种方法得到SharedPreferences对象：
    1. Context类的getSharedPreferences(文件名，模式)
    - Activity类的getPreferences(模式)，自动将当前活动的类名作为文件名
    - PreferenceManager类中的getDefaultSharedPreferences(Context)，自动将当前应用程序包名作为前缀
 - 存储数据的三步：
    1.  SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE).edit();
    - editor.putString("name", "Tom");
    - editor.commit();
 - 读取数据的两步：
    1. SharedPreferences pref = getSharedPreferences(FILE_NAME, MODE);
    - String NAME = pref.getString(KEY, DEFAULT_VALUE);

### Table of contents
 - ``getSharedPreferences``
 - ``getPreferences``
 - ``getDefaultSharedPreferences``
 - ``edit()``
 - ``putString, putInt, putBoolean``
 - ``getString, getInt, getBoolean``
 - ``commit``
 - ``clear``
 - ``getSharedPreferences(FILE_NAME, MODE)``以获取``SharedPreferences``对象
 - ``getString(KEY, DEFAULT_VALUE)``以获取值
 - ``CheckBox``
 - ``setChecked(true)``
 - ``isChecked()``