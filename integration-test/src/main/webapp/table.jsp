<%@ page import="org.shamdata.Sham" %>
<%@ page import="org.shamdata.person.Person" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  ~ The MIT License (MIT)
  ~
  ~ Copyright (c) 2014 the original author or authors
  ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a copy
  ~ of this software and associated documentation files (the "Software"), to deal
  ~ in the Software without restriction, including without limitation the rights
  ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  ~ copies of the Software, and to permit persons to whom the Software is
  ~ furnished to do so, subject to the following conditions:
  ~
  ~ The above copyright notice and this permission notice shall be included in
  ~ all copies or substantial portions of the Software.
  ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  ~ THE SOFTWARE.
  --%>

<!doctype>
<html>
<head>
    <title>Table page</title>
</head>
<body>
<h1>Table page</h1>
<%
    Sham sham = Sham.getInstance();
    sham.setSeed(126944277797809L);
    String rowsParam = request.getParameter("rows");
    int rows = rowsParam != null ? Integer.parseInt(rowsParam) : 3;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
%>
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Username</th>
            <th>Email address</th>
            <th>DOB</th>
        </tr>
    </thead>
    <tbody>
<%
    for(int i = 0; i < rows; i++) {
        Person p = sham.nextPerson();
%>
        <tr>
            <td class="id"><%= sham.getRandom().nextInt(100) + 1 %></td>
            <td class="name"><%= p.getName()%></td>
            <td class="username"><%= p.getUsername() %></td>
            <td class="email"><%= p.getEmail() %></td>
            <td class="dob"><%= format.format(p.getDob()) %></td>
        </tr>
<% } %>
    </tbody>
</table>
</body>
