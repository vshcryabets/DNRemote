package com.v2soft.dnremote;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.v2soft.SecureJabber.DAO.Contact;
import com.v2soft.SecureJabber.DAO.Group;

/**
 * 
 * @author V.Shcriyabets (vshcryabets@gmail.com)
 *
 */
public class ApplicationSettings {
    //-----------------------------------------------------------------------
    // Constants
    //-----------------------------------------------------------------------
    //    private static final String LOG_TAG = ApplicationSettings.class.getSimpleName();
    private static final String PREF_ENABLE_XML_CONSOLE = "server_ip";
    public static final String PREF_SAVE_MESSAGE_HISTORY = "key_application_history";
    public static final String PREF_ENABLE_LANDSCAPE = "key_application_landscape";
    public static final String PREF_SHOW_OFFLINE = "key_roster_show_offline";
    public static final String PREF_SHOW_GROUPS = "key_roster_show_groups";
    public static final String PREF_SHOW_WRITING_TO_YOU = "key_chat_show_write";
    public static final String PREF_SHOW_SMILES = "key_chat_show_smiles";
    public static final String PREF_ON_NEW_MESSAGE = "key_events_onnewmessage";
    public static final String PREF_ON_NEW_UNKNOWN_MESSAGE = "key_events_onnewunknown";
    public static final String PREF_ENABLE_SOUNDS = "key_sounds_enabled";
    public static final String PREF_ENABLE_VIBRO = "key_sounds_vibro_enabled";
    public static final String PREF_KEY_PASSWORD = "key_password";
    //-----------------------------------------------------------------------
    // Private fields
    //-----------------------------------------------------------------------
    private SharedPreferences mSettings;
    private SharedPreferences.Editor mPrefEditor;
    private Context mContext;
    // Values of preferences
    private boolean isXMLConsoleEnabled;

    // Вести историю сообщений
    private boolean saveMessageHistory;
    // Разрешить поворот экрана
    private boolean enableLandscape;
    // Показывать контакты не в сети
    private boolean showOffline;
    // Показывать группы
    private boolean showGroups;
    // Показывать \"вам печатают\"
    private boolean showWritingToYou;
    // Графические смайлики
    private boolean showSmiles;
    // Открывать окно при получении нового сообщения
    private boolean onNewMessage;
    // Игнорировать сообщения от пользователей не из контакт листа
    private boolean onNewUnknownMessage;
    // Разрешить звуки
    private boolean enableSounds;
    // Разрешить вибрацию
    private boolean enableVibro;

    private List<Contact> mLastChats;

    public boolean isSaveMessageHistory() {
        return saveMessageHistory;
    }

