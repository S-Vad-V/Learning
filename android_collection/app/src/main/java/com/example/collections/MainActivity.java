package com.example.collections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        list = new ArrayList<>();
    }

    public void createList(View view) {
        list = new ArrayList<>();
        list.add("String1");
        list.add("String2");
        list.add("String3");
        list.add("String4");
        list.add("String5");
        list.add("String6");
        list.add("String7");
        ListView listView = (ListView) findViewById(R.id.lvList1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);


        // Toast - маленькая всплывашка
        listView.setOnItemClickListener((adapterView, view1, i, l) -> Toast.makeText(getApplicationContext(),
                "Нажато '" + ((TextView) view1).getText() + "'",
                Toast.LENGTH_SHORT).show());
    }
}