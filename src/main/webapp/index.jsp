<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Test</title>
    </head>
    <body>
    <input type="hidden" value="<%=session.getId().substring(0,6)%>" id="chat_id" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <h2>WebSocket test</h2>
<button onclick="openSocket();">Open Socket</button>
<button onclick="sendMessage();">Send Message</button>
<button onclick="closeSocket();">Close Socket</button>
<br/>
<input type="text" id="messageInput" placeholder="Enter message">
<div id="messages"></div>
</body>
<script type="text/javascript">
    var websocket;
    var inputMessage = document.getElementById('messageInput');
    var chat_id = document.getElementById('chat_id').value;
    function openSocket(){
        // Ensure only one connection is open at a time
        if(websocket !== undefined && websocket.readyState !== WebSocket.CLOSED){
            writeResponse("WebSocket is already opened.");
            return;
        }
        // Create a new instance of the websocket

        websocket = new WebSocket("ws://localhost:8080/webSocketTest/websocket");
        websocket.onopen = function(event){
            if(event.data === undefined) return;
            writeResponse(event.data);
        };
        websocket.onmessage = function(event){
            //ws객체에 전달받은 메세지가 있으면 실행되는 함수
            var message = event.data.split("|");
            //보낼 때 id랑 같이 보냄. |로 구분
            var sender = message[0];
            //보낸 사람 id
            var content = message[1];
            //msg
            console.log(message);
            var messages = document.getElementById("messages");
            messages.innerHTML += "<p class='chat_content'>" + sender + " : " + content + "</p>";

            // $("#messages").html($("#messages").html()
            //     + "<p class='chat_content'>" + sender + " : " + content + "</p>");
        };
        websocket.onclose = function(event){
            writeResponse("Connection closed");
        };

    }
    function sendMessage(){
        var text = document.getElementById("messageInput").value;
        websocket.send(chat_id + "|" + text);
        //ws 객체에 session id랑 msg보냄
        inputMessage.value = "";

    }
    function closeSocket(){
        websocket.close();

    }
    function writeResponse(text){
        var messages = document.getElementById("messages");
        messages.innerHTML += "<br/>" + text;
    }

</script>

</html>
