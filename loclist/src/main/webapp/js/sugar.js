(function($) {
  var base;
  
  $.fn.sugar = function(args) {
    base = $(this);
    load(args.url);
    if (args.confirm === undefined) {
    	args.confirm = 'a.confirm';
    }
    if (args.submit === undefined) {
    	args.submit = 'input.submit';
    }
    if (args.expand === undefined) {
    	args.expand = 'input.expand';
    }
    
    function load(url) {
      if (url.substr(0, 1) === '!') {
        url = url.substr(1);
        $.get(url);
      }
      else {
        base.load(url, function() {
          args.onLoad();
          $(args.confirm).click(function(event) {
            event.preventDefault();
            event.stopImmediatePropagation();
            var title = $(this).attr('title');
            if (title !== undefined) {
              if (confirm(title)) { 
                doLoad(this);
                $(this).trigger('confirm');
              }
            }
          });
          $('a').click(function(event) {
            event.preventDefault();
            doLoad(this);
          });
          $(args.submit).each(function() {
            var form = $(this).closest('form');
            $(this).change(function() { doSubmit(form); });
            $(form).submit(function(event) { event.preventDefault(); });
          });
          $(args.expand).each(function() {
            var e = this;
            doExpand(e);
            $(this).keypress(function() { doExpand(e); });
            $(this).change(function() { doExpand(e); });
          });
        });
      }
    }
    
    function doExpand(e) {
  	  $(e).attr('size', Math.max(3, $(e).val().length));
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
