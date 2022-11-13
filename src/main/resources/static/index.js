let userInfo = $('#tableAllUsers');
let rolesInfo = $('#addRole');
let editRolesInfo = $('#editRole');
let allUser = [];
let allRoles = [];

getAllUsers();


function getAllUsers() {
    fetch("api/admin").then((response) => {
        console.log(response.statusText + response.status)
        if (response.ok) {
            response.json().then((users) => {
                users.forEach((user) => {
                    console.log(user)
                    addUserForTable(user)
                    allUser.push(user)
                });
            });
            console.log(allUser)
        } else {
            console.error(response.statusText + response.status)
        }
    });
}


function addUserForTable(user) {
    userInfo.append(
        '<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.firstName + '</td>' +
        '<td>' + user.lastName + '</td>' +
        '<td>' + user.email + '</td>' +
        '<td>' + user.age + '</td>' +
        '<td>' + user.roles.map(roleUser => roleUser.name) + '</td>' +
        '<td>' +
        '<button onclick="editUserById(' + user.id + '); getAllRoles(); " class="btn btn-info edit-btn" data-toggle="modal" data-target="#edit"' +
        '>Edit</button></td>' +
        '<td>' +
        '<button onclick="deleteUserById(' + user.id + ')" class="btn btn-danger" data-toggle="modal" data-target="#delete"' +
        '>Delete</button></td>' +
        '</tr>'
    )
}

let navbarInfo = $('#navBarInfo');
inNavBar()
function inNavBar(){
    fetch("/api/user").then((response) => {
        console.log(response.statusText + response.status)
        if (response.ok) {
            response.json().then((user) => {
                console.log(user)

                let a = document.createElement('a');
                a.setAttribute('class', "navbar-text text-white");
                a.appendChild(document.createTextNode(user.email+ " with roles : " +user.roles.map(roleUser => roleUser.name)))
                navbarInfo.append(a);


            });
        } else {
            console.error(response.statusText + response.status)
        }
    })
}


function addNewUser() {

    let roleList = () => {
        let array = []
        let options = document.querySelector('#addRole').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                let role = {id: options[i].value, name: options[i].text}
                array.push(role)
            }
        }
        return array;
    }

    let user = {
        firstName: document.getElementById("addFirstName").value,
        lastName: document.getElementById("addLastName").value,
        email: document.getElementById("addEmail").value,
        age: document.getElementById("addAge").value,
        password: document.getElementById("addPassword").value,
        roles: roleList()
    }


    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let request = new Request('api/admin', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });
    console.log(user);

    fetch(request).then((response) => {
        response.json().then((userAdd) => {
            allUser.push(userAdd)
            addUserForTable(userAdd)
            console.log(userAdd)
        })

        console.log(allUser)

        $('#usersTableActive').tab('show');
        // $('.nav-tabs a[href="#usersTableActive"]').tab('show');
        userClearModal()
    })
}

function userClearModal() {
    $('#addFirstName').empty().val('');
    $('#addLastName').empty().val('');
    $('#addEmail').empty().val('');
    $('#addAge').empty().val('');
    $('#addPassword').empty().val('');
    $('#addRole').val('');

}

function editUserById(id) {
    fetch("api/admin/" + id, {method: "GET", dataType: 'json',})
        .then((response) => {
            response.json().then((user) => {
                $('#editId').val(user.id);
                $('#editFirstName').val(user.firstName);
                $('#editLastName').val(user.lastName);
                $('#editEmail').val(user.email);
                $('#editAge').val(user.age);
                $('#editPassword').val(user.password);
                $('#editRole').val(user.roles.map(roleUser => roleUser.name));

                console.log(user)
            })
        })
}

function editButton() {
    let roleList = () => {
        let array = []
        let options = document.querySelector('#editRole').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                let role = {id: options[i].value, name: options[i].text}
                array.push(role)
            }
        }
        return array;
    }

    let editUser = {
        id: document.getElementById("editId").value,
        firstName: document.getElementById("editFirstName").value,
        lastName: document.getElementById("editLastName").value,
        email: document.getElementById("editEmail").value,
        password: document.getElementById("editPassword").value,
        age: document.getElementById("editAge").value,
        roles: roleList()
    }
    console.log(editUser);

    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let request = new Request("api/admin/", {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(editUser),
    });

    let userEditId = ($('#editId').val())
    console.log(userEditId)
    fetch(request).then((response) => {
        response.json().then((userEdit) => {
            console.log(userEdit);
            userInfo.empty();
            allUser = allUser.map(user => user.id !== userEdit.id ? user : userEdit)
            console.log(allUser)
            allUser.forEach((user) => {
                addUserForTable(user)
            })
        })
        $('#edit').modal('hide');
    });
}

function deleteUserById(id) {
    fetch("api/admin/" + id, {method: "GET", dataType: 'json',})
        .then((response) => {
            response.json().then((user) => {
                $('#deleteId').val(user.id)
                $('#deleteFirstName').val(user.firstName)
                $('#deleteLastName').val(user.lastName)
                $('#deleteEmail').val(user.email)
                $('#deletePassword').val(user.password)
                $('#deleteRole').val(user.roles.map(roleUser => roleUser.name))
                $('#deleteAge').val(user.age)
            })
        })
}

function deleteButton() {
    let userId = ($('#deleteId').val());
    console.log(userId)
    fetch("api/admin/" + userId, {method: "DELETE"})
        .then((response) => {
            userInfo.empty()
            allUser = allUser.filter(user => user.id !== Number(userId))
            console.log(allUser)

            allUser.forEach((user) => {
                addUserForTable(user)
            })
            $('#delete').modal('hide');
        })
}

$('#panelTabs a').on('click', function (event) {
    event.preventDefault()
    $(this).tab('show')
})


function getAllRoles() {
    fetch("api/roles").then((response) => {
        console.log(response.statusText + response.status)
        if (response.ok) {
            response.json().then((roles) => {
                roles.forEach((role) => {
                    console.log(role)
                    if (allRoles.length <= 3) {
                        // ограничения по массиву нужен, что бы роли не двоились при нажатии на кнопки edit и new User
                        addRolesForSelect(role)
                        addRolesForEdit(role)

                        allRoles.push(role)
                    }
                });
            });
            console.log(allRoles)
        } else {
            console.error(response.statusText + response.status)
        }
    });
}

function addRolesForSelect(role) {
    rolesInfo.append(
        '<option value="' + role.id + '" text="' + role.name + '">' + role.name + '</option>'
    )
}
function addRolesForEdit(role) {
    editRolesInfo.append(
        '<option value="' + role.id + '" text="' + role.name + '">' + role.name + '</option>'
    )
}
