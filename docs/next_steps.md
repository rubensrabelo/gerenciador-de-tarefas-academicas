# Próximos Passos

## Backend
### Meta - 
- [ ] Observabilidade/logs
    - [x] Consolidar sua estrutura de pastas
    - [x] Configurar o docker-compose.yml principal
    - [x] Configurar os arquivos do Loki e Promtail
    - [x] Ajustar os logs dos microsserviços
    - [x] Subir tudo
    - [x] Visualizar os logs no Grafana
    - [x] (Opcional) Manter os docker-compose individuais
- [ ] Métricas e health checks (Actuator + Prometheus)
    - [x] Dependências (adicionar ao pom.xml do service)
    - [x] application.yml do serviço (task-service)
    - [X] Dockerfile do serviço (expor a porta do Actuator)
    - [x] Docker Compose — configurar hostname e mapear só a porta do Actuator
    - [x] prometheus.yml (configurar scrape do Actuator)
    - [x] Build e subir os containers
    - [x] Testes / verificações
- [ ] Métricas/tracing
- [ ] Resiliência
- [ ] Segurança
- [ ] Automação

---

## Frontend
### Meta

---

## Testes
### Meta


---

## Estudos
### Meta
- [ ] Template Method
- [ ] 9 Tips for Containerizing Your Spring Boot Code
- [ ] Ler sobre enum em TS
- [ ] Ler sobre microsserviços