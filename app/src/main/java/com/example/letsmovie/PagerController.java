package com.example.letsmovie;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {
    private  int tabCounts;

    public PagerController(FragmentManager fm, int tabCounts) {
        super(fm);
        this.tabCounts = tabCounts;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:

                return new today();

            case 1:
                return new tomorrow();
            case 2:
                return new next();
            case 3:
                return new next2();
                default:
                    return null;

        }

    }

    @Override
    public int getCount() {
        return tabCounts;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
