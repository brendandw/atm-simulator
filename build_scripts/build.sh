#!/usr/bin/env bash

# This script needs to be run from the root directory (atm-cash-dispenser)

# Stop on failure
set -e

docker run --rm \
-v "$(pwd)":/usr/src/mymaven \
-w /usr/src/mymaven maven:3.6.3-amazoncorretto-11 mvn clean package -U -B  \
-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

./build_scripts/fix_permissions.sh
