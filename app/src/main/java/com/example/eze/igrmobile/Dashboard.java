package com.example.eze.igrmobile;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Dashboard extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TextView lastMonth, currentMonth, yestarday, today, billerName;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setUpToolBarMenu();
        setUpNavigationDrawerMenu();

        setTextView();
    }

    private void setTextView() {
        lastMonth = (TextView) findViewById(R.id.lastMonth);
        currentMonth = (TextView) findViewById(R.id.currentMonth);
        yestarday = (TextView) findViewById(R.id.yestarday);
        today = (TextView) findViewById(R.id.today);

        View header = navigationView.getHeaderView(0);
        billerName = (TextView) header.findViewById(R.id.billerName);

        Bundle extra = getIntent().getExtras();
        if (extra == null){
            Log.d("Dashboard","Missing param");
        }else {

            lastMonth.setText(numberFormat(extra.getString("lastMonth")));
            currentMonth.setText(numberFormat(extra.getString("currentMonth")));
            yestarday.setText(numberFormat(extra.getString("yestarday")));
            today.setText(numberFormat(extra.getString("today")));
            billerName.setText(extra.getString("name"));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overlay_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                break;
        }
        return true;
    }

    private void setUpNavigationDrawerMenu() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void setUpToolBarMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
    }

    private String numberFormat(String number){
        double num = Double.parseDouble(number);
        DecimalFormat money = new DecimalFormat("###,###,###,###");
        String formattedText = "₦" + money.format(num);

        return formattedText;
    }

}

