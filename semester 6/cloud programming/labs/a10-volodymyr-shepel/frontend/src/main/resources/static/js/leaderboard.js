document.addEventListener("DOMContentLoaded", function() {
    fetch(`https://${ipAddress}/api/v1/rating/ranking`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("#leaderboardTableBody");
            tableBody.innerHTML = "";
            data.forEach(rating => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${rating.playerId}</td>
                    <td>${rating.score}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching leaderboard:', error));
});
