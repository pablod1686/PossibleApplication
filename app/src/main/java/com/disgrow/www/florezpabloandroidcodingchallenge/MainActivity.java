package com.disgrow.www.florezpabloandroidcodingchallenge;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private int widthScreen;
    private int heightScreen;
    private RecyclerView recyclerView;
    private List<Books> listBooks = new ArrayList<Books>();
    private Context c;
    private Typeface font;
    public static ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        c = getApplicationContext();
        font = Typeface.createFromAsset(getAssets(), "font.otf");

        widthScreen = Methods.getWidthScreen(this);
        heightScreen = Methods.getHeightScreen(this, getResources());
        recyclerView = findViewById(R.id.recycler_view);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);



        if (Methods.isOnline(c)) {
            getBooksWithRetrofit();
        } else {
            Toast.makeText(c, R.string.no_internet, Toast.LENGTH_LONG);
        }

    }

    private void getBooksWithRetrofit() {

        Call<List<Books>> call = apiInterface.getBooks();

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {

                for (Books book : response.body()) {
                    listBooks.add(book);
                }

                if(isThereData()){
                    inicializeRecyclerView();
                }else{
                    Toast.makeText(c, R.string.error_message,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                Toast.makeText(c, R.string.error_message, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void inicializeRecyclerView() {

        BooksAdapter mAdapter = new BooksAdapter(
                listBooks,
                widthScreen,
                heightScreen,
                font,
                c
        );

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    private boolean isThereData() {
        if (listBooks.isEmpty())
            return false;
        else
            return true;
    }

    public int w(float percent) {
        return (int) (widthScreen * (percent / 100));
    }

    public int h(float percent) {
        return (int) (heightScreen * (percent / 100));
    }
}
