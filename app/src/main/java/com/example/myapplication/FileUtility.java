package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
public class FileUtility {
    @SuppressLint("NewApi")
    public static String getPath(Context context, Uri uri) {
        String filePath = null;

        // Check if the URI scheme is "content"
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath = cursor.getString(columnIndex);
                }
            }
        }

        // If the file path is still null, attempt to extract it from the URI itself
        if (filePath == null) {
            filePath = uri.getPath();
        }

        return filePath;
    }

}
