<!--
  * 系统设置表单
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-06-08 21:50:41 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" :title="form.configId ? '편집' : '추가'" ok-text="OK" cancel-text="취소" @ok="onSubmit" @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }">
      <a-form-item label="매개변수 키" name="configKey">
        <a-input v-model:value="form.configKey" placeholder="请输入参数Key" />
      </a-form-item>
      <a-form-item label="매개변수 이름" name="configName">
        <a-input v-model:value="form.configName" placeholder="请输入参数名称" />
      </a-form-item>
      <a-form-item label="매개변수 값" name="configValue">
        <a-input v-model:value="form.configValue" placeholder="请输入参数值" />
      </a-form-item>
      <a-form-item label="비고" name="remark">
        <textarea v-model="form.remark" style="width: 100%; height: 100px; outline: none"></textarea>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import { reactive, ref } from 'vue';
  import { configApi } from '/@/api/support/config/config-api';
  import { smartSentry } from '/@/lib/smart-sentry';
  import { SmartLoading } from '/@/components/framework/smart-loading';

  // emit
  const emit = defineEmits('reloadList');

  //  组件
  const formRef = ref();

  const formDefault = {
    configId: undefined,
    configKey: '',
    configName: '',
    configValue: '',
    remark: '',
  };
  let form = reactive({ ...formDefault });
  const rules = {
    configKey: [{ required: true, message: '파라미터 키를 입력하세요.' }],
    configName: [{ required: true, message: '매개변수 이름을 입력하세요.' }],
    configValue: [{ required: true, message: '파라미터 값을 입력하세요.' }],
  };
  // 是否展示
  const visible = ref(false);

  function showModal(rowData) {
    Object.assign(form, formDefault);
    if (rowData) {
      Object.assign(form, rowData);
    }
    visible.value = true;
  }

  function onClose() {
    Object.assign(form, formDefault);
    visible.value = false;
  }

  function onSubmit() {
    formRef.value
      .validate()
      .then(async () => {
        SmartLoading.show();
        try {
          if (form.configId) {
            await configApi.updateConfig(form);
          } else {
            await configApi.addConfig(form);
          }
          message.success(`${form.configId ? '수정' : '추가'}성공`);
          emit('reloadList');
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

  defineExpose({
    showModal,
  });
</script>
