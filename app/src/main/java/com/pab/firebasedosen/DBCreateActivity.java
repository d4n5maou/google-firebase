package com.pab.firebasedosen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DBCreateActivity extends AppCompatActivity {

    private EditText nipEdit;
    private EditText namaDosenEdit;
    private Spinner spinnerJA;
    private Button btnSubmit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcreate);

        //bind
        nipEdit = findViewById(R.id.nipEdit);
        namaDosenEdit = findViewById(R.id.namaDosenEdit);
        spinnerJA = findViewById(R.id.spinnerJA);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nip = nipEdit.getText().toString();
                String namaDosen = namaDosenEdit.getText().toString();
                String selectJA = spinnerJA.getSelectedItem().toString();

                if (nip.isEmpty() || namaDosen.isEmpty()) {
                    Toast.makeText(DBCreateActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                //add data to db
                addDatatoDB(nip, namaDosen, selectJA);
            }
        });
    }

    private void addDatatoDB(String nip, String namaDosen, String selectJA) {
        // Mendapatkan referensi ke Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dosen");

        // Menghasilkan ID unik untuk setiap data dosen
        String id = myRef.push().getKey();

        // Membuat objek dosen dengan ID unik
        Dosen dosen = new Dosen(id, nip, namaDosen, selectJA);

        // Menambahkan data dosen ke database
        myRef.child(id).setValue(dosen).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Data berhasil ditambahkan
                Toast.makeText(DBCreateActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            } else {
                // Gagal menambahkan data
                Toast.makeText(DBCreateActivity.this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
