$(document).ready(function(){
  $(".active a").click(function(event) {
    $("input#fileInput").click();
  });
  $("input#fileInput").change(function(event) {
    $("form#uploadForm").submit();
  });
});
