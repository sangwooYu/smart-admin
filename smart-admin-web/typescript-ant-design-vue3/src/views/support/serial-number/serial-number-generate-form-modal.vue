<!--
  * 生成
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" title="주문 번호 생성" ok-text="OK" cancel-text="취소" @ok="onSubmit" @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }">
      <a-form-item label="운영">
        <a-input v-model:value="form.businessName" :disabled="true" />
      </a-form-item>
      <a-form-item label="형식">
        <a-input v-model:value="form.format" :disabled="true" />
      </a-form-item>
      <a-form-item label="주기 시간">
        <a-input v-model:value="form.ruleType" :disabled="true" />
      </a-form-item>
      <a-form-item label="마지막으로 생성된 주문 번호">
        <a-input v-model:value="form.lastNumber" :disabled="true" />
      </a-form-item>
      <a-form-item label="발전기 수" name="count">
        <a-input-number v-model:value="form.count" />
      </a-form-item>
      <a-form-item label="결과 생성">
        <a-textarea v-model:value="generateResult" :rows="2" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import { reactive, ref } from 'vue';
  import { serialNumberApi } from '/@/api/support/serial-number/serial-number-api';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import _ from 'lodash';
  import { smartSentry } from '/@/lib/smart-sentry';

  // emit
  const emit = defineEmits(['refresh']);
  defineExpose({
    showModal,
  });

  // ----------------------- 表单 隐藏 与 显示 ------------------------
  // 是否展示
  const visible = ref(false);
  function showModal(data) {
    form.serialNumberId = data.serialNumberId;
    form.businessName = data.businessName;
    form.format = data.format;
    form.ruleType = data.ruleType;
    form.lastNumber = data.lastNumber;
    form.count = 1;
    generateResult.value = '';
    visible.value = true;
  }

  function onClose() {
    visible.value = false;
    emit('refresh');
  }

  // ----------------------- 表单 ------------------------
  const rules = {
    count: [{ required: true, message: '수량을 입력하세요.' }],
  };

  //生成结果
  const generateResult = ref('');

  //  组件
  const formRef = ref();

  const form = reactive({
    serialNumberId: -1,
    businessName: '',
    format: '',
    ruleType: '',
    lastNumber: -1,
    count: 1,
  });

  function onSubmit() {
    formRef.value
      .validate()
      .then(async () => {
        SmartLoading.show();
        try {
          let res = await serialNumberApi.generate(form);
          message.success('성공적으로 생성됨');
          generateResult.value = _.join(res.data, ', ');
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
