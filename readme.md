[![Build Status](https://www.travis-ci.org/zhenik/order-placement-system.svg?branch=master)](https://www.travis-ci.org/zhenik/order-placement-system)

# Order placement system - tech task
## Design[diagram]
// todo
## How to run
| cmd                   | description                       | 
| -------------         |:-------------:                    |
| make                  | build project                     |
| make build            | build project & docker images     |
| make e2e-tests        | run end to end tests with docker  |
| make run              | run application with docker       |
| make down             | down application with docker      |


## Todo
- [x] test:
  - [x] restAssured
  - [x] wireMock
  - [x] coverage 
  - [x] e2e 'testcontainers'
- [x] docker
- [x] CI
- [ ] CD
- [x] automate with Makefile
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

