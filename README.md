sample-jms-client
=================

Sample JMS client  that sends and receives text messages to the HornetQ JMS provider.

Maven, Spring, JMS, HornetQ

Steps to run the test:

1) Install HornetQ 

2) Define new queue "TestQueue1".

   In the {HornetQ-Install-path}\config\stand-alone\non-clustered\hornetq-jms.xml file add below mentioned new queue definition,

    <queue name="TestQueue1">
      <entry name="/queue/TestQueue1"/>
   </queue>


3) Start HornetQ in standalone mode.

   {HornetQ-Install-path}\bin\run.bat

4) Edit configuration in messagequeue.properties file to point to the HornetQ, if necessary. 

5) Run test.

     mvn test