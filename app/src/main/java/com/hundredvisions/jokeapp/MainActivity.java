package com.hundredvisions.jokeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static android.R.attr.onClick;
import static android.R.attr.settingsActivity;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.text_view)
    TextView textView;

    private Retrofit retrofit;

    public interface ICNDB {
        @GET("/jokes/random")
        Call<IcndbJoke> getJoke(@Query("firstName") String firstName,
                             @Query("lastName") String lastName,
                             @Query("limitTo") String limitTo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Turn on Butterknife
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.icndb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @OnClick(R.id.joke_button)
    public void getJoke() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String first = prefs.getString("first", "Chris");
        String last = prefs.getString("last", "Winikka");

        ICNDB icndb = retrofit.create(ICNDB.class);
        Call<IcndbJoke> jokeCall = icndb.getJoke(first, last, "[nerdy]");
        jokeCall.enqueue(new Callback<IcndbJoke>() {
            @Override
            public void onResponse(Call<IcndbJoke> call, Response<IcndbJoke> response) {
                if (response.isSuccessful()) {
                    textView.setText(response.body().getValue().getJoke());
                } else {
                    textView.setText(R.string.no_joke_found);
                }
            }

            @Override
            public void onFailure(Call<IcndbJoke> call, Throwable throwable) {
                Log.v("Error", throwable.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.get_joke:
                getJoke();
                return true;
            case R.id.preferences:
                Intent intent = new Intent(this,
                        SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
