<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Example</title>
    <script type="text/javascript">
        var websocket;
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
                console.log(event);
                writeResponse(event.data);
            };

            websocket.onclose = function(event){
                writeResponse("Connection closed");
            };
        }

        function sendMessage(){
            var text = document.getElementById("messageInput").value;
            websocket.send(text);
        }

        function closeSocket(){
            websocket.close();
        }

        function writeResponse(text){
            var messages = document.getElementById("messages");
            messages.innerHTML += "<br/>" + text;
        }
    </script>
</head>
<body>
<h2>WebSocket Example</h2>
<button onclick="openSocket();">Open Socket</button>
<button onclick="sendMessage();">Send Message</button>
<button onclick="closeSocket();">Close Socket</button>
<br/>
<input type="text" id="messageInput" placeholder="Enter message">
<div id="messages"></div>
</body>
</html>
