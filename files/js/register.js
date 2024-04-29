document.getElementById('register-form').addEventListener('submit', function(event) {
    event.preventDefault();

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    fetch('/api/user/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    }).then(function(response) {
        if (response.ok) {
            alert('Registration successful');
        } else {
            alert('Registration failed');
        }
    });
});