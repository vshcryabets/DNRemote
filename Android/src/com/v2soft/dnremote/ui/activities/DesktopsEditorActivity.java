package com.v2soft.dnremote.ui.activities;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.v2soft.dnremote.IPCConstants;
import com.v2soft.dnremote.R;
import com.v2soft.dnremote.dao.Server;
import com.v2soft.dnremote.ui.fragments.DesktopsEditorFragment;
/**
 * Desktop profiles selection activity
 * @author vshcryabets@gmail.com
 *
 */
public class DesktopsEditorActivity 
extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v2andlib_single_fragment);
        
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            // Do first time initialization -- add fragment.
            Server server = null;
            boolean createNew = true;
            if ( bundle != null && bundle.containsKey(IPCConstants.EXTRA_SERVER)) {
                server = bundle.getParcelable(IPCConstants.EXTRA_SERVER);
                createNew = false;
            }
            Fragment newFragment = new DesktopsEditorFragment(
                    server, createNew);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.v2andLibFragment, newFragment).commit();
        }        
    }
}

