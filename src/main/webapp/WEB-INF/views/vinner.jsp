<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

       <title>Vinner</title>

</head>
<body>
    <h1>Vinner</h1>
    <p>Legg til hvem du tror kommer til å vinne em. Dette teller 20 poeng. </p>
    <form:form method="POST" action="/players">
       <table class="table winner-table">
           <thead>
               <tr>
                   <th>Info</th>
                   <th>Vinner</th>
               </tr>
           </thead>
           <tbody></tbody>
       </table>
       <input class="btn btn-default winner-btn" type="submit" value="Neste"/>
       <input type="hidden" class="input_JSON" name="winner" required />

    </form:form>


    <script>
        var json = ${json};
    </script>
    <script src="<c:url value="/js/winner.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-select.js"/>"></script>

</body>
</html>
