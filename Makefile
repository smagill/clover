clean:
	mvn clean

install:
	mvn install

dev:
	mvn -pl q-runner quarkus:dev

native: install
	mvn -pl q-runner -Pnative package

run-native:
	q-runner/target/clover-runner-DEV-SNAPSHOT-runner

run-jar: install
	java -jar q-runner/target/clover-runner-DEV-SNAPSHOT-runner.jar
