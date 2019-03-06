package com.disgrow.www.florezpabloandroidcodingchallenge;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("books.json")
    Call<List<Books>> getBooks();

}
