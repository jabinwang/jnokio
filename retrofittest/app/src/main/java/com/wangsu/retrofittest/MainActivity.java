package com.wangsu.retrofittest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTest();
    }

    private void getTest() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGetRequest request = retrofit.create(IGetRequest.class);
//        ("/ajax.php?a=fy&f=auto&t=auto&w=hello%20world")

        Call<GetModel> call = request.getCall("fy", "auto","auto","hello%20world");

        call.enqueue(new Callback<GetModel>() {
            @Override
            public void onResponse(Call<GetModel> call, Response<GetModel> response) {
                Log.e(TAG, "onResponse: " + response.code() + " " + response.body().toString());
            }

            @Override
            public void onFailure(Call<GetModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ");
            }
        });
    }

    public void testOkio(View view) {

    }

}