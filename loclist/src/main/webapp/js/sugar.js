(function($) {
  var base;
  
  $.fn.sugar = function(args) {
    base = $(this);
    load(args.url);
    
    function load(url) {
      if (url.substr(0, 1) === '!') {
        url = url.substr(1);
        $.get(url);
//        args.onPostLoad();
      }
      else {
        base.load(url, function() {
          args.onLoad();
          $('a.delete').click(function(event) {
            event.preventDefault();
            event.stopImmediatePropagation();
            var title = $(this).attr('title');
            if (title !== undefined) {
              if (confirm(title)) { doLoad(this); }
            }
          });
          $('a').click(function(event) {
            event.preventDefault();
            doLoad(this);
          });
          $('input').each(function() {
            var form = $(this).closest('form');
            $(this).change(function() { doSubmit(form); });
            $(form).submit(function(event) { event.preventDefault(); });
          });
//          args.onPostLoad();
        });
      }
    }

    function doLoad(e) {
      var url = $(e).attr('href');
      if (url == undefined) {
        return;
      }
      load(url);
    }

    function doSubmit(form) {
      var url = $(form).attr('action') + '?';
      $(form).find('input').each(function(index, input) {
        var value = encodeURI($(input).attr('value'));
        var name = $(input).attr('name');
        url = url + name + '=' + value + '&';
      });
      load(url);
    }
  };
})(jQuery);
