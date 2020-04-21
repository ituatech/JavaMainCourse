var ws;

init = function () {
    // open the connection if one does not exist
    if (ws !== undefined
        && ws.readyState !== WebSocket.CLOSED) {
        return;
    }
    ws = new WebSocket("ws://localhost:8080/statistic");
    // on open
    ws.onopen = function (event) {
        console.clear();
        console.log("send message on open")
    }
    // on message
    ws.onerror= function (event) {
        console.log("websocket error")
    }


    ws.onmessage = function (event) {
        console.log("send message on message")
        var name = document.getElementById("userName");
        var age = document.getElementById("userAge");
        console.log(event.data);
        const obj = JSON.parse(event.data);
        name.value = obj.userName;
        age.value = obj.userAge;

    }
    // on close
    ws.onclose = function (event) {
        console.log("send message on close")
    }
};

function sendMessage() {
    var messageField = document.getElementById("message");
    ws.send(messageField.value);
    console.log("Send message to server: "+messageField.value);
    messageField.value = '';
}