let currentUser = "";

fetch("http://localhost:8080/api/current").then(
    res => {
        res.json().then(
            data => {
                if (data != null) {
                    currentUser = data;
                    console.log(currentUser);
                    getUserInfo(currentUser);
                }
            }
        )
    })

function getUserInfo(event) {
    let temp = "";
    temp += "<td>" + event.id + "</td>"
    temp += "<td>" + event.firstName + "</td>"
    temp += "<td>" + event.lastName + "</td>"
    temp += "<td>" + event.username + "</td>"
    temp += "<td>" + getRoles(event.roles) + "</td>"
    document.getElementById("tbodyAboutUser").innerHTML = temp;
}

function getRoles(list) {
    let userRoles = [];
    for (let role of list) {
        if (role === 2 || role.id === 2) {
            userRoles.push("USER");
        }
        if (role === 1 || role.id === 1) {
            userRoles.push("ADMIN");
        }
    }
    return userRoles.join(" , ");
}