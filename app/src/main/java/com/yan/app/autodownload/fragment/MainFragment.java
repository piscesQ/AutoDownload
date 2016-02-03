package com.yan.app.autodownload.fragment;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.yan.app.autodownload.R;
import com.yan.app.autodownload.service.AutoDownloadService;
import com.yan.app.autodownload.utils.ConfigUtils;

public class MainFragment extends PreferenceFragment {

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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName(ConfigUtils.NAME);
        addPreferencesFromResource(R.xml.preferecne_config);

        SwitchPreference switchDownload = (SwitchPreference) findPreference(ConfigUtils.SWITCH_DOWNLOAD);
        switchDownload.setChecked(true);    //设置默认打开
        switchDownload.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getActivity(), "switchDownload content : " + newValue, Toast.LENGTH_SHORT).show();
                if((Boolean)newValue){      //true
                    if (AutoDownloadService.singleService != null){
//                        Intent intent = new Intent(ConfigUtils.SERVICE_ACTION);
//                        getActivity().startService(intent);
//                        AutoDownloadService.singleService.onServiceConnected();

                        try {
//                            Class<?> autoDownloadService = Class.forName("com.yan.app.autodownload.service.AutoDownloadService");
//                            Object instance = autoDownloadService.newInstance();
//                            Method method = autoDownloadService.getMethod("onServiceConnected", null);
//                            method.setAccessible(true);
//                            method.invoke(instance,null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else{      //false
                    if (AutoDownloadService.singleService != null){
                        AutoDownloadService.singleService.onInterrupt();    //调用中断方法
                    }
                }
                return true;     //属性的值是否改变，true：改变； false：不变
            }
        });

        EditTextPreference editTextPreference = (EditTextPreference) findPreference(ConfigUtils.EDIT_PREFERENCE);
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                getPreferenceManager().setSharedPreferencesName(ConfigUtils.EDIT_PREFERENCE);
                Toast.makeText(getActivity(), "edit content : " + newValue, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ListPreference listPreference = (ListPreference) findPreference(ConfigUtils.LIST_PREFERENCE);
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                getPreferenceManager().setSharedPreferencesName(ConfigUtils.LIST_PREFERENCE);
                Toast.makeText(getActivity(), "list content : " + newValue, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
