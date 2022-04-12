package com.example.sqlnavigationviewmenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private ConnectionClass connectionClass;
    private Connection connection;

    private boolean check = false;

    private String url;
    private String name;
    private int id;

    private int counter = 0;

    private Menu menu;
    private MenuItem menuItem;
    private ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionClass = new ConnectionClass();
        connection = connectionClass.connection();

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);

        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        urls = new ArrayList<>();

        try{
            if(connection == null){
                check = false;
            }else{
                String query = "select * from table1";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(resultSet != null){
                    while (resultSet.next()){
                        name = resultSet.getString("name");
                        url = resultSet.getString("url");
                        id = resultSet.getInt("id");

                        menu = navigationView.getMenu();
                        menu.add(0,id,counter,name);

                        menuItem = menu.getItem(counter);
                        menuItem.setTitle(name);

                        urls.add(url);

                        counter++;
                    }
                    check = true;
                }else{
                    check = false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            check = false;
        }
        if(check){
            for(int i=0;i<urls.size();i++){
                final int FinalI = i;
                navigationView.setItemIconTintList(null);
                Glide.with(getApplicationContext()).asBitmap().load(urls.get(FinalI)).into(new CustomTarget<Bitmap>(150,150){

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(getResources(),resource);
                        menu.getItem(FinalI).setIcon(drawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
            }
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,0,0);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this,"Tittle= "+item.getTitle()+"\n Id= "+item.getItemId(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}