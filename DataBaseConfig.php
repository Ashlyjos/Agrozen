<?php

class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {

        $this->servername = 'localhost';
        $this->username = 'adminOfApp';
        $this->password = 'nai*3588$';
        $this->databasename = 'mca_agrozen';

    }
}

?>
