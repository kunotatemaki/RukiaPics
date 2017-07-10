package com.rukiasoft.rukiapics.utilities;

/**
 * Created by Roll on 8/7/17.
 */

public class RukiaConstants {

    public static final String BASE_URL = "https://api.flickr.com/services/rest/";

    public static final String FLICKR_METHOD = "method";
    public static final String FLICKR_API_KEY = "api_key";
    public static final String FLICKR_TAGS = "tags";
    public static final String FLICKR_SAFE_SEARCH = "safe_search";
    public static final String FLICKR_EXTRAS = "extras";
    public static final String FLICKR_FORMAT = "format";
    public static final String FLICKR_PER_PAGE = "per_page";
    public static final String FLICKR_SORT = "sort";
    public static final String FLICKR_NO_JSON_CALLBACK = "nojsoncallback";

    public static final String MIME_TYPE_PICTURE = "image/jpeg";
    public static final int REQUEST_CODE_ANIMATION = 11;
    public static final int REQUEST_CODE_SETTINGS = 12;

    public enum Order {
        PUBLISHED,
        TAKEN
    }

}
