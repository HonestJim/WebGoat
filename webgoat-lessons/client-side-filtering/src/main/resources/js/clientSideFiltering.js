var dataFetched = false;

function selectUser() {

    var newEmployeeID = $("#UserSelect").val();
    // Use safe DOM cloning or textContent as appropriate:
    document.getElementById("employeeRecord").textContent = document.getElementById(newEmployeeID).textContent;
}

function fetchUserData() {
    if (!dataFetched) {
        dataFetched = true;
        ajaxFunction(document.getElementById("userID").value);
    }
}

function ajaxFunction(userId) {
    $.get("clientSideFiltering/salaries?userId=" + userId, function (result, status) {
        var html = "<table border = '1' width = '90%' align = 'center'";
        html = html + '<tr>';
        html = html + '<td>UserID</td>';
        html = html + '<td>First Name</td>';
        html = html + '<td>Last Name</td>';
        html = html + '<td>SSN</td>';
        html = html + '<td>Salary</td>';

        for (var i = 0; i < result.length; i++) {
            html = html + '<tr id = "' + result[i].UserID + '"</tr>';
            html = html + '<td>' + escapeHtml(result[i].UserID) + '</td>';
            html = html + '<td>' + escapeHtml(result[i].FirstName) + '</td>';
            html = html + '<td>' + escapeHtml(result[i].LastName) + '</td>';
            html = html + '<td>' + escapeHtml(result[i].SSN) + '</td>';
            html = html + '<td>' + escapeHtml(result[i].Salary) + '</td>'; // escapeHtml should encode special HTML characters
            html = html + '</tr>';
        }
        html = html + '</tr></table>';

        var newdiv = document.createElement("div");
        newdiv.innerHTML = sanitizeHtml(html); // sanitizeHtml should clean HTML of XSS payloads
        var container = document.getElementById("hiddenEmployeeRecords");
        container.appendChild(newdiv);
    });
}