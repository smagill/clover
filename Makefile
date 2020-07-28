VERSION=DEV-SNAPSHOT

#ISSUE_DIR=-Dissue-dir=${HOME}/cossmass/issues/002-2020-05-may
ISSUE_DIR=-Dissue-dir=${HOME}/cossmass/issues/003-2020-09-september
FONT_FILE=-Dfont-file=${HOME}/cossmass/binder/fonts/Snowslider/SnowSL_Std.OTF
RUN_PARAMS=${ISSUE_DIR} ${FONT_FILE}

graphs:
	mvn validate

install: .install

.install:
	mvn install
	touch .install

test:
	mvn test ${RUN_PARAMS}

dev:
	mvn -pl runner quarkus:dev ${RUN_PARAMS}

run: install
	java ${RUN_PARAMS} -jar runner/target/clover-runner-DEV-SNAPSHOT-runner.jar

clean:
	mvn clean
	if [ -f .install ];then rm .install;fi

quick-build:
	mvn clean install -DskipTests -DskipITs

kill-runners:
	ps ax|grep clover-runne[r]|cut -b-5|xargs kill -9

native:
	mvn verify -Pnative -DskipTests -DskipITs -Dpitest.skip

run-native:
	./runner/target/clover-runner-${VERSION}-runner
