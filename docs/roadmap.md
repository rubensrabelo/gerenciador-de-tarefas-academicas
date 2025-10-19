# Roadmap

## MVP (03/10 a 13/10)
> Objetivo: criar um MVP funcional para validar a arquitetura de microsserviços.

- [x] Criar Microsserviço de Tarefas
- [x] Criar telas referentes ao MS Tarefas
- [x] Criar Eureka Server
- [x] Criar API Gateway
- [x] Construção de cada Dockerfile e docker-compose dos microsserviços

---

## Fase 2 - Infraestrutura e Observabilidade
> Objetivo: garantir que o sistema possa ser monitorado, depurado e analisado facilmente.

- [x] Criar `docker-compose.yml` principal unificando todos os microsserviços
- [x] Configurar rede única (`app-network`) entre serviços
- [ ] Implementar stack de logs centralizados (Loki + Promtail + Grafana)
- [ ] Padronizar formato de logs JSON nos microsserviços (via Logback)
- [ ] Adicionar métricas de aplicação com Spring Boot Actuator
- [ ] (Opcional) Configurar Prometheus para coletar métricas do Actuator
- [ ] Criar dashboard no Grafana para logs e métricas básicas

---

## Fase 3 - Resiliência e Comunicação
> Objetivo: tornar os microsserviços tolerantes a falhas e melhorar a comunicação entre eles.

- [ ] Implementar comunicação via OpenFeign entre microsserviços
- [ ] Adicionar Resilience4j (Circuit Breaker, Retry e Timeout)
- [ ] Configurar fallback methods em chamadas externas
- [ ] Implementar filas assíncronas com RabbitMQ ou Kafka (opcional)
- [ ] Testar cenários de falha e reconexão (ex: serviço fora do ar)

---

## Fase 4 - Segurança e Autenticação
> Objetivo: proteger o ecossistema e controlar o acesso de usuários e serviços.

- [ ] Implementar autenticação via JWT
- [ ] Centralizar autenticação no API Gateway
- [ ] Criar microsserviço de Usuários (cadastro, login, perfis)
- [ ] Adicionar autorização baseada em papéis (role-based access)
- [ ] Garantir segurança nas comunicações entre microsserviços

---

## Fase 5 - Deploy e Escalabilidade
> Objetivo: preparar o sistema para execução em ambiente de produção.

- [ ] Configurar variáveis de ambiente e secrets no Docker
- [ ] Adicionar health checks e readiness checks
- [ ] Preparar deploy no Docker Swarm ou Kubernetes
- [ ] Adicionar versionamento de imagens Docker
- [ ] Documentar endpoints e fluxos no Swagger/OpenAPI

---

## Fase 6 - Melhoria Contínua
> Objetivo: evoluir continuamente com base em feedback e métricas.

- [ ] Analisar métricas de logs e desempenho
- [ ] Corrigir gargalos identificados
- [ ] Refatorar serviços com base em boas práticas DDD
- [ ] Adicionar testes automatizados (unitários e integração)
- [ ] Expandir funcionalidades conforme necessidade do usuário
