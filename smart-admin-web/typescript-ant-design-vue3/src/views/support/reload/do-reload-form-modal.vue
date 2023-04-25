<!--
  * reload 表单
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" title="다시 로드 실행" ok-text="OK" cancel-text="취소" @ok="onSubmit" @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }">
      <a-form-item label="태그">
        <a-input v-model:value="form.tag" :disabled="true" />
      </a-form-item>
      <a-form-item label="운영 식별" name="identification">
        <a-input v-model:value="form.identification" placeholder="러닝 로고를 입력하세요." />
      </a-form-item>
      <a-form-item label="매개변수" name="args">
        <a-input v-model:value="form.args" placeholder="매개변수를 입력하세요." />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import { reactive, ref } from 'vue';
  import { reloadApi } from '/@/api/support/reload/reload-api';
import { smartSentry } from '/@/lib/smart-sentry';
  import { SmartLoading } from '/@/components/framework/smart-loading';

  // emit
  const emit = defineEmits(['refresh']);
  defineExpose({
    showModal,
  });

  // ----------------------- 表单 隐藏 与 显示 ------------------------
  // 是否展示
  const visible = ref(false);
  function showModal(tag) {
    form.tag = tag;
    form.identification = '';
    form.args = '';
    visible.value = true;
  }

  function onClose() {
    Object.assign(form, formDefault);
    visible.value = false;
  }

  //  组件
  const formRef = ref();

  const formDefault = {
    tag: '',
    identification: '',
    args: '',
  };
  let form = reactive({ ...formDefault });
  const rules = {
    identification: [{ required: true, message: '러닝 로고를 입력하세요.' }],
    args: [{ required: true, message: '파라미터 값을 입력하세요.' }],
  };

  // ----------------------- 提交 ------------------------

  function onSubmit() {
    formRef.value
      .validate()
      .then(async () => {
        SmartLoading.show();
        try {
          await reloadApi.reload(form);
          message.success('reload성공');
          emit('refresh');
          onClose();
        } catch (error) {
          smartSentry.captureError(error);
        } finally {
          SmartLoading.hide();
        }
      })
      .catch((error) => {
        console.log('error', error);
        message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
      });
  }
</script>
