// Navigasi menu pada website publik (mobile)
const menuToggle = document.querySelector("[data-menu-toggle]");
const publicNav = document.querySelector("[data-public-nav]");
if (menuToggle && publicNav) {
  menuToggle.addEventListener("click", () => publicNav.classList.toggle("open"));
}

// Simulasi login lokal. Ini bukan autentikasi yang aman untuk aplikasi sungguhan.
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;
    const message = document.getElementById("loginMessage");
    if (username === "admin" && password === "admin123") {
      sessionStorage.setItem("himatifDemoLogin", "true");
      window.location.href = "dashboard.xhtml";
    } else {
      message.textContent = "Username atau password demo tidak sesuai.";
    }
  });
}

// Konten dashboard menggunakan data contoh (dummy).
const dashboardContent = document.getElementById("dashboardContent");
if (dashboardContent) {
  const viewData = {
    dashboard: {
      title: "Dashboard", desc: "Ringkasan informasi dan aktivitas organisasi.",
      html: `<div class="stat-grid">
        <div class="stat-card"><span>Total Anggota</span><strong>128</strong><small>↑ Data contoh anggota aktif</small></div>
        <div class="stat-card"><span>Program Kerja</span><strong>12</strong><small>4 sedang berjalan</small></div>
        <div class="stat-card"><span>Event Tahun Ini</span><strong>8</strong><small>3 event mendatang</small></div>
        <div class="stat-card"><span>Menunggu Persetujuan</span><strong>5</strong><small>Perlu ditinjau</small></div>
      </div>
      <div class="panel"><div class="panel-heading"><h3>Aktivitas Terbaru</h3><span>Data simulasi</span></div>${makeTable(["Aktivitas","Pengguna","Tanggal","Status"],[["Pengajuan program kerja","Divisi Riset","01 Nov 2026","pending"],["Pembaruan data anggota","Admin","31 Okt 2026","approved"],["Pengajuan dana kegiatan","Divisi Acara","30 Okt 2026","pending"],["Dokumen LPJ diunggah","Sekretaris","29 Okt 2026","approved"]])}</div>
      <div class="simple-grid"><div class="panel"><div class="panel-heading"><h3>Ringkasan Organisasi</h3></div><p class="muted small">Periode kepengurusan 2026/2027</p><h2>4 Departemen</h2><p class="muted small">Data ini merupakan contoh untuk mengisi rancangan dashboard.</p></div><div class="panel"><div class="panel-heading"><h3>Pengingat</h3></div><p class="small">• Tinjau 5 pengajuan yang menunggu persetujuan.</p><p class="small">• Perbarui agenda kegiatan bulan depan.</p></div></div>`
    },
    anggota: {title:"Manajemen Anggota",desc:"Data anggota dan status keanggotaan organisasi.",html:tablePanel(["NIM","Nama","Departemen","Angkatan","Status"],[["2024001","Andi Pratama","Riset & Teknologi","2024","approved"],["2024002","Siti Rahma","Kominfo","2024","approved"],["2023008","Budi Santoso","PSDM","2023","approved"],["2025004","Dina Putri","Kewirausahaan","2025","pending"]])},
    kepengurusan:{title:"Kepengurusan",desc:"Periode, departemen, jabatan, dan struktur organisasi.",html:infoPanel([["Badan Pengurus Harian","Ketua, Wakil Ketua, Sekretaris, Bendahara"],["Riset & Teknologi","Pengembangan keilmuan dan teknologi"],["Komunikasi dan Informasi","Publikasi, informasi, dan dokumentasi"],["Pengembangan SDM","Pengembangan kapasitas anggota"]])},
    program:{title:"Program Kerja",desc:"Pengelolaan program kerja berdasarkan departemen dan periode.",html:tablePanel(["Nama Program","Departemen","Periode","Anggaran","Status"],[["Tech Sharing","Riset & Teknologi","2026/2027","Rp2.500.000","approved"],["Informatics Day","Kominfo","2026/2027","Rp4.000.000","pending"],["Member Development","PSDM","2026/2027","Rp1.500.000","approved"]])},
    event:{title:"Manajemen Event",desc:"Jadwal, kategori, dan keterkaitan event dengan program kerja.",html:tablePanel(["Nama Event","Tanggal","Kategori","Penanggung Jawab","Status"],[["Technology Sharing","15 Des 2026","Seminar","Riset & Teknologi","approved"],["Web Development Workshop","20 Des 2026","Workshop","Riset & Teknologi","pending"],["Informatics Gathering","10 Jan 2027","Internal","PSDM","approved"]])},
    keuangan:{title:"Keuangan",desc:"Pencatatan pemasukan, pengeluaran, dan persetujuan transaksi.",html:`<div class="stat-grid"><div class="stat-card"><span>Total Pemasukan</span><strong>Rp8,5 jt</strong></div><div class="stat-card"><span>Total Pengeluaran</span><strong>Rp3,2 jt</strong></div><div class="stat-card"><span>Saldo</span><strong>Rp5,3 jt</strong></div><div class="stat-card"><span>Menunggu Review</span><strong>2</strong></div></div>${tablePanel(["Tanggal","Keterangan","Kategori","Jenis","Nominal","Status"],[["01 Nov 2026","Iuran anggota","Iuran","Pemasukan","Rp2.000.000","approved"],["02 Nov 2026","Konsumsi rapat","Operasional","Pengeluaran","Rp350.000","pending"],["03 Nov 2026","Sponsor kegiatan","Sponsor","Pemasukan","Rp1.500.000","approved"]])}`},
    dokumen:{title:"Manajemen Dokumen",desc:"Arsip proposal, laporan pertanggungjawaban, dan dokumen organisasi.",html:tablePanel(["Nama Dokumen","Kategori","Pemilik","Tanggal","Status"],[["Proposal Tech Sharing","Proposal","Riset & Teknologi","01 Nov 2026","pending"],["LPJ Informatics Day","LPJ","Kominfo","28 Okt 2026","approved"],["Notulen Rapat","Administrasi","Sekretaris","25 Okt 2026","approved"]])},
    surat:{title:"Manajemen Surat",desc:"Pencatatan surat masuk dan surat keluar organisasi.",html:tablePanel(["Nomor Surat","Perihal","Jenis","Tanggal","Prioritas"],[["001/HIMATIF/XI/2026","Undangan kegiatan","Masuk","01 Nov 2026","Normal"],["002/HIMATIF/XI/2026","Permohonan peminjaman","Keluar","02 Nov 2026","Tinggi"],["003/HIMATIF/XI/2026","Surat kerja sama","Masuk","03 Nov 2026","Normal"]])},
    approval:{title:"Persetujuan",desc:"Daftar pengajuan program kerja, event, keuangan, dan dokumen.",html:tablePanel(["Pengajuan","Modul","Pengaju","Tanggal","Status"],[["Proposal Tech Sharing","Program Kerja","Riset & Teknologi","01 Nov 2026","pending"],["Dana konsumsi","Keuangan","PSDM","02 Nov 2026","pending"],["LPJ Informatics Day","Dokumen","Kominfo","28 Okt 2026","approved"]])},
    konten:{title:"Artikel & Galeri",desc:"Pengelolaan konten yang ditampilkan pada website publik.",html:infoPanel([["Artikel","Buat dan kelola artikel informasi organisasi."],["Kategori Artikel","Kelompokkan artikel berdasarkan topik."],["Galeri","Kelola foto dokumentasi kegiatan."],["Publikasi Event","Tampilkan informasi kegiatan pada halaman publik."]])},
    statistik:{title:"Statistik Organisasi",desc:"Ringkasan data keanggotaan, kegiatan, dan keuangan.",html:`<div class="stat-grid"><div class="stat-card"><span>Anggota Aktif</span><strong>128</strong></div><div class="stat-card"><span>Program Selesai</span><strong>8</strong></div><div class="stat-card"><span>Event Terlaksana</span><strong>6</strong></div><div class="stat-card"><span>Rasio Program Selesai</span><strong>67%</strong></div></div><div class="panel"><h3>Ringkasan Periode 2026/2027</h3><p class="muted small">Visualisasi statistik dan laporan detail dapat ditambahkan setelah data backend tersedia.</p><div class="simple-grid"><div class="info-box"><strong>Keanggotaan</strong><p>128 anggota terdaftar sebagai data simulasi.</p></div><div class="info-box"><strong>Kegiatan</strong><p>8 program kerja dan 6 event terlaksana sebagai data simulasi.</p></div></div></div>`}
  };

  function makeTable(headers, rows) {
    return `<div class="table-wrap"><table><thead><tr>${headers.map(h=>`<th>${h}</th>`).join("")}</tr></thead><tbody>${rows.map(row=>`<tr>${row.map((cell,i)=>`<td>${i===row.length-1 && ["approved","pending","rejected"].includes(cell) ? `<span class="status ${cell==="pending"?"pending":cell==="rejected"?"rejected":""}">${cell==="approved"?"Disetujui":cell==="pending"?"Menunggu":"Ditolak"}</span>`:cell}</td>`).join("")}</tr>`).join("")}</tbody></table></div>`;
  }
  function tablePanel(headers, rows) {
    return `<div class="panel"><div class="panel-heading"><h3>Daftar Data</h3><span>${rows.length} data contoh</span></div><div class="toolbar"><input type="search" placeholder="Cari data (tampilan demo)"><button type="button" class="demo-add">+ Tambah Data</button></div>${makeTable(headers,rows)}</div>`;
  }
  function infoPanel(items) {
    return `<div class="simple-grid">${items.map(([title,desc])=>`<div class="panel info-box"><strong>${title}</strong><p>${desc}</p><button class="action-btn demo-action">Lihat Detail</button></div>`).join("")}</div>`;
  }
  function renderView(name) {
    const data = viewData[name] || viewData.dashboard;
    document.getElementById("breadcrumb").textContent = data.title;
    dashboardContent.innerHTML = `<div class="page-heading"><div><h1>${data.title}</h1><p>${data.desc}</p></div><span class="tag">Periode 2026/2027</span></div>${data.html}`;
    document.querySelectorAll(".side-link[data-view]").forEach(link=>link.classList.toggle("active",link.dataset.view===name));
    dashboardContent.querySelectorAll(".demo-add,.demo-action").forEach(button=>button.addEventListener("click",()=>alert("Ini masih prototype frontend. Fungsi ini akan dihubungkan ke backend pada tahap berikutnya.")));
  }
  document.querySelectorAll(".side-link[data-view]").forEach(link=>link.addEventListener("click",event=>{
    event.preventDefault();renderView(link.dataset.view);
    document.getElementById("sidebar").classList.remove("open");
  }));
  renderView("dashboard");
  document.getElementById("sidebarToggle")?.addEventListener("click",()=>document.getElementById("sidebar").classList.toggle("open"));
  document.getElementById("logoutButton")?.addEventListener("click",()=>{
    sessionStorage.removeItem("himatifDemoLogin");window.location.href="login.xhtml";
  });
}
