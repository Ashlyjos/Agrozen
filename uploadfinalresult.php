<?php
$servername = "localhost";
$username = "adminOfApp";
$password = "nai*3588$";
$dbname = "mca_agrozen";
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
 

$url = $_GET['k1'];
$rs = $_GET['k2'];
$nm = $_GET['k3'];
  
  $result="UPDATE `imgtable` SET `result`='$rs', `dname`=$nm  WHERE `imageurl`='$url' " ;
 

  if(mysqli_query($conn,$result))
{

echo "upload successfully";
  echo $url;
  echo $rs;
  echo $nm;
}
else
{
	echo"not upload",mysqli_error($conn);
}
  
mysqli_close($conn);
exit();
?>