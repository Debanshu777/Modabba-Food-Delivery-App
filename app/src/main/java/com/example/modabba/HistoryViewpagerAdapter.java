package com.example.modabba;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HistoryViewpagerAdapter extends FragmentPagerAdapter {



    private int tabCount;

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Subscription";
            case 1:
                return "Transaction";
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 :
                return new SubcriptionHistoryFragment();
            case 1:
                return new TransactionHistoryFragment();
        }
        return null;
    }

    public HistoryViewpagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabCount = tabs;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
