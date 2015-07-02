package superdeduper.android.ggl.com.superdeduper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by fangbrian on 7/2/15.
 */
public class PhotoSetManager implements GalleryChangeManager.OnGalleryChangeListener {

    public interface OnPhotoSetListener {
        public void onPhotoSetAdded(PhotoSet photoSet);
    }

    private static PhotoSetManager sManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private static final Integer IMAGE_DELAY_TIMEOUT = 5000;
    private PhotoSet photoSet = new PhotoSet();
    private OnPhotoSetListener mListener;

    public static PhotoSetManager getInstance() {
        if (sManager != null) return sManager;

        sManager = new PhotoSetManager();

        return sManager;
    }

    public void setGalleryChangeListener() {
        GalleryChangeManager.getInstance().setListener(sManager);
    }

    public void setPhotoSetListener(OnPhotoSetListener listener) {
        mListener = listener;
    }

    @Override
    public void onPhotoAdded(Photo photo) {
        Log.d("PhotoSetManager", photo.getTimestamp().toString());
        if (runnable != null) mHandler.removeCallbacks(runnable);

        photoSet.add(photo);
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mListener != null && photoSet.size() > 0) {
                    Log.d("PhotoAdded", "There are a total of " + photoSet.size() + " photos.");
                    mListener.onPhotoSetAdded(photoSet);
                    photoSet.clear();
                }
            }
        };

        mHandler.postDelayed(runnable, IMAGE_DELAY_TIMEOUT);



    }
}