    public void setSaveMessageHistory(boolean saveMessageHistory) {
        this.saveMessageHistory = saveMessageHistory;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_SAVE_MESSAGE_HISTORY, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_SAVE_MESSAGE_HISTORY, saveMessageHistory);
        mPrefEditor.commit();
    }

    public boolean isEnableLandscape() {
        return enableLandscape;
    }

    public void setEnableLandscape(boolean enableLandscape) {
        this.enableLandscape = enableLandscape;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_ENABLE_LANDSCAPE, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_ENABLE_LANDSCAPE, enableLandscape);
        mPrefEditor.commit();
    }

    public boolean isShowOffline() {
        return showOffline;
    }

    public void setShowOffline(boolean showOffline) {
        this.showOffline = showOffline;
        Group.sHideOffline = showOffline;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_SHOW_OFFLINE, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_SHOW_OFFLINE, showOffline);
        mPrefEditor.commit();
    }

    public boolean isShowGroups() {
        return showGroups;
    }

    public void setShowGroups(boolean showGroups) {
        this.showGroups = showGroups;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_SHOW_GROUPS, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_SHOW_GROUPS, showGroups);
        mPrefEditor.commit();
    }

    public boolean isShowWritingToYou() {
        return showWritingToYou;
    }

    public void setShowWritingToYou(boolean showWritingToYou) {
        this.showWritingToYou = showWritingToYou;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_SHOW_WRITING_TO_YOU, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_SHOW_WRITING_TO_YOU, showWritingToYou);
        mPrefEditor.commit();
    }

    public boolean isShowSmiles() {
        return showSmiles;
    }

    public void setShowSmiles(boolean showSmiles) {
        this.showSmiles = showSmiles;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_SHOW_SMILES, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_SHOW_SMILES, showSmiles);
        mPrefEditor.commit();
    }

    public boolean isOnNewMessage() {
        return onNewMessage;
    }

    public void setOnNewMessage(boolean onNewMessage) {
        this.onNewMessage = onNewMessage;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_ON_NEW_MESSAGE, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_ON_NEW_MESSAGE, onNewMessage);
        mPrefEditor.commit();
    }

    public boolean isOnNewUnknownMessage() {
        return onNewUnknownMessage;
    }

    public void setOnNewUnknownMessage(boolean onNewUnknownMessage) {
        this.onNewUnknownMessage = onNewUnknownMessage;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_ON_NEW_UNKNOWN_MESSAGE, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_ON_NEW_UNKNOWN_MESSAGE, onNewUnknownMessage);
        mPrefEditor.commit();
    }

    public boolean isEnableSounds() {
        return enableSounds;
    }

    public void setEnableSounds(boolean enableSounds) {
        this.enableSounds = enableSounds;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_ENABLE_SOUNDS, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_ENABLE_SOUNDS, enableSounds);
        mPrefEditor.commit();
    }

    public boolean isEnableVibro() {
        return enableVibro;
    }

    public void setEnableVibro(boolean enableVibro) {
        this.enableVibro = enableVibro;
        SharedPreferences sharedPreference = mContext.getSharedPreferences(
                ApplicationSettings.PREF_ENABLE_VIBRO, Activity.MODE_PRIVATE);
        mPrefEditor = sharedPreference.edit();
        mPrefEditor.putBoolean(ApplicationSettings.PREF_ENABLE_VIBRO, enableVibro);
        mPrefEditor.commit();
    }

    public ApplicationSettings(Context c) {
        mSettings = PreferenceManager.getDefaultSharedPreferences(c);
        mContext = c;
        mLastChats = new ArrayList<Contact>();
        loadSettings();
    }

    /**
     * загрузка настроек
     */
    public void loadSettings() {
        saveMessageHistory = mSettings.getBoolean(PREF_SAVE_MESSAGE_HISTORY, true);
        enableLandscape = mSettings.getBoolean(PREF_ENABLE_LANDSCAPE, true);
        showOffline = mSettings.getBoolean(PREF_SHOW_OFFLINE, true);
        showGroups = mSettings.getBoolean(PREF_SHOW_GROUPS, true);
        showWritingToYou = mSettings.getBoolean(PREF_SHOW_WRITING_TO_YOU, false);
        showSmiles = mSettings.getBoolean(PREF_SHOW_SMILES, true);
        onNewMessage = mSettings.getBoolean(PREF_ON_NEW_MESSAGE, false);
        onNewUnknownMessage = mSettings.getBoolean(PREF_ON_NEW_UNKNOWN_MESSAGE, false);
        enableSounds = mSettings.getBoolean(PREF_ENABLE_SOUNDS, true);
        enableVibro = mSettings.getBoolean(PREF_ENABLE_VIBRO, true);
        isXMLConsoleEnabled = mSettings.getBoolean(PREF_ENABLE_XML_CONSOLE, false);
        Group.sHideOffline = showOffline;
        
        setEnableSounds(enableSounds);
        setEnableVibro(enableVibro);
    }

    public boolean getXMLConsoleEnabled() {
        return isXMLConsoleEnabled;
    }

    public void setXMLConsoleEnabled(boolean value){
        mPrefEditor = mSettings.edit();
        mPrefEditor.putBoolean(PREF_ENABLE_XML_CONSOLE, value);
        mPrefEditor.commit();
        isXMLConsoleEnabled=value;
    }

    public List<Contact> getLastChats() {
        return mLastChats;
    }

    public void setLastChats(List<Contact> lastChats) {
        mLastChats = lastChats;
    }

    public String getKeyPassword() {
        return mSettings.getString(PREF_KEY_PASSWORD, null);
    }

    public void setKeyPwd(String pwd) {
        mPrefEditor = mSettings.edit();
        mPrefEditor.putString(PREF_KEY_PASSWORD, pwd);
        mPrefEditor.commit();
    }

}
