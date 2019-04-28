.PHONY: all
all: build

MAVEN := './mvnw'
VERSION := '1.0-SNAPSHOT'

.PHONY: build
build: build-source build-docker

.PHONY: build-source
build-source:
	${MAVEN} clean install

.PHONY: build-source-no-tests
build-source-no-tests:
	${MAVEN} clean install -DskipTests

.PHONY: e2e-tests
e2e-tests:
	${MAVEN} clean install -pl e2e -DskipIntegrationTests=false

.PHONY: build-docker
build-docker:
	docker-compose build

.PHONY: run-docker
run-docker:
	docker-compose up -d

.PHONY: run
run: build-source-no-tests build-docker run-docker

.PHONY: down
down:
	docker-compose down
