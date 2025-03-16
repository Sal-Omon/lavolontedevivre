document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("jwt");

    // ✅ Controlla se il token esiste
    if (!token) {
        alert("Accesso negato. Effettua il login.");
        window.location.href = "Login.html";
        return;
    }

    // ✅ Se il token è valido, mostra il body
    document.body.style.display = "block";

    // ✅ Logout: Rimuove il token e reindirizza alla pagina di login
    document.getElementById("logoutBtn").addEventListener("click", function (event) {
        event.preventDefault(); // Evita il comportamento di default del link
        localStorage.removeItem("jwt");
        window.location.href = "Login.html";
    });
});
