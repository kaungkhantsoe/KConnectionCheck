package com.yammobots.kconnectioncheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements KConnectionCheck.ConnectionStatusChangeListener {

    BottomNavigationView bottomNavigationView;

    CoordinatorLayout coordinatorLayout;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        coordinatorLayout = findViewById(R.id.coordinator);
        textView = findViewById(R.id.toolbar_text);

        KConnectionCheck.CustomConnectionBuilder builder = new KConnectionCheck.CustomConnectionBuilder();
        builder.setBottomNavigationView(bottomNavigationView);

        KConnectionCheck.addConnectionCheck(this,
                this,
                this,
                builder
        );

    }

    @Override
    public void onConnectionStatusChange(boolean status) {

    }
}