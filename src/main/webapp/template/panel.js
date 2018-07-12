window.onload = function(){
  //includeTargetElement();
  includeFile("https://code.jquery.com/ui/1.12.0/jquery-ui.min.js", "js");
  includeFile("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css", "css");
  includeFile("https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js", "js")

  $(function() {
    $('#panel-btn').click(function(){
      $('#panel').toggleClass("panel-closed", 500, "swing", function(){
        if($('#panel').attr('class') == 'panel-closed'){
          $('#panel-btn-icon i').attr('class', "fas fa-door-closed");
          $('#panel-btn-desc').text('Open');
        } else {
          $('#panel-btn-icon i').attr('class', "fas fa-door-open");
          $('#panel-btn-desc').text('Close');
        }
      });
    });
  });
}
