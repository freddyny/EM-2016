<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>

       <title>Leaderboard</title>


</head>
<body>
    <h1>Leaderboard</h1>
    <p>Dette er resultatslisten. Denne vil bli oppdatert etter hver kamp.</p>
    <table class="table score-table">
        <thead>
           <tr>
               <th>#</th>
               <th>Brukernavn</th>
               <th>Score</th>
           </tr>
        </thead>
    <tbody></tbody>
    </table>


    <script>
        var json = ${json};
    </script>
    <script src="<c:url value="/js/score.js"/>"></script>

</body>
</html>



