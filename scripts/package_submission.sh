#!/usr/bin/env sh
set -eu

ROOT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)"
cd "$ROOT_DIR"

mkdir -p submission
OUT="submission/a4-cloud-edge-warehouse-sim-source.zip"

if command -v zip >/dev/null 2>&1; then
  zip -r "$OUT" . \
    -x '*/.git/*' '*/node_modules/*' '*/target/*' '*/dist/*' '*/build/*' \
    -x '*/videos/raw/*' '*/media/raw/*' '*.pem' '*.key' '*.p12' '*.jks' '.env' '.env.*' \
    -x 'submission/*.zip'
elif command -v ditto >/dev/null 2>&1; then
  echo "zip 不存在，macOS 可使用 ditto；请先人工确认排除规则后执行打包。"
  exit 1
else
  echo "缺少 zip/ditto，无法打包。"
  exit 1
fi

echo "已生成 $OUT；不会自动提交到比赛平台。"

