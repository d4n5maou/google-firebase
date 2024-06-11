package com.pab.firebasedosen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DBReadActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DosenAdapter dosenAdapter;
    private List<Dosen> dosenList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbread);

        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacingItemDecoration(16));  // Add margin between items
        dosenList = new ArrayList<>();
        dosenAdapter = new DosenAdapter(dosenList, this);  // Pass context to adapter
        recyclerView.setAdapter(dosenAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("dosen");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dosenList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Dosen dosen = postSnapshot.getValue(Dosen.class);
                    dosenList.add(dosen);
                }
                dosenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}
