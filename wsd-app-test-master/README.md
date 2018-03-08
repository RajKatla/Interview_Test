WSD coding test

This project contains a simple spring webapp
open a command line
cd .../wsd-app-test

mvn jetty:run


if all successful this will be running on: http://localhost:8686

2 endpoints are already configured:
http://localhost:8686/api/ui/test/ping
http://localhost:8686/api/ui/test/service

The second uses an autowired service to show it all wires up


--------------
The Exercise
-------------
In the folder 
src/main/resources/schema

are 2 schemas RegX and EPTEMT

and there is a sample xml file

src/test/resources/sample.xml

1. Create an endpoint that uses standard Java Xml libs to validate an xml submission against the RegX schema
and either returns 200:OK or an xml validation error.

2. Build this endpoint out (or add a separate one) which

* receives the same xml
* Marshalls into Java objects (auto or otherwise)
* finds PriipList.Priip.PriipData.Risk
* converts this into the EPTEMT version of PriipList.Priip.PriipData.Risk
* and returns it as xml or json based on request header.

















