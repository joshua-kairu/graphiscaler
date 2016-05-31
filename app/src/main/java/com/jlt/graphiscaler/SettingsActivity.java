package com.jlt.graphiscaler;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

/**

    Graphiscaler

    Tutorial for showcasing typographical scale

    Copyright (C) 2016 Kairu Joshua Wambugu

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.

*/

// begin class SettingsActivity
public class SettingsActivity extends PreferenceActivity {

    @Override
    // begin onCreate
    protected void onCreate( Bundle savedInstanceState ) {

        // 0. super things

        // 0. super things

        super.onCreate( savedInstanceState );

    } // end onCreate

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    // begin method isXLargeTablet
    private static boolean isXLargeTablet( Context context ) {

        return (
                context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK
                ) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;

    } // end method isXLargeTablet



    /**
     * {@inheritDoc}
     */
    @Override
    // method onIsMultiPane
    public boolean onIsMultiPane() { return isXLargeTablet( this ); }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    // begin sBindPreferenceSummaryToValueListener
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange( Preference preference, Object value ) {
            String stringValue = value.toString();

            if ( preference instanceof ListPreference ) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = ( ListPreference ) preference;
                int index = listPreference.findIndexOfValue( stringValue );

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[ index ]
                                : null );

            } else if ( preference instanceof RingtonePreference ) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if ( TextUtils.isEmpty( stringValue ) ) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary( R.string.pref_ringtone_silent );

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse( stringValue ) );

                    if ( ringtone == null ) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary( null );
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle( preference.getContext() );
                        preference.setSummary( name );
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary( stringValue );
            }
            return true;

        }
    }; // end sBindPreferenceSummaryToValueListener

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    // begin onBuildHeaders
    public void onBuildHeaders( List< Header > target ) {
        loadHeadersFromResource( R.xml.pref_headers, target );
    } // end onBuildHeaders

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    // begin bindPreferenceSummaryToValue
    private static void bindPreferenceSummaryToValue( Preference preference ) {

        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener( sBindPreferenceSummaryToValueListener );

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange( preference,
                PreferenceManager
                        .getDefaultSharedPreferences( preference.getContext() )
                        .getString( preference.getKey(), "" ) );

    } // end bindPreferenceSummaryToValue

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    // begin fragment GeneralPreferenceFragment
    public static class GeneralPreferenceFragment extends PreferenceFragment {

        @Override

        public void onCreate( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            addPreferencesFromResource( R.xml.pref_general );
            setHasOptionsMenu( true );

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue( findPreference( "example_text" ) );
            bindPreferenceSummaryToValue( findPreference( "example_list" ) );

        }

        @Override
        public boolean onOptionsItemSelected( MenuItem item ) {

            int id = item.getItemId();

            if ( id == android.R.id.home ) {

                startActivity( new Intent( getActivity(), SettingsActivity.class ) );

                return true;

            }

            return super.onOptionsItemSelected( item );

        }

    } // end fragment GeneralPreferenceFragment

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    // begin fragment NotificationPreferenceFragment
    public static class NotificationPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            addPreferencesFromResource( R.xml.pref_notification );
            setHasOptionsMenu( true );

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue( findPreference( "notifications_new_message_ringtone" ) );

        }

        @Override
        public boolean onOptionsItemSelected( MenuItem item ) {

            int id = item.getItemId();

            if ( id == android.R.id.home ) {

                startActivity( new Intent( getActivity(), SettingsActivity.class ) );

                return true;

            }

            return super.onOptionsItemSelected( item );

        }

    } // end fragment NotificationPreferenceFragment

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    public static class DataSyncPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate( Bundle savedInstanceState ) {
            super.onCreate( savedInstanceState );
            addPreferencesFromResource( R.xml.pref_data_sync );
            setHasOptionsMenu( true );

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue( findPreference( "sync_frequency" ) );
        }

        @Override
        public boolean onOptionsItemSelected( MenuItem item ) {
            int id = item.getItemId();
            if ( id == android.R.id.home ) {
                startActivity( new Intent( getActivity(), SettingsActivity.class ) );
                return true;
            }
            return super.onOptionsItemSelected( item );
        }
    }
} // end  class SettingsActivity
