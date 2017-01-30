package app.com.example.althomas04.basicmalayalam;

//custom array

public class word {

    /**
     * Default translation is the users native language
     */
    private String mDefaultTranslation;

    private String mMalayalamTranslation;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResourseId;

    public word(String defaultTranslation, String malayalamTranslation, int audioResourseId) {
        mDefaultTranslation = defaultTranslation;
        mMalayalamTranslation = malayalamTranslation;
        mAudioResourseId = audioResourseId;
    }

    public word(String defaultTranslation, String malayalamTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMalayalamTranslation = malayalamTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourseId = audioResourceId;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMalayalamTranslation() {
        return mMalayalamTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioResourseId() {
        return mAudioResourseId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}

