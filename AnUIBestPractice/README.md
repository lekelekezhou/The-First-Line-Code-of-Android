# An UI Best Practice

### Overview

``Adapter``的第二例子，使用``draw9patc``h制作``Nine-Patch``图片，左&上边框表示拉伸区域，右&下边框表示内容放置区域。

### Table of contents

 1. ``MessageAdapter extends ArrayAdapter<Message>``
 - ``@Override getView``
 - ``getItem``
 - ``ViewHolder``
 - ``setTag``, ``getTag``
 - ``new MessageAdapter(MainActivity.this, R.layout.item_list_message, messageList)``
 - ``setAdapter``
 - ``notifyDataSetChanged``
 - ``setSelection``
 - [BaseAdapter](https://developer.android.com/reference/android/widget/BaseAdapter.html)
 - [9-patch使用细节参考](http://www.cnblogs.com/tinyphp/p/3826219.html)