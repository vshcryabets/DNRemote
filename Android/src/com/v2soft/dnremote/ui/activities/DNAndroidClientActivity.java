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

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;

import com.v2soft.dnremote.IPCConstants;
import com.v2soft.dnremote.R;
import com.v2soft.dnremote.dao.PointerEvent;
import com.v2soft.dnremote.dao.Server;
import com.v2soft.styxlib.library.StyxClientManager;
import com.v2soft.styxlib.library.StyxFile;
import com.v2soft.styxlib.library.exceptions.StyxException;

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
    private FrameLayout mTouchFrame;

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
        mTouchFrame = (FrameLayout) findViewById(R.id.touchView);
        mTouchFrame.setBackgroundColor(getResources().getColor(R.color.touch_unready));
        new AsyncConnection(mTouchFrame, mTouchListener).execute(new Server[]{mServer});
    }

    @Override
    protected void onDestroy() {
        try {
            closeConnection();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString(), e);
        }
        super.onDestroy();
    }

    // ===============================================================
    // Touch handler
    // ===============================================================
    private OnTouchListener mTouchListener = new OnTouchListener() {
        
        @Override
        public boolean onTouch(View v, MotionEvent event) {
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
            return false;
        }
    };
    // ===============================================================
    // Async connection
    // ===============================================================
    private class AsyncConnection extends AsyncTask<Server, Void, Boolean> {
        private FrameLayout mTouchFrame;
        private OnTouchListener mListener;
        
        public AsyncConnection(FrameLayout frame, OnTouchListener listener) {
            mTouchFrame = frame;
            mListener = listener;
        }
        
        @Override
        protected void onPreExecute() {
            ProgressBar bar = new ProgressBar(mTouchFrame.getContext());
            mTouchFrame.addView(bar, new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
                    LayoutParams.WRAP_CONTENT, 
                    Gravity.CENTER));
            super.onPreExecute();
        }
        
        @Override
        protected Boolean doInBackground(Server... params) {
            // connect to server
            try {
                connectToServer(mServer);
//                Thread.sleep(5000);
                return true;
            } catch (Exception e) {
                Log.d(LOG_TAG, e.toString(), e);
            }           
            return false;
        }
        
        @Override
        protected void onPostExecute(Boolean result) {
            if ( result ) {
                mTouchFrame.removeAllViews();
                mTouchFrame.setOnTouchListener(mListener);
                mTouchFrame.setBackgroundColor(getResources().getColor(R.color.touch_ready));
            } else {
                // TODO show something, about error
            }
            super.onPostExecute(result);
        }
    };
    // ===============================================================
    // Server routines
    // ===============================================================
    private void sendEvent(PointerEvent event) {
        try {
            event.write(mOut);
            mOut.flush();
        } catch (IOException e) {
            Log.d(LOG_TAG, e.toString(), e);
        }                
    }
    
    private void connectToServer(Server server) throws IOException, StyxException, InterruptedException, TimeoutException {
        mConnection = new StyxClientManager(InetAddress.getByName(mServer.getAddress()), 
                mServer.getPort(), false);
        mConnection.connect();
        mStyxFile = new StyxFile(mConnection, "mouse");
        mOut = mStyxFile.openForWrite();       
    }

    private void closeConnection() throws IOException {
        mOut.close();
        mStyxFile.close();
        mConnection.close();
    }

}