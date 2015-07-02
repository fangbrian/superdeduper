package superdeduper.android.ggl.com.superdeduper;

/**
 * Created by fangbrian on 7/2/15.
 */
public class Photo {
    private final String mPath;
    private final Integer mTimestamp;
    private final Long mId;

    public Photo(Long id, String path, Integer timestamp) {
        mId = id;
        mPath = path;
        mTimestamp = timestamp;
    }

    public Integer getTimestamp() {
        return mTimestamp;
    }

    public String getPath() {
        return mPath;
    }

    public Long getId() {
        return mId;
    }
}
