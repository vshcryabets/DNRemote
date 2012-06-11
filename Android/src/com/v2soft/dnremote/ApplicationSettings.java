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
package com.v2soft.dnremote;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;

import android.content.Context;
import android.util.Log;

import com.v2soft.AndLib.application.BaseApplicationSettings;
import com.v2soft.AndLib.dao.ProfileStorage;
import com.v2soft.dnremote.dao.Server;

/**
 * Application settings class 
 * @author V.Shcriyabets (vshcryabets@gmail.com)
 *
 */
public class ApplicationSettings extends BaseApplicationSettings {
    //-----------------------------------------------------------------------
    // Constants
    //-----------------------------------------------------------------------
    private static final String LOG_TAG = ApplicationSettings.class.getSimpleName();
    //-----------------------------------------------------------------------
    // Private fields
    //-----------------------------------------------------------------------
    private ProfileStorage<Server> mDesktops;

    public ApplicationSettings(Context context) {
        super(context);
        mDesktops = new ProfileStorage<Server>(Server.sFactory);
        loadSettings();
    }

    @Override
    protected void loadSettings() {
        // load desktop servers
        try {
            FileInputStream input = mContext.openFileInput("desktops.json");
            mDesktops.load(input);
            input.close();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString(), e);
        }        
    }

    public Collection<Server> getProfiles() {
        return mDesktops;
    }

    @Override
    public void saveSettings() {
        try {
            FileOutputStream out = mContext.openFileOutput("desktops.json", 
                    Context.MODE_PRIVATE);
            mDesktops.save(out);
            out.close();
        } catch (Exception e) {
            Log.d(LOG_TAG, e.toString(), e);
        }
    }
}
