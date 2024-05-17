document
  .getElementById("login-form")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var requestBody = {
        username: username,
        password: password,
    };

    console.log(requestBody);

    fetch("/api/user/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(requestBody),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.token) {
          localStorage.setItem("token", data.token);
          window.location.href = "/";
          // Redirect the user to the home page or dashboard
        } else {
          // Show an error message
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  });
