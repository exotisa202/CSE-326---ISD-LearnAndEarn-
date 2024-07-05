#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
sh ./mvnw clean
exec sh ./run.sh "$@"
