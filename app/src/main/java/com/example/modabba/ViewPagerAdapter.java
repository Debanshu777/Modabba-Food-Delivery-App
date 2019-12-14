package com.example.modabba;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.modabba.Fragments.NonVegMenuFragment;
import com.example.modabba.Fragments.VegMenuFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Veg";
            case 1:
                return "Non Veg";
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 :
                return new VegMenuFragment();
            case 1:
                return new NonVegMenuFragment();
        }
        return null;
    }

    public ViewPagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabCount = tabs;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

