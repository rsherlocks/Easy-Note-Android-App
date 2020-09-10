package com.example.androidshaper.easynote.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.androidshaper.easynote.UIFragment.AssignmentFragment;
import com.example.androidshaper.easynote.UIFragment.CalendarFragment;
import com.example.androidshaper.easynote.UIFragment.HeartFragment;
import com.example.androidshaper.easynote.UIFragment.HomeFragment;
import com.example.androidshaper.easynote.R;
import com.example.androidshaper.easynote.UIFragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.homeId);

        if (savedInstanceState==null)
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment=null;
                if (item.getItemId()==R.id.homeId)
                {
                    fragment=new HomeFragment();
                }

                else if(item.getItemId()==R.id.calendarId)
                {
                    fragment=new CalendarFragment();
                }

                else if(item.getItemId()==R.id.assignmentId)
                {
                    fragment=new AssignmentFragment();
                }

                else if(item.getItemId()==R.id.heartId)
                {
                    fragment=new HeartFragment();
                }
                else if(item.getItemId()==R.id.settingId)
                {
                    fragment=new SettingFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();


                return true;
            }
        });




    }
}