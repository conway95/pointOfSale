package com.example.pointofsale;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter
{
    private Context context;
    int TotalTabs;

    public LoginAdapter(FragmentManager fm, int totalTabs)
    {
        super(fm);
        this.context = context;
        this.TotalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return TotalTabs;
    }

    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                loginTabFragment loginTabFragment = new loginTabFragment();
                return loginTabFragment;
            case 1:
                signupTabFragment signupTabFragment = new signupTabFragment();
                return signupTabFragment;
            default:
                return null;
        }
    }
}
