<?php
include_once 'gallery/Gallery.php';
$galleries = Gallery::loadAllGalleries();
foreach ($galleries as $gallery):
?>
<div><?php echo $gallery->name ?></div>
<?php
endforeach;
?>
