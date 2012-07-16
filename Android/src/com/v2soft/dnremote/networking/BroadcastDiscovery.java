package com.v2soft.dnremote.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.v2soft.AndLib.networking.UDPBroadcastDiscovery;
import com.v2soft.dnremote.dao.Server;

/**
 * 
 * @author V.Shcriyabets (vshcryabets@gmail.com)
 *
 */
public class BroadcastDiscovery extends UDPBroadcastDiscovery {
    private static final String REQUEST_UUID = "DNRemoteServer?";
    private static final String KEY_SERVERTYPE = "servertype";

    public BroadcastDiscovery(InetAddress source, InetAddress target) {
        super(5, 1500, 1235, source, target);
    }

    @Override
    protected void handleIncomePacket(DatagramPacket packet) {
        byte [] data = packet.getData();
        String received = new String(data, 0, packet.getLength());
        try {
            JSONObject serverJSON = new JSONObject(received);
            if ( !serverJSON.get(KEY_SERVERTYPE).equals("DNR") ) {
                return;
            }
            final Server server = new Server();
            server.setName(serverJSON.get(Server.KEY_NAME).toString());
            server.setAddress(packet.getAddress().getHostAddress());
            server.setPort(Integer.parseInt(serverJSON.get(Server.KEY_PORT).toString()));
            server.setId(UUID.fromString(serverJSON.getString(Server.KEY_ID)));
            if (  mListener != null ) {
                mListener.onNewServer(server);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected DatagramPacket prepareRequest() {
        final byte[] buf = REQUEST_UUID.getBytes();
        return new DatagramPacket(buf, buf.length);
    }
}
