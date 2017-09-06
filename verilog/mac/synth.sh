#!/bin/bash 
if [ $# -eq 0 ]
  	then
    	echo "No file specified, call this script using this syntax:"
        echo "\"$ ./synth.sh <verilogFile.v>\""
	else
		cp $1 ./autoyosys/tmp.v
        yosys -s ./autoyosys/auto.ysy
        cp ./autoyosys/tmp.json ./$1.json
fi
