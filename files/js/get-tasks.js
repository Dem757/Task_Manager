// Fetch tasks from the API
fetch("/api/task")
  .then((response) => response.json())
  .then((tasks) => {
    // Iterate over the tasks
    tasks.forEach((task) => {
      // Get the appropriate board item based on the task's label
      let boardItem = document.querySelector(
        `#${task.label.toLowerCase()} .message-body`,
      );

      // Create a new div for the task
      let taskDiv = document.createElement("div");
      taskDiv.className = "board-item";

      // Create a new div for the task content
      let taskContentDiv = document.createElement("button");
      taskContentDiv.className = "board-item-content task";
      taskContentDiv.textContent = task.name;
      taskContentDiv.setAttribute("data-task-id", task.id);

      // Append the task content to the task div
      taskDiv.appendChild(taskContentDiv);

      // Append the task div to the board item
      boardItem.appendChild(taskDiv);

      // Add an event listener to the task
      taskContentDiv.addEventListener("click", function () {
        fetch(`/api/task/${task.id}`)
          .then((response) => response.json())
          .then((task) => {
            // Fill the form with the task details
            document.getElementById("name").value = task.name;
            document
              .getElementById("name")
              .setAttribute("data-task-id", task.id);
            document.getElementById("description").value = task.description;
            document.getElementById("label").value = task.label;
            document.getElementById("owner").value = task.owner;
            document.getElementById("status").value = task.status;

            // Show the form
            modal.style.display = "block";
          });
      });
    });
  })
  .catch((error) => console.error("Error:", error));
