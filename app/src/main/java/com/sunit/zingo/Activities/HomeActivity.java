package com.sunit.zingo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sunit.zingo.R;

public class HomeActivity extends AppCompatActivity {

    CardView newOrder, manageProducts;

    boolean haveConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        newOrder = findViewById(R.id.new_order);
        manageProducts = findViewById(R.id.manage_products);

        haveConnection = checkNetworkConnection();
        if (!haveConnection){
            Toast.makeText(this, "Please Turn on Data Connection", Toast.LENGTH_SHORT).show();
            return;
        }


        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        manageProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProductCatalogActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkNetworkConnection() {

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}