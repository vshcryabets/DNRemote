package com.v2soft.dnremote.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import com.v2soft.AndLib.ui.R;
import com.v2soft.AndLib.ui.Adapters.CustomViewAdapter;
import com.v2soft.AndLib.ui.fragments.BaseFragment;
import com.v2soft.AndLib.ui.loaders.UDPScannerLoader;
import com.v2soft.AndLib.ui.views.IDataView;
import com.v2soft.dnremote.ApplicationSettings;
import com.v2soft.dnremote.DNRemoteApplication;
import com.v2soft.dnremote.IPCConstants;
import com.v2soft.dnremote.dao.Server;
import com.v2soft.dnremote.networking.BroadcastDiscovery;

/**
 * 
 * @author vshcryabets@gmail.com
 *
 */
public class DesktopsScannerFragment
extends BaseFragment<DNRemoteApplication, ApplicationSettings> 
implements LoaderCallbacks<List<Server>>,
OnItemClickListener{
    private List<InetAddress> mLocalAddresses;
    private List<InetAddress> mBroadcastAddresses;
    protected CustomViewAdapter<Server> mAdapter;

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
            getLoaderManager().initLoader(0, null, this);
            LoaderManager.enableDebugLogging(true);
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
        ListView list = (ListView) view.findViewById(android.R.id.list);
        mAdapter = new BluetoothDeviceAdapter(getActivity());
        list.setOnItemClickListener(this);
        list.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    // ===================================================================
    // Server list view
    // ===================================================================
    private class ServerListView extends LinearLayout implements IDataView<Server> {
        private Server mData;

        public ServerListView(Context context) {
            super(context);
            inflate(context, R.layout.v2andlib_listitem_twolines, this);
        }

        @Override
        public void setData(Server data) {
            mData = data;
            ((TextView)findViewById(R.id.v2andlib_1line)).setText(data.getName());
            ((TextView)findViewById(R.id.v2andlib_2line)).setText(data.getAddress());
        }

        @Override
        public Server getData() {
            return mData;
        }
    };

    private class BluetoothDeviceAdapter extends CustomViewAdapter<Server>{
        public BluetoothDeviceAdapter(
                Context context) {
            super(context, new CustomViewAdapterFactory<Server, IDataView<Server>>() {
                @Override
                public IDataView<Server> createView(Context context) {
                    return new ServerListView(context);
                }
            });
        }
    };
    // ===================================================================
    // Loader callback
    // ===================================================================
    @Override
    public Loader<List<Server>> onCreateLoader(int id, Bundle args) {
        return new UDPScannerLoader<Server>(getActivity(),
                new BroadcastDiscovery(
                        mLocalAddresses.get(0), 
                        mBroadcastAddresses.get(0)));
    }

    @Override
    public void onLoadFinished(Loader<List<Server>> arg0, List<Server> arg1) {
        mAdapter.setData(arg1);
    }

    @Override
    public void onLoaderReset(Loader<List<Server>> arg0) {
        mAdapter.clear();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
    // ===================================================================
    // list click listener
    // ===================================================================
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        final Server server = (Server) mAdapter.getItem(position);
        final Intent intent = new Intent();
        intent.putExtra(IPCConstants.EXTRA_SERVER, server);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
