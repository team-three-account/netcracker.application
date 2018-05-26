var stompClient = null;
var notification = null;

function showNotificationOutput() {

    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages/' + "25", function (message) {
            showNotification(JSON.parse(message.body));
        });
    });
}

function showNotification(message) {

    notification = "<div class=\"alert alert-info\" id = \"success-alert\" role=\"alert\"><a class=\"close\" data-dismiss=\"notification\">×</a>\n" +
        "  <strong>"+message.from+" </strong> "+message.text+" "+message.time+"</div>";
    $("#notification").prepend($(notification))
    document.getElementById("music").volume =1;
    document.getElementById("music").play();
    $("#success-alert").fadeTo(10000, 2000).slideUp(500, function(){
        $("#success-alert").slideUp(2000);
    });

}



