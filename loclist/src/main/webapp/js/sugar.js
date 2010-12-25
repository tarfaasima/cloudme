(function($) {
  var element;
  var hasChanged;
  
  $.fn.sugar = function(args) {
    element = $(this);
    load(args.url);
    
    function load(url) {
      if (url.substr(0, 1) === '!') {
        url = url.substr(1);
        $.get(url);
        args.onPostLoad();
      }
      else {
        element.load(url, function() {
          args.onPreLoad();
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
            var input = this;
            var form = $(this).closest('form');
            //$(this).change(function() { hasChanged = true; });
            $(this).change(function() { doSubmit(form); });
            $(form).submit(function(event) {
              event.preventDefault();
              doSubmit(form, input);
            });
          });
          args.onPostLoad();
        });
      }
    }

    function doLoad(e) {
      var url = $(e).attr('href');
      load(url);
    }

    function doSubmit(form, input) {
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
