G = {};

//app公共参数
App = {};

(function($){

    var stompClient = null;

    App['initSystem'] =  function () {
        $('#connect').click(function () {
            connect();
        });

        $('#disconnect').click(function () {
            disconnect();
        });

        $('#send').click(function () {
            sendMessage();
        });
    };

    function setConnected(connected) {
        document.getElementById('connect').disabled                 = connected;
        document.getElementById('disconnect').disabled              = !connected;
        document.getElementById('contentDiv').style.visibility = connected ? 'visible' : 'hidden';
        $('#sendMessage').text('');
        $('#receiveMessageSystemDate').text('');
        $('#receiveMessageBroadcast').text('');
        $('#receiveMessageToMe').text('');
    }

    //建立连接
    function connect() {
        var token=$.url(window.location.href).param('access_token');

        var user = $('#user').val();
        var socket  = new SockJS('http://localhost:7100/websocket-base-msg/websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({'Authorization': "Bearer " + token,'Auth-Token': token,user:user}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);

            //订阅广播的系统时间
            stompClient.subscribe('/topic/system-date', function (response) {
                showSystemDate(response.body);
            });

            //订阅广播的测试
            stompClient.subscribe('/topic/broadcast', function (response) {
                showBroadcast(response.body);
            });

            //订阅单独用户消息队列
            stompClient.subscribe('/user/queue/to-user', function (response) {
                showToMe(response.body);
            });
        });
    }

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendMessage() {
        var user = $('#user').val();
        var toUser = document.getElementById('toUser').value;
        var sendMessage = document.getElementById('sendMessage').value;

        var msg = JSON.stringify({'toUser': toUser,'fromUser':user, message:sendMessage});
        $('#sendMessageBody').text(msg);

        stompClient.send("/app/broadcast", {}, msg);
        stompClient.send("/app/to-user", {}, msg);
    }

    function showSystemDate(message) {
        $('#receiveMessageSystemDate').val(message)
    }

    function showBroadcast(message) {
        $('#receiveMessageBroadcast').val(message)
    }

    function showToMe(message) {
        $('#receiveMessageToMe').val(message)
    }

})(jQuery);

