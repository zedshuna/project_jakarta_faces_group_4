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

const sidebarToggle = document.getElementById("sidebarToggle");
const sidebar = document.getElementById("sidebar");
if (sidebarToggle && sidebar) {
  sidebarToggle.addEventListener("click", () => sidebar.classList.toggle("open"));
}

const logoutButton = document.getElementById("logoutButton");
if (logoutButton) {
  logoutButton.addEventListener("click", () => sessionStorage.removeItem("himatifDemoLogin"));
}