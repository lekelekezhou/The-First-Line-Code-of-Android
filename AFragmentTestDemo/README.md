# A Fragment Test Demo

### Overview

替换fragment的例子，简述fragment生命周期

### Table of contents
 1. ``onAttach``:activity基础上建立与fragment关联
 - ``onCreate``
 - ``onCreateView``:为fragment创建layout
 - ``onActivityCreated``:确保与fragment相关联的活动已经创建完毕时调用
 - ``onStart``
 - ``onResume``
 - ``onPause``
 - ``onStop``
 - ``onDestroyView``:与fragment关联的视图被移除
 - ``onDestroy``
 - ``onDetach``:当fragment与activity解除关联
 - ``FragmentTransaction``
 - ``getSupportFragmentManager``:从布局文件中获取fragment实例
 - ``getActivity``:fragment中获得相关活动