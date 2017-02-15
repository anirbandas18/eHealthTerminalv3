/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var xmlhttp;
var fileName;
var win;
           function xyz(btn) {
               var remarks = prompt("Give Remarks");
               var url = "Remarks=" + remarks + "&";
               url = url + "FileName=" + btn.id.toString();
               if(remarks.length !== 0) {
                   if(remarks !== "null") {
                        loadXMLDoc("RemarksSubmission", url, function() {
                            if (xmlhttp.readyState===4 && xmlhttp.status===200)
                            {   
                                if(xmlhttp.responseType === "false") {
                                    alert("Failed to save remarks for this set of reading!");
                                } else {
                                    alert("Remarks successfully submitted!");
                                }
                            }
                        }, true);
                    }
               }
           }
            function loadXMLDoc(url,params,cfunc,flag) {
                if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp=new XMLHttpRequest();
                }
                else {// code for IE6, IE5
                    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.onreadystatechange=cfunc;
                xmlhttp.open("POST",url,false);
                if(flag === true) {
                    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                } 
                xmlhttp.send(params);
            }
            function enableButton(rd) {
                var b = document.getElementsByName("view");
                for(var i = 0; i < b.length; i++){
                    b[i].disabled = true;
                }
                var btn = document.getElementById(rd.value);
                btn.disabled = false;
            }
            function viewData(btn, x) {
                var url = "fileName=";
                url = url + x.toString() + "_" + btn.id + ".xml&";
                url = url + "id=" + x.toString();
                //alert(url);
                loadXMLDoc("LoadFromXML", url, function() {
                    if (xmlhttp.readyState===4 && xmlhttp.status===200)
                    {
                        document.getElementById("display").innerHTML = xmlhttp.responseText;
                    }
                }, true);
            }
            function generateReport(id) {
                var url = "fileName=";
                url = url + id.toString() + "_" + fileName + ".xml&";
                url = url + "id=" + id.toString();
                loadXMLDoc("GenerateReport", url, function() {
                    if (xmlhttp.readyState===4 && xmlhttp.status===200)
                    {
                        document.getElementById("console").innerHTML = "Report Generated Successfully";
                    } else {
                        document.getElementById("console").innerHTML = "Report Generation Failed";
                    }
                }, true);
            }
            function viewGraph(btn) {
                var url = "fileName=";
                url = url + btn.id.toString() + ".jpg";
                loadXMLDoc("GetGraphImages", url, function() {
                    if (xmlhttp.readyState===4 && xmlhttp.status===200)
                    {
                        var ret = xmlhttp.responseText;
                        var data = ret.split("\n");
                        win = window.open("", "Graph Instances", "width=450, height=450");
                        if(data.length !== 1) {
                            win.document.write("<img src='" + data[0] + "' width='400' height='200'><br><br><img src='" 
                                + data[1] + "' width='400' height='200'>");
                        } else {
                            win.document.write("Failed to retrieve graph instances from server");
                        }
                        check();
                    }
                }, true);
            }
            function check() {
                if(win.document) {
                    win.document.title = "Graph Instances";
                } else {
                    setTimeout(check, 10);
                }
            }
           
            function validateImage(id) {
                var imageFile = document.getElementById("image_file");
                if(imageFile.value.length !== 0) {
                    var sampleFile = imageFile.files[0];
                    var formdata = new FormData();
                    formdata.append("ID", id);
                    formdata.append("File", sampleFile);
                    loadXMLDoc("SaveImage", formdata, function() {
                        if (xmlhttp.readyState===4 && xmlhttp.status===200) {
                            if(xmlhttp.responseText === "true") {
                                viewImage(id);
                            } else {
                                alert("Failed to load image!");
                            }
                        }
                    }, false);
                }
            }
            
            function viewImage(id) {
                var img = document.getElementById("img");
                var url = "ID=" + id;
                loadXMLDoc("GetPatientImage", url, function() {
                    if (xmlhttp.readyState===4 && xmlhttp.status===200)
                    {
                        var ret = xmlhttp.responseText;
                        if(ret !== "false") {
                            img.src = ret;
                        } else {
                            alert("Failed to load image!");
                        }
                    }
                }, true);
            }
            
            $(function () {
                $('input[type="file"]').change(function () {
                    if ($(this).val() !== "") {
                        $(this).css('color', '#333');
                    }else{
                        $(this).css('color', 'transparent');
                    }
                });
            }) ;
            
       
