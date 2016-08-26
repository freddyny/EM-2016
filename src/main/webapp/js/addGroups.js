var tr = $('<tr>');
var group = $('<input>')
                 .attr('type', 'text')
                 .attr('name', 'group')
                 .addClass('form-control');
var tdGroup = $('<td>').append(group);
tr.append(tdGroup);

var first = $('<input>')
                 .attr('type', 'text')
                 .attr('name', 'firstPlace')
                 .addClass('form-control');
var tdFirst = $('<td>').append(first);
tr.append(tdFirst);

var second = $('<input>')
                 .attr('type', 'text')
                 .attr('name', 'secondPlace')
                 .addClass('form-control');
var tdSecond = $('<td>').append(second)
tr.append(tdSecond);

var third = $('<input>')
                  .attr('type', 'text')
                  .attr('name', 'thirdPlace')
                  .addClass('form-control');
var tdThird = $('<td>').append(third);

tr.append(tdThird);

var forth = $('<input>')
                  .attr('type', 'text')
                  .attr('name', 'forthPlace')
                  .addClass('form-control');
var tdForth = $('<td>').append(forth);
tr.append(tdForth);

$("table.groups-table > tbody").append(tr);