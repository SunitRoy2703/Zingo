package com.sunit.zingo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        apiInterface = ApiClient.getClient().create(APIInterface.class);

        getCategories(AppUtil.getHEADER());



    }
    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new CategoryAdapter(categoryList);
        recyclerView.setLayoutManager(new GridLayoutManager(CategoryActivity.this,2));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(CategoryActivity.this, ProductActivity.class);
                intent.putExtra("categoryId", categoryList.get(position).getCategory_id());
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

            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {

                Log.d("throw", String.valueOf(t));

                Toast.makeText(CategoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}