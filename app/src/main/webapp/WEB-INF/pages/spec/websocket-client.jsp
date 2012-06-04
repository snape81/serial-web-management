<%--
  Created by IntelliJ IDEA.
  User: claudio
  Date: 04/06/12
  Time: 11.06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");%>
<html><head><title>Web Socket Test</title></head>
<body>
<script type="text/javascript">
var socket;
if (!window.WebSocket) {
  window.WebSocket = window.MozWebSocket;
}
if (window.WebSocket) {
  socket = new WebSocket("ws://localhost:8080/websocket");
  socket.onmessage = function(event) { var ta = document.getElementById('responseText'); ta.value = ta.value + '\n' + event.data };
  socket.onopen = function(event) { var ta = document.getElementById('responseText'); ta.value = "Web Socket opened!"; };
  socket.onclose = function(event) { var ta = document.getElementById('responseText'); ta.value = ta.value + "Web Socket closed"; };
} else {
  alert("Your browser does not support Web Socket.");
}

function send(message) {
  if (!window.WebSocket) { return; }
  if (socket.readyState == WebSocket.OPEN) {
    socket.send(message);
  } else {
    alert("The socket is not open.");
  }
}
</script>
<form onsubmit="return false;">
<input type="text" name="message" value="Hello, World!"/><input type="button" value="Send Web Socket Data" onclick="send(this.form.message.value)" />
<h3>Output</h3>
<textarea id="responseText" style="width: 500px; height:300px;"></textarea>
</form>
</body>
</html>