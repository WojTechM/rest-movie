let userLogin;
const IMG_WIDTH = 220;
const IMG_HEIGHT = 160;

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
}

function updateRecommendations(text) {
    var jsonData = JSON.parse(text);
    var recommendations = document.getElementById("recommendations");
    removeCurrentRecommendations(recommendations);

    for (let i = 0; i < jsonData.length && i < 3; i++) {
        var movie = document.createElement("movie");
        var img = document.createElement("img");
        img.setAttribute('src', jsonData[i].imgUrl);
        img.setAttribute('alt', '404ImgNotFound');
        img.width = IMG_WIDTH;
        img.height = IMG_HEIGHT;
        var title = document.createElement("span");
        title.innerHTML = jsonData[i].title;
        movie.appendChild(img);
        movie.appendChild(title);
        recommendations.appendChild(movie);
        console.log(jsonData[i].id);
    }
}

function removeCurrentRecommendations(recommendations) {
    while (recommendations.firstChild) {
        recommendations.removeChild(recommendations.firstChild);
}
}

