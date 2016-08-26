<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

       <title>Players</title>

</head>
<body>
    <form:form method="POST" action="/addPlayerScoresPOST">
       <h2>Toppscorer</h2>
       <p>Her får man poeng for antall mål og riktig spiller.</p>
       <p>BESTE SPILLER 2 ER HVILKET LAG SOM VINNER!</p>

       <table class="table toppscorer-table">
           <thead>
               <tr>
                   <th>#</th>
                   <th>Spiller</th>
                   <th>Goals</th>

               </tr>
           </thead>
           <tbody></tbody>
       </table>

       <h2>Beste Spiller</h2>
       <p>Her får man poeng for riktig spiller.</p>
        <table class="table bestplayer-table">
           <thead>
               <tr>
                   <th>#</th>
                   <th>Spiller</th>
               </tr>
           </thead>
           <tbody></tbody>
       </table>


       <input class="btn btn-default player-btn" type="submit" value="Neste"/>
       <input type="hidden" class="input_JSON" name="players" required />

    </form:form>
    <script src="<c:url value="/js/addPlayer.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-select.js"/>"></script>

</body>
</html>
