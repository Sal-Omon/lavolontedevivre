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

    fetchData();
    setInterval(fetchData, 2000);
});

const datiTemperaturaAir = [];
const datiUmiditaAir = [];
const datiTemperaturaGround = [];
const datiUmiditaGround = [];
const datiLight = [];
const datiWind = [];

let graficoAir, graficoGround, graficoLight, graficoWind;

async function fetchData() {
    try {
        const response = await fetch('http://localhost:8080/dati');
        const dati = await response.json();

        document.getElementById("spanTempAir").textContent = dati.temperaturaAria.toFixed(2);
        document.getElementById("spanHumAir").textContent = dati.umiditaAria.toFixed(2);
        document.getElementById("spanTempGround").textContent = dati.temperaturaTerreno.toFixed(2);
        document.getElementById("spanHumGround").textContent = dati.umiditaTerreno.toFixed(2);
        document.getElementById("spanLight").textContent = dati.intensitaLuce;
        document.getElementById("spanWind").textContent = dati.velocitaVento.toFixed(2);

        datiTemperaturaAir.push(dati.temperaturaAria);
        datiUmiditaAir.push(dati.umiditaAria);
        datiTemperaturaGround.push(dati.temperaturaTerreno);
        datiUmiditaGround.push(dati.umiditaTerreno);
        datiLight.push(dati.intensitaLuce);
        datiWind.push(dati.velocitaVento);

        if (datiTemperaturaAir.length > 20) {
            datiTemperaturaAir.shift();
            datiUmiditaAir.shift();
            datiTemperaturaGround.shift();
            datiUmiditaGround.shift();
            datiLight.shift();
            datiWind.shift();
        }

        aggiornaGrafici();

    } catch (error) {
        console.error('Errore nel recupero dei dati:', error);
    }
}

function aggiornaGrafici() {
    aggiornaGraficoAir();
    aggiornaGraficoGround();
    aggiornaGraficoLight();
    aggiornaGraficoWind();
}

function aggiornaGraficoAir() {
    const ctx = document.getElementById("canvasAir").getContext("2d");

    if (graficoAir) {
        graficoAir.data.datasets[0].data = datiTemperaturaAir;
        graficoAir.data.datasets[1].data = datiUmiditaAir;
        graficoAir.data.labels = Array.from({ length: datiTemperaturaAir.length }, (_, i) => i + 1);
        graficoAir.update({ duration: 1, easing: 'linear' });
    } else {
        graficoAir = new Chart(ctx, {
            type: "line",
            data: {
                labels: Array.from({ length: datiTemperaturaAir.length }, (_, i) => i + 1),
                datasets: [
                    {
                        label: "Temperatura (°C)",
                        data: datiTemperaturaAir,
                        borderColor: "red",
                        backgroundColor: "rgba(255, 0, 0, 0.3)",
                        fill: true,
                    },
                    {
                        label: "Umidità (%)",
                        data: datiUmiditaAir,
                        borderColor: "blue",
                        backgroundColor: "rgba(0, 0, 255, 0.3)",
                        fill: true,
                    },
                ],
            },
            options: {
                scales: { x: { display: false }, y: { beginAtZero: true } },
                animation: { duration: 1 },
                plugins: { title: { display: true, text: 'Aria', font: { size: 16 } } }
            },
        });
    }
}

function aggiornaGraficoGround() {
    const ctx = document.getElementById("canvasGround").getContext("2d");

    if (graficoGround) {
        graficoGround.data.datasets[0].data = datiTemperaturaGround;
        graficoGround.data.datasets[1].data = datiUmiditaGround;
        graficoGround.data.labels = Array.from({ length: datiTemperaturaGround.length }, (_, i) => i + 1);
        graficoGround.update({ duration: 1, easing: 'linear' });
    } else {
        graficoGround = new Chart(ctx, {
            type: "line",
            data: {
                labels: Array.from({ length: datiTemperaturaGround.length }, (_, i) => i + 1),
                datasets: [
                    {
                        label: "Temperatura (°C)",
                        data: datiTemperaturaGround,
                        borderColor: "green",
                        backgroundColor: "rgba(0, 255, 0, 0.3)",
                        fill: true,
                    },
                    {
                        label: "Umidità (%)",
                        data: datiUmiditaGround,
                        borderColor: "purple",
                        backgroundColor: "rgba(128, 0, 128, 0.3)",
                        fill: true,
                    },
                ],
            },
            options: {
                scales: { x: { display: false }, y: { beginAtZero: true } },
                animation: { duration: 1 },
                plugins: { title: { display: true, text: 'Terreno', font: { size: 16 } } }
            },
        });
    }
}

function aggiornaGraficoLight() {
    const ctx = document.getElementById("canvasLight").getContext("2d");

    if (graficoLight) {
        graficoLight.data.datasets[0].data = datiLight;
        graficoLight.data.labels = Array.from({ length: datiLight.length }, (_, i) => i + 1);
        graficoLight.update({ duration: 1, easing: 'linear' });
    } else {
        graficoLight = new Chart(ctx, {
            type: "line", // Modificato a "line" per il grafico ad area
            data: {
                labels: Array.from({ length: datiLight.length }, (_, i) => i + 1),
                datasets: [{
                    label: "Intensità Luce",
                    data: datiLight,
                    backgroundColor: "rgba(255, 206, 86, 0.3)", // Aggiunto sfondo per l'area
                    borderColor: "rgba(255, 206, 86, 1)",
                    fill: true, // Aggiunto fill: true
                }],
            },
            options: {
                scales: { x: { display: false }, y: { beginAtZero: true } },
                animation: { duration: 1 },
                plugins: { title: { display: true, text: 'Luce', font: { size: 16 } } }
            },
        });
    }
}

function aggiornaGraficoWind() {
    const ctx = document.getElementById("canvasWind").getContext("2d");

    if (graficoWind) {
        graficoWind.data.datasets[0].data = datiWind;
        graficoWind.data.labels = Array.from({ length: datiWind.length }, (_, i) => i + 1);
        graficoWind.update({ duration: 1, easing: 'linear' });
    } else {
        graficoWind = new Chart(ctx, {
            type: "line", // Modificato a "line" per il grafico ad area
            data: {
                labels: Array.from({ length: datiWind.length }, (_, i) => i + 1),
                datasets: [{
                    label: "Velocità Vento",
                    data: datiWind,
                    backgroundColor: "rgba(75, 192, 192, 0.3)", // Aggiunto sfondo per l'area
                    borderColor: "rgba(75, 192, 192, 1)",
                    fill: true, // Aggiunto fill: true
                }],
            },
            options: {
                scales: { x: { display: false }, y: { beginAtZero: true } },
                animation: { duration: 1 },
                plugins: { title: { display: true, text: 'Vento', font: { size: 16 } } }
            },
        });
    }
}

