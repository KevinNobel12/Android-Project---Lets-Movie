package com.example.letsmovie;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private TabItem today;
    private   TabItem tomorrow;
    private   TabItem next;
    private    TabItem next2 ;
    private ViewPager mPager;
    public PagerController mpagerController;
    ArrayList<ExampleItem> exampleList;

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;








private Timer timer;

    private int current_position = 0;

    private ViewFlipper v_flipper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exampleList = new ArrayList<>();

            exampleList.add(new ExampleItem(R.drawable.avengers,"Line1","Line2"));
            exampleList.add(new ExampleItem(R.drawable.frozen,"Line3","Line4"));
            exampleList.add(new ExampleItem(R.drawable.ford,"Line5","Line6"));
            exampleList.add(new ExampleItem(R.drawable.joker,"Line7","Line8"));
            exampleList.add(new ExampleItem(R.drawable.it,"Line9","Line10"));


            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setNestedScrollingEnabled(false);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new ExampleAdapter(exampleList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

     v_flipper = findViewById(R.id.v_flipper);

    int images[] = {R.drawable.avengers, R.drawable.ford, R.drawable.frozen, R.drawable.it, R.drawable.joker};



    for (int image: images){
        flipperImages(image);
    }


  mTabLayout  = (TabLayout) findViewById(R.id.tabLayout);
  today = (TabItem) findViewById(R.id.today);
  tomorrow= (TabItem) findViewById(R.id.tommorrow);
  next =(TabItem) findViewById(R.id.next);
  next2=(TabItem) findViewById(R.id.next2);
    mPager =(ViewPager) findViewById(R.id.viewPager);

    mpagerController = new PagerController(getSupportFragmentManager(),mTabLayout.getTabCount());
    mPager.setAdapter(mpagerController);

    mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mPager.setCurrentItem(tab.getPosition());

            if (tab.getPosition()==0) {
                mpagerController.notifyDataSetChanged();
            }
              else  if (tab.getPosition()==1) {
                mpagerController.notifyDataSetChanged();
            }
                  else  if (tab.getPosition()==2) {
                mpagerController.notifyDataSetChanged();

            }   else    if (tab.getPosition()==3){
                            mpagerController.notifyDataSetChanged();
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });

    mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        dl= (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.profile){

                    Toast.makeText(MainActivity.this,"Profile",Toast.LENGTH_LONG).show();

                    Intent i = new Intent(MainActivity.this,Profile.class);
                    startActivity(i);
                }
                else if(id == R.id.logout){
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent i = new Intent(MainActivity.this,First.class);
                    startActivity(i);
                }

                return false;
            }
        });
    }




    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(2000);
        v_flipper.setAutoStart(true);


        //animation
        v_flipper.setInAnimation(this,android.R.anim.fade_in);
        v_flipper.setOutAnimation(this,android.R.anim.fade_out);


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);





    }

   
}
