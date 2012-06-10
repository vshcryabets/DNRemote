package com.v2soft.dnremote.dao;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

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
    //-----------------------------------------------------------------------
    // Staic fields
    //-----------------------------------------------------------------------    
    public static final JSONSerializable.Factory<Server> sFactory = new Factory<Server>() {
        @Override
        public Server create(JSONObject data) throws JSONException {
            return new Server(data);
        }
        public Server create(String name) {
            return new Server(name, "", 8080);
        }
    };
    // ==========================================================
    // Class fields
    // ==========================================================
    private String mServer;
    private int mPort;
    // ==========================================================
    // Constructors
    // ==========================================================
    public Server(String name, String server, int port) {
        super(name);
        mServer = server;
        mPort = port;
    }

    public Server(JSONObject in) throws JSONException {
        super(in);
        mServer = in.getString(KEY_SERVER);
        mPort = in.getInt(KEY_PORT);
    }
    public Server(Parcel in) {
        this(in.readString(), in.readString(), in.readInt() );
    }
    public String getAddress() {return mServer;}
    public int getPort() {return mPort;}
    // ==========================================================
    // AbstractProfile methods
    // ==========================================================
    @Override
    public void updateFrom(AbstractProfile profile) {
        Server server = (Server) profile;
        mServer = server.mServer;
        mPort = server.mPort;
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
        dest.writeString(mName);
        dest.writeString(mServer);
        dest.writeInt(mPort);
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
    public void setAddress(String string) {
        mServer = string;
    }
    //----------------------------------------------------------------
    // JSON methods
    //----------------------------------------------------------------
    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject result = super.toJSON();
        result.put(KEY_SERVER, mServer);
        result.put(KEY_PORT, mPort);
        return result;
    }
}
