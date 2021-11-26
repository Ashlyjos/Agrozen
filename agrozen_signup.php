<?php

require_once 'config.php';

$response=array();
$message = 'Params ';
$is_error = false;

if(empty($_POST['fullname']) || empty($_POST['username']) || empty($_POST['phonenumber']) || empty($_POST['password']))
{
	$response['result']="0";
	$response['message']="Please fill all the details";
}

else
{

$fullname=$_REQUEST['fullname'];
$username=$_REQUEST['username'];
$phonenumber=$_REQUEST['phonenumber'];
$password=$_REQUEST['password'];

    $query= "INSERT into agro_users (`fullname`, `username`, `phonenumber`, `password`) VALUES ('$fullname', '$username', '$phonenumber',
    '$password')";
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