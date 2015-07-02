package superdeduper.android.ggl.com.superdeduper;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by fangbrian on 6/29/15.
 */
public class GalleryChangeManager {

    private static GalleryChangeManager sManager;
    private static boolean sInitialized = false;

    private Context mContext;
    private ImageContentObserver mImageObserver;


    public static GalleryChangeManager getInstance() {
        if (sManager != null) return sManager;

        sManager = new GalleryChangeManager();
        return sManager;
    }

    public void initializeObserver(Context context) {
        if(sInitialized) return;

        mContext = context.getApplicationContext();
        mImageObserver = new ImageContentObserver(new Handler());
        mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, mImageObserver);
        sInitialized = true;

    }




    private class ImageContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public ImageContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d("ContentObserver", "There is a change detected");
        }
    }

}
