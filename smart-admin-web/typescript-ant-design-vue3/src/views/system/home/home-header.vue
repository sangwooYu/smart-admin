<!--
  * 首页 用户头部信息
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-09-12 22:34:00
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
  *
-->
<template>
  <div class="user-header">
    <a-page-header :title="welcomeSentence" :sub-title="departmentName" >
<!--      <template #tags>-->
<!--        <a-tag color="blue">테스트</a-tag>-->
<!--        <a-tag color="success">테스트</a-tag>-->
<!--        <a-tag color="error">테스트</a-tag>-->
<!--      </template>-->
      <template #extra>
        <p>{{ dayInfo }}</p>
      </template>
      <a-row class="content">
        <span class="heart-sentence">
          <h3>{{ heartSentence }}</h3>
<!--          <p class="last-login-info">{{ lastLoginInfo }}</p>-->
          <div></div>
        </span>
<!--        <div class="weather">-->
<!--          <iframe-->
<!--            width="100%"-->
<!--            scrolling="no"-->
<!--            height="60"-->
<!--            frameborder="0"-->
<!--            allowtransparency="true"-->
<!--            src="//i.tianqi.com/index.php?c=code&id=12&icon=1&num=5&site=12"-->
<!--          ></iframe>-->
<!--        </div>-->
      </a-row>
    </a-page-header>
  </div>
</template>
<script setup lang="ts">
  import { computed } from 'vue';
  import { useUserStore } from '/@/store/modules/system/user';
  import _ from 'lodash';

  const userStore = useUserStore();

  const departmentName = computed(() => useUserStore.departmentName);

  // 환영 메시지
  const welcomeSentence = computed(() => {
    let sentence = '';
    let now = new Date().getHours();
      sentence = 'hello.';
    return sentence + userStore.$state.actualName;
  });

  //마지막 로그인 정보
  // const lastLoginInfo = computed(() => {
  //   let info = '';
  //   if (userStore.$state.lastLoginTime) {
  //     info = info + '마지막 로그인:' + userStore.$state.lastLoginTime;
  //   }
  //   if (userStore.$state.lastLoginIp) {
  //     info = info + '; IP:' + userStore.$state.lastLoginIp;
  //   }
  //   if (userStore.$state.lastLoginUserAgent) {
  //     let ua = uaparser(userStore.$state.lastLoginUserAgent);
  //     info = info + '; browser:';
  //     if (ua.browser.name) {
  //       info = info + ' ' + ua.browser.name;
  //     }
  //     if (ua.os.name) {
  //       info = info + ' ' + ua.os.name;
  //     }
  //     let device = ua.device.vendor ? ua.device.vendor + ua.device.model : null;
  //     if (device) {
  //       info = info + ' ' + device;
  //     }
  //   }
  //   return info;
  // });

  // 毒鸡汤
  const heartSentenceArray = [
    'Admin 관련 문의 : sw.yu@iskra.world ',
  ];
  const heartSentence = computed(() => {
    return heartSentenceArray[_.random(0, heartSentenceArray.length - 1)];
  });
</script>
<style scoped lang="less">
  .user-header {
    width: 100%;
    background-color: #fff;
    margin-bottom: 10px;

    .heart-sentence {
      width: calc(100% -660px);
      h3 {
        color: rgba(0, 0, 0, 0.75);
      }
    }

    .content {
      display: flex;
      justify-content: space-between;
      .weather {
        width: 650px;
      }
    }

    .last-login-info {
      font-size: 13px;
      color: rgba(0, 0, 0, 0.45);
      overflow-wrap: break-word;
    }
  }
</style>
