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

$name = $_POST['name'];
$image = $_POST['image'];
$resul = $_POST['res'];
$dnm = $_POST['dname'];


	
  $filePath = "img_process/$name.jpg";
  $url = "https://concepthover.com/agrozen/img_process/$name.jpg";
  
  $result="INSERT INTO `imgtable`(`name` , `imageurl`, `result`,`dname`)  VALUES ('$name','$url','$resul','$dnm')";
 
  
  if(mysqli_query($conn,$result))
{
file_put_contents($filePath,base64_decode($image));

echo "Register successfully";
}
else
{
	echo"not register",mysqli_error($conn);
}
	
  
mysqli_close($conn);
exit();
?>