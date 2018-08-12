package com.yourdevelopername.yourappname;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.yourdevelopername.yourappname.tabs.Tab1;
import com.yourdevelopername.yourappname.tabs.Tab2;


public class TabFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public InterstitialAd mInterstitialAd;
    int tab_change_counter;
    AdRequest adRequest;

    public static int int_items = 2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        interstitital_ad();
        mInterstitialAd.loadAd(adRequest);


        View v =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        // Manage Interstitial Ad
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                tab_change_counter++;

                if(Integer.parseInt(getText(R.string.interstitial_ad_frequency).toString()) == tab_change_counter){
                    if(mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();

                        AdRequest adRequest = new AdRequest.Builder().build();
                        mInterstitialAd = new InterstitialAd(getContext());
                        mInterstitialAd.setAdUnitId(getText(R.string.interstitial_ad_unit_id) + "");
                        mInterstitialAd.loadAd(adRequest);

                        tab_change_counter = 0;
                    }
                }
            }
        });
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                    tabLayout.setupWithViewPager(viewPager);
                   }
        });
        return v;
    }


    class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        // Tab positions
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new Tab1();
            }
            if(position == 1){
                return new Tab2();
            }
        return null;
        }


        // Tab titles
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return getText(R.string.tab1);
                case 1 :
                    return getText(R.string.tab2);
            }
                return null;
        }



        @Override
        public int getCount() {
            return int_items;
        }
    }

    // Interstitial Ad
    public void interstitital_ad(){
        adRequest = new AdRequest.Builder().build();
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getText(R.string.interstitial_ad_unit_id) + "");
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // Ads loaded
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                // Ads closed
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                // Ads couldn't loaded
            }
        });
    }

}
