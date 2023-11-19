const baseURL = "http://localhost:8088";

// Bearer Token 
let bearerToken = `Bearer ${getToken()}`
// Fetch and display customer list
function getCustomerList() {
    // Making a GET request to get the customer list
    fetch(`${baseURL}/get-customer-list`, {
        method: "GET",
        headers: {
            "Authorization": bearerToken,
        },
    })
    .then(response => response.json())
    .then(customers => {
        const customersTable = document.getElementById("customers");

        // Clear existing table content
        customersTable.innerHTML = "";

        // Populate the table with customer details
        customers.forEach(customer => {
            const row = customersTable.insertRow();
            
            // Add UUID cell
            const uuidCell = row.insertCell();
            uuidCell.textContent = customer.uuid;

            // Add other cells
            const cells = ["first_name", "last_name", "street", "address", "city", "state", "email", "phone"];
            cells.forEach(cellName => {
                const cell = row.insertCell();
                cell.textContent = customer[cellName];
            });

            // Add Update button
            const updateCell = row.insertCell();
            const updateButton = document.createElement("button");
            updateButton.textContent = "Update";
            updateButton.onclick = function() {
            
                // passing customer details in the url
                const redirectURL = `create-customer.html?uuid=${customer.uuid}&firstName=${encodeURIComponent(customer.first_name)}&lastName=${encodeURIComponent(customer.last_name)}&street=${encodeURIComponent(customer.street)}&address=${encodeURIComponent(customer.address)}&city=${encodeURIComponent(customer.city)}&state=${encodeURIComponent(customer.state)}&email=${encodeURIComponent(customer.email)}&phone=${encodeURIComponent(customer.phone)}`;

                // Redirect to the Create Customer page
                window.location.href = redirectURL;
            };
           
            updateCell.appendChild(updateButton);

            // Adding Delete button
            const deleteCell = row.insertCell();
            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Delete";
            deleteButton.onclick = function() {
                //delete logic 
                fetch("http://localhost:8088/delete-customer?uuid=" + customer.uuid, {
                    method: "POST",
                    headers: {
                        "Authorization": bearerToken,
                    },
                })
                .then(response => {
                    // Checking the response status
                    if (response.ok) {
                        return response.text();
                    } else {
                        throw new Error(`Error: ${response.statusText}`);
                    }
                })
                .then(result => {
                    // Displaying the result
                   
                    alert(result)
                    getCustomerList();
                })
                .catch(error => console.error("Error deleting customer:", error));
                console.log("Delete clicked for UUID:", customer.uuid);
            };
            deleteCell.appendChild(deleteButton);
        });
    })
    .catch(error => console.error("Error fetching customer list:", error));
}


// Logout
function logout() {
    // Clearing the bearer token and redirect to the login page
    bearerToken = undefined;
    window.location.href = "login.html";
}
// Fetching and displaying customer list on page load
window.onload = function () {
    getCustomerList();
};


document.getElementById("createCustomerButton").addEventListener("click",()=>{
    window.location.href = "create-customer.html"
})


function getToken(){
 
    let to = getTokenFromStorage();
    return to["access_token"];
    
    
}

function getTokenFromStorage() {
    
    return JSON.parse(localStorage.getItem('accessToken'));
}