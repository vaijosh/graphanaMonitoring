# monitoring-aop
Demo app to show monitoring aspect usage

Follow steps below If you are interested in checking out this sample

Install common-library
* Go to monitoring-aop/common-library
* mvn clean install

Install hello-world-service 
* Go to monitoring-aop/hello-world-service
* mvn clean install

Build docker image for hello-world-service
* Go to parent folder monitoring-aop
* docker-compose -f metrics.yml build

Run the entire stack including graphite, grafana
* docker-compose -f metrics.yml up

Once the docker started access graphana as follows
1. Create datasources
    * In graphana create data source UI set http://graphite as Http URL. Accept other defaults and click Save and Test.
    * Create dashboards using above datasource 
2. SampleDashboards.json
3. Using Postman hit REST APIs localhost:8090/exceptionDemo and localhost:8090/helloWorld and see graph for the activity updates
