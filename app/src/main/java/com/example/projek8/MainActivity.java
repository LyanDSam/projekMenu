package com.example.projek8;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.parseColor("#DDD9FF"));

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Fandom"));
        viewPager = findViewById(R.id.viewPager);
        MyPagerAdapter adapter = new MyPagerAdapter(this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position){
                        tabLayout.selectTab((tabLayout.getTabAt(position)));
                    }
        });

        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override public void onTabUnselected(TabLayout.Tab tab) {}
                    @Override public void onTabReselected(TabLayout.Tab tab) {}
                }
        );

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                closeActivity();
            }
        });
    }

    private MenuItem eyeItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        eyeItem = menu.findItem(R.id.eye);
        return true;
    }

    boolean isOff = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.accessibility) {
            makeToast(this, "ACCESIBILITY");

        } else if (id == R.id.home) {
            showHome();
            return true;

        } else if (id == R.id.about) {
            showAbout();
            return true;

        } else if (id == R.id.close) {
            closeActivity();
            return true;
        } else if (id == R.id.eye) {
            isOff = !isOff;
            switchEye(isOff);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showHome() {
        makeToast(this, "HOME");
        viewPager.setCurrentItem(0, true);
    }

    private void showAbout() {
        makeToast(this, "ABOUT");
        viewPager.setCurrentItem(1, true);
    }

    private void switchEye(boolean state) {
        if (state) {
            eyeItem.setIcon(R.drawable.eye_slash);
        } else {
            eyeItem.setIcon(R.drawable.eye_open);
        }
    }

    private void closeActivity() {
        new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }


    private void makeToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

}
