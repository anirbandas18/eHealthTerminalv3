var xmlhttp;
var mobile;
var cmd_record;
var xmlhttpab;
var canvasECG, canvasResp;
var xmlhttpi;
var xmlhttpd;
var xmlhttpe;
var coordinatePointsECG;
var sizeECG;
var graphicsECG;
var tECG = 0;
var onECG = true;
var w = 480;
var h = 210;
var coordinatePointsResp;
var sizeResp;
var tResp = 0;
var onResp = true;
var graphicsResp;
var tMonitor;
var onMonitor = true;
var onAbnormal = true;
var tAbnormal;
var inte = 1000;
var tBP;
var onBP = true;
            function loadXMLDocResp(url,cfunc) {
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=cfunc;
                xmlhttp.open("POST",url,false);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send();
            }
            function loadXMLDocEnd(url,params,cfunc) {
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttpe=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttpe=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttpe.onreadystatechange=cfunc;
                xmlhttpe.open("POST",url,false);
                xmlhttpe.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttpe.send(params);
            }
            function loadXMLDocInit(url,params,cfunc) {
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttpi=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttpi=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttpi.onreadystatechange=cfunc;
                xmlhttpi.open("POST",url,false);
                xmlhttpi.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttpi.send(params);
            }
            function loadXMLDocDP(url,cfunc) {
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttpdp=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttpdp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttpdp.onreadystatechange=cfunc;
                xmlhttpdp.open("POST",url,false);
                xmlhttpdp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttpdp.send();
            }
            function workRespRate() {
                if(onResp === true) {
                    tResp = setInterval(drawRespRateGraph,inte);
                } else {
                    clearInterval(tResp);
                    graphicsResp.clearRect(0,0,w,h);
                    drawRespRate();
                    onResp = true;
                }
            }
            function drawRespRate() {
                graphicsResp.fillStyle = "#FFFFFF";
                graphicsResp.fillRect(0,0,w,h);
                graphicsResp.strokeStyle = "#F5D0A9";
                var v = 0;
                graphicsResp.lineWidth = 1;
                for(var i = 0 ; i <= h ; i = i + 5) {
                    if(v % 5 === 0) {
                        graphicsResp.lineWidth = 3;
                    } else {
                        graphicsResp.lineWidth = 1;
                    }
                    v++;
                    graphicsResp.beginPath();
                    graphicsResp.moveTo(0,i);
                    graphicsResp.lineTo(w,i);
                    graphicsResp.closePath();
                    graphicsResp.stroke();
                }
                v = 0;
                for(var i = 0 ; i <= w ; i = i + 5) {
                    if(v % 5 === 0) {
                        graphicsResp.lineWidth = 3;
                    } else {
                        graphicsResp.lineWidth = 1;
                    }
                    v++;
                    graphicsResp.beginPath();
                    graphicsResp.moveTo(i,0);
                    graphicsResp.lineTo(i,h);
                    graphicsResp.closePath();
                    graphicsResp.stroke();
                }
                graphicsResp.font = "12px Serif";
                graphicsResp.strokeStyle = "#000000";
                graphicsResp.strokeText("0 sec", 5, 190);
                graphicsResp.strokeText("10 sec", 345, 190);
                graphicsResp.strokeText("X-Axis : 1Box = 0.66 sec", 230, 15);
                graphicsResp.strokeText("Respiratory Rate", 10, 15);
                graphicsResp.strokeStyle = "#000000";
                graphicsResp.lineWidth = 1;
                
            }
            function drawRespRateGraph() {
                 onResp = false;
                 graphicsResp.clearRect(0,0,w,h);
                    drawRespRate();
                loadXMLDocResp("RespRatePanel", function() {
                    if (xmlhttp.readyState===4 && xmlhttp.status===200)
                    {
                       formCoordinatesResp(xmlhttp.responseText);
                       for(var i = 0 ; i < sizeResp - 1 ; i++ ) {
                           var x1 = coordinatePointsResp[i][0];
                           var y1 = coordinatePointsResp[i][1];
                           var x2 = coordinatePointsResp[i + 1][0];
                           var y2 = coordinatePointsResp[i + 1][1];
                           graphicsResp.beginPath();
                           graphicsResp.moveTo(x1,y1);
                           graphicsResp.lineTo(x2,y2);
                           graphicsResp.closePath();
                           graphicsResp.stroke();
                       }
                    }
                });
            }
            function getSizeResp(text) {
                var list = text.split(" ");
                sizeResp = parseInt(list[0]);
                coordinatePointsResp = new Array(sizeResp);
                for(var c = 0 ; c < sizeResp ; c++) {
                    coordinatePointsResp[c] = new Array(2);
                }
                return list[1];
            }
            function formCoordinatesResp(line) {
                line = getSizeResp(line);
                var c = 0;
                var list = line.split("_");
                for(var i = 0 ; i < list.length ; i++ ) {
                    var points = list[i].split(":");
                    for(var j = 0 ; j < points.length ; j++ ) {
                        var coordinates = points[j].split(",");
                        var x = coordinates[0].substring(1,coordinates[0].length);
                        var y = coordinates[1].substring(0,coordinates[1].length - 1);
                        coordinatePointsResp[c][0] = parseInt(x);
                        coordinatePointsResp[c][1] = parseInt(y);
                        c++;
                    }
                }
            }
            function loadXMLDocECG(url,cfunc) {
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=cfunc;
                xmlhttp.open("POST",url,false);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send();
            }
            function loadXMLDocAbn(url,cfunc) {
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttpab=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttpab=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttpab.onreadystatechange=cfunc;
                xmlhttpab.open("POST",url,false);
                xmlhttpab.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttpab.send();
            }
            function workECG() {
                if(onECG === true) {
                    tECG = setInterval(drawECGGraph,inte);
                } else {
                    clearInterval(tECG);
                    graphicsECG.clearRect(0,0,w,h);
                    drawECG();
                    onECG = true;
                }
            }
            function drawECG() {
                graphicsECG.fillStyle = "#FFFFFF";
                graphicsECG.fillRect(0,0,w,h);
                graphicsECG.strokeStyle = "#F5D0A9";
                var v = 0;
                graphicsECG.lineWidth = 1;
                for(var i = 0 ; i <= h ; i = i + 5) {
                    if(v % 5 === 0) {
                        graphicsECG.lineWidth = 3;
                    } else {
                        graphicsECG.lineWidth = 1;
                    }
                    v++;
                    graphicsECG.beginPath();
                    graphicsECG.moveTo(0,i);
                    graphicsECG.lineTo(w,i);
                    graphicsECG.closePath();
                    graphicsECG.stroke();
                }
                v = 0;
                for(var i = 0 ; i <= w ; i = i + 5) {
                    if(v % 5 === 0) {
                        graphicsECG.lineWidth = 3;
                    } else {
                        graphicsECG.lineWidth = 1;
                    }
                    v++;
                    graphicsECG.beginPath();
                    graphicsECG.moveTo(i,0);
                    graphicsECG.lineTo(i,h);
                    graphicsECG.closePath();
                    graphicsECG.stroke();
                }
                
                graphicsECG.font = "12px Serif";
                graphicsECG.strokeStyle = "#000000";
                graphicsECG.strokeText("0 sec", 5, 190);
                graphicsECG.strokeText("3 sec", 345, 190);
                graphicsECG.strokeText("X-Axis : 1Box = 0.2 sec", 230, 15);
                graphicsECG.strokeText("Electro-Cardiogram Monitor", 10, 15);
            }
            function drawECGGraph() {
                onECG = false;
                graphicsECG.clearRect(0,0,w,h);
                drawECG();
                loadXMLDocECG("ECGPanel", function() {
                    if (xmlhttp.readyState===4 && xmlhttp.status===200)
                    {
                        var x = xmlhttp.responseText;
                       formCoordinates(x);
                       graphicsECG.strokeStyle = "#000000";
                       graphicsECG.lineWidth = 1;
                       for(var i = 0 ; i < sizeECG - 1 ; i++ ) {
                           var x1 = coordinatePointsECG[i][0];
                           var y1 = coordinatePointsECG[i][1];
                           var x2 = coordinatePointsECG[i + 1][0];
                           var y2 = coordinatePointsECG[i + 1][1];
                           graphicsECG.beginPath();
                           graphicsECG.moveTo(x1,y1);
                           graphicsECG.lineTo(x2,y2);
                           graphicsECG.closePath();
                           graphicsECG.stroke();
                       }
                    }
                });
            }
            function getSize(text) {
                var list = text.split(" ");
                sizeECG = parseInt(list[0]);
                coordinatePointsECG = new Array(sizeECG);
                for(var c = 0 ; c < sizeECG ; c++) {
                    coordinatePointsECG[c] = new Array(2);
                }
                return list[1];
            }
            function formCoordinates(line) {
                line = getSize(line);
                var c = 0;
                var list = line.split("_");
                for(var i = 0 ; i < list.length ; i++ ) {
                    var points = list[i].split(":");
                    for(var j = 0 ; j < points.length ; j++ ) {
                        var coordinates = points[j].split(",");
                        var x = coordinates[0].substring(1,coordinates[0].length);
                        var y = coordinates[1].substring(0,coordinates[1].length - 1);
                        coordinatePointsECG[c][0] = parseInt(x);
                        coordinatePointsECG[c][1] = parseInt(y);
                        c++;
                    }
                }
            }
            function loadXMLDoc(url,params,cfunc)
            {
                if (window.XMLHttpRequest)
                {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else
                {// code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=cfunc;
                xmlhttp.open("POST",url,false);
                xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                xmlhttp.send(params);
            }
            function saveAsXML() {
                mobile = prompt("Enter an associate mobile number for the Patient (without +91)");
                if(validate1()) {
                    var url = "";
                    url = url + "id=" + document.getElementById("patient_id").value + "&";
                    url = url + "name=" + document.getElementById("name").value + "&";
                    url = url + "age=" + document.getElementById("age").value + "&";
                    url = url + "mobile=" + mobile + "&";
                    url = url + "sys=" + document.getElementById("sys").innerHTML.trim() + "&";
                    url = url + "dia=" + document.getElementById("dia").innerHTML.trim() + "&";
                    url = url + "mem=" + document.getElementById("mem").innerHTML.trim() + "&";
                    url = url + "breathing_rate=" + document.getElementById("breathing_rate").innerHTML.trim() + "&";
                    url = url + "position=" + document.getElementById("position").innerHTML.trim() + "&";
                    url = url + "ecg=" + document.getElementById("ecg").innerHTML.trim() + "&";
                    url = url + "o2=" + document.getElementById("o2").innerHTML.trim() + "&";
                    url = url + "bpm=" + document.getElementById("bpm").innerHTML.trim() + "&";
                    url = url + "temperature=" + document.getElementById("temperature").innerHTML.trim() + "&";
                    url = url + "cond=" + document.getElementById("cond").innerHTML.trim() + "&";
                    url = url + "res=" + document.getElementById("res").innerHTML.trim() + "&";
                    url = url + "respRateGraph=" + canvasResp.toDataURL("image/jpeg", 1.0) + "&";
                    url = url + "ecgGraph=" + canvasECG.toDataURL("image/jpeg", 1.0);
                    loadXMLDoc("SaveAsXML", url, function()
                    {
                        if (xmlhttp.readyState===4 && xmlhttp.status===200)
                        {
                            document.getElementById("console").innerHTML = xmlhttp.responseText;
                        }
                    });
                } 
            }
            function resetDisplayFields() {
                document.getElementById("sys").innerHTML = "0";
                    document.getElementById("dia").innerHTML = "0";
                    document.getElementById("mem").innerHTML = "0";
                    document.getElementById("breathing_rate").innerHTML = "0";
                    document.getElementById("position").innerHTML = "IDLE";
                    document.getElementById("ecg").innerHTML = "ECG";
                    document.getElementById("o2").innerHTML = "0";
                    document.getElementById("bpm").innerHTML = "0";
                    document.getElementById("temperature").innerHTML = "0.0";
                    document.getElementById("cond").innerHTML = "0";
                    document.getElementById("res").innerHTML = "0";
            }
            function resetFields() {
                var r = confirm("Are you sure you want to reset all the fields?");
                if(r === true) {
                    document.getElementById("time_int").value = "1";
                    document.getElementById("xively_int").value = "5";
                    document.getElementById("field").value = "SHOW ALL";
                    document.getElementById("patient_id").value = "";
                    document.getElementById("name").value = "";
                    document.getElementById("age").value = "";
                    resetDisplayFields();
                }
            }
            function isAlphabetKey(evt){
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122) || charCode === 32)
                    return true;
                return false;
            }    
            function isNumericKey(evt){
                var charCode = (evt.which) ? evt.which : event.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57))
                    return false;
                return true;
            }
            
            function monitorWiFi(btn) {
                var v = btn.value;
                var usb = document.getElementById("usb");
                if(v === "Start Monitoring WiFi") {
                    usb.disabled = true;
                    monitoringW(v);
                    workPatientData();
                    workAbnormality();
                    workECG();
                    workRespRate();
                    btn.value = "Stop Monitoring WiFi";
                } else {
                    usb.disabled = false;
                    monitoringW(v);
                    workPatientData();
                    workAbnormality();
                    workECG();
                    workRespRate();
                    resetDisplayFields();
                    btn.value = "Start Monitoring WiFi";
                }
            }
            function startXivelyFeed(btn) {
                var v = btn.value;
                var w = document.getElementById("xively_int").value;
                if(v === "Start Xively Post") {
                    monitoringX(v,w);
                    btn.value = "Stop Xively Post";
                } else {
                    monitoringX(v,w);
                    btn.value = "Start Xively Post";
                }
            }
            function recordBP() {
            }
            function monitorUSB(btn) {
                var v = btn.value;
                var wifi = document.getElementById("wifi");
                actionPerformed();
                if(v === "Start Monitoring USB") {
                    wifi.disabled = true;
                    monitoring(v);
                    workPatientData();
                    workAbnormality();
                    workECG();
                    workRespRate();
                    btn.value = "Stop Monitoring USB";
                } else {
                    wifi.disabled = false;
                    monitoring(v);
                    workPatientData();
                    workAbnormality();
                    workECG();
                    workRespRate();
                    resetDisplayFields();
                    btn.value = "Start Monitoring USB";
                }
            }
            function checkID() {
                var url = "";
                url = url + "patient_id=" + document.getElementById("patient_id").value;
                loadXMLDoc("SearchDataByID", url, function()
                {
                    if (xmlhttp.readyState===4 && xmlhttp.status===200)
                    {
                        var text = xmlhttp.responseText;
                        var n = text.localeCompare("true");
                        if(n === 1) {
                            var x = checkDates();
                            if(x === true) {
                                document.getElementById("console").innerHTML = "Console";
                                document.search_form.submit();
                            } 
                        } else {
                            document.getElementById("console").innerHTML = text;
                        }
                    }
                });
            }
            function checkName() {
                var url = "";
                url = url + "patient_name=" + document.getElementById("name").value;
                loadXMLDoc("SearchDataByName", url, function()
                {
                    if (xmlhttp.readyState===4 && xmlhttp.status===200)
                    {
                        var text = xmlhttp.responseText;
                        var n = text.localeCompare("true");
                        if(n === 1) {
                            var x = checkDates();
                            if(x === true) {
                                document.getElementById("console").innerHTML = "Console";
                                document.search_form.action = "ViewDataByName";
                                document.search_form.submit();
                            } 
                        } else {
                            document.getElementById("console").innerHTML = text;
                        }
                    }
                });
            }
            function isValidDate(dt) {
                if(dt.length !== 0) {
                    return true;
                } else {
                    return false;
                }
            }
            function setColor(dt,fl) {
                if(fl === false) {
                    dt.style.border = "1px solid red"; 
                } else {
                    dt.style.border = "";
                }
            }
            function checkDates() {
                var field = document.getElementById("field").value;
                var from_dt = document.getElementById("from");
                var to_dt = document.getElementById("to");
                setColor(from_dt, true);
                setColor(to_dt, true);
                if(field === "from_to") {
                    var from = from_dt.value;
                    var to = to_dt.value;
                    if(isValidDate(from) && isValidDate(to)) {
                        var from_date = new Date(from);
                        var to_date = new Date(to);
                        var from_time = from_date.getTime();
                        var to_time = to_date.getTime();
                        //check the dates
                        if(from_time <= to_time) {
                            document.getElementById("console").innerHTML = "Console";
                            return true;
                        } else {
                            document.getElementById("console").innerHTML = "Error! From date is after To date. Please rectify them.";
                            setColor(from_dt, false);
                            return false;
                        }
                    } else if(isValidDate(from) && !isValidDate(to)){
                        setColor(to_dt, false);
                        document.getElementById("console").innerHTML = "You have entered invalid END date! Please rectify it marked in red";
                        return false;
                    } else if(!isValidDate(from) && isValidDate(to)) {
                        setColor(from_dt, false);
                        document.getElementById("console").innerHTML = "You have entered invalid START date! Please rectify it marked in red";
                        return false;
                    } else {
                        setColor(from_dt, false);
                        setColor(to_dt, false);
                        document.getElementById("console").innerHTML = "You have entered invalid dates! Please rectify them marked in red";
                        return false;
                    }
                } else {
                    return true;
                }
            }
            function enableFields(combo) {
                var field = combo.value;
                var from = document.getElementById("from");
                var to = document.getElementById("to");
                var time_int = document.getElementById("time_int");
                if(field === "from_to") {
                     from.disabled = false;
                     to.disabled = false;
                     time_int.disabled = true;
                } else if(field === "show_all") {
                    from.disabled = true;
                     to.disabled = true;
                     time_int.disabled = true;
                } else {
                    from.disabled = true;
                     to.disabled = true;
                     time_int.disabled = false;
                }
            }
            function validate1() {
                var id = document.getElementById("patient_id");
                var name = document.getElementById("name");
                var age = document.getElementById("age");
                var b = new Array(4);
                var falg;
                if(id.value === "") {
                    id.style.border = "1px solid red";
                    b[0] = false;
                } else {
                    id.style.border = "";
                    b[0] = true;
                }
                if(name.value === "") {
                    name.style.border = "1px solid red"; 
                    b[1] = false;
                } else {
                    name.style.border = "";
                    b[1] = true;
                }
                if(age.value === "") {
                    age.style.border = "1px solid red";
                    b[2] = false;
                } else {
                    age.style.border = "";
                    b[2] = true;
                }
                b[3] = validateMobile();
                for(var i = 0;i<4;i++) {
                    if(b[i] === false) {
                        falg = false; 
                        break;
                    } else {
                        falg = true;
                    }
                }
                if(b[3] === false) {
                    alert("You have entered an invalid mobile number!");
                }
                if(!b[0] && !b[1] && !b[2]) {
                    alert("Please rectify the fields marked in Red!");
                }
                return falg;
            }
            function monitoring(v) {
                var url = "";
                url = url + "monitoringUSB=" + v;
                loadXMLDocInit("MonitorUSB", url, function() {
                    if (xmlhttpi.readyState===4 && xmlhttpi.status===200) {
                        var retText = xmlhttpi.responseText;
                        document.getElementById("console").innerHTML = retText;
                    }
                });
            }
            function monitoringW(v) {
                var url = "";
                url = url + "monitoringWiFi=" + v;
                //alert(url);
                loadXMLDocInit("MonitorWiFi", url, function() {
                    if (xmlhttpi.readyState===4 && xmlhttpi.status===200) {
                        var retText = xmlhttpi.responseText;
                        document.getElementById("console").innerHTML = retText;
                    }
                });
            }
            function monitoringX(v,w) {
                var url = "";
                url = url + "xivelyFeed=" + v + "&";
                url = url + "xivelyInterval=" + w;
                loadXMLDocInit("XivelyFeed", url, function() {
                    if (xmlhttpi.readyState===4 && xmlhttpi.status===200) {
                        var retText = xmlhttpi.responseText;
                        document.getElementById("console").innerHTML = retText;
                    }
                });
            }
            function parseData(line) {
                var pair = line.split(",");
                var s = pair.length * 2;
                var c = 0;
                var x = "";
                var v = new Array(s);
                for(var i = 0 ; i < pair.length ; i++ ) {
                    var kv = pair[i].split(":");
                    v[c++] = kv[0];
                    v[c++] = kv[1];
                }
                for(var i = 0 ; i < s ; i++ ) {
                    x = x + v[i] + " ";
                }
                return v;
            }
           function init() {
               initECG();
               initRespRate();
               cmd_record  = document.getElementById("cmd_record");
               var wifi = document.getElementById("wifi");
               
           }
           function initECG() {
                canvasECG = document.getElementById("ecg_panel");
                graphicsECG = canvasECG.getContext("2d");
                drawECG();
           }
           function initRespRate() {
               canvasResp = document.getElementById("resp_rate_panel");
                graphicsResp = canvasResp.getContext("2d");
                drawRespRate();
           }
           function workPatientData() {
                if(onMonitor === true) {
                    tMonitor = setInterval(getPatientData,inte);
                } else {
                    clearInterval(tMonitor);
                    onMonitor = true;
                }
           }
           function getPatientData() {
               onMonitor = false;
               loadXMLDocDP("DataPanel", function() {
                    if (xmlhttpdp.readyState===4 && xmlhttpdp.status===200) {
                        var d = xmlhttpdp.responseText;
                        var v = parseData(d);
                        for(var i = 0 ; i < v.length ; i = i + 2 ) {
                            document.getElementById(v[i]).innerHTML = v[i + 1];
                        }
                    }
                });
           }
           function terminateSystem() {
               var url = "mode=Stop";
                loadXMLDocEnd("TerminateSystem", url, function()
                {
                    if (xmlhttpe.readyState===4 && xmlhttpe.status===200)
                    {
                        console.log(xmlhttpe.responseText);
                    }
                });
           }
           function workAbnormality() {
               if(onAbnormal === true) {
                    tAbnormal = setInterval(monitoringAbnormality,inte);
                } else {
                    clearInterval(tAbnormal);
                    onAbnormal = true;
                }
            }
            function monitoringAbnormality() {
                onAbnormal = false;
                loadXMLDocAbn("MonitorAbnormality", function() {
                    if (xmlhttpab.readyState===4 && xmlhttpab.status===200) {
                        var retText = xmlhttpab.responseText;
                        //var arr = retText.split("\n");
                        if(retText !== "No abnormality") {
                            generate('warning', retText);
                        }
                    }
                });
            }
            function generate(type, text) {

            var n = noty({
                text        : text,
                type        : type,
                dismissQueue: true,
                layout      : 'topLeft',
                closeWith   : ['click'],
                theme       : 'relax',
                maxVisible  : 10,
                animation   : {
                    open  : 'animated bounceInLeft',
                    close : 'animated bounceOutLeft',
                    easing: 'swing',
                    speed : 500
                }
            });
            console.log('html: ' + n.options.id);
        }
        function validateMobile() {
            if(mobile !== null) {
                if(mobile.length !== 10) {
                    return false;
                } else {
                    return !isNaN(mobile);
                }
            } else {
                return false;
            }
        }
        function recordAction() {
            cmd_record.disabled = true;
            cmd_record.value = "Connect BP";
            loadXMLDocDP("BPRecord", function() {
                    if (xmlhttpdp.readyState===4 && xmlhttpdp.status===200) {
                        var d = xmlhttpdp.responseText;
                        if(d === "BP Started") {
                            tBP = setInterval(actionPerformed, 5000);
                        }
                    }
                });
        }
        function actionPerformed() {
            cmd_record.disabled = false;
            cmd_record.value = "BP Record";
        }
        