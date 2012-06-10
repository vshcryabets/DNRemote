package com.v2soft.dnremote.ui.fragments;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    private static final String LOG_TAG = DesktopsSelectionFragment.class.getSimpleName();

    private ArrayAdapter<Server> mAdapter;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desktop_selection, null);
        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setOnItemClickListener(this);
        registerForContextMenu(mListView);
        return view;
    }

    public void onResume(){
        super.onResume();
        try {
            final Collection<Server> profiles= mSettings.getProfiles();
            List<Server> servers = new ArrayList<Server>(profiles);
            servers.add(0, new Server(getString(R.string.btn_add_new_desktop), "", 0));
            mAdapter = new ArrayAdapter<Server>(getActivity(),
                    R.layout.listitem_serverselection,  
                    servers);
            mListView.setAdapter(mAdapter);
            //            if ( profiles.size() < 1 ) {
            //                showDialog(BaseActivity.DIALOG_CREATE_PROFILE);
            //            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        final Server server = mAdapter.getItem(position);
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

