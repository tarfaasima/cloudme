$.fn.dynaHeight = function(elements) {
  $(this).each(function() {
	var top = $(this).offset().top;
	var maxBottom = 0;
	$(elements).each(function() {
	  var pos = $(this).offset();
	  var height = $(this).outerHeight();
	  var bottom = pos.top + height;
	  if (bottom > maxBottom) {
	    maxBottom = bottom;
	  }
	});
    $(this).css({"height": (maxBottom - top) + "px"});
  });
  return this;
};
