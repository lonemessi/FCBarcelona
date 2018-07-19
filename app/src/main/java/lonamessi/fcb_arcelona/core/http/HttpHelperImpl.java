package lonamessi.fcb_arcelona.core.http;

import lonamessi.fcb_arcelona.core.http.api.GeeksApis;

/**
 * Created by gyp on 2018/7/19.
 */
public class HttpHelperImpl implements HttpHelper {
    private GeeksApis mGeeksApis;

    public HttpHelperImpl(GeeksApis mGeeksApis) {
        this.mGeeksApis = mGeeksApis;
    }
}
