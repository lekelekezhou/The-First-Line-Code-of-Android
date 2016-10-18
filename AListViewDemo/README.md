# A ListView Demo

### Overview

``Adapter``第一例子，使用自定义和原始``adapter``的范例，实体类为``Fruit``

### Table of contents

 1. ``class Fruit``定义实体类
 - ``class FruitAdapter extends ArrayAdapter<Fruit>`` 自定义adapter
 - FruitAdapter 构造函数
 - listView.setAdapter
 - ``@Override getView``
 - ``getItem``
 - ``ViewHolder``
 - ``setTag``, ``getTag``
 - ``LayoutInflater.from(getContext()).inflate(id, null)`` : 从给定的context(getContext)中获取LayoutInflater，并将对应的View对象使用布局文件(id)实例化
 - [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html)