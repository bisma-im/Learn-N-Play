package com.example.learnnplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChildProfiles extends AppCompatActivity {

    private ListView listView;
    private List<String> childrenList;
    private Button addChild;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profiles);

        listView = findViewById(R.id.listView);
        addChild = findViewById(R.id.addProfile);
        childrenList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();

        // Fetch child names from Firestore
        fetchChildNamesFromFirestore();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_view, R.id.textView9, childrenList);
        listView.setAdapter(adapter);

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

    private void fetchChildNamesFromFirestore() {
        CollectionReference childProfilesRef = firestore.collection("childProfiles");

        // Fetch document snapshots and extract document IDs (child names)
        childProfilesRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String childName = documentSnapshot.getId();
                childrenList.add(childName);
            }

            // Notify the adapter that the data set has changed
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) listView.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(e -> {
            // Handle the failure to fetch data
            e.printStackTrace();
        });
    }
}
