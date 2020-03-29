ISSUE_DIR="-Dissue-dir=${HOME}/cossmass/issues/002-2020-05-may/"
BASE_DIR="-Dbase-dir=${HOME}/repos-kemitix/"
RUN_PARAMS=${ISSUE_DIR} ${BASE_DIR}

install:
	mvn install

test:
	mvn test ${RUN_PARAMS}

dev:
	mvn -pl q-runner quarkus:dev ${RUN_PARAMS}

native: install
	mvn -pl q-runner -Pnative package

run-native:
	q-runner/target/clover-runner-DEV-SNAPSHOT-runner ${RUN_PARAMS}

run: install
	java -jar q-runner/target/clover-runner-DEV-SNAPSHOT-runner.jar ${RUN_PARAMS}

clean:
	mvn clean

