package com.jannik.zelfitestandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    public String readFile() {
        String result = "";
        try {
            InputStream is = getAssets().open("random_ten");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<joke> parse() {
        String jsonData = readFile();
        //joke
        int idJson;
        String typeJson;
        String setupJson;
        String punchlineJson;

        ArrayList<joke> jokeList = new ArrayList<>();

        try {
            // get jokes from json (into arraylist)
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                idJson = obj.getInt("id");
                typeJson = obj.getString("type");
                setupJson = obj.getString("setup");
                punchlineJson = obj.getString("punchline");

                joke tmp = new joke(idJson, typeJson, setupJson, punchlineJson);

                jokeList.add(tmp);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jokeList;
    }


    private ListView listView;
    private ListViewAdapter listViewAdapter;

    final Context context = this;

    ArrayList<String> allTypes = new ArrayList<>();
    ArrayList<String> allSetups = new ArrayList<>();
    ArrayList<String> allPunchlines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // loading all jokes
        ArrayList<joke> jokeList = parse();

        // pushing types and setups into an array
        for (int i = 0; i < jokeList.size(); i++) {
            allTypes.add(jokeList.get(i).getType());
            allSetups.add(jokeList.get(i).getSetup());
            allPunchlines.add(jokeList.get(i).getPunchline());
        }

        listView = (ListView) findViewById(R.id.jokes);
        listViewAdapter = new ListViewAdapter(this, allTypes, allSetups);

        listView.setAdapter(listViewAdapter);

        // Long click on type/setup item
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(this);


    }

    /*public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
        Toast.makeText(this,allPunchlines.get(position), Toast.LENGTH_LONG).show();
    }*/

    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
        // TODO Auto-generated method stub
        TextView tmpTV;

        // get punchline_dialogbox.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.punchline_dialogbox, null);

        // get textview item in the punchline dialogbox xml
        tmpTV = (TextView) promptsView.findViewById(R.id.punchline);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set punchline_dialogbox.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        // change text to right punchline
        tmpTV.setText(allPunchlines.get(pos));

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        return true;
    }

}
