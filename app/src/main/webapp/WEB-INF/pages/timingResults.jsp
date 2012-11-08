<%--
  Created by IntelliJ IDEA.
  User: claudio
  Date: 08/11/12
  Time: 12.05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head><title>Web Socket Test</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
    <script type="text/javascript">
        var socket;
        if (!window.WebSocket) {
            window.WebSocket = window.MozWebSocket;
        }
        if (window.WebSocket) {
            socket = new WebSocket("ws://10.0.0.221:9091/timingsocket");
            socket.onmessage = function (event) {
                if (event.data.indexOf("EMPTY") >= 0) {
                    alert("No card readed since last reset!");

                }

                if (event.data.indexOf("CLEAN") >= 0) {
                    alert("All stats are cleared!");

                }


                var timingResults = jQuery.parseJSON(event.data);
                var cardIdx;
                for (cardIdx in timingResults) {

                    alert(cardIdx);
                    var oneCardTiming = timingResults[cardIdx];
                    var idGenerale = "read" + cardIdx;
                    $("#timingTemplate").clone().attr("id", idGenerale).appendTo("#timingContainer");


                    $("div#" + idGenerale + "  span#code").text(oneCardTiming.mscCode);

                    for (var timingDetailIdx in oneCardTiming.listaTimeDetails) {
                        var actualDetail = oneCardTiming.listaTimeDetails[timingDetailIdx];
                        var nanosDelta = actualDetail.delta;
                        var deltaMillis = actualDetail.delta/1000000;

                        $("div#" + idGenerale + " table#timingtable").append(
                                $('<tr>').append($('<td>').text(actualDetail.operation),
                                        $('<td>').text(actualDetail.nanos),

                                        $('<td align="right">').text(deltaMillis)
                                )

                        )
                    }


                }
            }
        }

        function getTimingResults() {
            $("#timingContainer").empty();
            send('TIMING');
        }

        function resetStats() {
            $("#timingContainer").empty();
            send('RESET');
        }

        function send(message) {
            if (!window.WebSocket) {
                return;
            }
            if (socket.readyState == WebSocket.OPEN) {
                socket.send(message);
            } else {
                alert("The socket is not open.");
            }
        }

    </script>
</head>


<body>

<h1> Till Proof Of Concept Timing Results </h1>

<h3><input type="button" onclick="getTimingResults()" value="GET RESULTS"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"
                                                                                                          onclick="resetStats();"
                                                                                                          value="RESET ALL STATS"/>
</h3>

<div id="timingContainer"></div>

<div style="display: none">
    <div id="timingTemplate">
        <h3> SPORTWETTE </h3>
        <h4> code <span id="code" style="color: blue;"></span></h4>
        <table id="timingtable" border="2px">
            <thead>
            <tr>
                <th>Operation</th>
                <th>Nanos</th>
                <th>Delta(millis)</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>

    </div>
</div>


</body>
</html>