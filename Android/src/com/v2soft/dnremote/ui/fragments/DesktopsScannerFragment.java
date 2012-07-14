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

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.UUID;

import com.v2soft.AndLib.ui.fragments.BaseFragment;
import com.v2soft.dnremote.ApplicationSettings;
import com.v2soft.dnremote.DNRemoteApplication;
import com.v2soft.dnremote.IPCConstants;
import com.v2soft.dnremote.R;
import com.v2soft.dnremote.dao.Server;
/**
 * Desktop editor fragment
 * @author vshcryabets@gmail.com
 *
 */

public class DesktopsScannerFragment 
extends BaseFragment<DNRemoteApplication, ApplicationSettings> {

    private static final String LOG_TAG = DesktopsScannerFragment.class.getSimpleName();
	private static final String KEY_CREATE_NEW = "";

    private Server mServer;
    private EditText mConnectionName, mAddress, mPort;
    private CheckBox mRelativeCheck;
    private boolean mCreateNew;

    public static Fragment newInstance() {
    	DesktopsScannerFragment result = new DesktopsScannerFragment();
    	return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//    	Bundle data = getArguments();
//    	if ( savedInstanceState != null ) {
//    		data = getArguments();
//    	}
//    	mServer = data.getParcelable(IPCConstants.EXTRA_SERVER);
//    	mCreateNew = data.getBoolean(KEY_CREATE_NEW);
    	if ( mCreateNew  ) {
    		 mServer = new Server(UUID.randomUUID(), "", "", 8080, false, null);
    	}
        View view = inflater.inflate(R.layout.fragment_desktop_editor, null);
        registerOnClickListener(new int[]{R.id.btnCancel, R.id.btnSave}, view);
        mConnectionName = (EditText) view.findViewById(R.id.editConnectionName);
        mAddress = (EditText) view.findViewById(R.id.editAddress);
        mPort = (EditText) view.findViewById(R.id.editPort);
        mRelativeCheck = (CheckBox) view.findViewById(R.id.checkRelative);
        
        mConnectionName.setText(mServer.getName());
        mAddress.setText(mServer.getAddress());
        mPort.setText(String.valueOf(mServer.getPort()));
        mRelativeCheck.setChecked(!mServer.isRelativeMode());
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    	outState.putParcelable(IPCConstants.EXTRA_SERVER, mServer);
    	outState.putBoolean(KEY_CREATE_NEW, mCreateNew);
    	super.onSaveInstanceState(outState);
    }
    
    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        switch (id) {
        case R.id.btnSave:
            mServer.setName(mConnectionName.getEditableText().toString());
            mServer.setAddress(mAddress.getEditableText().toString());
            mServer.setRelative(!mRelativeCheck.isChecked());
            if ( mCreateNew ) {
                // save new
                mSettings.getProfiles().add(mServer);
            } else {
                // update
                for ( Server server : mSettings.getProfiles() ) {
                    if ( server.equals(mServer) ) {
                        server.updateFrom(mServer);
                    }
                }
            }
            mSettings.saveSettings();
            getActivity().finish();
            break;
        case R.id.btnCancel:
            getActivity().finish();
            break;
        default:
            break;
        }
    }
}

