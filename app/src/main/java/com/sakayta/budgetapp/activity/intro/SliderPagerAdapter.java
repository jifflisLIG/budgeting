package com.sakayta.budgetapp.activity.intro;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderPagerAdapter extends FragmentPagerAdapter {

  List<Fragment> fragments =  new ArrayList<>();

  public SliderPagerAdapter(
          @NonNull FragmentManager fm,
          int behavior
  ) {
    super(fm, behavior);
  }

  @NonNull @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }


  @Override public int getCount() {
    return fragments.size();
  }

  public void setFragments(List<Fragment> fragments){
    this.fragments = fragments;
  }
  public Fragment getFragment(int position){
    return  fragments.get(position);
  }
}