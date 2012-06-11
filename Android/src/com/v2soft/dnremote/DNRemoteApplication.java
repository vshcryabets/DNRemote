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

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import com.v2soft.AndLib.application.BaseApplication;

@ReportsCrashes(formKey = "",
mailTo = "info@2vsoft.com",
mode = ReportingInteractionMode.TOAST,
resToastText = R.string.crash_toast_text)

public class DNRemoteApplication 
    extends BaseApplication<ApplicationSettings> {
    //-----------------------------------------------------------------------
    // Constants
    //-----------------------------------------------------------------------
    public static final String LOG_TAG = DNRemoteApplication.class.getSimpleName();
    public static final boolean DEBUG = false; // SHOULD BE ALWAYS FALSE for alpha build
    //-----------------------------------------------------------------------
    // Initializers
    //-----------------------------------------------------------------------
    @Override
    public void onCreate() {
        if ( !DNRemoteApplication.DEBUG ) {
            ACRA.init(this);
        }
        super.onCreate();
    }
    
    @Override
    protected void onCreateSettings(ApplicationSettings settings) {
        super.onCreateSettings(new ApplicationSettings(this));
    }
}
