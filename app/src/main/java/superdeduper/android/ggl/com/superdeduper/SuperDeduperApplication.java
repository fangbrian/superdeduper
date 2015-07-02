package superdeduper.android.ggl.com.superdeduper;

import android.app.Application;
import android.content.Intent;

/**
 * Created by fangbrian on 7/2/15.
 */
public class SuperDeduperApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(this, SuperDeduperService.class));
    }
}
