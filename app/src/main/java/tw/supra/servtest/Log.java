package tw.supra.servtest;

import java.util.HashSet;

/**
 * Created by supra on 16-7-27.
 */
public class Log {

    private static final HashSet<LogListener> LISTENERS = new HashSet<LogListener>();

    public static void regLogListener(LogListener listener) {
        LISTENERS.add(listener);
    }

    public static void unregLogListener(LogListener listener) {
        LISTENERS.remove(listener);
    }

    public static int i(String tag, String msg) {
        notifyListeners(tag, msg);
//        sBuilder.append(String.format("%s::%s", tag, msg));
        return android.util.Log.i(tag, msg);
    }

    private static void notifyListeners(String tag, String msg) {
        for (LogListener listener : LISTENERS) {
            listener.onLog(tag, msg);
        }
    }

    public interface LogListener {
        void onLog(String tag, String msg);
    }


}
