        document.getElementById("queueForm").addEventListener("submit", function (e) {
            e.preventDefault();
            const formData = new FormData(this);

            fetch("/calculate", {
                method: "POST",
                body: formData,
            })
            .then(response => response.text())
            .then(data => {
                // Split the data into lines and add each line as a separate paragraph
                const lines = data.split('\n');
                const resultsContainer = document.getElementById("results");
                resultsContainer.innerHTML = ''; // Clear previous results
                lines.forEach(line => {
                    const paragraph = document.createElement("p");
                    paragraph.textContent = line;
                    resultsContainer.appendChild(paragraph);
                });
            })
            .catch(error => console.error("Error:", error));
        });