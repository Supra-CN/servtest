package tw.supra.servtest.MyService;

import android.content.Intent;
import android.os.IBinder;

import tw.supra.servtest.BaseService;

public class MyService1 extends BaseService {
    public MyService1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
