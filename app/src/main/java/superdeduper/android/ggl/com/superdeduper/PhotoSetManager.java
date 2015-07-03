package superdeduper.android.ggl.com.superdeduper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by fangbrian on 7/2/15.
 */
public class PhotoSetManager implements GalleryChangeManager.OnGalleryChangeListener {

    public interface OnPhotoSetListener {
        public void onPhotoSetAdded(PhotoSet photoSet, Long timestamp);
    }

    private static PhotoSetManager sManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private static final Integer IMAGE_DELAY_TIMEOUT = 5000;
    private PhotoSet photoSet = new PhotoSet();
    private OnPhotoSetListener mListener;
    private HashMap<Long, PhotoSet> mPhotoHash = new HashMap<>();

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

    public PhotoSet getPhotoSet(Long timestamp) {
        PhotoSet test = mPhotoHash.get(timestamp);
        return mPhotoHash.get(timestamp);
    }

    @Override
    public void onPhotoAdded(Photo photo) {
        if (runnable != null) mHandler.removeCallbacks(runnable);

        photoSet.add(photo);
        runnable = new Runnable() {
            @Override
            public void run() {
                Long timestampId = System.currentTimeMillis();
                if (mListener != null && photoSet.size() > 1) {
                    mListener.onPhotoSetAdded(photoSet, timestampId);
                }

                mPhotoHash.put(timestampId, photoSet);

                photoSet = new PhotoSet();

            }
        };

        mHandler.postDelayed(runnable, IMAGE_DELAY_TIMEOUT);



    }
}
