package com.example.e_commerceandroid;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.e_commerceandroid.view_models.IsFavorite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new IsFavorite(this);

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

        BottomNavigationView navigation = findViewById(R.id.nav_bar);

        NavigationUI.setupWithNavController(navigation, navController);

        navigation.setOnNavigationItemSelectedListener(menuItem -> {
            Log.d("onNavigationI", "menu " + menuItem.getItemId());
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    Log.d("onNavigationI", "menu home" + menuItem.getItemId());
                    navController.navigate(R.id.homeFragment);
                    return true;
                case R.id.navigation_deals:
                    Log.d("onNavigationI", "menu favs" + menuItem.getItemId());
                    navController.navigate(R.id.dealsFragment);
                    return true;
                case R.id.navigation_favs:
                    Log.d("onNavigationI", "menu favs" + menuItem.getItemId());
                    navController.navigate(R.id.favoritesFragment);
                    return true;
                case R.id.navigation_cart:
                    Log.d("onNavigationI", "menu favs" + menuItem.getItemId());
                    navController.navigate(R.id.cartFragment);
                    return true;
                case R.id.navigation_profile:
                    Log.d("onNavigationI", "menu prof" + menuItem.getItemId());
                    navController.navigate(R.id.profileFragment);
                    return true;
            }
            return false;
        });
    }
}