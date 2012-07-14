/*
 * Copyright (C) 2012 V.Shcryabets (vshcryabets@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.v2soft.dnremote.ui.fragments;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.v2soft.AndLib.ui.fragments.BaseFragment;
import com.v2soft.dnremote.ApplicationSettings;
import com.v2soft.dnremote.DNRemoteApplication;
import com.v2soft.dnremote.networking.BroadcastDiscovery;

/**
 * 
 * @author vshcryabets@gmail.com
 *
 */
public class DesktopsScannerFragment 
extends BaseFragment<DNRemoteApplication, ApplicationSettings>  {
    private BroadcastDiscovery mDiscovery;
    private List<InetAddress> mLocalAddresses;
    private List<InetAddress> mBroadcastAddresses;

    public static Fragment newInstance() {
        return new DesktopsScannerFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            mBroadcastAddresses = new LinkedList<InetAddress>();
            mLocalAddresses = new LinkedList<InetAddress>();
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                if ( intf.isLoopback() ) continue;

                for (InterfaceAddress interfaceAddress : intf.getInterfaceAddresses()) {
                    if ( interfaceAddress.getBroadcast() == null ) continue;
                    mBroadcastAddresses.add(interfaceAddress.getBroadcast());
                    mLocalAddresses.add(interfaceAddress.getAddress());
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(com.v2soft.AndLib.ui.R.layout.v2andlib_fragment_list, null);
        mDiscovery = new BroadcastDiscovery();
        return view;
    }

    @Override
    public void onResume() {
        try {
            mDiscovery.startDiscovery(1235, 
                    mLocalAddresses.get(0), mBroadcastAddresses.get(0));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        
    }
}
