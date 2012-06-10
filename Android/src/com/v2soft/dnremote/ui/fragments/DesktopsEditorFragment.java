package com.v2soft.dnremote.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.v2soft.AndLib.ui.fragments.BaseFragment;
import com.v2soft.dnremote.ApplicationSettings;
import com.v2soft.dnremote.DNRemoteApplication;
import com.v2soft.dnremote.R;
import com.v2soft.dnremote.dao.Server;
/**
 * Desktop editor fragment
 * @author vshcryabets@gmail.com
 *
 */

public class DesktopsEditorFragment 
extends BaseFragment<DNRemoteApplication, ApplicationSettings> {

    private static final String LOG_TAG = DesktopsEditorFragment.class.getSimpleName();

    private Server mServer;
    private boolean mCreateNew;
    private EditText mConnectionName, mAddress, mPort;

    public DesktopsEditorFragment(Server server, boolean createNew) {
        mServer = server;
        mCreateNew = createNew;
        if ( mCreateNew ) {
            mServer = new Server("", "", 8080);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desktop_editor, null);
        registerOnClickListener(new int[]{R.id.btnCancel, R.id.btnSave}, view);
        mConnectionName = (EditText) view.findViewById(R.id.editConnectionName);
        mAddress = (EditText) view.findViewById(R.id.editAddress);
        mPort = (EditText) view.findViewById(R.id.editPort);

        mConnectionName.setText(mServer.getName());
        mAddress.setText(mServer.getAddress());
        mPort.setText(String.valueOf(mServer.getPort()));
        return view;
    }

    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        switch (id) {
        case R.id.btnSave:
            mServer.setName(mConnectionName.getEditableText().toString());
            mServer.setAddress(mAddress.getEditableText().toString());
            if ( mCreateNew ) {
                // save new
                mSettings.getProfiles().add(mServer);
            } else {
                // update
//                mSettings.getProfiles().
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

