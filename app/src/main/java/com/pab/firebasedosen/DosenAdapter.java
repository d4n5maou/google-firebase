package com.pab.firebasedosen;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.DosenViewHolder> {
    private List<Dosen> dosenList;
    private Context context;

    public DosenAdapter(List<Dosen> dosenList, Context context) {
        this.dosenList = dosenList;
        this.context = context;
    }

    @NonNull
    @Override
    public DosenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dosen, parent, false);
        return new DosenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DosenViewHolder holder, int position) {
        Dosen dosen = dosenList.get(position);
        holder.nipText.setText(dosen.getNip());
        holder.namaDosenText.setText(dosen.getNamaDosen());
        holder.jabatanText.setText(dosen.getJabatan());

        holder.itemView.setOnClickListener(v -> showOptionsDialog(dosen));
    }

    @Override
    public int getItemCount() {
        return dosenList.size();
    }

    private void showOptionsDialog(Dosen dosen) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pilih Aksi");
        builder.setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {
            if (which == 0) {
                showEditDialog(dosen);
            } else if (which == 1) {
                deleteDosen(dosen);
            }
        });
        builder.show();
    }

    private void showEditDialog(Dosen dosen) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_edit_dosen, null);
        builder.setView(view);

        EditText editNip = view.findViewById(R.id.editNip);
        EditText editNamaDosen = view.findViewById(R.id.editNamaDosen);
        Spinner editSpinnerJA = view.findViewById(R.id.editSpinnerJA);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);

        editNip.setText(dosen.getNip());
        editNamaDosen.setText(dosen.getNamaDosen());

        // Assuming that spinner entries match the values in the dosen.getJabatan()
        String[] jabatanArray = context.getResources().getStringArray(R.array.JA);
        for (int i = 0; i < jabatanArray.length; i++) {
            if (jabatanArray[i].equals(dosen.getJabatan())) {
                editSpinnerJA.setSelection(i);
                break;
            }
        }

        AlertDialog dialog = builder.create();

        btnUpdate.setOnClickListener(v -> {
            String newNip = editNip.getText().toString();
            String newNamaDosen = editNamaDosen.getText().toString();
            String newJabatan = editSpinnerJA.getSelectedItem().toString();

            updateDosen(dosen.getId(), newNip, newNamaDosen, newJabatan);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void updateDosen(String id, String nip, String namaDosen, String jabatan) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dosen").child(id);
        Dosen updatedDosen = new Dosen(id, nip, namaDosen, jabatan);
        databaseReference.setValue(updatedDosen).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDosen(Dosen dosen) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dosen").child(dosen.getId());
        databaseReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class DosenViewHolder extends RecyclerView.ViewHolder {
        TextView nipText, namaDosenText, jabatanText;

        public DosenViewHolder(@NonNull View itemView) {
            super(itemView);
            nipText = itemView.findViewById(R.id.nipText);
            namaDosenText = itemView.findViewById(R.id.namaDosenText);
            jabatanText = itemView.findViewById(R.id.jabatanText);
        }
    }
}
