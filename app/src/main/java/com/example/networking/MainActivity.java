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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";
    private final String JSON_FILE = "mountains.json";
    /*ArrayList<Mountain> mountains = new ArrayList<>(Arrays.asList(
            new Mountain("Mount Everest","Asia" ,8849),
            new Mountain("Mont Blanc","Europe" ,4810),
            new Mountain("Denali", "North America" ,6190)
    ));
     */
    ArrayList<Mountain> mountains;
    //Mountain[] mountains;
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
        Log.d("MainActivity","The following text was found in textfile:\n\n"+s);

        Gson gson = new Gson();

        mountains = gson.fromJson(s, ArrayList.class);

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
        /*Gson gson = new Gson();
        mountains = gson.fromJson(json, ArrayList.class);
        for(int i = 0; i < mountains.size(); i++){
            Log.d("hej", "Nya berg" + mountains.get(i).getName());
        }*/

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
            //new JsonTask(this).execute(JSON_URL);
            String s = readFile("mountains.json");
            Log.d("MainActivity","The following text was found in textfile:\n\n"+s);

            /*Gson gson = new Gson();
            mountains = gson.fromJson(s, ArrayList.class);

             */
            Log.d("==>","Update RecyclerView");
            return true;




        }
        return super.onOptionsItemSelected(item);
    }
}
