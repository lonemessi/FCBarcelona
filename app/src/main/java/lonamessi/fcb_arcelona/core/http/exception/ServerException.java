package lonamessi.fcb_arcelona.core.http.exception;

/**
 * Created by gyp on 2018/7/19.
 */
public class ServerException extends Exception {
    private int code;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
