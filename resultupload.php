<?php
$servername = "localhost";
$username = "adminOfApp";
$password = "nai*3588$";
$dbname = "mca_agrozen";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$f1 = $_GET['k1'];  //result
$f2 = $_GET['k2']; //image 
$f3 = $_GET['k3']; // name of decies


 $result="UPDATE imgtable SET `result`='$f1', `dname`='$f3'  WHERE `imageurl`='$f2' ";
 
  
  if(mysqli_query($conn,$result))
  
  {
    echo "Register successfully";
  }
  
  else
  
  {
	echo "not register",mysqli_error($conn);
  }
	
  
mysqli_close($conn);
exit();
?>