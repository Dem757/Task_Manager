// Get the modal
var modal = document.getElementById("create-task-modal");

// Get the button that opens the modal
var btn = document.getElementById("create-task-btn");

// When the user clicks the button, open the modal
btn.onclick = function () {
  modal.style.display = "block";
};

// Get the form and the submit button
var form = document.getElementById("task-form");
var submitButton = document.getElementById("submit-task");

window.onclick = function (event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
};

// Add an event listener to the submit button
submitButton.addEventListener("click", function (event) {
  // Prevent the default form submission
  event.preventDefault();

  // Create a new FormData object from the form
  var formData = new FormData(form);

  // Convert the FormData to JSON
  var jsonObject = {};
  for (const [key, value] of formData.entries()) {
    jsonObject[key] = value;
  }
  var json = JSON.stringify(jsonObject);

  // Use the fetch API to send the form data to the server
  var taskId = document.getElementById("name").getAttribute("data-task-id");
  var method = taskId ? "PUT" : "POST";
  var url = taskId ? `/api/task/${taskId}` : "/api/task";

  fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
    body: json,
  })
    .then((response) => {
      response.json();
      window.location.reload();
    })
    .then((data) => {
      console.log(data);
      // Close the modal after successful submission
      modal.style.display = "none";
      window.location.reload();
    })
    .catch((error) => {
      console.error("Error:", error);
    });
});
