package superdeduper.android.ggl.com.superdeduper;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by fangbrian on 7/2/15.
 */
public class SuperDeduperService extends IntentService implements PhotoSetManager.OnPhotoSetListener {

    private int mNotificationId;

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
    public void onPhotoSetAdded(PhotoSet photoSet, Long timestamp) {
        Log.d("Photo Set Added ", "Photo Set Size: " + photoSet.size());

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentTitle("Did you just take new photos?")
                .setAutoCancel(true)
                .setContentText(photoSet.size() + " photos");

        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(MainActivity.PHOTOSET_TIMESTAMP, timestamp);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(mNotificationId++, builder.build());
    }
}
