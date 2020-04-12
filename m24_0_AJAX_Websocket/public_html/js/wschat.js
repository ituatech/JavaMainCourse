var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8081/chat");
    // on open
    ws.onopen = function (event) {
        //empty
    }
    // on message
    ws.onmessage = function (event) {
        var $textarea = document.getElementById("messages");
        $textarea.value = $textarea.value + event.data + "\n";
    }
    // on close
    ws.onclose = function (event) {
        //empty
    }
};

function sendMessage() {
    var messageField = document.getElementById("message");
    var userNameField = document.getElementById("username");
    var message = userNameField.value + ":" + messageField.value;
    ws.send(message);
    messageField.value = '';
}