ISSUE_DIR="-Dissue-dir=${HOME}/cossmass/issues/002-2020-05-may/"

clean:
	mvn clean

install:
	mvn install

dev:
	mvn -pl q-runner quarkus:dev ${ISSUE_DIR}

native: install
	mvn -pl q-runner -Pnative package

run-native:
	q-runner/target/clover-runner-DEV-SNAPSHOT-runner ${ISSUE_DIR}

run-jar: install
	java -jar q-runner/target/clover-runner-DEV-SNAPSHOT-runner.jar ${ISSUE_DIR}
