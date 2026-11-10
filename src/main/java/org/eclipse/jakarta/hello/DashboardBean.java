package org.eclipse.jakarta.hello;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Named("dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy", new Locale("id", "ID"));

    private final ArrayList<DashboardRecord> anggota = new ArrayList<>();
    private final ArrayList<DashboardRecord> kepengurusan = new ArrayList<>();
    private final ArrayList<DashboardRecord> program = new ArrayList<>();
    private final ArrayList<DashboardRecord> event = new ArrayList<>();
    private final ArrayList<DashboardRecord> keuangan = new ArrayList<>();
    private final ArrayList<DashboardRecord> dokumen = new ArrayList<>();
    private final ArrayList<DashboardRecord> surat = new ArrayList<>();
    private final ArrayList<DashboardRecord> persetujuan = new ArrayList<>();
    private final ArrayList<DashboardRecord> konten = new ArrayList<>();
    private final ArrayList<DashboardRecord> aktivitas = new ArrayList<>();

    private String currentView = "dashboard";
    private ArrayList<String> formValues = new ArrayList<>();
    private int editingIndex = -1;

    public DashboardBean() {
        anggota.add(new DashboardRecord("2024001", "Andi Pratama", "Riset & Teknologi", "2024", "Aktif"));
        anggota.add(new DashboardRecord("2024002", "Siti Rahma", "Komunikasi dan Informasi", "2024", "Aktif"));
        anggota.add(new DashboardRecord("2023008", "Budi Santoso", "Pengembangan SDM", "2023", "Aktif"));
        anggota.add(new DashboardRecord("2025004", "Dina Putri", "Kewirausahaan", "2025", "Menunggu"));

        kepengurusan.add(new DashboardRecord("Badan Pengurus Harian", "Ketua", "Nadia Putri", "2026/2027"));
        kepengurusan.add(new DashboardRecord("Riset & Teknologi", "Koordinator", "Andi Pratama", "2026/2027"));
        kepengurusan.add(new DashboardRecord("Komunikasi dan Informasi", "Koordinator", "Siti Rahma", "2026/2027"));
        kepengurusan.add(new DashboardRecord("Pengembangan SDM", "Koordinator", "Budi Santoso", "2026/2027"));

        program.add(new DashboardRecord("Tech Sharing", "Riset & Teknologi", "2026/2027", "2500000", "Berjalan"));
        program.add(new DashboardRecord("Informatics Day", "Komunikasi dan Informasi", "2026/2027", "4000000", "Perencanaan"));
        program.add(new DashboardRecord("Member Development", "Pengembangan SDM", "2026/2027", "1500000", "Selesai"));

        event.add(new DashboardRecord("Technology Sharing", "2026-12-15", "Seminar", "Riset & Teknologi", "Akan datang"));
        event.add(new DashboardRecord("Web Development Workshop", "2026-12-20", "Workshop", "Riset & Teknologi", "Akan datang"));
        event.add(new DashboardRecord("Informatics Gathering", "2027-01-10", "Internal", "Pengembangan SDM", "Terlaksana"));

        keuangan.add(new DashboardRecord("2026-11-01", "Iuran anggota", "Iuran", "Pemasukan", "2000000", "Disetujui"));
        keuangan.add(new DashboardRecord("2026-11-02", "Konsumsi rapat", "Operasional", "Pengeluaran", "350000", "Menunggu"));
        keuangan.add(new DashboardRecord("2026-11-03", "Sponsor kegiatan", "Sponsor", "Pemasukan", "1500000", "Disetujui"));

        dokumen.add(new DashboardRecord("Proposal Tech Sharing", "Proposal", "Riset & Teknologi", "2026-11-01", "Menunggu"));
        dokumen.add(new DashboardRecord("LPJ Informatics Day", "LPJ", "Komunikasi dan Informasi", "2026-10-28", "Disetujui"));
        dokumen.add(new DashboardRecord("Notulen Rapat", "Administrasi", "Sekretaris", "2026-10-25", "Disetujui"));

        surat.add(new DashboardRecord("001/HIMATIF/XI/2026", "Undangan kegiatan", "Masuk", "2026-11-01", "Normal"));
        surat.add(new DashboardRecord("002/HIMATIF/XI/2026", "Permohonan peminjaman", "Keluar", "2026-11-02", "Tinggi"));
        surat.add(new DashboardRecord("003/HIMATIF/XI/2026", "Surat kerja sama", "Masuk", "2026-11-03", "Normal"));

        persetujuan.add(new DashboardRecord("Proposal Tech Sharing", "Program Kerja", "Riset & Teknologi", "2026-11-01", "Menunggu"));
        persetujuan.add(new DashboardRecord("Dana konsumsi", "Keuangan", "Pengembangan SDM", "2026-11-02", "Menunggu"));
        persetujuan.add(new DashboardRecord("LPJ Informatics Day", "Dokumen", "Komunikasi dan Informasi", "2026-10-28", "Disetujui"));

        konten.add(new DashboardRecord("Artikel", "Tips memulai perjalanan di dunia pemrograman", "Akademik", "Terbit"));
        konten.add(new DashboardRecord("Artikel", "Kolaborasi dalam organisasi mahasiswa", "Organisasi", "Terbit"));
        konten.add(new DashboardRecord("Galeri", "Dokumentasi Technology Sharing", "Kegiatan", "Draf"));

        aktivitas.add(new DashboardRecord("Pengajuan program kerja", "Divisi Riset", "01 Nov 2026", "Menunggu"));
        aktivitas.add(new DashboardRecord("Pembaruan data anggota", "Administrator", "31 Okt 2026", "Selesai"));
        aktivitas.add(new DashboardRecord("Pengajuan dana kegiatan", "Divisi Acara", "30 Okt 2026", "Menunggu"));
        aktivitas.add(new DashboardRecord("Dokumen LPJ diunggah", "Sekretaris", "29 Okt 2026", "Selesai"));
    }

    public String open(String view) {
        if (view == null || !isKnownView(view)) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Menu tidak dikenali.");
            return null;
        }
        currentView = view;
        resetForm();
        return null;
    }

    public String save() {
        List<String> columns = getColumns();
        if (formValues.size() != columns.size()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Form tidak lengkap. Silakan muat ulang halaman.");
            return null;
        }

        ArrayList<String> values = new ArrayList<>();
        for (String value : formValues) {
            if (value == null || value.trim().isEmpty()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Semua kolom wajib diisi.");
                return null;
            }
            values.add(value.trim());
        }
        if ("anggota".equals(currentView) && !values.get(0).matches("\\d{1,20}")) {
            addMessage(FacesMessage.SEVERITY_ERROR, "NIM hanya boleh berisi angka.");
            return null;
        }
        if ("anggota".equals(currentView) && !values.get(3).matches("\\d{4}")) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Angkatan harus berupa tahun dengan 4 angka.");
            return null;
        }
        if ("keuangan".equals(currentView) && !values.get(4).matches("\\d{1,15}")) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Nominal harus berupa angka tanpa pemisah, maksimal 15 digit.");
            return null;
        }
        if ("program".equals(currentView) && !values.get(3).matches("\\d{1,15}")) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Anggaran harus berupa angka tanpa pemisah, maksimal 15 digit.");
            return null;
        }
        for (int i = 0; i < values.size(); i++) {
            List<DashboardField> fields = getFields();
            List<String> options = fields.get(i).getOptions().stream()
                    .map(option -> (String) option.getValue()).toList();
            if (!options.isEmpty() && !options.contains(values.get(i))) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Pilihan pada kolom " + columns.get(i) + " tidak valid.");
                return null;
            }
        }
        for (int i = 0; i < values.size(); i++) {
            if (isDateField(i)) {
                try {
                    LocalDate.parse(values.get(i));
                } catch (java.time.format.DateTimeParseException exception) {
                    addMessage(FacesMessage.SEVERITY_ERROR, "Tanggal harus dipilih melalui kalender.");
                    return null;
                }
            }
        }

        ArrayList<DashboardRecord> records = recordsFor(currentView);
        if (records == null) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Data pada menu ini tidak dapat diubah.");
            return null;
        }

        boolean updating = editingIndex >= 0;
        if (updating) {
            if (editingIndex >= records.size()) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Data yang akan diperbarui sudah tidak tersedia.");
                resetForm();
                return null;
            }
            records.get(editingIndex).setValues(values);
        } else {
            records.add(new DashboardRecord(values));
        }

        addActivity((updating ? "Memperbarui " : "Menambahkan ") + getTitle() + ": " + values.get(0));
        addMessage(FacesMessage.SEVERITY_INFO, updating ? "Perubahan berhasil disimpan." : "Data berhasil ditambahkan.");
        resetForm();
        return null;
    }

    public String edit(Integer rowIndex) {
        ArrayList<DashboardRecord> records = recordsFor(currentView);
        int index = rowIndex == null ? -1 : rowIndex;
        if (records == null || index < 0 || index >= records.size()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Data yang dipilih tidak tersedia.");
            return null;
        }
        formValues = new ArrayList<>(records.get(index).getValues());
        editingIndex = index;
        return null;
    }

    public String delete(Integer rowIndex) {
        ArrayList<DashboardRecord> records = recordsFor(currentView);
        int index = rowIndex == null ? -1 : rowIndex;
        if (records == null || index < 0 || index >= records.size()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Data yang akan dihapus tidak tersedia.");
            return null;
        }
        String label = records.get(index).getValues().get(0);
        records.remove(index);
        addActivity("Menghapus " + getTitle() + ": " + label);
        if (editingIndex == index) {
            resetForm();
        } else if (editingIndex > index) {
            editingIndex--;
        }
        addMessage(FacesMessage.SEVERITY_INFO, "Data berhasil dihapus.");
        return null;
    }

    public String updateApproval(Integer rowIndex, String status) {
        if (!"Disetujui".equals(status) && !"Ditolak".equals(status)) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Status persetujuan tidak valid.");
            return null;
        }
        int index = rowIndex == null ? -1 : rowIndex;
        if (index < 0 || index >= persetujuan.size()) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Pengajuan yang dipilih tidak tersedia.");
            return null;
        }
        persetujuan.get(index).getValues().set(4, status);
        addActivity(status + " pengajuan: " + persetujuan.get(index).getValues().get(0));
        addMessage(FacesMessage.SEVERITY_INFO, "Status pengajuan diperbarui.");
        return null;
    }

    public String cancelEdit() {
        resetForm();
        return null;
    }

    private void resetForm() {
        formValues = new ArrayList<>();
        for (int i = 0; i < getColumns().size(); i++) {
            formValues.add("");
        }
        editingIndex = -1;
    }

    private ArrayList<DashboardRecord> recordsFor(String view) {
        return switch (view) {
            case "anggota" -> anggota;
            case "kepengurusan" -> kepengurusan;
            case "program" -> program;
            case "event" -> event;
            case "keuangan" -> keuangan;
            case "dokumen" -> dokumen;
            case "surat" -> surat;
            case "approval" -> persetujuan;
            case "konten" -> konten;
            default -> null;
        };
    }

    private boolean isKnownView(String view) {
        return List.of("dashboard", "anggota", "kepengurusan", "program", "event",
                "keuangan", "dokumen", "surat", "approval", "konten", "statistik").contains(view);
    }

    private void addActivity(String description) {
        aktivitas.add(0, new DashboardRecord(description, "Administrator",
                LocalDate.now().format(DATE_FORMAT), "Selesai"));
        if (aktivitas.size() > 10) {
            aktivitas.remove(aktivitas.size() - 1);
        }
    }

    private void addMessage(FacesMessage.Severity severity, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, null));
    }

    private BigDecimal totalFor(String type) {
        BigDecimal total = BigDecimal.ZERO;
        for (DashboardRecord transaction : keuangan) {
            List<String> values = transaction.getValues();
            if (values.get(3).equalsIgnoreCase(type)) {
                total = total.add(new BigDecimal(values.get(4)));
            }
        }
        return total;
    }

    private String formatCurrency(BigDecimal amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        format.setMaximumFractionDigits(0);
        return format.format(amount);
    }

    private long countStatus(List<DashboardRecord> records, int statusIndex, String status) {
        return records.stream()
                .filter(record -> record.getValues().size() > statusIndex)
                .filter(record -> record.getValues().get(statusIndex).equalsIgnoreCase(status))
                .count();
    }

    public String getTitle() {
        return switch (currentView) {
            case "anggota" -> "Manajemen Anggota";
            case "kepengurusan" -> "Kepengurusan";
            case "program" -> "Program Kerja";
            case "event" -> "Manajemen Event";
            case "keuangan" -> "Keuangan";
            case "dokumen" -> "Manajemen Dokumen";
            case "surat" -> "Manajemen Surat";
            case "approval" -> "Persetujuan";
            case "konten" -> "Artikel & Galeri";
            case "statistik" -> "Statistik Organisasi";
            default -> "Dashboard";
        };
    }

    public String getDescription() {
        return switch (currentView) {
            case "anggota" -> "Kelola data anggota, departemen, dan status keanggotaan.";
            case "kepengurusan" -> "Kelola struktur, jabatan, dan penanggung jawab organisasi.";
            case "program" -> "Pantau dan perbarui program kerja organisasi.";
            case "event" -> "Kelola agenda kegiatan, kategori, dan penanggung jawab.";
            case "keuangan" -> "Catat pemasukan dan pengeluaran organisasi.";
            case "dokumen" -> "Kelola arsip proposal, laporan, dan dokumen organisasi.";
            case "surat" -> "Kelola pencatatan surat masuk dan surat keluar.";
            case "approval" -> "Tinjau dan perbarui status pengajuan organisasi.";
            case "konten" -> "Kelola artikel dan dokumentasi yang disiapkan untuk publikasi.";
            case "statistik" -> "Pantau capaian organisasi berdasarkan data yang dikelola.";
            default -> "Ringkasan informasi dan aktivitas organisasi.";
        };
    }

    public List<String> getColumns() {
        return switch (currentView) {
            case "anggota" -> List.of("NIM", "Nama", "Departemen", "Angkatan", "Status");
            case "kepengurusan" -> List.of("Departemen", "Jabatan", "Penanggung Jawab", "Periode");
            case "program" -> List.of("Nama Program", "Departemen", "Periode", "Anggaran (Rp)", "Status");
            case "event" -> List.of("Nama Event", "Tanggal", "Kategori", "Penanggung Jawab", "Status");
            case "keuangan" -> List.of("Tanggal", "Keterangan", "Kategori", "Jenis", "Nominal (Rp)", "Status");
            case "dokumen" -> List.of("Nama Dokumen", "Kategori", "Pemilik", "Tanggal", "Status");
            case "surat" -> List.of("Nomor Surat", "Perihal", "Jenis", "Tanggal", "Prioritas");
            case "approval" -> List.of("Pengajuan", "Modul", "Pengaju", "Tanggal", "Status");
            case "konten" -> List.of("Jenis Konten", "Judul / Kegiatan", "Kategori", "Status Publikasi");
            default -> List.of();
        };
    }

    public List<DashboardField> getFields() {
        return switch (currentView) {
            case "anggota" -> List.of(
                    field("NIM", "digits"), field("Nama"), field("Departemen", "select",
                            "Badan Pengurus Harian", "Riset & Teknologi", "Komunikasi dan Informasi",
                            "Pengembangan SDM", "Kewirausahaan"),
                    field("Angkatan", "year"), field("Status", "select", "Aktif", "Nonaktif", "Menunggu"));
            case "kepengurusan" -> List.of(
                    field("Departemen"), field("Jabatan"), field("Penanggung Jawab"), field("Periode"));
            case "program" -> List.of(
                    field("Nama Program"), field("Departemen"), field("Periode"), field("Anggaran (Rp)", "number"),
                    field("Status", "select", "Perencanaan", "Berjalan", "Selesai", "Ditunda"));
            case "event" -> List.of(
                    field("Nama Event"), field("Tanggal", "date"), field("Kategori"), field("Penanggung Jawab"),
                    field("Status", "select", "Akan datang", "Berlangsung", "Terlaksana", "Dibatalkan"));
            case "keuangan" -> List.of(
                    field("Tanggal", "date"), field("Keterangan"), field("Kategori"),
                    field("Jenis", "select", "Pemasukan", "Pengeluaran"), field("Nominal (Rp)", "number"),
                    field("Status", "select", "Menunggu", "Disetujui", "Ditolak"));
            case "dokumen" -> List.of(
                    field("Nama Dokumen"), field("Kategori"), field("Pemilik"), field("Tanggal", "date"),
                    field("Status", "select", "Menunggu", "Disetujui", "Ditolak", "Diarsipkan"));
            case "surat" -> List.of(
                    field("Nomor Surat"), field("Perihal"), field("Jenis", "select", "Masuk", "Keluar"),
                    field("Tanggal", "date"), field("Prioritas", "select", "Normal", "Tinggi", "Mendesak"));
            case "approval" -> List.of(
                    field("Pengajuan"), field("Modul", "select", "Program Kerja", "Event", "Keuangan", "Dokumen"),
                    field("Pengaju"), field("Tanggal", "date"),
                    field("Status", "select", "Menunggu", "Disetujui", "Ditolak"));
            case "konten" -> List.of(
                    field("Jenis Konten", "select", "Artikel", "Galeri"), field("Judul / Kegiatan"),
                    field("Kategori"), field("Status Publikasi", "select", "Draf", "Terbit", "Diarsipkan"));
            default -> List.of();
        };
    }

    private DashboardField field(String label) {
        return field(label, "text");
    }

    private DashboardField field(String label, String type, String... options) {
        return new DashboardField(label, type, options);
    }

    private boolean isDateField(int index) {
        return switch (currentView) {
            case "event" -> index == 1;
            case "keuangan" -> index == 0;
            case "approval" -> index == 3;
            case "dokumen", "surat" -> index == 3;
            default -> false;
        };
    }

    public List<DashboardRecord> getRecords() {
        ArrayList<DashboardRecord> records = recordsFor(currentView);
        return records == null ? List.of() : records;
    }

    public List<DashboardRecord> getActivities() {
        return aktivitas;
    }

    public ArrayList<String> getFormValues() {
        return formValues;
    }

    public void setFormValues(ArrayList<String> formValues) {
        this.formValues = formValues;
    }

    public String getCurrentView() {
        return currentView;
    }

    public boolean isEditing() {
        return editingIndex >= 0;
    }

    public boolean isManagedView() {
        return recordsFor(currentView) != null;
    }

    public int getMemberCount() {
        return anggota.size();
    }

    public long getActiveMemberCount() {
        return countStatus(anggota, 4, "Aktif");
    }

    public int getProgramCount() {
        return program.size();
    }

    public long getCompletedProgramCount() {
        return countStatus(program, 4, "Selesai");
    }

    public int getEventCount() {
        return event.size();
    }

    public long getUpcomingEventCount() {
        return event.stream().filter(record -> record.getValues().get(4).equalsIgnoreCase("Akan datang")).count();
    }

    public long getCompletedEventCount() {
        return countStatus(event, 4, "Terlaksana");
    }

    public long getPendingApprovalCount() {
        return countStatus(persetujuan, 4, "Menunggu");
    }

    public String getTotalIncome() {
        return formatCurrency(totalFor("Pemasukan"));
    }

    public String getTotalExpenses() {
        return formatCurrency(totalFor("Pengeluaran"));
    }

    public String getBalance() {
        return formatCurrency(totalFor("Pemasukan").subtract(totalFor("Pengeluaran")));
    }

    public String getProgramCompletionRate() {
        if (program.isEmpty()) {
            return "0%";
        }
        return Math.round((double) getCompletedProgramCount() * 100 / program.size()) + "%";
    }
}
