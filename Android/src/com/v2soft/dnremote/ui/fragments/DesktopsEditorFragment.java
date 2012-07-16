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

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import com.v2soft.dnremote.ui.activities.DesktopsScanActivity;
/**
 * Desktop editor fragment
 * @author vshcryabets@gmail.com
 *
 */

public class DesktopsEditorFragment 
extends BaseFragment<DNRemoteApplication, ApplicationSettings> {
    private static final String KEY_CREATE_NEW = "new";
    private static final int REQUEST_SCAN = 1;
    private Server mServer;
    private EditText mConnectionName, mAddress, mPort;
    private CheckBox mRelativeCheck;
    private boolean mCreateNew;
//    private ProgressDialogFragment mProgressDialogFragment;

    public static Fragment newInstance(Server server, boolean createNew) {
        DesktopsEditorFragment result = new DesktopsEditorFragment();
        Bundle params = new Bundle();
        params.putParcelable(IPCConstants.EXTRA_SERVER, server);
        params.putBoolean(KEY_CREATE_NEW, createNew);
        result.setArguments(params);
        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Bundle data = getArguments();
        if ( savedInstanceState != null ) {
            data = getArguments();
        }
        mServer = data.getParcelable(IPCConstants.EXTRA_SERVER);
        mCreateNew = data.getBoolean(KEY_CREATE_NEW);
        if ( mCreateNew  ) {
            mServer = new Server(UUID.randomUUID(), "", "", 8080, false, null);
        }
        View view = inflater.inflate(R.layout.fragment_desktop_editor, null);
        registerOnClickListener(new int[]{R.id.btnCancel, R.id.btnSave, R.id.btnScan}, view);
        mConnectionName = (EditText) view.findViewById(R.id.editConnectionName);
        mAddress = (EditText) view.findViewById(R.id.editAddress);
        mPort = (EditText) view.findViewById(R.id.editPort);
        mRelativeCheck = (CheckBox) view.findViewById(R.id.checkRelative);
        fillUI();
        return view;
    }

    private void fillUI() {
        mConnectionName.setText(mServer.getName());
        mAddress.setText(mServer.getAddress());
        mPort.setText(String.valueOf(mServer.getPort()));
        mRelativeCheck.setChecked(!mServer.isRelativeMode());
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
        case R.id.btnScan:
            startActivityForResult(new Intent(getActivity(), DesktopsScanActivity.class), 
                    REQUEST_SCAN);
        default:
            break;
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == REQUEST_SCAN && resultCode == Activity.RESULT_OK ) {
            mServer = data.getParcelableExtra(IPCConstants.EXTRA_SERVER);
            fillUI();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

