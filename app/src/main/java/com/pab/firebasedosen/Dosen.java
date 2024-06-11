package com.pab.firebasedosen;

public class Dosen {
    private String id;
    private String nip;
    private String namaDosen;
    private String jabatan;

    public Dosen() {
        // Default constructor required for calls to DataSnapshot.getValue(Dosen.class)
    }

    public Dosen(String id, String nip, String namaDosen, String jabatan) {
        this.id = id;
        this.nip = nip;
        this.namaDosen = namaDosen;
        this.jabatan = jabatan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNamaDosen() {
        return namaDosen;
    }

    public void setNamaDosen(String namaDosen) {
        this.namaDosen = namaDosen;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
