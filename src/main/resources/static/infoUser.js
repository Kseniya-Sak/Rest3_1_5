let infoUser= $('#tableUser')

let navbarInfo = $('#navBarInfo');

showUserInfo()
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

function showUserInfo() {
    fetch("/api/user").then((response) => {
        console.log(response.statusText + response.status)
        if (response.ok) {
            response.json().then((user) => {
                console.log(user)
                addUserForTable(user)
            });
        } else {
            console.error(response.statusText + response.status)
        }
    })
}

function addUserForTable(user) {
    infoUser.append(
        '<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.firstName + '</td>' +
        '<td>' + user.lastName + '</td>' +
        '<td>' + user.email + '</td>' +
        '<td>' + user.age + '</td>' +
        '<td>' + user.roles.map(roleUser => roleUser.name) + '</td>' +
        '</tr>'
    )
}

