

document.addEventListener('DOMContentLoaded', function () {
    const tableContainer = document.getElementById('tableContainer');
    const generateTableButton = document.getElementById('generateTable');

    generateTableButton.addEventListener('click', function () {
        const rowCountInput = document.getElementById('rowCount');
        const rowCount = parseInt(rowCountInput.value);

        if (rowCount >= 5 && rowCount <= 20) {
            // Valid input
            generateRandomMultiplicationTable(rowCount, tableContainer);
        } else {
            // Invalid input, use a default value
            tableContainer.innerHTML = "Invalid input. Using n=13.";
            rowCountInput.value = 13;
            generateRandomMultiplicationTable(13, tableContainer);
        }
    });

    function generateRandomMultiplicationTable(n, container) {
        const randomValues = [];

        // Generate n random values
        for (let i = 0; i <= n; i++) {
            randomValues.push(Math.floor(Math.random() * 99) + 1);
        }

        const table = document.createElement('table');

        for (let i = 0; i <= n; i++) {
            const row = document.createElement('tr');
            for (let j = 0; j <= n; j++) {
                // Top left should be empty
                if (i === 0 && j === 0) {
                    const th = document.createElement('th');
                    row.appendChild(th);
                } 
                // first row should be headers (values from which the table consists)
                else if (i === 0) {
                    const th = document.createElement('th');
                    th.textContent = randomValues[j];
                    row.appendChild(th);
                } 
                // first column also should be headers (values from which the table consists)
                else if (j === 0) {
                    const th = document.createElement('th');
                    th.textContent = randomValues[i];
                    row.appendChild(th);
                // if it is not a header, put the result of multiplication and set its class based on result (even or odd)
                } else {
                    const result = randomValues[i] * randomValues[j];
                    
                    const cellClass = result % 3 == 0 ? "reminder0" : result % 3 == 1 ? "reminder1" : "reminder2" ;
                    const td = document.createElement('td');
                    td.className = cellClass;
                    td.textContent = result;
                    row.appendChild(td);
                }
            }
            table.appendChild(row);
        }

        // Clear the container and append the new table
        container.innerHTML = '';
        container.appendChild(table);
    }
});
