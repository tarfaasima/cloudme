$(document).ready(function() {
  var accounts;
  
  $('div#accountEntry form').submit(doSubmit);
  $('input[type=search]').keyup(function() {
    var searchValue = $(this).val();
    console.log(searchValue);
    var filteredAccounts = $.grep(accounts, function(account) {
      return account.title.indexOf(searchValue) != -1;
    });
    render(accounts);
  });
  $.getJSON('/account', function(data) {
    accounts = data;
    render(accounts);
  });
});

function render(data) {
  $('div#accountList ul').empty();
  $('#accountListTmpl').tmpl(data).appendTo('div#accountList ul');
}

function doSubmit(event) {
  alert('doSubmit');
  var form = $(this);
  event.preventDefault();
  var url = form.attr('action');
  var method = form.attr('method');
  var data = form.serialize();
  console.log(url);
  console.log(method);
  console.log(data); 
  $.ajax({
    url : url,
    type : method,
    data : data,
    success : function(data) {
      alert('done');
      form.find('input[type=text]').val('');
    }
  });
  return false;
}
