let tableUsers = [];
let currentUser = "";
let deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
let editModal = new bootstrap.Modal(document.getElementById('editModal'));
let request = new Request("http://localhost:8080/api/users", {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json'
    }
});

fetch(request).then(
    res => {
        res.json().then(
            data => {
                if (data.length > 0) {
                    console.log(data)
                    data.forEach((user) => {
                        if (user.id != null) {
                            tableUsers.push(user)
                        }
                    })
                    console.log(tableUsers);
                    showUsers(tableUsers);
                }
            }
        )
    })

fetch("http://localhost:8080/api/current").then(
    res => {
        res.json().then(
            data => {
                if (data != null) {
                    currentUser = data;
                    console.log(currentUser);
                    showOneUser(currentUser);
                }
            }
        )
    })

function showUsers(event) {
    let temp = "";
    console.log(event);
    event.forEach((user) => {
        temp += "<tr>"
        temp += "<td>" + user.id + "</td>"
        temp += "<td>" + user.firstName + "</td>"
        temp += "<td>" + user.lastName + "</td>"
        temp += "<td>" + user.username + "</td>"
        temp += "<td>" + getRoles(user.roles) + "</td>"
        temp += "<td>" + `<a onclick='showEditModal(${user.id})' class="btn btn-primary" id="edit">Edit</a>` + "</td>"
        temp += "<td>" + `<a onclick='showDeleteModal(${user.id})' class="btn btn-danger" id="deleteButton" >Delete</a>` + "</td>"
        temp += "</tr>"
    })
    document.getElementById("tbodyAllUserTable").innerHTML = temp;
}

function showOneUser(event) {
    let temp = "";
    console.log(event);
    temp += "<td>" + event.id + "</td>"
    temp += "<td>" + event.firstName + "</td>"
    temp += "<td>" + event.lastName + "</td>"
    temp += "<td>" + event.username + "</td>"
    temp += "<td>" + getRoles(event.roles) + "</td>"
    document.getElementById("tbodyAboutUser").innerHTML = temp;
}

function getRoles(list) {
    let userRoles = [];
    for(let i in list) {
        if (list[i] === 2 || list[i].id === 2) {
            userRoles.push("USER");
        }
        if (list[i] === 1 || list[i].id === 1) {
            userRoles.push("ADMIN");
        }
    }
    return userRoles.join(" , ");
}

function rolesUser(event) {
    let rolesAdmin = {};
    let rolesUser = {};
    let roles = [];
    let allRoles = [];
    let sel = document.querySelector(event);
    for (let i = 0, n = sel.options.length; i < n; i++) {
        if (sel.options[i].selected) {
            roles.push(sel.options[i].value);
        }
    }
    if (roles.includes('1')) {
        rolesAdmin["id"] = 1;
        rolesAdmin["name"] = "ROLE_ADMIN";
        allRoles.push(rolesAdmin);
    }
    if (roles.includes('2')) {
        rolesUser["id"] = 2;
        rolesUser["name"] = "ROLE_USER";
        allRoles.push(rolesUser);
    }
    return allRoles;
}

document.getElementById('newUser').addEventListener('submit', addNewUser);

function addNewUser(e) {
    e.preventDefault();
    let newUserForm = new FormData(e.target);
    let user = {};
    newUserForm.forEach((value, key) => user[key] = value);
    user["roles"] = rolesUser("#roles");
    let request = new Request("http://localhost:8080/api/users", {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json',
        },
    });

    fetch(request).then(res => res.json()).then(newUser => {
            tableUsers.push(newUser);
            showUsers(tableUsers);
        }
    );
    e.target.reset();
    const triggerEl = document.querySelector('#v-pills-tabContent button[data-bs-target="#nav-home"]')
    bootstrap.Tab.getInstance(triggerEl).show() // Select tab by name
}

function submitFormDeleteUser(id) {
    let request = new Request('http://localhost:8080/api/' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    fetch(request);
}


function showDeleteModal(id) {
    let request = new Request("http://localhost:8080/api/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    fetch(request).then(res => res.json()).then(deleteUser => {
            console.log(deleteUser);
            document.getElementById('firstNameDel').setAttribute('value', deleteUser.firstName);
            document.getElementById('lastNameDel').setAttribute('value', deleteUser.lastName);
            document.getElementById('emailDel').setAttribute('value', deleteUser.email);
            document.getElementById('passwordDel').setAttribute('value', deleteUser.password);
            if (getRoles(deleteUser.roles).includes("USER") && getRoles(deleteUser.roles).includes("ADMIN")) {
                document.getElementById('rolesDel1').setAttribute('selected', 'true');
                document.getElementById('rolesDel2').setAttribute('selected', 'true');
            } else if (getRoles(deleteUser.roles).includes("USER")) {
                document.getElementById('rolesDel1').setAttribute('selected', 'true');
            } else if (getRoles(deleteUser.roles).includes("ADMIN")) {
                document.getElementById('rolesDel2').setAttribute('selected', 'true');
            }
            deleteModal.show();
        }
    );

    document.getElementById('deleteUser').addEventListener('submit', function (event) {
        event.preventDefault();
        submitFormDeleteUser(id);
        let deleteUser = tableUsers.find(item => item.id === id);
        tableUsers = tableUsers.filter(function (user) {
            return user !== deleteUser;
        })
        showUsers(tableUsers);
        deleteModal.hide();
    });
}

function showEditModal(id) {
    let request = new Request("http://localhost:8080/api/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    fetch(request).then(res => res.json()).then(editUser => {
            console.log(editUser);
            document.getElementById('idRed').setAttribute('value', editUser.id);
            document.getElementById('firstNameRed').setAttribute('value', editUser.firstName);
            document.getElementById('lastNameRed').setAttribute('value', editUser.lastName);
            document.getElementById('usernameRed').setAttribute('value', editUser.username);
            document.getElementById('passwordRed').setAttribute('value', editUser.password);
            if ((editUser.roles.id = 1) && (editUser.roles.id = 2)) {
                document.getElementById('rolesRed1').setAttribute('selected', 'true');
                document.getElementById('rolesRed2').setAttribute('selected', 'true');
            } else if ((editUser.roles.id = 1)) {
                document.getElementById('rolesRed1').setAttribute('selected', 'true');
            } else if ((editUser.roles.id = 2)) {
                document.getElementById('rolesRed2').setAttribute('selected', 'true');
            }
            editModal.show();
        }
    );

    document.getElementById('editUser').addEventListener('submit', submitFormEditUser);
}

function submitFormEditUser(event) {
    event.preventDefault();
    let redUserForm = new FormData(event.target);
    let user = {};
    redUserForm.forEach((value, key) => user[key] = value);
    user["roles"] = rolesUser("#rolesRed");
    let request = new Request('http://localhost:8080/api/users/', {
        method: 'PUT',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json',
        },
    });
    fetch(request).then(
        function (response) {
            console.log(response)
            let indexEditUser = tableUsers.findIndex(item => item.id === user["id"])
            tableUsers.splice(indexEditUser, 1, user)
            showUsers(tableUsers);
            editModal.hide();
        });
}