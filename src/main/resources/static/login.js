document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault(); // ❌ Evita il refresh della pagina

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    console.log("Tentativo di login con:", username, password);

    try {
        const response = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password }),
            credentials: "include" // 🔥 Necessario per ricevere i cookie
        });

        console.log("Risposta HTTP:", response.status); // Log dello stato HTTP

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error("Errore: " + response.status + " - " + errorText);
        }

        console.log("Login riuscito! Redirigo a MAT.html");
        window.location.href = "MAT.html"; // ✅ Reindirizza alla pagina protetta

    } catch (error) {
        console.error("Errore login:", error.message);
        alert(error.message); // ✅ Mostra un messaggio di errore
    }
});
