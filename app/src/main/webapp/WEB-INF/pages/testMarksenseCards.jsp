<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Web Socket Test</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
    <link href="/static/css/style.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<script type="text/javascript">
    var socket;

    var PRE_RIGA_VUOTA = '<div id="row';

    var POST_RIGA_VUOTA = '" class="row">' +
            '<div class="cell cel_1 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_2 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div>' +
            '<div class="cell cel_3 "></div></div>';

    function getRigaVuota(index) {
        return PRE_RIGA_VUOTA + index + POST_RIGA_VUOTA;
    }

    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = new WebSocket("ws://10.1.0.83:9090/websocket");
        socket.onmessage = function (event) {


            var ta = document.getElementById('responseText');
            var lr = document.getElementById('lastRead');

            if (event.data && event.data.indexOf("PRINTED") < 0) {
                // dato letto
                var markSenseCard = jQuery.parseJSON(event.data);
                document.getElementById("schedinaContainer").style.display = "none";
                // $("#schedinaContainer").removeClass("schedinawette");
                // $("#schedinaContainer").removeClass("schedina2");


                lr.value = markSenseCard.rawString;

                if (document.getElementById('autoprint').checked == true) {

                    send(markSenseCard.rawString);

                }

                var numeroTotaleRighe;
                if (markSenseCard.typeStr == "013E30") {
                    // sportwette
                    $("#schedinaContainer").removeClass("schedina2");
                    $("#schedinaContainer").removeClass("schedina3");
                    $("#schedinaContainer").removeClass("schedina4");

                    $("#schedinaContainer").addClass("schedinawette");
                    numeroTotaleRighe = 58;
                } else if (markSenseCard.typeStr == "013630") {
                    // schedina2
                    $("#schedinaContainer").removeClass("schedinawette");
                    $("#schedinaContainer").removeClass("schedina3");
                    $("#schedinaContainer").removeClass("schedina4");
                    $("#schedinaContainer").addClass("schedina2");
                    numeroTotaleRighe = 58;
                } else if (markSenseCard.typeStr == "011D00") {
                    //schedina3
                    $("#schedinaContainer").removeClass("schedinawette");
                    $("#schedinaContainer").removeClass("schedina2");
                    $("#schedinaContainer").removeClass("schedina4");
                    $("#schedinaContainer").addClass("schedina3");
                    numeroTotaleRighe = 43;
                } else if (markSenseCard.typeStr == "010230") {
                    //schedina3
                    $("#schedinaContainer").removeClass("schedinawette");
                    $("#schedinaContainer").removeClass("schedina2");
                    $("#schedinaContainer").removeClass("schedina3");
                    $("#schedinaContainer").addClass("schedina4");
                    numeroTotaleRighe = 40;
                }


                if (markSenseCard.empty) {

                    ta.value = ta.value + '\n' + markSenseCard.rawString + "(schedina vuota)";


                } else {
                    ta.value = ta.value + '\n' + markSenseCard.rawString;
                    // caricare immagine background corretta
                    var grid = "";
                    grid += getRigaVuota(1);
                    for (var i = 2; i <= numeroTotaleRighe; i++) {
                        if (markSenseCard.listaRighe[i]) {
                            grid += generateRowSchedina(i, markSenseCard.listaRighe[i].markedCells);
                        } else {
                            grid += getRigaVuota(i);
                        }
                    }
                    document.getElementById("gridContainer").innerHTML = grid;
                    document.getElementById("schedinaContainer").style.display = "block";
                }

            } else {
                ta.value = ta.value + '\n' + event.data
            }


        };
        socket.onopen = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = "Web Socket opened!";
        };
        socket.onclose = function (event) {
            var ta = document.getElementById('responseText');
            ta.value = ta.value + "Web Socket closed";
        };
    } else {
        alert("Your browser does not support Web Socket.");
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

    function generateRowSchedina(index, arrayCells) {
        var contentent = "";

        contentent += '<div id="row' + index + '" class="row">';
        for (var j = 1; j < 15; j++) {
            var indiceRiga;
            if (j == 1) {
                indiceRiga = 1;
            } else if (j > 1 && j < 5) {
                indiceRiga = 2;

            } else if (j > 4 && j < 9) {
                indiceRiga = 3;
            } else if (j > 8 && j < 11) {
                indiceRiga = 2;
            } else {
                indiceRiga = 3
            }
            var selectedToken = "";

            if (arrayCells[j - 1] == true) {
                selectedToken = 'selected';
            }

            contentent += '<div class="cell cel_' + indiceRiga + ' ' + selectedToken + '"></div>';
        }
        contentent += '</div>';
        return contentent;
    }


</script>


<div style="display: block; width: 600px; float: left;">
    <form onsubmit="return false;">
        <h3>Last Data Read </h3>
        <textarea readonly="readonly" id="lastRead" style="width: 600px; height:50px;"
                  name="message"></textarea><br/><br/>
        <input type="button" value="PRINT" onclick="send(this.form.message.value)"/>
        Automatic print: <input type="checkbox" name="autoprint" id="autoprint"/>

        <h3>Log AREA</h3>
        <textarea readonly="readonly" id="responseText" style="width: 500px; height:300px;"></textarea>
    </form>
</div>

<div class="schedina" id="schedinaContainer" style="display: none;">
    <div class="grid" id="gridContainer">

    </div>
</div>

</body>
</html>