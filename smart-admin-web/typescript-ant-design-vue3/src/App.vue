<!--
  * 主应用页面
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-12 23:46:47 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->

<template>
  <a-config-provider :locale="antdLocale">
    <!---全局loading--->
    <a-spin :spinning="spinning" tip="잠시만요, 필사적으로 로드중입니다.." size="large">
      <!--- 路由 -->
      <RouterView />
    </a-spin>
  </a-config-provider>
</template>

<script setup lang="ts">
  import dayjs from 'dayjs';
  import { computed, ComputedRef } from 'vue';
  import { messages } from '/@/i18n/index';
  import { useAppConfigStore } from '/@/store/modules/system/app-config';
  import { useSpinStore } from './store/modules/system/spin';
  import { LanguageType } from '/@/types/config';

  let language: LanguageType = useAppConfigStore().language;
  let lang: string = language.toString();

  const antdLocale: ComputedRef<string> = computed(() => messages[lang].antdLocale);
  const dayjsLocale = computed(() => messages[lang].dayjsLocale);
  dayjs.locale(dayjsLocale.value);

  let spinStore = useSpinStore();
  const spinning = computed(() => spinStore.loading);
</script>
