<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
    <?php
    include_once 'security/Security.php';
    Security::checkAuthenticate('login.php');
    ?>
<html>
    <head>
        <title>Gallery</title>
        <script src="lib/jquery-1.3.2.min.js"></script>
        <script>
            $(document).ready(function() {
                $("#upload").bind("change", function(e) {
                    $("#uploadForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <a href="logout.php">logout</a>
        <form action="upload.php" method="post" enctype="multipart/form-data" id="uploadForm">
            <input type="hidden" name="MAX_FILE_SIZE" value="2000000">
            <input type="file" name="image" accept="image/*" id="upload"/>
            <!--input type="submit" value="Upload"/-->
        </form>
    </body>
</html>