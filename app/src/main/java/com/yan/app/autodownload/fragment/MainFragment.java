package com.yan.app.autodownload.fragment;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.yan.app.autodownload.R;
import com.yan.app.autodownload.utils.ConfigUtils;

public class MainFragment extends PreferenceFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName(ConfigUtils.NAME);
        addPreferencesFromResource(R.xml.preferecne_config);

        SwitchPreference switchDownload = (SwitchPreference) findPreference(ConfigUtils.SWITCH_DOWNLOAD);
        switchDownload.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                getPreferenceManager().setSharedPreferencesName(ConfigUtils.SWITCH_DOWNLOAD);
                return false;
            }
        });

        EditTextPreference editTextPreference = (EditTextPreference) findPreference(ConfigUtils.EDIT_PREFERENCE);
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                getPreferenceManager().setSharedPreferencesName(ConfigUtils.EDIT_PREFERENCE);
                Toast.makeText(getActivity(),"edit content : " + newValue, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ListPreference listPreference = (ListPreference) findPreference(ConfigUtils.LIST_PREFERENCE);
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                getPreferenceManager().setSharedPreferencesName(ConfigUtils.LIST_PREFERENCE);
                Toast.makeText(getActivity(),"list content : " + newValue, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
