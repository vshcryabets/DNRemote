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
package com.v2soft.dnremote.ui.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.v2soft.dnremote.ApplicationSettings;
import com.v2soft.dnremote.DNRemoteApplication;
import com.v2soft.dnremote.IPCConstants;
import com.v2soft.dnremote.R;
import com.v2soft.dnremote.dao.Server;
import com.v2soft.dnremote.ui.activities.DNAndroidClientActivity;
import com.v2soft.dnremote.ui.activities.DesktopsEditorActivity;

import com.v2soft.AndLib.ui.fragments.BaseFragment;
/**
 * Desktop profiles selection activity
 * @author vshcryabets@gmail.com
 *
 */

public class DesktopsSelectionFragment 
extends BaseFragment<DNRemoteApplication, ApplicationSettings>
implements OnItemClickListener {
    private ArrayAdapter<Server> mAdapter;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desktop_selection, null);
        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);
        registerForContextMenu(mListView);
        mAdapter = new ArrayAdapter<Server>(getActivity(),
                R.layout.listitem_serverselection,  
                new ArrayList<Server>());
        mListView.setAdapter(mAdapter);
        return view;
    }

    private void refreshConnectionsList() {
        final Collection<Server> profiles= mSettings.getProfiles();
        List<Server> servers = new ArrayList<Server>(profiles);
        servers.add(0, new Server(UUID.randomUUID(), 
                getString(R.string.btn_add_new_desktop), "", 0, false, null));
        mAdapter.clear();
        for (Server server : servers) {
            mAdapter.add(server);
        }
//        mAdapter.addAll(servers);
    }

    public void onResume(){
        super.onResume();
        refreshConnectionsList();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        if ( position == 0 ) {
            // add new desktop
            final Intent intent = new Intent(getActivity(), DesktopsEditorActivity.class);
            startActivity(intent);
            return;
        }
        connectToServer(mAdapter.getItem(position));
    }
    
    private void connectToServer(Server server) {
        final Intent intent = new Intent(getActivity(), DNAndroidClientActivity.class);
        intent.putExtra(IPCConstants.EXTRA_SERVER, server);
        startActivity(intent);
        
    }
    //============================== Context Menu =======================================
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.serverselection_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final Server profile = mAdapter.getItem(info.position);

        switch (item.getItemId()) {
        case R.id.delete_server:
            mSettings.getProfiles().remove(profile);
            mSettings.saveSettings();
            refreshConnectionsList();
            return true;
        case R.id.edit_server:
            final Intent intent = new Intent(getActivity(), DesktopsEditorActivity.class);
            intent.putExtra(IPCConstants.EXTRA_SERVER, profile);
            startActivity(intent);
            return true;
        case R.id.connect_server:
            connectToServer(profile);
            return true;
        default:
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
    }
}

