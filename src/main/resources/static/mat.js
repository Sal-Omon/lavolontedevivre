document.addEventListener("DOMContentLoaded", async function () {
    await checkAuth(); // Verifica l'autenticazione all'avvio
    initLogout(); // Inizializza il logout
    fetchData(); // Recupera i dati iniziali
    setInterval(fetchData, 2000); // Recupera i dati ogni 2 secondi
});

async function checkAuth() {
    try {
        const response = await fetch("http://localhost:8080/auth/validate", {
            method: "GET",
            credentials: "include"
        });

        if (!response.ok) {
            throw new Error("Accesso negato. Effettua il login.");
        }
        console.log("Utente autenticato con successo");
    } catch (error) {
        alert(error.message);
        window.location.href = "Login.html";
    }
}

function initLogout() {
    document.getElementById("logoutBtn").addEventListener("click", async function (event) {
        event.preventDefault();
        try {
            await fetch("http://localhost:8080/auth/logout", {
                method: "POST",
                credentials: "include"
            });
            window.location.href = "Login.html";
        } catch (error) {
            console.error("Errore durante il logout:", error);
            alert("Errore durante il logout. Riprova.");
        }
    });
}

const datiTemperaturaAir = [];
const datiUmiditaAir = [];
const datiTemperaturaGround = [];
const datiUmiditaGround = [];
const datiLight = [];
const datiWind = [];

let graficoAir, graficoGround, graficoLight, graficoWind;

async function fetchData() {
    try {
        const response = await fetch('http://localhost:8080/data');
        if (!response.ok) {
            throw new Error("Errore nel recupero dei dati.");
        }
        const dati = await response.json();
        updateSpans(dati);
        updateDataArrays(dati);
        aggiornaGrafici();
    } catch (error) {
        console.error('Errore nel recupero dei dati:', error);
        alert("Errore nel recupero dei dati. Riprova.");
    }
}

function updateSpans(dati) {
    document.getElementById("spanTempAir").textContent = dati.temperaturaAria.toFixed(2);
    document.getElementById("spanHumAir").textContent = dati.umiditaAria.toFixed(2);
    document.getElementById("spanTempGround").textContent = dati.temperaturaTerreno.toFixed(2);
    document.getElementById("spanHumGround").textContent = dati.umiditaTerreno.toFixed(2);
    document.getElementById("spanLight").textContent = dati.intensitaLuce;
    document.getElementById("spanWind").textContent = dati.velocitaVento.toFixed(2);
}

function updateDataArrays(dati) {
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
}

function aggiornaGrafici() {
    aggiornaGraficoAir();
    aggiornaGraficoGround();
    aggiornaGraficoLight();
    aggiornaGraficoWind();
}

function aggiornaGrafico(canvasId, labels, datasets, title) {
    const ctx = document.getElementById(canvasId).getContext("2d");
    if (window[canvasId.replace('canvas', 'grafico')]) {
        window[canvasId.replace('canvas', 'grafico')].data.datasets = datasets;
        window[canvasId.replace('canvas', 'grafico')].data.labels = labels;
        window[canvasId.replace('canvas', 'grafico')].update({ duration: 1, easing: 'linear' });
    } else {
        window[canvasId.replace('canvas', 'grafico')] = new Chart(ctx, {
            type: "line",
            data: { labels, datasets },
            options: {
                scales: { x: { display: false }, y: { beginAtZero: true } },
                animation: { duration: 1 },
                plugins: { title: { display: true, text: title, font: { size: 16 } } }
            },
        });
    }
}

function aggiornaGraficoAir() {
    aggiornaGrafico("canvasAir", Array.from({ length: datiTemperaturaAir.length }, (_, i) => i + 1), [
        { label: "Temperatura (°C)", data: datiTemperaturaAir, borderColor: "red", backgroundColor: "rgba(255, 0, 0, 0.3)", fill: true },
        { label: "Umidità (%)", data: datiUmiditaAir, borderColor: "blue", backgroundColor: "rgba(0, 0, 255, 0.3)", fill: true }
    ], "Aria");
}

function aggiornaGraficoGround() {
    aggiornaGrafico("canvasGround", Array.from({ length: datiTemperaturaGround.length }, (_, i) => i + 1), [
        { label: "Temperatura (°C)", data: datiTemperaturaGround, borderColor: "green", backgroundColor: "rgba(0, 255, 0, 0.3)", fill: true },
        { label: "Umidità (%)", data: datiUmiditaGround, borderColor: "purple", backgroundColor: "rgba(128, 0, 128, 0.3)", fill: true }
    ], "Terreno");
}

function aggiornaGraficoLight() {
    aggiornaGrafico("canvasLight", Array.from({ length: datiLight.length }, (_, i) => i + 1), [
        { label: "Intensità Luce", data: datiLight, backgroundColor: "rgba(255, 206, 86, 0.3)", borderColor: "rgba(255, 206, 86, 1)", fill: true }
    ], "Luce");
}

function aggiornaGraficoWind() {
    aggiornaGrafico("canvasWind", Array.from({ length: datiWind.length }, (_, i) => i + 1), [
        { label: "Velocità Vento", data: datiWind, backgroundColor: "rgba(75, 192, 192, 0.3)", borderColor: "rgba(75, 192, 192, 1)", fill: true }
    ], "Vento");
}