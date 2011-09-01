$(document).ready(function(){
  $("a.upload").click(function(event) {
    $("input#fileInput").click();
  });
  $("input#fileInput").change(function(event) {
    $("form#uploadForm").submit();
  });
});
