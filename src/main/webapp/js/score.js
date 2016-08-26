
// Of course, normally we would receive this as a string:
var jsonData = JSON.stringify(json);

console.debug(jsonData);
// Now we can render our data using for instance a function:
getUsers(jsonData);




function getUsers(jsonData) {
    // First parse it from json:
    var users = JSON.parse(jsonData);

    // Check your console! You will see that the date
    // of each match is just a string! We want to work
    // with JavaScript Date objects, so let's do a quick
    // map! (This is a bit complex, you can either ignore
    // it or read some docs at:
    // http://www.w3schools.com/jsref/jsref_map.asp.
    // It's pretty cool though, probably worth it!)

    //JSON:    [{"userName":"r","score":0},{"userName":"freddyny","score":0}]
    // Okay, furthermore: we know this is a list,
    // which means we can iterate over it the "easy" way:
    for(var i = 0; i < users.length; i++) {
        // Now let's retrieve each match and work
        // with them individually:
        var user = users[i];
        //alert(match.homeTeam);

        // In order to render the match, we must
        // create a jQuery element, more specifically
        // a <tr> node.
        var hlink = $('<a>').attr('href','game/'+user.id);


        var tr = $('<tr>');


        // Or in one line:
        tr.append($('<td>').append(""+ (i+1)));

        tr.append($('<td>').append(user.userName));
        tr.append($('<td>').append(user.score));
        var hlink = $('<a>').attr('href','game_'+user.id).append("Spill");
        tr.append($('<td>').append(hlink));




        $("table.score-table > tbody").append(tr);
    }
}





