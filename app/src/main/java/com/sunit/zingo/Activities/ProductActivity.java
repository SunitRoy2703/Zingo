package com.sunit.zingo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunit.zingo.Adapters.CategoryAdapter;
import com.sunit.zingo.Adapters.ProductAdapter;
import com.sunit.zingo.Models.Category;
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

public class ProductActivity extends AppCompatActivity {

    private APIInterface apiInterface;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList = new ArrayList<>();
    private ProgressBar progressBar;

    int categoryId;
    String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryId = getIntent().getIntExtra("categoryId", 0);
        categoryName = getIntent().getStringExtra("categoryName");
        getSupportActionBar().setTitle(categoryName);

        progressBar = findViewById(R.id.progress_bar);

        apiInterface = ApiClient.getClient().create(APIInterface.class);

        getProducts();

    }

    private void buildRecyclerView() {
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        verticalDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, getTheme())));
        horizontalDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, getTheme())));


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new ProductAdapter(this, productList);
        recyclerView.setLayoutManager(new GridLayoutManager(ProductActivity.this, 2));
        recyclerView.addItemDecoration(verticalDecoration);
        recyclerView.addItemDecoration(horizontalDecoration);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onAddClick(int position) {

                addProduct(position);
            }
        });
    }

    private void addProduct(int position) {
        Product product = productList.get(position);

        Call<Product> call = apiInterface.addProduct(AppUtil.getHEADER(), AppUtil.getVENDORID(), product.getProduct_id(), product.getCategory_id());
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Toast.makeText(ProductActivity.this, "Added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }

    private void getProducts() {

        Call<ProductList> call = apiInterface.getCategoryProducts(AppUtil.getHEADER(), categoryId, AppUtil.getPage(), AppUtil.getLimit(), AppUtil.getVENDORID());

        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {

                if (!response.isSuccessful()) {
                    Log.d("Code: ", String.valueOf(response.code()));
                    return;
                }

                productList = response.body().getData();
                buildRecyclerView();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {

            }
        });
    }
}