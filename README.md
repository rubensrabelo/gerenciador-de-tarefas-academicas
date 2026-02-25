# Gerenciador de Tarefas Acadêmicas

Projeto de microsserviço para gerenciar atividades, prazos de entrega e notificações, desenvolvido para estudar arquitetura distribuída, mensageria e boas práticas em microsserviços.

## Tecnologias e Conceitos

O projeto utiliza diversas tecnologias modernas de microsserviços:

- **Microsserviços**: Serviço de Tarefas, Eureka Server, API Gateway, Notificações, Task Overdue Checker  
- **Containerização**: Docker e Docker Compose  
- **Comunicação entre serviços**: OpenFeign  
- **Resiliência**: Resilience4J para tolerância a falhas  
- **Mensageria**: RabbitMQ para atualização assíncrona de status  
- **Monitoramento e Observabilidade**: Prometheus, Grafana, Loki, Zipkin, Micrometer  

## Front-end

- Desenvolvido com **React** e **TypeScript**  
- Interfaces simples para integração com os microsserviços do back-end  

## Objetivo do Projeto

O projeto serve como base prática para aprofundar conhecimentos em:

- Arquitetura de microsserviços  
- Comunicação e integração entre serviços distribuídos  
- Padrões de resiliência e mensageria  
- Monitoramento e métricas em sistemas distribuídos  
- Boas práticas em desenvolvimento de microsserviços  

## Estrutura do Projeto

```

├── eurekaserver/          # Serviço de registro de microsserviços (Eureka Server)
├── gateway/               # API Gateway para roteamento de requests
├── loki/                  # Configuração de logs centralizados (Grafana Loki)
├── notification/          # Microsserviço de notificações
├── prometheus/            # Monitoramento e métricas (Prometheus, Micrometer)
├── task-overdue-checker/  # Verificação de tarefas atrasadas
└── task/                  # Microsserviço principal de tarefas

````
