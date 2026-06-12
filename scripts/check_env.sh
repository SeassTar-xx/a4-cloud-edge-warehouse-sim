#!/usr/bin/env sh
set -eu

echo "== path =="
pwd

echo "== system =="
uname -a
if command -v sw_vers >/dev/null 2>&1; then
  sw_vers
fi

check_tool() {
  name="$1"
  version_cmd="$2"
  if command -v "$name" >/dev/null 2>&1; then
    command -v "$name"
    sh -c "$version_cmd" || true
  else
    echo "MISSING: $name"
  fi
}

echo "== tools =="
check_tool git "git --version"
check_tool gh "gh --version"
check_tool python3 "python3 --version"
check_tool node "node --version"
check_tool npm "npm --version"
check_tool java "java -version"
check_tool mvn "mvn -version"
check_tool docker "docker --version"
check_tool mysql "mysql --version"
check_tool redis-server "redis-server --version"

