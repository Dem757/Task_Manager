document
  .getElementById("register-form")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    fetch("/api/user/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    }).then(function (response) {
      var notification = document.querySelector(".notification");
      if (response.ok) {
        notification.textContent = "Registration successful";
        notification.classList.add("success");
        notification.hidden = false;
        setTimeout(function () {
          window.location.href = "/login.html";
        }, 2000);
      } else {
        notification.textContent = "Registration failed";
        notification.classList.add("error");
        notification.hidden = false;
      }
    });
  });
