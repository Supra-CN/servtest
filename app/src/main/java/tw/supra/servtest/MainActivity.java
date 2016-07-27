package tw.supra.servtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import tw.supra.servtest.MyService.MyService0;
import tw.supra.servtest.MyService.MyService1;
import tw.supra.servtest.MyService.MyService2;
import tw.supra.servtest.MyService.MyService3;
import tw.supra.servtest.MyService.MyService4;
import tw.supra.servtest.MyService.MyService5;
import tw.supra.servtest.MyService.MyService6;
import tw.supra.servtest.MyService.MyService7;
import tw.supra.servtest.MyService.MyService8;
import tw.supra.servtest.MyService.MyService9;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Log.LogListener {

    /**
     * 运行时引用
     */
    private static final Runtime RUNTIME = Runtime.getRuntime();

    private static Handler sHandler = new Handler();

    private final Class<?>[] services = {
            MyService0.class,
            MyService1.class,
            MyService2.class,
            MyService3.class,
            MyService4.class,
            MyService5.class,
            MyService6.class,
            MyService7.class,
            MyService8.class,
            MyService9.class
    };

    private TextView mMonitor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_mem).setOnClickListener(this);
        mMonitor = (TextView) findViewById(R.id.monitor);
        resetMonitor("press btn to start service");
        Log.regLogListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.unregLogListener(this);
    }

    @Override
    public void onLog(String tag, String msg) {
        updateMonitor(String.format("%s::%s", tag, msg));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                start();
                break;
            case R.id.btn_stop:
                stop();
                break;
            case R.id.btn_mem:
                printMemInfo();
                break;
        }
    }

    private void printMemInfo() {
        updateMonitor(new MemInfo().toString());
    }


    private void start() {
        resetMonitor("\n++++++++++ START ++++++++++");
        compareMemInfoDelay(new MemInfo());
        for (Class c : services) {
            startService(new Intent(this, c));
        }
    }

    private void compareMemInfoDelay(final MemInfo oldInfo) {
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MemInfo newInfo = new MemInfo();
                long delta = newInfo.mAllocatedMemory - oldInfo.mAllocatedMemory;
                updateMonitor(String.format("delta(%s)=newAllocated(%s)-oldAllocated(%s);", Utils.humanReadable(delta),
                        Utils.humanReadable(newInfo.mAllocatedMemory), Utils.humanReadable(oldInfo.mAllocatedMemory)));
            }
        }, 500);
    }

    private void stop() {
        updateMonitor("\n---------- STOP ----------");
        compareMemInfoDelay(new MemInfo());
        for (Class c : services) {
            stopService(new Intent(this, c));
        }
    }

    private void resetMonitor(String... msg) {
        mMonitor.setText("");
        updateMonitor(msg);
    }

    private void updateMonitor(String... msg) {
        for (String s : msg) {
            mMonitor.append(s + "\n");

        }
    }

    private class MemInfo {
        final long mMaxMemory = RUNTIME.maxMemory();
        final long mTotalMemory = RUNTIME.totalMemory();
        final long mFreeMemory = RUNTIME.freeMemory();
        final long mAllocatedMemory = mTotalMemory - mFreeMemory;

        @Override
        public String toString() {
            return String.format("max(%s)\n  allocated(%s)=total(%s)-free(%s);",
                    Utils.humanReadable(mMaxMemory), Utils.humanReadable(mAllocatedMemory),
                    Utils.humanReadable(mTotalMemory), Utils.humanReadable(mFreeMemory));
        }
    }


}
