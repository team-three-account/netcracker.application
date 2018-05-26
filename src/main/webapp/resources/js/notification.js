var stompClient = null;
var notification = null;
var authUserId;
var chats;

function showNotificationOutput(notification) {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        for (i in notification) {
            ;
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/messages/' + notification[i], function (message) {
                showNotification(JSON.parse(message.body));
            });


        }
    });
}

function showNotification(message) {

    notification = "<div style='background-color: #0a0a0a; border-radius: 15px' class=\"alert alert-info\" id = \"success-alert\" role=\"alert\">" +
        "<a class=\"close\" data-dismiss=\"notification\">×</a>\n" +
        "  <strong style='color: white'>New Message from " + message.from + "</br></br>" +
        " <div class=\"text-left \" id=\"showMessageOutputFromData\">\n" +
        "                                        <input type=\"hidden\" id=\"sender\" value=\"" + message.senderId + "\">\n" +
        "                                        " +
        "<a href='/account/eventList/eventChat/main/\"+message.chatId+\"-\"+message.eventId+\"\"'><p style='display: inline-flex;'><a href='/account/" + message.senderId + "'><img class=\"img-circle\" style=\"width: 40px;height: 40px;margin: 5px;\"\n" +
        "                                                src=\"" + message.senderPhoto + "\"></a>" + "  " + message.text + "\n" +
        "                                        </p></a>\n" +
        "<p>" + message.time + "</p>\n" +
        "                                    </div>";
    "</div>";
    $("#notification").prepend($(notification))
    document.getElementById("music").volume = 1;
    document.getElementById("music").play();
    $("#success-alert").fadeTo(10000, 2000).slideUp(500, function () {
        $("#success-alert").slideUp(2000);
    });

}

$(document).ready(function () {
    authUserId = $("#authUserId").val();
});

function loadChats() {

    $.ajax({
        type: 'GET',
        url: "/account/eventList/eventChat/main/notification",
        dataType: 'json',
        data: {
            authUserId: authUserId
        },
        success: function (data) {
            console.log(JSON.stringify(data));
            data.forEach(function (notification) {
                console.log(chats);
                showNotificationOutput(notification);
            });
        },
        error: function (data) {
            console.log(JSON.stringify(data));
        }
    })

}



