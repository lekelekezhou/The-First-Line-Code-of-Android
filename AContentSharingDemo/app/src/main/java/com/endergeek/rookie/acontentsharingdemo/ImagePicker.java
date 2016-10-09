package com.endergeek.rookie.acontentsharingdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsenhui on 2016/8/9.
 */
public final class ImagePicker {

    private static final int PICK_IMAGE_ID = 1000;
    private static final int DEFAULT_MIN_WIDTH_QUALITY = 100;
    private static final int DEFAULT_MIN_HEIGHT_QUALITY = 100;
    private static final String TAG = ImagePicker.class.getSimpleName();
    private static final String TEMP_IMAGE_NAME = "tempImage";

    private static int minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY;
    private static int minHeightQuality = DEFAULT_MIN_HEIGHT_QUALITY;
    public static Uri selectedImage;


    public static void pickImage(Activity activity) {
        String chooserTitle = activity.getString(R.string.pic_select);
        pickImage(activity, chooserTitle);
    }
    public static void pickImage(Activity activity, String chooserTitle) {
        Intent chooseImageIntent = getPickImageIntent(activity, chooserTitle);
        activity.startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    private static Intent getPickImageIntent(Context context, String chooserTitle) {
        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra("return data", true);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTemporalFile(context)));
        intentList = addIntentList(context, intentList, pickIntent);
        intentList = addIntentList(context, intentList, takePhotoIntent);

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    chooserTitle);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    intentList.toArray(new Parcelable[intentList.size()]));
        }
        return chooserIntent;
    }

    private static List<Intent> addIntentList(Context context, List<Intent> list, Intent intent) {

        Log.i(TAG, "ADD intents of type: " + intent.getAction());
        List<ResolveInfo> resolveInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo rInfo : resolveInfo) {
            String packageName = rInfo.activityInfo.packageName;
            Intent targetIntent = new Intent(intent);
            targetIntent.setPackage(packageName);
            list.add(targetIntent);
            Log.i(TAG, "App package: " + packageName);
        }
        return list;
    }

    public static Bitmap getImageFromResult(Context context, int requestCode, int resultCode,
                                            Intent imageReturnedIntent) {
        Log.i(TAG, "getImageFromResult() called with: " + "resultCode = [" + resultCode + "]");
        Bitmap bm = null;
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_ID) {
            File imageFile = getTemporalFile(context);
            boolean isCamera = (imageReturnedIntent == null ||
                    imageReturnedIntent.getData() == null ||
                    imageReturnedIntent.getData().toString().contains(imageFile.toString()));
            if (isCamera) {
                selectedImage = Uri.fromFile(imageFile);
            } else {
                selectedImage = imageReturnedIntent.getData();
            }

            Log.i(TAG, "selectedImage:" + selectedImage);

            bm = getImageResized(context, selectedImage);
            int rotation = ImageRotator.getRotation(context, selectedImage, isCamera);
            bm = ImageRotator.rotate(bm, rotation);

        }
        return bm;
    }

    private static Bitmap getImageResized(Context context, Uri selectedImage) {
        Bitmap bm;
        int[] sampleSizes = new int[]{5, 3, 2, 1};
        int i = 0;
        do {
            bm = decodeBitmap(context, selectedImage, sampleSizes[i]);
            i++;

            Log.i(TAG, "In Progress width = " + bm.getWidth() + "height: " + bm.getHeight());
        } while (bm != null &&
                (bm.getWidth() < minWidthQuality || bm.getHeight() < minHeightQuality) &&
                i < sampleSizes.length);
        Log.i(TAG, "Final bitmap width = " + (bm != null ? bm.getWidth() : "No final bitmap"));
        // return bitmap as result, which will be shown in UI.
        return bm;
    }

    private static Bitmap decodeBitmap(Context context, Uri theUri, int sampleSize) {
        Bitmap actuallyUsableBitmap = null;
        AssetFileDescriptor fileDescriptor = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        try {
            fileDescriptor = context.getContentResolver().openAssetFileDescriptor(theUri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileDescriptor != null) {
            actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
                    fileDescriptor.getFileDescriptor(), null, options);
            Log.i(TAG, "Trying sample size " + options.inSampleSize + "\t\tBitmap width: "
            + actuallyUsableBitmap.getWidth() + "\theight: " + actuallyUsableBitmap.getHeight());
        }

        return actuallyUsableBitmap;
    }

    private static File getTemporalFile(Context context) {
        return new File(context.getExternalCacheDir(), TEMP_IMAGE_NAME);
    }


    public static void setMinQuality(int minWidthQuality, int minHeightQuality) {
        ImagePicker.minHeightQuality = minHeightQuality;
        ImagePicker.minWidthQuality = minWidthQuality;
    }
}
