document.addEventListener("DOMContentLoaded", async function () {
    try {
        // 🔥 Controlla se il server riconosce l'utente autenticato
        const response = await fetch("http://localhost:8080/auth/validate", {
            method: "GET",
            credentials: "include" // 🔥 Necessario per inviare il cookie al server
        });

        if (!response.ok) {
            throw new Error("Accesso negato. Effettua il login.");
        }

        console.log("Utente autenticato con successo");

        // ✅ Mostra il body solo dopo la verifica dell'accesso
        document.body.style.display = "block";
    } catch (error) {
        alert(error.message);
        window.location.href = "Login.html";
    }

    // ✅ Logout: Distrugge il cookie dal server
    document.getElementById("logoutBtn").addEventListener("click", async function (event) {
        event.preventDefault();

        await fetch("http://localhost:8080/auth/logout", {
            method: "POST",
            credentials: "include" // 🔥 Invia il cookie al server per invalidarlo
        });

        window.location.href = "Login.html";
    });

    // ✅ Funzione per recuperare dati da Grafana API
    async function fetchGrafanaData() {
        try {
            const response = await fetch("http://localhost:3000/api/datasources/proxy/1/query", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer YOUR_GRAFANA_API_TOKEN`
                }
            });

            if (!response.ok) {
                throw new Error(`Errore HTTP ${response.status}: ${await response.text()}`);
            }

            const data = await response.json();
            console.log("Dati ricevuti da Grafana:", data);

            if (!data.results || data.results.length === 0 || !data.results[0].series || !data.results[0].series[0].values) {
                throw new Error("Struttura dei dati non valida ricevuta da Grafana");
            }

            return data.results[0].series[0].values;
        } catch (error) {
            console.error("Errore nel recupero dati da Grafana:", error);
            return [];
        }
    }

    // ✅ Funzione per visualizzare il grafico
    async function renderChart() {
        const rawData = await fetchGrafanaData();
        if (rawData.length === 0) {
            console.warn("Nessun dato disponibile per il grafico.");
            return;
        }

        const labels = rawData.map(item => new Date(item[0]).toLocaleString());
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

    renderChart();
});
