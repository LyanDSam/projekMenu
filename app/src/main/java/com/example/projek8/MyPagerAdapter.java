package com.example.projek8;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyPagerAdapter extends FragmentStateAdapter {

    private final int numTab;

    public MyPagerAdapter(@NonNull FragmentActivity fm, int numTab) {
        super(fm);
        this.numTab = numTab;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Home();
            case 1:
                return new Settings();
            case 2:
                return new ConvertFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return numTab;
    }
}
