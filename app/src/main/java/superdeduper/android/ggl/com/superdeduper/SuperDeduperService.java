package superdeduper.android.ggl.com.superdeduper;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by fangbrian on 7/2/15.
 */
public class SuperDeduperService extends IntentService implements PhotoSetManager.OnPhotoSetListener {

    public SuperDeduperService() {
        super("SuperDeduperService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        GalleryChangeManager.getInstance().initializeObserver(this);
        PhotoSetManager.getInstance().setGalleryChangeListener();
        PhotoSetManager.getInstance().setPhotoSetListener(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onPhotoSetAdded(PhotoSet photoSet) {
        Log.d("Photo Set Added ", "Photo Set Size: " + photoSet.size());

        //Send Notification
        //Send Intent to Main Activity
    }
}
