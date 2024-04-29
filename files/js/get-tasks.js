// Fetch tasks from the API
fetch('/api/task')
    .then(response => response.json())
    .then(tasks => {
        // Iterate over the tasks
        tasks.forEach(task => {
            // Get the appropriate board item based on the task's label
            let boardItem = document.querySelector(`#${task.label.toLowerCase()} .message-body`);

            // Create a new div for the task
            let taskDiv = document.createElement('div');
            taskDiv.className = 'board-item';

            // Create a new div for the task content
            let taskContentDiv = document.createElement('div');
            taskContentDiv.className = 'board-item-content';
            taskContentDiv.textContent = task.name;

            // Append the task content to the task div
            taskDiv.appendChild(taskContentDiv);

            // Append the task div to the board item
            boardItem.appendChild(taskDiv);
        });
    })
    .catch(error => console.error('Error:', error));