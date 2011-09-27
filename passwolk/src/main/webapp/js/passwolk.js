$(document).ready(function() {
  var markup = '<h1>Accounts</h1><ul>{{each data}}<li>${title}</li>{{/each}}</ul>';
  
  $.getJSON('/account', function(data) {
    console.log(data);
    data = {data: data};
    console.log(data);
    $.tmpl(markup, data).appendTo('body');
  });
//  load('/html/account_input.html');
});

function load(url) {
  $('body').load(url, function() {
    $('form').submit(function(event) {
      alert('submit');
      event.preventDefault();
      var url = $(this).attr('action');
      var method = $(this).attr('method');
      var data = $(this).serialize();
      console.log(url);
      console.log(method);
      console.log(data); 
      $.ajax({
        url : url,
        type : method,
        data : data,
        success : function(data) {
          alert('done');
        }
      });
      return false;
    });
  });
}