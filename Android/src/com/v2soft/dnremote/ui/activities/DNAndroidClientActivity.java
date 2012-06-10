package com.v2soft.dnremote.ui.activities;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;

import com.v2soft.dnremote.IPCConstants;
import com.v2soft.dnremote.R;
import com.v2soft.dnremote.R.layout;
import com.v2soft.dnremote.dao.Server;
import com.v2soft.styxlib.library.StyxClientManager;
import com.v2soft.styxlib.library.StyxFile;

public class DNAndroidClientActivity extends Activity {
    private static final String LOG_TAG = DNAndroidClientActivity.class.getSimpleName();
    private static final int TOTAL_WIDTH = 10000; 
    private StyxClientManager mConnection;
    private int mWidth, mHeight;
    private OutputStream mOut;
    private StyxFile mStyxFile;
    private boolean mRelativeMode = true;

    private int mMouseX= TOTAL_WIDTH/2, mMouseY = TOTAL_WIDTH/2;
    private float mOldX, mOldY;
    private Server mServer;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServer = getIntent().getParcelableExtra(IPCConstants.EXTRA_SERVER);
        setContentView(R.layout.main);
        Display display = getWindowManager().getDefaultDisplay();
        mHeight = display.getHeight();
        mWidth = display.getWidth();
    }

    @Override
    protected void onResume() {
        // connect to server
        try {
            mConnection = new StyxClientManager(InetAddress.getByName(mServer.getAddress()), 
                    mServer.getPort(), false);
            mConnection.connect();
            mStyxFile = new StyxFile(mConnection, "mouse");
            mOut = mStyxFile.openForWrite();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString(), e);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        try {
            mOut.close();
            mStyxFile.close();
            mConnection.close();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString(), e);
        }
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventType = event.getAction();
        switch (eventType) {
        case MotionEvent.ACTION_DOWN:
            mOldX = event.getX();
            mOldY = event.getY();
            break;
        case MotionEvent.ACTION_UP:
            mOldX = 0;
            mOldY = 0;
            break;
        case MotionEvent.ACTION_MOVE:
            float dx = event.getX()-mOldX;
            float dy = event.getY()-mOldY;
            mOldX = event.getX();
            mOldY = event.getY();
            mMouseX += dx*10000/mWidth;
            mMouseY += dy*10000/mHeight;

            if ( mMouseX < 0 ) {
                mMouseX = 0;
            } else if ( mMouseX > TOTAL_WIDTH ) {
                mMouseX = TOTAL_WIDTH;
            }
            if ( mMouseY < 0 ) {
                mMouseY = 0;
            } else if ( mMouseY > TOTAL_WIDTH ) {
                mMouseY = TOTAL_WIDTH;
            }

            byte [] buffer = new byte[]{
                    (byte) (mMouseX & 0xFF),
                    (byte) ((mMouseX >> 8) & 0xFF),
                    (byte) (mMouseY & 0xFF),
                    (byte) ((mMouseY >> 8) & 0xFF)
            };
            try {
                mOut.write(buffer);
                mOut.flush();
            } catch (IOException e) {
                Log.d(LOG_TAG, e.toString(), e);
            }
            break;
        default:
            break;
        }
        return super.onTouchEvent(event);
    }

}