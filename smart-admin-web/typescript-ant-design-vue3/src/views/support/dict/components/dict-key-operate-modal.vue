<!--
  * 字典key 弹窗
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-06-08 21:50:41 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" :title="form.dictKeyId ? '편집' : '추가'" ok-text="OK" cancel-text="취소" @ok="onSubmit" @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
      <a-form-item label="코딩" name="keyCode">
        <a-input v-model:value="form.keyCode" placeholder="코드를 입력하세요." />
      </a-form-item>
      <a-form-item label="이름" name="keyName">
        <a-input v-model:value="form.keyName" placeholder="이름을 입력하세요." />
      </a-form-item>

      <a-form-item label="비고" name="remark">
        <textarea v-model="form.remark" style="width: 100%; height: 100px; outline: none"></textarea>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import { ref, reactive } from 'vue';
  import { message } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { dictApi } from '/@/api/support/dict/dict-api';
  import { smartSentry } from '/@/lib/smart-sentry';

  // emit
  const emit = defineEmits(['reloadList']);

  //  组件
  const formRef = ref();

  const formDefault = {
    dictKeyId: undefined,
    keyCode: '',
    keyName: '',
    remark: '',
  };
  let form = reactive({ ...formDefault });
  const rules = {
    keyCode: [{ required: true, message: '코드를 입력하세요.' }],
    keyName: [{ required: true, message: '이름을 입력하세요.' }],
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
          if (form.dictKeyId) {
            await dictApi.keyEdit(form);
          } else {
            await dictApi.keyAdd(form);
          }
          message.success(`${form.dictKeyId ? '수정' : '추가'}성공`);
          emit('reloadList');
          onClose();
        } catch (error) {
          smartSentry.captureError(error);
        } finally {
          SmartLoading.hide();
        }
      })
      .catch((error) => {
        message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
      });
  }

  // ----------------------- 以下是暴露的方法内容 ------------------------
  defineExpose({
    showModal,
  });
</script>
