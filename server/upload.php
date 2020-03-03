<?php

        if($_SERVER['REQUEST_METHOD']=='POST'){
                $file_name = $_FILES['myFile']['name'];
                $file_size = $_FILES['myFile']['size'];
                $file_type = $_FILES['myFile']['type'];
                $temp_name = $_FILES['myFile']['tmp_name'];

                $location = "uploads/";

                move_uploaded_file($temp_name, $location.$file_name);
                echo "http://18.217.240.188/".$location.$file_name;
        }else{
                echo "Error";
        }

?>
