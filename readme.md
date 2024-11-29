# Monitoring Spring Boot Application using Micrometer, Prometheus and Grafana
* Micrometer provides a simple facade to integrate actuator metrics with external monitoring systems.
* Prometheus is configured to pull the metrics data from the actuators in the Sprint Boot application, and then it stores these data in a TimeSeries database.
* Grafana is configured to pull this data from the Prometheus data source and to show the visualization. Alerts can also be created using this data. So, when a condition is met, notification can be sent over multiple channels e.g. Slack/Email etc.

  <img width="1185" alt="Screenshot 2023-09-24 at 5 07 13 PM" src="https://github.com/HimanshubhusanRath/springboot-prometheus-graphana/assets/40859584/8d4bf807-3574-434c-80e3-4ffe3a252759">

* Check the video for details : https://www.youtube.com/watch?v=h4Sl21AKiDg


# Components:
* Spring Boot Application : 
  * Add the micrometer's prometheus dependency
  * Exposes metrics for Prometheus
    ```
    management.endpoints.web.exposure.include=*
    management.metrics.export.prometheus.enabled=true
    ```
  * Below custom metrics are generated from this application (refer ```CustomMetricsExporter```).
    * order-count
    * active-user-count
* Prometheus:
  * Start Prometheus (using Docker) using the ```prometheus.yml``` file. 
  * In this file, below job is configured to pull the data from the ```/actuator/prometheus``` metrics path of the SpringBoot application.
    ```
      - job_name: 'spring-actuator'
        metrics_path: '/actuator/prometheus'
        scrape_interval: 5s
        static_configs:
          - targets: ['springboot-grafana:8091']
    ```
* Grafana:
  * Configure Prometheus as a Data source in Grafana.


## Steps to set up the project:
* ### Package the Spring boot app as Docker image
    ```
    mvn clean package
    docker build -t himanshubhusan88/springboot-grafana:1.0
    ```
* #### Run the spring boot app, prometheus and grafana as containers
  ```
  # Create a network
  docker network create local-test-net
  
  # Run prometheus under the same network
  docker run --name prometheus --network local-test-net -p 9090:9090 -v prometheus.yml prom/prometheus
  
  # Run Grafana under the same network
  docker run --name grafana --network local-test-net -p 3000:3000 grafana/grafana
  
  # Run Spring-boot app under the same network
  docker run --name springboot-grafana --network local-test-net -p 8091:8091 himanshubhusan88/springboot-grafana:1.0
  ```
* #### Execute the below APIs to generate few custom metrics in the Springboot app.
  ```
  curl -X POST "http://localhost:8091/place-order?type=online" (10-15 times)
  curl -X POST "http://localhost:8091/place-order?type=offline" (6-8 times)
  curl -X POST "http://localhost:8091/active-users?count=100"
  curl -X POST "http://localhost:8091/active-users?count=200"
  curl -X POST "http://localhost:8091/active-users?count=300"
  ```
* ## Metrics Propagation:
* ### Spring Boot App
  * Open ```http://localhost:8091/actuator/prometheus```. We should be able to see list of metrics (with value) exposed to Prometheus.
  * Search for ```order_count``` and ```active_user_count``` metrics. We should be able to find these metrics in the result.
  * _NOTE: 
    * A hypen(-) in the metric's name is translated to underscore (__) by Spring. So, ```order-count``` becomes ```order_count```. 
* ### Prometheus
  * Login to Prometheus ```http://localhost:9090```
  * Search for ```order_count``` and ```active_user_count``` metrics in the search bar.
  * These metrics should be available in the search bar and upon selection we should be able to see the metrics data.
* ### Grafana
  * Login to Grafana ```http://localhost:3000``` using credentials (admin/admin)
  * Go to ```Data Source -> Prometheus -> Add```
  * Put ```http://prometheus:9090``` as the prometheus server url and save the data source.
  * Go to ```Dashboards -> Create Dashboard -> Visualizations```
  * Select the saved prometheus datasource.
  * Under the ```metric``` selection, choose any of the above two metrics and then select ```Run Queries```.
  * We should be able to see the metrics data in the dashboard.