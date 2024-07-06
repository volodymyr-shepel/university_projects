document.addEventListener("DOMContentLoaded", function() {
    fetch('https://' + ipAddress + '/api/v1/game-history/' + localStorage.getItem("playerName"))
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("#gameHistoryTableBody");
            tableBody.innerHTML = "";
            data.forEach(game => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${game.gameId}</td>
                    <td>${game.player1}</td>
                    <td>${game.player2}</td>
                    <td>${game.result}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching game history:', error));
});
