[![Build Status](https://www.travis-ci.org/zhenik/order-placement-system.svg?branch=master)](https://www.travis-ci.org/zhenik/order-placement-system)

# Order placement system - tech task
## Design[diagram]
// todo
## Build
build: `./mvnw clean install -DskipIntegrationTests=false`  

only IT: `./mvnw clean install -pl e2e -DskipIntegrationTests=false`
## Todo
- [x] test:
  - [x] restAssured
  - [x] wireMock
  - [x] coverage 
  - [x] e2e 'testcontainers'
- [x] docker
- [x] CI
- [ ] CD
- [ ] automate with Makefile
- [x] swagger doc
- [ ] tracing
- [ ] monitoring

## Design improvements
### Security
Requirements:
- cors policies, https, certificates
- encryption, hashing, salt: bcrypt, PBKDF2
- vault: HashiCorp vault

### Scaling
Requirements for horizontal scaling:
- healthchecks for services (spring default)
- service discovery (consul, eureka)
- gateway/load balancer (client, server types)

