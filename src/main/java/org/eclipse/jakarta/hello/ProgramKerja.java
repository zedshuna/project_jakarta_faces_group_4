package org.eclipse.jakarta.hello;

public class ProgramKerja {
    private String namaProker;
    private String kategori;
    private String status;

    // Constructor
    public ProgramKerja(String namaProker, String kategori, String status) {
        this.namaProker = namaProker;
        this.kategori = kategori;
        this.status = status;
    }

    // Getter dan Setter
    public String getNamaProker() { return namaProker; }
    public void setNamaProker(String namaProker) { this.namaProker = namaProker; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}