package com.v2soft.dnremote.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.v2soft.AndLib.networking.UDPBroadcastDiscovery;

/**
 * 
 * @author V.Shcriyabets (vshcryabets@gmail.com)
 *
 */
public class BroadcastDiscovery extends UDPBroadcastDiscovery {
    private static final String REQUEST_UUID = "DNRemoteServer?";

    public BroadcastDiscovery() {
        super(5, 1500);
    }

    @Override
    protected void handleIncomePacket(DatagramSocket socket, DatagramPacket packet) {
        byte [] data = packet.getData();
        String received = new String(data, 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);
    }

    @Override
    protected void sendRequest(InetAddress target, DatagramSocket socket) {
        byte[] buf;
        buf = REQUEST_UUID.getBytes();        
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length, target, mTargetPort);
            socket.send(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }        
    }
}
