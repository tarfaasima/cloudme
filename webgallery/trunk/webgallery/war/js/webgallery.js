$(function() {
  /* ---------- Lightbox ---------- */ 
  $('a.lightbox').lightBox({
	imageLoading:           '/images/lightbox/lightbox-ico-loading.gif',
	imageBtnPrev:           '/images/lightbox/lightbox-btn-prev.gif',
	imageBtnNext:           '/images/lightbox/lightbox-btn-next.gif',
	imageBtnClose:          '/images/lightbox/lightbox-btn-close.gif',
	imageBlank:             '/images/lightbox/lightbox-blank.gif',
	containerResizeSpeed:   200,
	txtImage:               'Photo'
  });

  /* ---------- Tooltip ---------- */
  $('.photos img').tooltip({
      tip:      '.tooltip',
      position: 'bottom center'
  });

  /* ---------- Messages ---------- */
  $("#messages li").delay(2500).fadeOut("slow", function() {
    $("#messages").hide();
  });
});
