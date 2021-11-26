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

$details_of_project=array();

$sql="SELECT * FROM `imgtable`";
 
$result = mysqli_query($conn,$sql);

   if ($result->num_rows > 0)
   {
	   while($row = $result->fetch_assoc()) 
	   {
           array_push($details_of_project,$row);
       } 
	
       $finalarray=array(); 
       $finalarray["project_details"]=$details_of_project;

       echo json_encode($finalarray);
       echo "success";
	
    } 
    else
    {
        echo "no data found";
    }
	
  
mysqli_close($conn);
exit();
?>