let userLogin;

function saveLogin() {
    let inputNodes = document.getElementById("login");
    userLogin = inputNodes.getElementsByTagName("input")[0].value;
    loadRecommendations();
    loadHistory();
}

function loadRecommendations() {
    var xhttp = new XMLHttpRequest();
    let scope = this;

    xhttp.onreadystatechange =
    function() {
        console.log("Load recommendations state: " + this.readyState);
        if (this.readyState == 4 && this.status == 200) {
            updateRecommendations(this.responseText);
        }
    };
    xhttp.open("GET", "/recommendation/" + userLogin, false);
    xhttp.send();
}

function loadHistory() {
    var xhttp = new XMLHttpRequest();
    let scope = this;

    xhttp.onreadystatechange =
    function() {
        console.log("Load history state: " + this.readyState);
        if (this.readyState == 4 && this.status == 200) {
            updateHistory(this.responseText);
        }
    };
    xhttp.open("GET", "/history/" + userLogin, false);
    xhttp.send();
}

function updateRecommendations(text) {
    var jsonData = JSON.parse(text);
    var recommendations = document.getElementById("recommendations");
    removeCurrentRecommendations(recommendations);

    for (let i = 0; i < jsonData.length && i < 3; i++) {
        var json = JSON.parse(jsonData[i]);
        var movie = document.createElement("movie");
        var img = document.createElement("img");
        img.setAttribute('src', json.imgUrl);
        img.setAttribute('alt', '404ImgNotFound');
        var title = document.createElement("span");
        title.innerHTML = json.title;
        movie.appendChild(img);
        movie.appendChild(title);
        recommendations.appendChild(movie);
    }
}

function updateHistory(text) {
    console.log("Update history")
    var jsonData = JSON.parse(text);
    var browsingHistory = document.getElementById("browsingHistory");
    removeCurrentHistory(browsingHistory);

debugger;
    for (let i = 0; i < jsonData.length && i < 3; i++) {
        var json = JSON.parse(jsonData[i]);
        var movie = document.createElement("movie");
        var img = document.createElement("img");
        img.setAttribute('src', json.imgUrl);
        img.setAttribute('alt', '404ImgNotFound');
        var title = document.createElement("span");
        title.innerHTML = json.title;
        movie.appendChild(img);
        movie.appendChild(title);
        browsingHistory.appendChild(movie);
    }
}

function removeCurrentRecommendations(recommendations) {
    while (recommendations.firstChild) {
        recommendations.removeChild(recommendations.firstChild);
    }
}

function removeCurrentHistory(browsingHistory) {
    while (browsingHistory.firstChild) {
        browsingHistory.removeChild(browsingHistory.firstChild);
    }
}
