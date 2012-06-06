package com.v2soft.dnremote;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;

import com.v2soft.styxlib.library.StyxClientManager;
import com.v2soft.styxlib.library.StyxFile;

public class DNAndroidClientActivity extends Activity {
    private static final String LOG_TAG = DNAndroidClientActivity.class.getSimpleName();
    private StyxClientManager mConnection;
    private int mWidth, mHeight;
    private OutputStream mOut;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
            mConnection = new StyxClientManager(InetAddress.getByName("192.168.1.5"), 8080, false);
            mConnection.connect();
            StyxFile file = new StyxFile(mConnection, "mouse");
            mOut = file.openForWrite();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString(), e);
        }
        Display display = getWindowManager().getDefaultDisplay();
        mHeight = display.getHeight();
        mWidth = display.getWidth();
        Log.d("DATADISPLAY", mWidth+"--"+mHeight);        
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) (event.getX()*10000/mWidth);
        int y = (int) (event.getY()*10000/mHeight);
//        Log.d("DATA", x+"--"+y);
        byte [] buffer = new byte[]{
                (byte) (x & 0xFF),
            (byte) ((x >> 8) & 0xFF),
            (byte) (y & 0xFF),
            (byte) ((y >> 8) & 0xFF)
        };
        try {
            mOut.write(buffer);
            mOut.flush();
        } catch (IOException e) {
            Log.d(LOG_TAG, e.toString(), e);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        mConnection.close();
        super.onDestroy();
    }
}