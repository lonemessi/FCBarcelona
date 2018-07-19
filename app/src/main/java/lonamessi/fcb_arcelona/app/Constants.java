package lonamessi.fcb_arcelona.app;

import java.io.File;

/**
 * Created by gyp on 2018/7/19.
 */
public class Constants {
    /**
     * path
     */
    public static  final String PATH_DATA = FCBarcelonaApp.getInstance().getCacheDir().getAbsolutePath()+File.separator+"data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
}
