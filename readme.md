# Monitoring Spring Boot Application using Micrometer, Prometheus and Grafana
* Micrometer provides a simple facade to integrate actuator metrics with external monitoring systems.
* Prometheus is configured to pull the metrics data from the actuators in the Sprint Boot application, and then it stores these data in a TimeSeries database.
* Grafana is configured to pull this data from the Prometheus data source and to show the visualization. Alerts can also be created using this data. So, when a condition is met, notification can be sent over multiple channels e.g. Slack/Email etc.

  <img width="1185" alt="Screenshot 2023-09-24 at 5 07 13 PM" src="https://github.com/HimanshubhusanRath/springboot-prometheus-graphana/assets/40859584/8d4bf807-3574-434c-80e3-4ffe3a252759">


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
* Check the video for details : https://www.youtube.com/watch?v=h4Sl21AKiDg
  

