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

$("table.matchresult-table > tbody").append(tr);