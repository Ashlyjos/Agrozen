<?php

require_once 'config.php';

$response=array();
$message = 'Params ';
$is_error = false;

if(empty($_POST['productname']) || empty($_POST['username']) || empty($_POST['phonenumber']) || empty($_POST['price']) || empty($_POST['duration']) || empty($_POST['location']))
{
	$response['result']="0";
	$response['message']="Please fill all the details";
}

else
{

$productname=$_REQUEST['productname'];
$username=$_REQUEST['username'];
$phonenumber=$_REQUEST['phonenumber'];
$price=$_REQUEST['price'];
$location=$_REQUEST['location'];
$duration=$_REQUEST['duration'];

    $query= "INSERT into agro_rent (`productname`, `username`, `phonenumber`, `price`,`duration`,`location`) VALUES ('$productname', '$username', '$phonenumber','$price','$duration','$location')";
                $res=$con->query($query) or die(error());
                if($res)
                {	
                	$listing_id = $con->insert_id;
					$response['result']="1";
                    $response['message']= "Success";
					$response['username']=$username;
                }
                else
                {
                	$response['result']="0";
                    $response['message'] = 'Fail';
                }
}
header('Content-type: application/json');
echo json_encode($response);
$con->close();
?>