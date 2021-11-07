package com.msgr2.fyreapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.msgr2.fyreapp.databinding.ActivityMainBinding;
import com.msgr2.fyreapp.menu.CallsFragment;
import com.msgr2.fyreapp.menu.ChartsFragment;
import com.msgr2.fyreapp.menu.StoriesFragment;
import com.msgr2.fyreapp.view.settings.MainSettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpWithViewPager(binding.viewPager1);
        binding.tabLayout1.setupWithViewPager(binding.viewPager1);
        setSupportActionBar(binding.mtoobar1);

        binding.viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeFabCondition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_search:
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_new_group:
                Toast.makeText(MainActivity.this, "more", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_new_broadcast_message:
                Toast.makeText(MainActivity.this, "more", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_starred_message:
                Toast.makeText(MainActivity.this, "more", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, MainSettingsActivity.class));
                break;
            case R.id.action_more:
                Toast.makeText(MainActivity.this, "more", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFabCondition(final int index) {
        binding.fabAction.hide();
        new Handler().postDelayed(() -> {
            switch (index) {
                case 0:
                    binding.fabAction.setImageResource(R.drawable.ic_baseline_message_24);
                    break;
                case 1:
                    binding.fabAction.setImageResource(R.drawable.ic_baseline_photo_camera_24);
                    break;
                case 2:
                    binding.fabAction.setImageResource(R.drawable.ic_baseline_call_24);
                    break;
            }
            binding.fabAction.show();
        }, 0);

    }

    private void setUpWithViewPager(ViewPager viewPager) {
        MainActivity.SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChartsFragment(), "Chats");
        adapter.addFragment(new StoriesFragment(), "Stories");
        adapter.addFragment(new CallsFragment(), "Calls");
        viewPager.setAdapter(adapter);

    }

    private static class SectionPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> titles = new ArrayList<>();

        public SectionPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

    }
}