# A Fragment Best Practice

### Overview

listview+fragment+adapter结合使用，手机与平板适配demo，textview文本过长滚屏

### Table of contents
 1. ``MainActivity``
 - activity_main.xml
     - ``NewsTitleFragment``(标题列表，单页)
 - activity_main.xml(sw600dp)
    - ``NewsContenFragment``(标题列表+内容，双页)
 - ``NewsTitleFragment``为公用，在此判断单双页
    - 双页:``NewsContentFragment``
    - 单页:``NewsContentActivity``
 - ``News``和``NewsAdapter``支持``NewsTitleFragment``设置适配器渲染``ListView``数据

---

 1. ``onAttach``数据初始化
 - ``onCreateView``加载布局，增加点击事件
 - ``onActivityCreated``判断单双页模式
 - ``onItemClick``根据单双页模式响应点击行为
 - ``actionStart``借鉴列参数方式
 - ``android:name``
 - ``tools:layout``
 - ``android:scrollbars = "vertical"``
 - ``yourTextView.setMovementMethod(new ScrollingMovementMethod());``
 - ``<receiver>``