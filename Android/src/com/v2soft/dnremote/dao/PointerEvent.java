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
package com.v2soft.dnremote.dao;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author vshcryabets@gmail.com
 *
 */
public class PointerEvent {
    // ===========================================================
    // Constants
    // ============================================================
    public static final byte POINTER_EVENT = 1;
    public static final byte KEYBOARD_EVENT = 2;
    public static final byte SPECIAL_KEY = 3;
    public static final byte HOT_KEY_COMMAND = 4;
    
    public static final byte MOVE = 1;
    public static final byte POINTER_DOWN = 2;
    public static final byte POINTER_UP = 3;
    public static final byte POINTER_CLICK = 4;
    
    private static final int MESSAGE_SIZE = 10;
    // ===========================================================
    // Class fields
    // ============================================================
    private short mPointerID;
    private byte mPointerEventType;
    private boolean mRelative;
    private int mX, mY;
    private int mButtonID;

    public PointerEvent(short pointerID, byte pointerEventType,
            boolean relative, int x, int y,
            int buttonID ) {
        mPointerID = pointerID;
        mPointerEventType = pointerEventType;
        mRelative = relative;
        mX = x;
        mY = y;
        mButtonID = buttonID;
    }

    // ===========================================================
    // Getters
    // ============================================================
    public short getPointerID() {return mPointerID;}
    public byte getPointerEventType() {return mPointerEventType;}
    public boolean getRelative(){return mRelative;}
    public int getX(){return mX;}
    public int getY(){return mY;}
    public int getButtonID(){return mButtonID;}

    public void write(OutputStream out) throws IOException {
        byte[] buffer = new byte[MESSAGE_SIZE];
        buffer[0] = POINTER_EVENT;
        buffer[1] = (byte)mPointerID;
        buffer[2] = mPointerEventType;
        buffer[3] = (byte) ( mRelative ? 1 : 0);
        buffer[4] = (byte) (mX >> 8);
        buffer[5] = (byte) (mX & 0xFF);
        buffer[6] = (byte) (mY >> 8);
        buffer[7] = (byte) (mY & 0xFF);
        buffer[8] = (byte) (mButtonID >> 8);
        buffer[9] = (byte) (mButtonID & 0xFF);
        out.write(buffer);
    }

}
