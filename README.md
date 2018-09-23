# monitoring-aop
Demo app to show monitoring aspect usage

Follow steps below If you are interested in checking out this sample

##Compile code
From project run ```mvn clean install```

##Build Docker
From project directory run
```docker-compose -f metrics.yml build```

##Start containers
Run the entire stack including graphite, grafana
```docker-compose -f metrics.yml up```

##Graphana Configuration
Once the docker started access graphana as follows
1. Create datasources
    * In graphana create data source UI set http://graphite as Http URL. Accept other defaults and click Save and Test.
    * Create dashboards using above datasource 
2. SampleDashboards.json
3. Using Postman hit REST APIs localhost:8090/exceptionDemo and localhost:8090/helloWorld and see graph for the activity updates
