<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

       <title>Players</title>

</head>
<body>
    <h1>Players</h1>
    <p>Legg til hvem du tror blir den beste spilleren og toppscorer.</p>
    <p>Skriv inn riktig Etternavn. Vet du ikke hvordan du skriver etternavnet nøyaktig,</p>
    <p>Sjekk denne siden <a href="http://www.mirror.co.uk/sport/football/news/euro-2016-squads-confirmed-provisional-7986775">her.</a></p>
    <p>Trykk på det laget spilleren spiller på, også trykk på squad.</p>
    <p>PS. Dersom spilleren har 2 etternavn, så inkluderer du begge</p>



    <form:form method="POST" action="/finished">
       <h2>Toppscorer</h2>
       <p>Her får man poeng for antall mål og riktig spiller.</p>

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
    <script src="<c:url value="/js/players.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-select.js"/>"></script>

</body>
</html>
