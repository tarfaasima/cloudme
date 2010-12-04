(function($) {
  var element;
  var hasChanged;
  
  $.fn.sugar = function(args) {
    element = $(this);
    load(args.url);
    
    function load(url) {
      if (url.substr(0, 1) === '!') {
        $.get(url.substr(1));
      }
      else {
        element.load(url, function() {
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
            $(this).change(function() { hasChanged = true; });
            $(this).blur(function() { doSubmit(form); });
            $(form).submit(function(event) {
              event.preventDefault();
              doSubmit(form);
            });
          });
        });
      }
    }

    function doLoad(e) {
      var url = $(e).attr('href');
      load(url);
    }

    function doSubmit(form) {
      if (hasChanged) {
        hasChanged = false;
        var url = $(form).attr('action') + '?';
        $(form).find('input').each(function(index, input) {
          var value = encodeURI($(input).attr('value'));
          var name = $(input).attr('name');
          url = url + name + '=' + value + '&';
        });
        load(url);
      }
    }
  };

})(jQuery);
