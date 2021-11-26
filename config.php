<?php
$con = new mysqli("localhost", "adminOfApp", "nai*3588$", "mca_agrozen");
 
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $con->connect_error);
}
else{
//echo "Connected successfully";
}
?>