package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class ChildProfiles extends AppCompatActivity {
    MyDBHelper dbHelper = new MyDBHelper(this);
    ListView listView;
    List<String> childrenList;
    Button addChild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profiles);
        listView= findViewById(R.id.listView);
        childrenList = dbHelper.getChildData();
        addChild= findViewById(R.id.addProfile);
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, R.layout.row_view, R.id.textView9, childrenList);
        listView.setAdapter(adp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putString("childName", childrenList.get(position)).apply();
                Intent intent = new Intent(ChildProfiles.this, HomeScreen.class);
                startActivity(intent);
            }
        });

        addChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildProfiles.this, CreateChildProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}