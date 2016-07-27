package tw.supra.servtest;

import android.app.Service;

/**
 * Created by supra on 16-7-27.
 */
public abstract class BaseService extends Service {

    private final String mSimpleName = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(mSimpleName, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(mSimpleName, "onDestroy");
    }
}
