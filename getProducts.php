<?php
	require_once 'config.php';
	$response=array();

	$my_query="SELECT * from agro_rent";
	$query_result=$con->query($my_query) or die($con->error());

	if($query_result->num_rows>0)
	{
		$response['message']="Success";
		while($row=$query_result->fetch_assoc())
		{
			$response['results'][]=$row;
		}
	}
	else
	{
		$response['message']="Not found";
	}

	header('Content-Type: application/json');
	echo json_encode($response);
?>