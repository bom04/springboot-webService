#!/usr/bin/env bash
# 나머지 4개 스크립트 파일에서 공용으로 사용할 profile과 포트를 체크하는 스크립트

# 쉬고 있는 profile 찾기: real1이 사용중이면 real2가 쉬고 있고, 반대면 real1이 쉬고 있음
function find_idle_profile()
{
    # 현재 엔진엑스가 바라고보깅ㅆ는 스프링부트가 정상적으로 수행중인지 확인해서 응답값을 HttpStatus로 받음
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 (400,500대 오류 발생한다면)
    then
        CURRENT_PROFILE=real2 # real2를 현재 profile로 사용하겠다
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2 # 엔진엑스에 연결되어있지 않은 profile을 저장
    else
      IDLE_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile) # bash 스크립트는 값을 반환하는 기능이 업으므로 마지막 줄에 echo로 결과 출력해서 리턴값처럼 사용함

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}
