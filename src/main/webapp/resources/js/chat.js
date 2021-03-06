var stompClient = null;
var connectState = true;
var messagesLimit = 15;
var messagesOffset = 0;
var authUserId;
var eventId;
var chatId;
var isChatWithCreator;

$(document).ready(function () {
    authUserId = $("#authUserId").val();
    eventId = $("#event").val();
    chatId = $("#chat").val();
    isChatWithCreator = $("#chatWithCreator").val();
    loadPrevMessages();
});

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('response').innerHTML = '';
}

function connect() {
    var chat = document.getElementById('chat').value;
    var socket = new SockJS('/chat');

    stompClient = Stomp.over(socket);
    var divElement = document.getElementById('sms');
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages/' + chat, function (message) {
            showMessageOutput(JSON.parse(message.body), true);
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
    var chat = document.getElementById('chat').value;
    var senderPhoto = document.getElementById('photo').value;

    stompClient.send("/app/chat/" + event + "/" + sender + "/" + chat, {},
        JSON.stringify({'from': from, 'text': text.trim(), 'senderId': sender, 'senderPhoto': senderPhoto}));
    document.getElementById('text').value = "";
    document.getElementById('sendMessage').disabled = true;
}

function showMessageOutput(message, isAppended) {
    var messageHtml;
    switch (authUserId != message.senderId) {
        case true: {
            var messageLeftHtml = " <div class=\"text-left \" id=\"showMessageOutputFromData\">\n" +
                "                                        <div class='col-md-6 smssender ' style='    padding: 5px; '><input type=\"hidden\" id=\"sender\" value=\"" + message.senderId + "\">\n" +
                "                                        <p style='display: inline-flex;'><a href='/account/"+message.senderId +"'><img class=\"img-circle\" style=\"width: 40px;height: 40px;margin: 5px;\"\n" +
                "                                                src=\"" + message.senderPhoto + "\"></a>" + "  " + message.text + "\n" +
                "                                        </p>\n" +
                "<p>"+message.time+"</p></div>\n"+
                "                                    </div>";
            messageHtml = messageLeftHtml;
            break;
        }
        case false: {
            var messageRightHtml = "<div class=\"text-right\" id=\"showMessageOutputFromData\">\n" +
                "                                      <div class=\"smssender col-md-6\" style='float: right !important;    padding: 5px;'>  <p style='display: inline-flex;'>" + message.text +" "+
                "                                           <a href='/account/"+message.senderId +"'><img class=\"img-circle\" style=\"width: 40px;height: 40px;margin: 5px;\"\n" +
                "                                                 src=\"" + message.senderPhoto + "\"/>\n" +
                "                                        </a></p>\n" +
                "                                        <p>"+ message.time+"</p></div>\n" +
                "                                    </div>";
            messageHtml = messageRightHtml;
            break;
        }
    }
    if (isAppended === true) {
        $("#sms").append($(messageHtml));
        document.getElementById('sms').scrollTop = 9999;
    } else {
        $("#sms").prepend($(messageHtml));
    }
}

function showMessageOutputFromData() {
    console.log('FILTER MESSAGE');
    var div = document.getElementById('showMessageOutput');
    var divElement = document.getElementById('sms');
    div.classList.add("text-right");
    div.scrollTop = 9999;
}

function checkParams() {
    var name = $('#text').val();

    if (name.trim() != 0) {
        $('#sendMessage').removeAttr('disabled');
        if (event.keyCode === 13) {
            $('#sendMessage').click();
        }
    } else {
        $('#sendMessage').attr('disabled', 'disabled');
    }

}

function loadPrevMessages() {
    var sms = document.getElementById('sms');
    if (sms.scrollTop === 0) {
        $.ajax({
            type: 'GET',
            url: "/account/eventList/eventChat/main/getChatMessages",
            dataType: 'json',
            data:{
                eventId: eventId,
                chatId: chatId,
                state: isChatWithCreator,
                limit: messagesLimit,
                offset: messagesOffset
            },
            success: function (data) {
                console.log(JSON.stringify(data));
                data.forEach(function (message) {
                    showMessageOutput(message, false); //TODO set last message id
                });
                if (messagesOffset === 0) {
                    sms.scrollTop = 9999;
                } else {
                    sms.scrollTop = 1;
                }
                messagesOffset += messagesLimit;
            },
            error: function (data) {
                console.log(JSON.stringify(data));
            }
        })
    }
}