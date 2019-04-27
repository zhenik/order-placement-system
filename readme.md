[![Build Status](https://www.travis-ci.org/zhenik/order-placement-system.svg?branch=master)](https://www.travis-ci.org/zhenik/order-placement-system)

# Order placement system - tech task
## Design[diagram]
// todo
## Todo
- [ ] test:
  - [x] restAssured
  - [x] wireMock
  - [ ] coverage 
  - [ ] e2e 'testcontainers'
- [ ] docker
- [ ] CD/CI
- [ ] automate with Makefile
- [ ] swagger doc
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

