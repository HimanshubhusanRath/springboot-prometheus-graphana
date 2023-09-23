# Monitoring Spring Boot Application using Micrometer, Prometheus and Grafana
* Micrometer provides a simple facade to integrate actuator metrics with external monitoring systems.
* Prometheus is configured to pull the metrics data from the actuators in the Sprint Boot application, and then it stores these data in a TimeSeries database.
* Grafana is configured to pull this data from the Prometheus data source and to show the visualization. Alerts can also be created using this data. So, when a condition is met, notification can be sent over multiple channels e.g. Slack/Email etc.

# Configurations:
* Spring Boot Application : 
  * Add the micrometer's prometheus dependency
* Prometheus:
  * Start Prometheus (using Docker) using the 'prometheus.yml' file. A job is configured to pull the data from the '/actuator/prometheus' metrics path of the SpringBoot application.
  * Command : docker run -p 9090:9090 -v prometheus.yml prom/prometheus
  * UI : http://localhost:9090
* Grafana:
  * Configure this Prometheus as a Data source.
  * Command :  docker run -d --name=grafana -p 3000:3000 grafana/grafana
  * UI : http://localhost:3000
    * Credentials: admin / admin


#### NOTE:
* Both Prometheus and Grafana is containerized (docker). So, they acccess the spring boot application (runs on the host system) using the host's IP.

