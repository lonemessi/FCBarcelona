package lonamessi.fcb_arcelona.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import lonamessi.fcb_arcelona.app.FCBarcelonaApp;
import lonamessi.fcb_arcelona.di.qualifier.FCBarcelona;

/**
 * Created by gyp on 2018/7/19.
 */
public class CommonUtils {
    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) FCBarcelonaApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
