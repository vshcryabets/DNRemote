package com.v2soft.dnremote;

import java.io.FileInputStream;
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
}
