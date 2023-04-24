<!--
  * 登录
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-12 22:34:00 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
  *
-->
<template>
  <div class="login-container">
    <div class="box-item desc">
      <div class="welcome">
        <p>클래시몬 admin</p>
        <p class="desc">
          spring 2 + view3 기반
        </p>
      </div>
    </div>
    <div class="box-item login">
      <img class="login-qr" :src="loginQR" />
      <div class="login-title">로그인</div>
      <a-form ref="formRef" class="login-form" :model="loginForm" :rules="rules">
        <a-form-item name="loginName">
          <a-input v-model:value.trim="loginForm.loginName" placeholder="사용자 이름을 입력하세요." />
        </a-form-item>
        <a-form-item name="password">
          <a-input-password
            v-model:value="loginForm.password"
            autocomplete="on"
            :type="showPassword ? 'text' : 'password'"
            placeholder="비밀번호를 입력하세요."
          />
        </a-form-item>
        <a-form-item name="captchaCode">
          <a-input class="captcha-input" v-model:value.trim="loginForm.captchaCode" placeholder="오른쪽 숫자를 입력하세요" />
          <img class="captcha-img" :src="captchaBase64Image" @click="getCaptcha" />
        </a-form-item>
        <a-form-item>
          <a-checkbox v-model:checked="rememberPwd">비밀번호 기억하기</a-checkbox>
        </a-form-item>
        <a-form-item>
          <div class="btn" @click="onLogin">로그인</div>
        </a-form-item>
      </a-form>
      <div class="more">
        <div class="title-box">
          <p class="line"></p>
          <p class="title">SNS 로그인</p>
          <p class="line"></p>
        </div>
        <div class="login-type">
<!--          <img :src="aliLogin" />-->
<!--          <img :src="qqLogin" />-->
          <img :src="googleLogin" />
<!--          <img :src="weiboLogin" />-->
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import { onMounted, onUnmounted, reactive, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { loginApi } from '/@/api/system/login/login-api';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { LOGIN_DEVICE_ENUM } from '/@/constants/system/login-device-const';
  import { useUserStore } from '/@/store/modules/system/user';
  import { saveTokenToCookie } from '/@/utils/cookie-util';

  import gongzhonghao from '/@/assets/images/1024lab/1024lab-gzh.jpg';
  import zhuoda from '/@/assets/images/1024lab/zhuoda-wechat.jpg';
  import loginQR from '/@/assets/images/login/login-qr.png';
  import xiaozhen from '/@/assets/images/1024lab/xiaozhen-gzh.jpg';

  import aliLogin from '/@/assets/images/login/ali-icon.png';
  import googleLogin from '/@/assets/images/login/google-icon.png';
  import qqLogin from '/@/assets/images/login/qq-icon.png';
  import weiboLogin from '/@/assets/images/login/weibo-icon.png';

  import { buildRoutes } from '/@/router/index';
  import { smartSentry } from '/@/lib/smart-sentry';

  //--------------------- 登录表单 ---------------------------------

  const loginForm = reactive({
    loginName: '',
    password: '',
    captchaCode: '',
    captchaUuid: '',
    loginDevice: LOGIN_DEVICE_ENUM.PC.value,
  });
  const rules = {
    loginName: [{ required: true, message: '사용자 이름은 비워 둘 수 없습니다.' }],
    password: [{ required: true, message: '비밀번호는 비워 둘 수 없습니다.' }],
    captchaCode: [{ required: true, message: '캡차는 비워둘 수 없습니다.' }],
  };

  const showPassword = ref(false);
  const router = useRouter();
  const formRef = ref();
  const rememberPwd = ref(false);

  onMounted(() => {
    document.onkeyup = (e) => {
      if (e.keyCode == 13) {
        onLogin();
      }
    };
  });

  onUnmounted(() => {
    document.onkeyup = null;
  });

  //登录
  async function onLogin() {
    formRef.value.validate().then(async () => {
      try {
        SmartLoading.show();
        const res = await loginApi.login(loginForm);
        stopRefrestCaptchaInterval();
        saveTokenToCookie(res.data.token ? res.data.token : '');
        message.success('로그인 성공');
        // 핀니아에 사용자 정보 업데이트
        useUserStore().setUserLoginInfo(res.data);
        //시스템의 라우팅 구축
        buildRoutes();
        router.push('/home');
      } catch (e) {
        if (e.data && e.data.code === 30001) {
          loginForm.captchaCode = '';
          getCaptcha();
        }
        smartSentry.captureError(e);
      } finally {
        SmartLoading.hide();
      }
    });
  }

  //---------------------인증 코드 ---------------------------------

  const captchaBase64Image = ref('');
  async function getCaptcha() {
    try {
      let captchaResult = await loginApi.getCaptcha();
      captchaBase64Image.value = captchaResult.data.captchaBase64Image;
      loginForm.captchaUuid = captchaResult.data.captchaUuid;
      beginRefrestCaptchaInterval(captchaResult.data.expireSeconds);
    } catch (e) {
      console.log(e);
    }
  }

  let refrestCaptchaInterval = null;
  function beginRefrestCaptchaInterval(expireSeconds) {
    if (refrestCaptchaInterval === null) {
      refrestCaptchaInterval = setInterval(getCaptcha, (expireSeconds - 5) * 1000);
    }
  }

  function stopRefrestCaptchaInterval() {
    if (refrestCaptchaInterval != null) {
      clearInterval(refrestCaptchaInterval);
      refrestCaptchaInterval = null;
    }
  }

  onMounted(getCaptcha);
</script>
<style lang="less" scoped>
  @import './login.less';
</style>
