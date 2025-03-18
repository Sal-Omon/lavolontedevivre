/*document.addEventListener("DOMContentLoaded", function () {
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
});*/



    document.addEventListener("DOMContentLoaded", async function () {
    const token = localStorage.getItem("jwt");

    // ✅ Controlla se il token esiste
    if (!token) {
    alert("Accesso negato. Effettua il login.");
    window.location.href = "Login.html";
    return;
}

    // ✅ Mostra il body dopo la verifica del token
    document.body.style.display = "block";

    // ✅ Logout: Rimuove il token e reindirizza alla pagina di login
    document.getElementById("logoutBtn").addEventListener("click", function (event) {
    event.preventDefault();
    localStorage.removeItem("jwt");
    window.location.href = "Login.html";
});

    // ✅ Funzione per recuperare dati da Grafana API
    async function fetchGrafanaData() {
    try {
    const response = await fetch("http://localhost:3000/api/datasources/proxy/1/query", {
    method: "GET",
    headers: {
    "Content-Type": "application/json",
    "Authorization": "Bearer YOUR_GRAFANA_API_TOKEN"
}
});

    if (!response.ok) {
    throw new Error("Errore nel recupero dati da Grafana");
}

    const data = await response.json();

    // Verifica struttura dei dati
    if (!data.results || !data.results[0] || !data.results[0].series || !data.results[0].series[0].values) {
    throw new Error("Dati non validi ricevuti da Grafana");
}

    return data.results[0].series[0].values;
} catch (error) {
    console.error("Errore:", error);
    return [];
}
}

    // ✅ Funzione per visualizzare il grafico
    async function renderChart() {
    const rawData = await fetchGrafanaData();
    if (rawData.length === 0) return;

    const labels = rawData.map(item => new Date(item[0]).toLocaleString()); // Timestamp in formato leggibile
    const values = rawData.map(item => item[1]);

    const ctx = document.getElementById("grafico").getContext("2d");
    new Chart(ctx, {
    type: "line",
    data: {
    labels: labels,
    datasets: [{
    label: "Dati Sensore",
    data: values,
    borderColor: "blue",
    fill: false
}]
}
});
}

    // ✅ Chiama la funzione per visualizzare il grafico
    renderChart();
});
