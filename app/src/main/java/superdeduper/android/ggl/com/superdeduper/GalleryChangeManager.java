package superdeduper.android.ggl.com.superdeduper;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by fangbrian on 6/29/15.
 */
public class GalleryChangeManager {

    public interface OnGalleryChangeListener {
        public void onPhotoAdded(Photo photo);
    }

    private static GalleryChangeManager sManager;
    private static boolean sInitialized = false;

    private Context mContext;
    private ImageContentObserver mImageObserver;
    private OnGalleryChangeListener mListener;

    public static GalleryChangeManager getInstance() {
        if (sManager != null) return sManager;

        sManager = new GalleryChangeManager();
        return sManager;
    }

    public void initializeObserver(Context context) {
        if (sInitialized) return;

        mContext = context.getApplicationContext();
        mImageObserver = new ImageContentObserver(new Handler());
        mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, mImageObserver);
        sInitialized = true;
    }

    public void setListener(OnGalleryChangeListener listener) {
        mListener = listener;
    }

    private class ImageContentObserver extends ContentObserver {

        private final String[] PROJECTION = new String[] {
            android.provider.BaseColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED
        };

        private final String ORDER_BY = MediaStore.MediaColumns.DATE_ADDED + " DESC LIMIT 1 ";

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
            Photo latestPhoto = getLatestPhoto();
            Log.d("GET PHOTO", latestPhoto.getTimestamp().toString());
            Log.d("GET PHOTO PATH", latestPhoto.getPath());
            mListener.onPhotoAdded(latestPhoto);
        }

        private Photo getLatestPhoto() {
            Cursor c = null;

            c = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION, null, null, ORDER_BY);
            c.moveToFirst();

            Long id = c.getLong(0);
            String path = c.getString(1);
            Integer timestamp = Integer.parseInt(c.getString(2));

            Photo photo = new Photo(id, path, timestamp);

            if (c != null) c.close();

            return photo;

        }
    }

}
