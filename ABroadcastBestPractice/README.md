# A Broadcast Best Practice

### Overview

广播机制的最佳实践-强制下线功能，此demo突然涉及Android 6.0 Draw over other apps高级权限，不能像一般权限那样在运行时请求获取，需要引导至开关处打开

### Table of contents
 1. ``Intent("CUSTOM_STRING_IN_MANIFEST_BROADCAST_RECEIVER)``
 - ``BaseActivity``
 - ``ActivityCollector``
 - ``Build.VERSION.SDK_INT``
 - ``Settings.ACTION_MANAGE_OVERLAY_PERMISSION``
 - ``<receiver>``
 - ``List<Activity>``
 - ``AlertDialog.Builder``
 - ``intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)``
 - ``dialogBuilder.create``
 - ``alertDialog.getWindow().setType``
 - ``TYPE_SYSTEM_ALERT``