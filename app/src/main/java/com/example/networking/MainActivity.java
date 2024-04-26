package com.example.networking;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private RecyclerViewAdapter adapter;
    private ArrayList<Mountain> mountains = new ArrayList<Mountain>();

    private RecyclerView view;
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

        new JsonFile(this, this).execute(JSON_FILE);
        String s = readFile("mountains.json");

        Gson gson = new Gson();
        Type type = new TypeToken<List<Mountain>>() {}.getType();
        List<Mountain>listOfMountains = gson.fromJson(s, type);
        assert listOfMountains != null;
        mountains.addAll(listOfMountains);

        view = findViewById(R.id.recycler_view_);
        view.setLayoutManager((new LinearLayoutManager(this)));
        adapter = new RecyclerViewAdapter(this, mountains, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Mountain items) {
                Toast.makeText(MainActivity.this, mountains.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        view.setAdapter(adapter);
    }

    @Override
    public void onPostExecute(String json) {
        Log.d("MainActivity", json);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Mountain>>() {}.getType();
        List<Mountain>newListOfMountains = gson.fromJson(json, type);
        mountains = new ArrayList<Mountain>();
        for(int i = 0; i < newListOfMountains.size(); i++) {
            mountains.add(newListOfMountains.get(i));
            Log.d("MainActivity", "new mountains" + mountains.get(i));
        }
        adapter.notifyDataSetChanged();
        /*adapter = new RecyclerViewAdapter(MainActivity.this, newMountains, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Mountain items) {
                Toast.makeText(MainActivity.this, newMountains.toString(), Toast.LENGTH_SHORT).show();
            }
        });
         */

        //this.view.setAdapter(adapter);
        //view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //adapter.notifyDataSetChanged();
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
            new JsonTask(this).execute(JSON_URL);

            Log.d("==>","Update RecyclerView");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
