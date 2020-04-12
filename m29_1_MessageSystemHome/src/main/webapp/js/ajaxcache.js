
function setCacheHitsAmdMiss(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status == 200) {
            document.getElementById('cacheHits').innerHTML = JSON.parse(this.responseText).cacheHits;
            document.getElementById('cacheMiss').innerHTML = JSON.parse(this.responseText).cacheMiss;
        }
    };
    xhttp.open("PUT", "/statistic", true);
    xhttp.send();
}

function refresh() {
    setCacheHitsAmdMiss();
}