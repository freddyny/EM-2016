var tr = $('<tr>');
var number = $('<input>')
                 .attr('type', 'number')
                 .attr('name', 'matchNr')
                 .addClass('form-control');
var tdNumber = $('<td>').append(number);
tr.append(tdNumber);

var result = $('<input>')
                 .attr('type', 'text')
                 .attr('name', 'result')
                 .addClass('form-control');
var tdResult = $('<td>').append(result)
tr.append(tdResult);

var HUB = $('<input>')
                  .attr('type', 'text')
                  .attr('name', 'HUB')
                  .addClass('form-control');
var tdHUB = $('<td>').append(HUB);

tr.append(tdHUB);

var homeTeam = $('<input>')
                  .attr('type', 'text')
                  .attr('name', 'homeTeam')
                  .addClass('form-control');
var tdHomeTeam = $('<td>').append(homeTeam);

tr.append(tdHomeTeam);

var awayTeam = $('<input>')
                  .attr('type', 'text')
                  .attr('name', 'awayTeam')
                  .addClass('form-control');
var tdAwayTeam = $('<td>').append(awayTeam);

tr.append(tdAwayTeam);

var kot = $('<input>')
                  .attr('type', 'text')
                  .attr('name', 'knockoutType')
                  .addClass('form-control');
var tdKnockout = $('<td>').append(kot);

tr.append(tdKnockout);

$("table.matchresult-table > tbody").append(tr);