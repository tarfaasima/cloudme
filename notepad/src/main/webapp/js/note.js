$(document).ready(function() {
  var inputTopic = $("#topic");
  inputTopic.autocomplete({
    source: [${f:join(actionBean.topics, "\"", "\"", ", ")}],
    autoFocus: true
  });
  
  if (inputTopic.val()) {
    $("#content").focus();
  }
  else {
    inputTopic.focus();
  }
});
