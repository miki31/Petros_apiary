package com.apiary.sch.mykhailo.petros_apiary;

import android.app.Application;

import com.apiary.sch.mykhailo.petros_apiary.servAPI.ServerLoginApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 02.11.2018.
 */

public class App extends Application {
    public static final String SERVER = "https://apiarybackend.herokuapp.com";

    private static ServerLoginApi serverLoginApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(SERVER)  // базова частина адреса
                .addConverterFactory(GsonConverterFactory.create()) // конвертер необїідний для перетворення JSON в об'єкти
                .build();

        serverLoginApi = retrofit.create(ServerLoginApi.class); // сворюємо обєкт з допомогою якого будемо виконувати запити
    }

    public static ServerLoginApi getServerLoginApi() {
        return serverLoginApi;
    }
}
