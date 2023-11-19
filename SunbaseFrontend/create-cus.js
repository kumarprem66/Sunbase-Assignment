const baseURL = "http://localhost:8088";

// Bearer Token 
let bearerToken = `Bearer ${getToken()}`

function createCustomer() {
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const street = document.getElementById("street").value;
    const address = document.getElementById("address").value;
    const city = document.getElementById("city").value;
    const state = document.getElementById("state").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;

    // if uuid will be null then save it otherwise update it
    
    const uuid = getParameterByName("uuid");
    if(uuid == null){
        // Make a POST request to create a new customer
        fetch(`${baseURL}/create-customer`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": bearerToken,
            },
            body: JSON.stringify({
                first_name: firstName,
                last_name: lastName,
                street: street,
                address: address,
                city: city,
                state: state,
                email: email,
                phone: phone,
               
            }),
        })
        .then(response => {
            if (response.status === 201) {
                // Customer created successfully, redirect to the customer list page
                alert("created successfully");
                window.location.href = "customer-list.html";
            } else {
                console.error("Error creating customer:", response.statusText);
            }
        })
        .catch(error => console.error("Error creating customer:", error));
    }else{
         // Make a POST request to create a new customer
        fetch("http://localhost:8088/update-customer?uuid=" + uuid, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": bearerToken,

            },
            body: JSON.stringify({
                first_name: firstName,
                last_name: lastName,
                street: street,
                address: address,
                city: city,
                state: state,
                email: email,
                phone: phone,
               
            }),
        })
        .then(response => {
            // Check the response status
            if (response.ok) {
                return response.text();
            } else {
                throw new Error(`Error: ${response.statusText}`);
            }
        })
        .then(result => {
            // Display the result
           
            alert("updated successfully")
        
            window.location.href = "customer-list.html"
        })
        .catch(error => console.error("Error deleting customer:", error));
        
    }

    
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

// Function to set input values from URL parameters
function fillFormFromURL() {
   
    document.getElementById("firstName").value = getParameterByName("firstName");
    document.getElementById("lastName").value = getParameterByName("lastName");
    document.getElementById("street").value = getParameterByName("street");
    document.getElementById("address").value = getParameterByName("address");
    document.getElementById("city").value = getParameterByName("city");
    document.getElementById("state").value = getParameterByName("state");
    document.getElementById("email").value = getParameterByName("email");
    document.getElementById("phone").value = getParameterByName("phone");
}

// Call the function on page load
window.onload = fillFormFromURL;


function getToken(){
 
    let to = getTokenFromStorage();
    return to["access_token"];
    
    
}

function getTokenFromStorage() {
    
    return JSON.parse(localStorage.getItem('accessToken'));
}
