<?php
class ClientGateway{
    
    private $socket = null;
    private $IP = null;
    private $Port = null;
    /**
     * Initialize all necessary variables and involve methods realized bellow
     * @param string $IP
     * @param string $Port
     */
    public function __construct($IP, $Port){
        
        $this->IP = $IP;
        $this->Port = $Port;
        
        try{
            $this->createSocket();
            $this->openGate();  
        } catch (Exception $ex) {
            echo $ex->getMessage();
        }
    }
    /**
     * Function create a socket for connection to server
     * @throws Exception
     */
    private function createSocket(){
        $this->socket = \fsockopen($this->IP, $this->Port, $errno, $errstr, 30);
        if (!$this->socket) {
            throw new Exception("$errstr ($errno)<br />\n");
        }
    }
    /**
     * Function open gate with using md5 hash
     * @throws Exception
     */
    private function openGate(){
        $out = md5($this->getTime());
        if(!fwrite($this->socket, $out)){
            throw new Exception("Connection error!");
        }
        echo "Opening door...";
        fclose($this->socket);
    }
    /**
     * Get time from api of api.geonames.org and take time from received data by regexp
     * @return string
     */
    private function getTime(){
        $file = file_get_contents("http://api.geonames.org/timezone?lat=47.01&lng=10.2&username=demo&style=full");
        preg_match("/(....-..-..\s..:..)/", $file, $result);
        return $result[0];
    }
}

static $IP = "192.168.0.100";
static $PORT = "4444";

$client = new ClientGateway($IP, $PORT);