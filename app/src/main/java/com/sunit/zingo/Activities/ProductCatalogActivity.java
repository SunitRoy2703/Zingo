package com.sunit.zingo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sunit.zingo.Adapters.ProductAdapter;
import com.sunit.zingo.Adapters.ProductCatalogAdapter;
import com.sunit.zingo.Models.Product;
import com.sunit.zingo.Models.ProductList;
import com.sunit.zingo.R;
import com.sunit.zingo.Utils.APIInterface;
import com.sunit.zingo.Utils.ApiClient;
import com.sunit.zingo.Utils.AppUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCatalogActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private RecyclerView recyclerView;
    private ProductCatalogAdapter adapter;
    private List<Product> productList = new ArrayList<>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        getSupportActionBar().setTitle("Manage Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progress_bar);

        apiInterface = ApiClient.getClient().create(APIInterface.class);

        getProductsCatalog();
    }

    private void getProductsCatalog() {

        Call<ProductList> call = apiInterface.getProductsCatalog(AppUtil.getHEADER(), AppUtil.getVENDORID(), AppUtil.getPage(), AppUtil.getLimit());

        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {

                productList = response.body().getData();
                buildRecyclerView();
                progressBar.setVisibility(View.GONE);
                }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {

            }
        });
    }

    private void buildRecyclerView() {

        DividerItemDecoration verticalDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new ProductCatalogAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductCatalogActivity.this));
        recyclerView.addItemDecoration(verticalDecoration);
        recyclerView.setAdapter(adapter);}
}