package superdeduper.android.ggl.com.superdeduper;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import com.squareup.picasso.Picasso;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;


public class MainActivity extends ActionBarActivity {

    public static final String PHOTOSET_TIMESTAMP = "PHOTOSET_TIMESTAMP";

    private VerticalFragmentAdapter mAdapter;
    private ViewPager mViewPager;
    private PhotoSet mPhotoSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);

        displayPhotoSet();

    }

    private void displayPhotoSet() {
        if (getIntent() == null || getIntent().getExtras() == null) return;

        Long timestamp = getIntent().getExtras().getLong(PHOTOSET_TIMESTAMP, 0);
        mPhotoSet = PhotoSetManager.getInstance().getPhotoSet(timestamp);
        if(mPhotoSet == null) return;

        mAdapter = new VerticalFragmentAdapter(this.getSupportFragmentManager(), mPhotoSet);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class VerticalFragmentAdapter extends FragmentPagerAdapter {

        private PhotoSet mPhotoSet;

        public VerticalFragmentAdapter(FragmentManager fm, PhotoSet photoSet) {
            super(fm);

            mPhotoSet = photoSet;

        }

        @Override
        public Fragment getItem(int i) {
            Photo photo = mPhotoSet.get(i);
            Log.d("****", "Current Index" + i);
            Log.d("****", photo.getPath());
            return VerticalFragment.newInstance(photo.getPath());
        }

        @Override
        public int getCount() {
            return mPhotoSet.size();
        }
    }

    public static class VerticalFragment extends Fragment {
        public static final String PATH_BUNDLE_KEY = "PATH_BUNDLE_KEY";
        private String mPath;
        private PhotoAdapter mAdapter;
        static VerticalFragment newInstance(String path) {
            VerticalFragment f = new VerticalFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putString(PATH_BUNDLE_KEY, path);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Bundle bundle = getArguments();
            mPath = (bundle == null) ? "" : bundle.getString(PATH_BUNDLE_KEY);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_view, container, false);
            //ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
            //File imgFile = new File(mPath);
            //Picasso.with(getActivity()).load(imgFile).fit().centerInside().into(imageView);
            VerticalViewPager mVerticalPager = (VerticalViewPager) v.findViewById(R.id.vertical_view);
            mAdapter = new PhotoAdapter(this.getChildFragmentManager(), mPath);
            mVerticalPager.setAdapter(mAdapter);

            return v;
        }
    }


}
