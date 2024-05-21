window.onload = function () {
  var token = localStorage.getItem("token");
  var signupButton = document.getElementById("signup-button");
  var loginButton = document.getElementById("login-button");
  var logoutButton = document.getElementById("logout-button");

  if (token) {
    // If a token is stored, hide the sign up and log in buttons and show the log out button
    signupButton.style.display = "none";
    loginButton.style.display = "none";
    logoutButton.style.display = "block";
  } else {
    // If no token is stored, show the sign up and log in buttons and hide the log out button
    signupButton.style.display = "block";
    loginButton.style.display = "block";
    logoutButton.style.display = "none";
  }

  logoutButton.addEventListener("click", function () {
    localStorage.removeItem("token");
    window.location.href = "/";
  });
};
