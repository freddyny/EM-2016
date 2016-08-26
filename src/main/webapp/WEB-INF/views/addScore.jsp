<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<html>
<head>

       <title>Username</title>

</head>
<body>
    <p>Legg til brukernavn</p>
    <form:form method="POST" action="/addScore">
       <table class="table username-table">
           <thead>
               <tr>
                   <th>Info</th>
                   <th>ID</th>
                   <th>Score</th>
               </tr>
           </thead>
           <tbody></tbody>
       </table>
       <input class="btn btn-default" type="submit" value="Neste"/>

    </form:form>

    <script>
        var tr = $('<tr>');
        tr.append($('<td>').append("ID: "));
        var id = $('<input>')
                    .attr('type', 'text')
                    .attr('name', 'id')
                    .addClass('form-control');
        var score = $('<input>')
                    .attr('type', 'text')
                    .attr('name', 'score')
                    .addClass('form-control');
        tr.append(id);
        tr.append(score);
        $("table.username-table > tbody").append(tr);
    </script>

</body>
</html>
