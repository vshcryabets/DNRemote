// Copyright (C) 2012 V.Shcryabets (vshcryabets@gmail.com)
//
// Author:  Vladimir Shcryabets <vshcryabets@gmail.com>
//
// This file is part of DNAndroidClient.
// This program is free software; you can redistribute it and/or
// modify it under the terms of version 3 of the GNU General 
// Public License as published by the Free Software Foundation.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// General Public License for more details.
//
// You should have received a copy of the GNU General Public
// License along with this program; if not, write to the
// Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA 02111-1307, USA.
package com.v2soft.dnremote.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;

import com.v2soft.dnremote.IPCConstants;
import com.v2soft.dnremote.R;
import com.v2soft.dnremote.dao.PointerEvent;
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
    private boolean mRelativeMode;

    private float mOldX, mOldY, mDownX, mDownY;
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
        mRelativeMode = mServer.isRelativeMode();
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
        float dx;
        float dy;
        switch (eventType) {
        case MotionEvent.ACTION_DOWN:
            mOldX = event.getX();
            mOldY = event.getY();
            mDownX = mOldX;
            mDownY = mOldY;
            return true;
        case MotionEvent.ACTION_UP:
            //            dx = Math.abs(event.getX()-mDownX);
            //            dy = Math.abs(event.getY()-mDownY);
            //            mOldX = 0;
            //            mOldY = 0;
            //            if ( dx+dy < 10 ) {
            //                Log.d(LOG_TAG, "Click!!!!!!!!!!!! ");
            //                // click
            //                int cmd = 20000;
            //                byte [] buffer = new byte[]{
            //                        (byte) (cmd & 0xFF),
            //                        (byte) ((cmd >> 8) & 0xFF),
            //                        (byte) (mMouseY & 0xFF),
            //                        (byte) ((mMouseY >> 8) & 0xFF)
            //                };
            //                try {
            //                    mOut.write(buffer);
            //                    mOut.flush();
            //                } catch (IOException e) {
            //                    Log.d(LOG_TAG, e.toString(), e);
            //                }                
            //            }
            return true;

        case MotionEvent.ACTION_MOVE:
            PointerEvent trEvent = null;
            if ( mRelativeMode ) {
                dx = event.getX()-mOldX;
                dy = event.getY()-mOldY;
                mOldX = event.getX();
                mOldY = event.getY();

                trEvent = new PointerEvent((short)0, 
                        PointerEvent.MOVE, 
                        true, 
                        (int)dx, 
                        (int)dy, 
                        0);
            } else {
                trEvent = new PointerEvent((short)0, 
                        PointerEvent.MOVE, 
                        false, 
                        (int)(event.getX()*TOTAL_WIDTH/mWidth), 
                        (int)(event.getY()*TOTAL_WIDTH/mHeight), 
                        0);
            }
            sendEvent(trEvent);
            return true;
        default:
            break;
        }
        return super.onTouchEvent(event);
    }

    private void sendEvent(PointerEvent event) {
        try {
            event.write(mOut);
            mOut.flush();
        } catch (IOException e) {
            Log.d(LOG_TAG, e.toString(), e);
        }                
    }

}