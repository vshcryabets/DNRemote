package com.v2soft.dnremote.dao;

import org.json.JSONException;
import org.json.JSONObject;

import com.v2soft.AndLib.dao.AbstractProfile;
import com.v2soft.AndLib.dao.JSONSerializable;

public class Server 
    extends AbstractProfile {
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
    // ==========================================================
    // AbstractProfile methods
    // ==========================================================
    @Override
    public void updateFrom(AbstractProfile profile) {
        Server server = (Server) profile;
        mServer = server.mServer;
        mPort = server.mPort;
    }
}
