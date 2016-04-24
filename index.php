<?php

include 'ClientGateway.php';

use ClientGateway\ClientGateway;

static $IP = "192.168.0.2";
static $PORT = "4444";

$client = new ClientGateway($IP, $PORT);
$client->openGate();