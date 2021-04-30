package com.sunit.zingo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCatalogActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private RecyclerView recyclerView;
    private ProductCatalogAdapter adapter;
    private List<Product> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

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
                }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {

            }
        });
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new ProductCatalogAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductCatalogActivity.this));
        recyclerView.setAdapter(adapter);}
}