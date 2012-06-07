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
