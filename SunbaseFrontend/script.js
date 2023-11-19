const baseURL = "http://localhost:8088";


// Authenticate User
function authenticateUser() {
    const email = document.getElementById("login_id").value;
    const password = document.getElementById("password").value;
    const loginError = document.getElementById("loginError");

    // Clearing previous error messages
    loginError.textContent = "";

    // Making a POST request to authenticate user
    fetch(`${baseURL}/authenticate`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            login_id: email,
            password: password,
        }),
    })
    .then(response => {
        if (!response.ok) {
            // Displaying error message for unsuccessful authentication
            loginError.textContent = "Invalid credentials. Please try again.";
            throw new Error("Invalid credentials");
        }
        // console.log(response);
        return response.text();
    })
    .then(token => {
       
        alert("login successfull")
        
       // saving the bearer token
        saveTokenToStorage(token)
       
        
        // Redirect to the customer list page
        // window.location.href = "customer-list.html";
    })
    .catch(error => console.error("Authentication error:", error));
}




function showList(){

    window.location.href = "customer-list.html";
}

function saveTokenToStorage(token) {
    localStorage.setItem('accessToken', token);
}

