package superdeduper.android.ggl.com.superdeduper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by fangbrian on 7/2/15.
 */
public class PhotoSetManager implements GalleryChangeManager.OnGalleryChangeListener {

    private static PhotoSetManager sManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private static final Integer IMAGE_DELAY_TIMEOUT = 5000;
    private Integer photoCount = 0;

    public static PhotoSetManager getInstance() {
        if (sManager != null) return sManager;

        sManager = new PhotoSetManager();

        return sManager;
    }

    public static void setListener() {
        GalleryChangeManager.getInstance().setListener(sManager);
    }

    @Override
    public void onPhotoAdded(Photo photo) {
        Log.d("PhotoSetManager", photo.getTimestamp().toString());
        if (runnable != null) mHandler.removeCallbacks(runnable);

        photoCount += 1;
        runnable = new Runnable() {
            @Override
            public void run() {
                if (photoCount > 0) {
                    Log.d("PhotoAdded", "There are a total of " + photoCount.toString() + " photos.");
                    photoCount = 0;
                }
                else {
                    photoCount += 1;
                }

            }
        };

        mHandler.postDelayed(runnable, IMAGE_DELAY_TIMEOUT);



    }
}
