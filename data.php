<?php
    $conn= mysqli_connect("localhost","user","Qwer!234","project") or die("MySQL 접속 실패");

    $sql ="SELECT * FROM covid"; 
    $Query = mysqli_query($conn, $sql);

    $result = array();

    while($row = mysqli_fetch_array($Query)){
        array_push($result, array('seq' => $row["seq"],'gubun' =>$row["gubun"],
        'defCnt'=>$row["defCnt"],'localOccCnt'=>$row["localOccCnt"], 'stdDay'=> $row["stdDay"]));
    }
    
    // $json = json_encode(array("covid"=>$result),JSON_UNESCAPED_UNICODE);
    // echo $json;
     $json = json_encode($result,JSON_UNESCAPED_UNICODE);
    echo $json;
    
    mysqli_close($conn);
?>