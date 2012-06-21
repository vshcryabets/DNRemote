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
package com.v2soft.dnremote.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.v2soft.dnremote.R;
import com.v2soft.dnremote.ui.fragments.DesktopsSelectionFragment;
/**
 * Desktop profiles selection activity
 * @author vshcryabets@gmail.com
 *
 */
public class DesktopsSelectionActivity 
extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v2andlib_single_fragment);
        
        if (savedInstanceState == null) {
            // Do first time initialization -- add fragment.
            DesktopsSelectionFragment newFragment = new DesktopsSelectionFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.v2andLibFragment, newFragment).commit();
        }        
    }
}

