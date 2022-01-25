#!/usr/bin/env bash
# 엔진엑스가 바라보는 스프링 부트를 최신 버전으로 변경하는 스크립트(즉 최신 버전으로 배포를 끝낸 포트로 바꿈)

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    # 하나의 문장을 만들어 파이프라인(|)로 넘겨주기 위해 echo를 사용하고 이렇게 넘겨받은 문장을 service-url.inc에 덮어씌움
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> 엔진엑스 Reload"
    sudo service nginx reload # 엔진엑스 설정을 다시 불러옴 (restart와 다르게 끊김없이 바로 불러옴. 그러나 중요한 설정들은 수정후 반영되지 않으므로 그건 restart 해야됨)
}
