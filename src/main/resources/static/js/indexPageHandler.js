let userLogin;

function saveLogin() {
    let inputNodes = document.getElementById("login");
    userLogin = inputNodes.getElementsByTagName("input")[0].value;
    loadRecommendations();
}

function loadRecommendations() {
    var xhttp = new XMLHttpRequest();
    let scope = this;

    xhttp.onreadystatechange =
    function() {
        if (this.readyState == 4 && this.status == 200) {
            updateRecommendations(this.responseText);
        }
    };
    xhttp.open("GET", "/movie", true);
    xhttp.send();
    this.prepareGameBoard();
}

function updateRecommendations(text) {
    var jsonData = JSON.parse(text);
    for (var counter in jsonData.counters) {
        console.log(jsonData.title);
    }
}
