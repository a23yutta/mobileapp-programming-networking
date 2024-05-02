package com.example.networking;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {
    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";
    private final String JSON_FILE = "mountains.json";
    private ArrayList<Mountain> mountains = new ArrayList<Mountain>();
    private RecyclerView view;
    private RecyclerViewAdapter adapter;

    private WebView myWebView;

    @SuppressWarnings("SameParameterValue")
    private String readFile(String fileName) {
        try {
            //noinspection CharsetObjectCanBeUsed
            return new Scanner(getApplicationContext().getAssets().open(fileName), Charset.forName("UTF-8").name()).useDelimiter("\\A").next();
        } catch (IOException e) {
            Log.e("TAG", "Could not read file: " + fileName);
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myWebView = findViewById(R.id.my_webView);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        adapter = new RecyclerViewAdapter(this, mountains, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Mountain mountain) {
                Toast.makeText(MainActivity.this, mountain.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        view = findViewById(R.id.recycler_view_);
        view.setLayoutManager((new LinearLayoutManager(this)));
        view.setAdapter(adapter);

        new JsonFile(this, this).execute(JSON_FILE);
        String s = readFile("mountains.json");
        Gson gson = new Gson();
        Type type = new TypeToken<List<Mountain>>() {
        }.getType();
        mountains = gson.fromJson(s, type);

    }
    private void getJson() {
        new JsonTask(this).execute(JSON_URL);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_updaterecycleview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update_recyclerview) {
            getJson();
            Log.d("==>", "Update RecyclerView");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostExecute(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Mountain>>() {}.getType();
        mountains = gson.fromJson(json, type);
        Log.d("MainActivity", "new mountains" + mountains.size());
        adapter = new RecyclerViewAdapter(this, mountains, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Mountain mountain) {
                myWebView.loadUrl(mountain.getAuxdata().getWiki());
                Toast.makeText(MainActivity.this, mountain.toString(), Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "get aux");
            }
        });
        view.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }
}
