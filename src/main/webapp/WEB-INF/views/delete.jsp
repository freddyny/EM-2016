<%@ page language="java" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<html>
<head>

       <title>Delete</title>

</head>
<body>
    <h1>Delete</h1>
    <p>Delete the player</p>
    <form:form method="POST" action="/delete">
       <table class="table delete-table">
           <thead>
               <tr>
                   <th>Info</th>
                   <th>UserId</th>
               </tr>
           </thead>
           <tbody></tbody>
       </table>
       <input class="btn btn-default" type="submit" value="Neste"/>

    </form:form>

    <script>
        var tr = $('<tr>');
        tr.append($('<td>').append("UserId: "));
        var userName = $('<input>')
                    .attr('type', 'text')
                    .attr('name', 'delete')
                    .addClass('form-control');

        tr.append(userName);
        $("table.delete-table > tbody").append(tr);
    </script>

</body>
</html>
