package com.sunit.zingo.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sunit.zingo.Adapters.CategoryAdapter;
import com.sunit.zingo.Models.Category;
import com.sunit.zingo.Models.CategoryList;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryActivity extends AppCompatActivity {
    private List<Category> categoryList = new ArrayList<>();
    private APIInterface apiInterface;

    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Browse by category");
        actionBar.setSubtitle("Choose Your Products");
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progress_bar);

        apiInterface = ApiClient.getClient().create(APIInterface.class);

        getCategories(AppUtil.getHEADER());



    }
    private void buildRecyclerView() {
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.HORIZONTAL);
        verticalDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, getTheme())));
        horizontalDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.divider, getTheme())));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new CategoryAdapter(categoryList);
        recyclerView.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
        recyclerView.addItemDecoration(verticalDecoration);
        recyclerView.addItemDecoration(horizontalDecoration);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(CategoryActivity.this, ProductActivity.class);
                intent.putExtra("categoryId", categoryList.get(position).getCategory_id());
                intent.putExtra("categoryName", categoryList.get(position).getName());
                startActivity(intent);
            }

            @Override
            public void onUploadClick(int position) {

            }
        });
    }

    private void getCategories(String header) {

        Call<CategoryList> call = apiInterface.getCategory(header);

        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code: ", String.valueOf(response.code()));
                    return;
                }
                categoryList = response.body().getData();
                buildRecyclerView();

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {

                Log.d("throw", String.valueOf(t));

                Toast.makeText(CategoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}