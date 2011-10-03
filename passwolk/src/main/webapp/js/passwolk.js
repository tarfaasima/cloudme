$(document).ready(function() {
  var accounts;
  
  $('body').delegate('input[type=search]', 'keyup', function() {
    var searchValue = $(this).val();
    var filteredAccounts = $.grep(accounts, function(account) {
      return account.title.indexOf(searchValue) != -1;
    });
    render('account_view', accounts);
  });
  
  $.get('/tmpl/account_view.tmpl', function(markup) {
    $.template('account_view', markup);
    $.getJSON('/account', function(data) {
      accounts = data;
      render('account_view', accounts);
    });
  });
});

function render(name, data) {
  $('body').empty();
  $.tmpl(name, {data: data}).appendTo('body');
}

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