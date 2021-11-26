<?php

require_once 'config.php';

$response=array();
$message = 'Params ';
$is_error = false;

 if(!empty($_POST['username']) || !empty($_POST['password'])){


 $username = $_POST['username'];
 $password = $_POST['password'];
 
 $query = "SELECT * from agro_users where `username`='$username' and `password`='$password'";
    $qres=$con->query($query);
    if($qres->num_rows>0){
        $response['result']="1";
        while($row=$qres->fetch_assoc()){
            $response['user_data'][]=$row;
        }
    }
    else{
		$response['result']="0";
        $response['message']="Wrong Credentials";
    }
 }else{
 echo "Check Again";
 }
 
 
header('Content-Type: application/json');
echo json_encode($response);

//mysqli_close($con);

?>