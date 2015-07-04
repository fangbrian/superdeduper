package superdeduper.android.ggl.com.superdeduper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by fangbrian on 7/3/15.
 */
public class ImageFragment extends Fragment {
    public static final String PATH_BUNDLE_KEY = "PATH_BUNDLE_KEY";
    private String mPath;

    public static ImageFragment newInstance(String path) {
        ImageFragment f = new ImageFragment();

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
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
        File imgFile = new File(mPath);
        Picasso.with(getActivity()).load(imgFile).fit().centerInside().into(imageView);

        return v;
    }
}
