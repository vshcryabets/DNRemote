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

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import com.v2soft.AndLib.dao.AbstractProfile;
import com.v2soft.AndLib.dao.JSONSerializable;

/**
 * Desktop connection parameters
 * @author vshcryabets@gmail.com
 *
 */
public class Server 
extends AbstractProfile 
implements Parcelable {
    // ==========================================================
    // Constants
    // ==========================================================
    protected final static String KEY_SERVER = "server";
    protected final static String KEY_PORT = "port";
    protected final static String KEY_ID = "id";
    protected final static String KEY_RELATIVE = "relative";
    //-----------------------------------------------------------------------
    // Static fields
    //-----------------------------------------------------------------------    
    public static final JSONSerializable.Factory<Server> sFactory = new Factory<Server>() {
        @Override
        public Server create(JSONObject data) throws JSONException {
            return new Server(data);
        }
        public Server create(String name) {
            return new Server(UUID.randomUUID(), name, "", 8080, true, null);
        }
    };
    // ==========================================================
    // Class fields
    // ==========================================================
    private UUID mId;
    private String mServer;
    private int mPort;
    private boolean mRelative;
    private byte [] mIPAddr; // some phones too long resolves ip adress from ip string, this variable is to resolve this problem
    // ==========================================================
    // Constructors
    // ==========================================================
    public Server(UUID id, String name, String server, int port,
            boolean relative, byte[] addr) {
        super(name);
        mId = id;
        mServer = server;
        mPort = port;
        mRelative = relative;
        mIPAddr = addr;
    }

    public Server(JSONObject in) throws JSONException {
        this(UUID.fromString(in.getString(KEY_ID)),
                in.getString(KEY_NAME),
                in.getString(KEY_SERVER),
                in.getInt(KEY_PORT),
                in.getBoolean(KEY_RELATIVE),
                null);
    }
    
    public Server(Parcel in) {
        this( UUID.fromString(in.readString()),
                in.readString(), 
                in.readString(), 
                in.readInt(),
                in.readByte() == 1,
                readIPAddr(in));
    }
    private static byte[] readIPAddr(Parcel in) {
        int count = in.readInt();
        byte [] res = new byte[count];
        in.readByteArray(res);
        return res;
    }

    public String getAddress() {return mServer;}
    public int getPort() {return mPort;}
    public boolean isRelativeMode() {return mRelative;}
    
    public void setIPAddr(byte [] mIPAddr) {
        this.mIPAddr = mIPAddr;
    }
    public void setAddress(String string) {
        mServer = string;
    }
    public void setRelative(boolean b) {
        mRelative = b;
    }
    public byte [] getIPAddr() {
        return mIPAddr;
    }
    // ==========================================================
    // AbstractProfile methods
    // ==========================================================
    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if ( o.getClass().equals(Server.class)) {
            return mId.equals(((Server)o).mId);
        }
        return false;
    }

    @Override
    public void updateFrom(AbstractProfile profile) {
        Server server = (Server) profile;
        mServer = server.mServer;
        mPort = server.mPort;
        mName = server.mName;
        mRelative = server.mRelative;
    }

    @Override
    public String toString() {
        return mName;
    }
    // ==========================================================
    // Parcelable methods
    // ==========================================================
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId.toString());
        dest.writeString(mName);
        dest.writeString(mServer);
        dest.writeInt(mPort);
        dest.writeByte((byte) (mRelative ? 1 : 0));
        if ( mIPAddr != null ) {
            dest.writeInt(mIPAddr.length);
            dest.writeByteArray(mIPAddr);
        } else {
            dest.writeInt(0);
        }
    }

    public static final Parcelable.Creator<Server> CREATOR = 
            new Parcelable.Creator<Server>() {
        public Server createFromParcel(Parcel in) {
            return new Server(in);
        }

        public Server[] newArray(int size) {
            return new Server[size];
        }
    };

    //----------------------------------------------------------------
    // JSON methods
    //----------------------------------------------------------------
    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject result = super.toJSON();
        result.put(KEY_SERVER, mServer);
        result.put(KEY_PORT, mPort);
        result.put(KEY_ID, mId.toString());
        result.put(KEY_RELATIVE, mRelative);
        return result;
    }

}
