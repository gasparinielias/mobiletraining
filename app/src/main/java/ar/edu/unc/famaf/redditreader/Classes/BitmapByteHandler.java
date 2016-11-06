package ar.edu.unc.famaf.redditreader.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by mono on 05/11/16.
 */

public class BitmapByteHandler {
    public static byte[] getBytes(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public static Bitmap getImage(byte[] image) {
        return image != null ? BitmapFactory.decodeByteArray(image, 0, image.length) : null;
    }
}
