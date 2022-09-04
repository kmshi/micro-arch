## A complete scalable and resilient microservice architecure demo

The latest trend is trying to decrease the SDK dependencies of Spring cloud as more as possible, and utilize the non-intrusive services(functions) provided by kubernetes and istio(service mesh). so in this demo, we have the following design considerations:

- ##### Use kubernete service instead of spring cloud discovery and load balancing service

- ##### Use kubernete/istio ingress instead of spring cloud gateway service

- ##### Use kubernete/istio configmap/secret instead of spring cloud config service

- ##### Still keep circuitbreaker,retry from spring cloud resilience4j as fall-fast logic is business related



Istio provides non-intrusive traffic management, security, and observability. In this demo, we use helm to show you some how-tos:

- ##### Distributed tracing

- ##### EFK stack for centralized log analysis

- ##### Dynamic routing

- ##### Centralized monitoring and alarms

- ##### Perform zero-downtime upgrades by canary tests & blue/green deployment


Also, security is the core of any system, in this demo, I will show you 3 options:

- ##### Integrate 3rd OAuth2 service (eg, auth0)

- ##### Home-grown OAuth2 authorization server (spring-cloud/authorization-server)

- ##### RBAC(Role based access control) service with JWT support (microservices/user-service)



We have different architectures for local env and dev/test/prod envs:

- ##### local env, use docker compose to manage images/containers of core services

![local env](https://github.com/kmshi/micro-arch/blob/master/doc/local_env.png?raw=true)

- ##### dev/test/prod envs, use helm to manage services in kubernete/istio

![dev_prod_env](https://github.com/kmshi/micro-arch/blob/master/doc/dev_prod_env.png?raw=true)



#### The services in the above charts can be divided into 3 categories：

1. ##### Core business services (blue)

   In this demo, product-composite, product, review, recommendation are our  sample business services,  to improve the overall performance, we adopt non-blocking synchronous reactive programming and distributed message -driven asynchronous model.

   They are all spring boot based projects, the spring boot version is 2.7.0, spring cloud version is 2021.0.3,  resilience4j version is 1.7.1, Java OpenJDK version is 17.0.4

   

2. ##### Infrastructure services(yellow)

   The mongodb(NoSQL), mariadb(DB), rabbit/kafka(MQ), redis(Cache), elastic search(EFK) are all infrastructure services,  we can set up them by ourselves, however, most of cloud providers provide cost-effective and high available corresponding services -- cloud native services, they are good choices for us too, especially in prod env.

   

3. ##### Management services(green)

   Kiali, jaeger, prometheus, and grafana are all management service which help us monitor and maintain our micro services.