<%-- 
    Document   : FooterPanel
    Created on : 17-Feb-2015, 15:48:06
    Author     : Stifler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!private static final int STEPS = 15;%>
<body class="body">
    <center>
        <table class="table">
                <tr> 
                    <td>
                        <label class="label">
                            <%
                                for(int i = 0;i<STEPS;i++) {
                                    out.println("&nbsp;");
                                }
                            %>
                        </label>
                    </td>
                    <td colspan="2"></td>
                    <td>
                        <label class="label">
                            <%
                                for(int i = 0;i<STEPS;i++) {
                                    out.println("&nbsp;");
                                }
                            %>
                        </label>
                </tr>   
                <tr>
                    <td></td>
                    <td colspan="2"><label style="font-size: large;" class="label">e-Health Project SMCC SaltLake</label></td>
                    <td></td>
                </tr>
                <tr>
                    <td>
                        <label class="label">
                           <%
                                for(int i = 0;i<STEPS;i++) {
                                    out.println("&nbsp;");
                                }
                            %>
                        </label>
                    <td colspan="2"></td>
                    <td>
                        <label class="label">
                           <%
                                for(int i = 0;i<STEPS;i++) {
                                    out.println("&nbsp;");
                                }
                            %>
                        </label>
                </tr>
            </table>
    </center>
</body>