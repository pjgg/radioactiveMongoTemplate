#!/usr/bin/env bash
#abort on errors
set -e
ACCEPTANCE_TEST_HOME="${ACCEPTANCE_TEST_HOME:-$INGEST_HOME}"
docker-compose -f ${ACCEPTANCE_TEST_HOME}/docker-compose.yml up -d

