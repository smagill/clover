ISSUE_DIR="-Dissue-dir=${HOME}/cossmass/issues/002-2020-05-may/"
BASE_DIR="-Dbase-dir=${HOME}/repos-kemitix/"
FONT_FILE="-Dfont-file=${HOME}/cossmass/binder/fonts/Snowslider/SnowSL_Std.OTF"
RUN_PARAMS=${ISSUE_DIR} ${BASE_DIR} ${FONT_FILE}

install:
	mvn install

test:
	mvn test ${RUN_PARAMS}

dev:
	mvn -pl runner quarkus:dev ${RUN_PARAMS}

native: install
	mvn -pl runner -Pnative package

run-native:
	runner/target/clover-runner-DEV-SNAPSHOT-runner ${RUN_PARAMS}

run: install
	java -jar runner/target/clover-runner-DEV-SNAPSHOT-runner.jar ${RUN_PARAMS}

clean:
	mvn clean

