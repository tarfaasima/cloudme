$(document).ready(function() {
  load('/html/account_input.html');
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