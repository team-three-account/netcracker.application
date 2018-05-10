var stompClient = null;
var connectState = true;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('response').innerHTML = '';
}

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    var divElement = document.getElementById('sms');
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessageOutput(JSON.parse(message.body));
        });
    });
    divElement.scrollTop = 9999;

}

function disconnect() {
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    var event = document.getElementById('event').value;
    var from = document.getElementById('from').value;
    var text = document.getElementById('text').value;
    var sender = document.getElementById('userId').value;
    var senderPhoto = document.getElementById('photo').value;
    var divElement = document.getElementById('sms');
    stompClient.send("/app/chat/" + event + "/" + sender, {},
        JSON.stringify({'from': from, 'text': text, 'senderId': sender, 'senderPhoto': senderPhoto}));
    divElement.scrollTop = 9999;


}

function showMessageOutput(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    var img = document.createElement('img');
    var userId = document.getElementById('userId').value;
    var senderPhoto = document.getElementById('photo').value;
    var div = document.createElement('div');
    var divElement = document.getElementById('sms');
    divElement.scrollTop = 9999;
    img.src =  message.senderPhoto;
    img.classList.add("img-circle");
    img.style.display = 'inline';
    img.style.width = '40px';
    img.style.height = '40px';
    img.classList.add("text-right");
    p.style.wordWrap = 'break-word';
    p.style.display = 'inline';
    if (message.senderId == userId) {
        div.classList.add("text-right")

        p.appendChild(document.createTextNode(message.from + ": "
            + message.text + " (" + message.time + ")"));

        div.appendChild(p);
        div.appendChild(img);
        response.appendChild(div);
    } else {
        div.appendChild(img);
        p.appendChild(document.createTextNode(message.from + ": "
            + message.text + " (" + message.time + ")"));

        div.appendChild(p);
        response.appendChild(div);
    }
    divElement.scrollTop = 9999;
}

function showMessageOutputFromData() {
    console.log('FILTER MESSAGE');
    var div = document.getElementById('showMessageOutput');
    var divElement = document.getElementById('sms');
    div.classList.add("text-right");
    div.scrollTop = 9999;

}
