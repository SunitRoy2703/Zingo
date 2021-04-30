package com.sunit.zingo.Utils;

import com.sunit.zingo.Models.Product;
import com.sunit.zingo.Models.ProductList;
import com.sunit.zingo.Models.Vendor;
import com.sunit.zingo.Models.CategoryList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("vendor_login")
    Call<Vendor> authenticate(@Header("Keydata") String header, @Body Vendor vendor);

    @GET("get_category")
    Call<CategoryList> getCategory(@Header("Keydata") String header);

    @FormUrlEncoded()
    @POST("category_product")
    Call<ProductList> getCategoryProducts(@Header("Keydata") String header,
                                          @Field("category_id") int categoryId,
                                          @Field("page") int page,
                                          @Field("limit") int limit,
                                          @Field("vendor_id") int vendor_id);

    @FormUrlEncoded()
    @POST("assign_product_vendor")
    Call<Product> addProduct(@Header("Keydata") String header,
                             @Field("vendor_id") int vendor_id,
                             @Field("product_id") int product_id,
                             @Field("category_id") int category_id);

    @FormUrlEncoded()
    @POST("get_products_vendor")
    Call<ProductList> getProductsCatalog(@Header("Keydata") String header,
                                         @Field("vendor_id") int vendor_id,
                                         @Field("page") int page,
                                         @Field("limit") int limit);


}
