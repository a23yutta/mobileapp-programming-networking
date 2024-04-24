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

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";
    private final String JSON_FILE = "mountains.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new JsonFile(this, this).execute(JSON_FILE);

        ArrayList<Mountain> mountains = new ArrayList<>(Arrays.asList(
                new Mountain("Mount Everest","Asia" ,8849),
                new Mountain("Mont Blanc","Europe" ,4810),
                new Mountain("Denali", "North America" ,6190)
        ));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mountains, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(Mountain items) {
                Toast.makeText(MainActivity.this, mountains.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView view = findViewById(R.id.recycler_view_);
        view.setLayoutManager((new LinearLayoutManager(this)));
        view.setAdapter(adapter);
    }

    @Override
    public void onPostExecute(String json) {
        Log.d("MainActivity", json);
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
            //onPostExecute(JSON_URL);
            Log.d("==>","Update RecyclerView");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
