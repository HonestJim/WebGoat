function submit_secret_credentials() {
    var xhttp = new XMLHttpRequest();
    xhttp['open']('POST', '#attack/307/100', true);
	//sending the request is obfuscated, to descourage js reading
    // Get username and password from HTML input fields instead of hardcoded values
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    xhttp.send(JSON.stringify({username: username, password: password}));
}