<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<html>
<head>

       <title>FormTest</title>

</head>
<body>

<h2>Tipp dine resultater i gruppespillet</h2>

<form:form method="POST" action="/addStudent">
   <table class="table match-table">
       <thead>
           <tr>
               <th>#</th>
               <th>Dato</th>
               <th>Klokken</th>
               <th>Vises på</th>
               <th>Hjemme</th>
               <th>Borte</th>
               <th>Resultat</th>
           </tr>
       </thead>
       <tbody></tbody>
   </table>
   <input class="btn btn-default" type="submit" value="Submit"/>

</form:form>
<script>
var data = ${data};</script>
<script src="<c:url value="/js/form.js"/>"></script>

</body>
</html>
