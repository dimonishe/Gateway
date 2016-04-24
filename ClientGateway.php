<?php

namespace ClientGateway;

use Exception;

class ClientGateway{
    
    private $socket = null;
    private $IP = null;
    private $Port = null;

    public function __construct($IP, $Port){
        
        $this->IP = $IP;
        $this->Port = $Port;
        
        try{
            $this->createSocket();
        } catch (Exception $ex) {
            echo $ex->getMessage();
        }
    }
    
    private function createSocket(){

        $this->socket = \fsockopen($this->IP, $this->Port, $errno, $errstr, 30);
        if (!$this->socket) {
            throw new Exception("$errstr ($errno)<br />\n");
        }
    }
    
    private function sendMessage(){
        
        $out = md5("Open");
        
        if(!fwrite($this->socket, $out)){
            throw new Exception("Connection error!");
        }
        
        echo "Opening door...";
        
        fclose($this->socket);
        
    }

    public function openGate() {
        try {
            $this->sendMessage();
        } catch (Exception $ex) {
            echo $ex->getMessage();
        }
    }

}