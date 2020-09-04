package com.sakayta.budgetapp.activity.intro;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.facebook.stetho.Stetho;
import com.google.android.material.tabs.TabLayout;
import com.sakayta.budgetapp.R;
import com.sakayta.budgetapp.activity.main.HomeViewModel;
import com.sakayta.budgetapp.util.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class IntroActivity extends AppCompatActivity {

  private ViewPager viewPager;
  private Button button;
  private SliderPagerAdapter adapter;


  private IntroViewModel viewModel;
  private TabLayout tabLayout;

  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Create an InitializerBuilder


    // making activity full screen
    if (Build.VERSION.SDK_INT >= 21) {
      getWindow().getDecorView()
          .setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    setContentView(R.layout.activity_intro);
    // hide action bar you can use NoAction theme as well
    getSupportActionBar().hide();
    // bind views
    viewPager = findViewById(R.id.pagerIntroSlider);
    tabLayout = findViewById(R.id.tabs);
    progressBar = findViewById(R.id.progress);

    button = findViewById(R.id.button);

    viewModel = new ViewModelProvider(this).get(IntroViewModel.class);
    observeFragmentList();
    viewModel.getFragments();


    // make status bar transparent
    changeStatusBarColor();

    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if(getFragment(viewPager.getCurrentItem()) instanceof SignUp ){
            ((SignUp)getFragment(viewPager.getCurrentItem())).save();
        }else{
          if (viewPager.getCurrentItem() < adapter.getCount()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
          }
        }
      }
    });

    /**
     * Add a listener that will be invoked whenever the page changes
     * or is incrementally scrolled
     */
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {


        if(getFragment(position) instanceof SignUp){
          button.setText("Sign-up");
        }else{
          button.setText(R.string.get_started);
        }
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });



  }

  private void observeFragmentList() {
    viewModel.observeFragments().observe(this, new Observer<Resource<List<Fragment>>>() {
      @Override
      public void onChanged(Resource<List<Fragment>> result) {

          if(result.getData()==null){
            progressBar.setVisibility(View.VISIBLE);
          }else{
            progressBar.setVisibility(View.GONE);
            List<Fragment> fragments = result.getData();
            setUpDetails(fragments);
          }

      }
    });
  }


  private  void setUpDetails(List<Fragment> fragments){
    // init slider pager adapter
    adapter = new SliderPagerAdapter(
            getSupportFragmentManager(),
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    adapter.setFragments(fragments);
    // set adapter
    viewPager.setAdapter(adapter);

    // set dot indicators
    tabLayout.setupWithViewPager(viewPager);
  }
  private  Fragment getFragment(int position){
    return adapter.getFragment(position);
  }


  private void changeStatusBarColor() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
    }
  }
}
