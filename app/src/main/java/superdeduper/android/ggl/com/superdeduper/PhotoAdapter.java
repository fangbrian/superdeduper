package superdeduper.android.ggl.com.superdeduper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by fangbrian on 7/3/15.
 */
public class PhotoAdapter extends FragmentPagerAdapter {

    private String mPath;
    private static final int NUMBER_OF_VERTICAL_SCREENS = 2;

    public PhotoAdapter(FragmentManager fm, String path) {
        super(fm);

        mPath = path;

    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0) {
            return ImageFragment.newInstance(mPath);
        }
        else {
            return ImageFragment.newInstance("");
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_VERTICAL_SCREENS;
    }
}