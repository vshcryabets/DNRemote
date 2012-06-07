package com.v2soft.dnremote.ui.activities;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.v2soft.dnremote.R;
import com.v2soft.dnremote.ui.fragments.DesktopsSelectionFragment;
/**
 * Desktop profiles selection activity
 * @author vshcryabets@gmail.com
 *
 */
public class DesktopsSelectionActivity 
extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v2andlib_single_fragment);
        
        if (savedInstanceState == null) {
            // Do first time initialization -- add fragment.
            DesktopsSelectionFragment newFragment = new DesktopsSelectionFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.v2andLibFragment, newFragment).commit();
        }        
    }
}

