package com.example.loginpge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Data;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private CardView card1;
    private CardView card2;
    private CardView card3;
    private CardView card4,card5;
    private TextView text;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navview;
    private ActionBarDrawerToggle action;
    private TextView hello;
    private View header;
    private DatabaseHandler db=new DatabaseHandler(Dashboard.this);
    long backpressed;
    private List<Data> infolist;
    private Data info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setNavigationViewListener();
        card1=findViewById(R.id.crd1);
        card2=findViewById(R.id.crd2);
        card3=findViewById(R.id.crd3);
        card4=findViewById(R.id.crd4);
        card5=findViewById(R.id.crd5);
        text=findViewById(R.id.below);
        toolbar=findViewById(R.id.toolbar);
        infolist=db.getinfo();
        //Drawer part
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer);
        navview=findViewById(R.id.navbar);
        navview.bringToFront();
        action=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(action);
        action.setDrawerIndicatorEnabled(true);
        action.syncState();
      String st=getIntent().getExtras().getString("id");
        text.setText("Hello " + st + " !");
        info=infolist.get(0);


        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Dashboard.this, Todopge.class);
                    startActivity(intent);


            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Dashboard.this, billreminder.class);
                    startActivity(intent);

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Dashboard.this, Expensepge.class);
                    startActivity(intent);

            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Dashboard.this, Graph.class);
                    startActivity(intent);

            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Dashboard.this, waterremainder.class);
                    startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        moveTaskToBack(true);
        super.onBackPressed();
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Mprofile:
                Intent intent1=new Intent(Dashboard.this,Profile.class);
                startActivity(intent1);
                break;
            case R.id.history:
                Intent intent =new Intent(Dashboard.this,billhistory.class);
                startActivity(intent);
                break;
            case R.id.delete:
                alertbox();
                break;
            case R.id.signout:
                SharedPreferences preferences =getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
                break;

        }
        return true;
    }
    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navbar);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void alertbox(){
        AlertDialog.Builder builder=new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("Delete Account ?");
        builder.setMessage("Are you sure you want to Delete the Account ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHandler db=new DatabaseHandler(Dashboard.this);
                db.deleteall();
                db.deletetodo();
                db.deletetable();
                db.deleteremind();
                db.deleteinfo();
                db.deletewremind();
                db.deleterecord();

                Intent intent=new Intent(Dashboard.this, Welcomepg.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this,"Account Deleted Successfully",Toast.LENGTH_SHORT).show();
                SharedPreferences preferences =getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}