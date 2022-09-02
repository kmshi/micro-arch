## A complete scalable and resilient microservice architecure demo

The latest trend is trying to decrease the SDK dependencies of Spring cloud as more as possible, and utilize the non-intrusive services(functions) provided by kubernetes and istio(service mesh). so in this demo, we have the following design considerations:

- ##### Use kubernete service instead of spring cloud discovery and load balancing service

- ##### Use kubernete/istio ingress instead of spring cloud gateway service

- ##### Use kubernete/istio configmap/secret instead of spring cloud config service

- ##### Still keep circuitbreaker,retry from spring cloud resilience4j



Istio provides non-intrusive traffic management, security, and observability. In this demo, we use helm to show you some how-tos:

- ##### Distributed tracing

- ##### EFK stack for centralized log analysis

- ##### Dynamic routing

- ##### Centralized monitoring and alarms

- ##### Perform zero-downtime upgrades by canary tests & blue/green deployment


Also, security is the core of any system, in this demo, I will show you 3 options:

- ##### Integrate 3rd OAuth2 service

- ##### Home-grown authorization server(OAuth2)

- ##### RBAC(Role based access control) service with JWT support



We have different architectures for local env and dev/test/prod envs:

- ##### local env, use docker compose to manage images/containers of core services

![local env](https://github.com/kmshi/micro-arch/blob/master/doc/local_env.png?raw=true)

- ##### dev/test/prod envs, use helm to manage services in kubernete/istio

![dev_prod_env](https://github.com/kmshi/micro-arch/blob/master/doc/dev_prod_env.png?raw=true)

