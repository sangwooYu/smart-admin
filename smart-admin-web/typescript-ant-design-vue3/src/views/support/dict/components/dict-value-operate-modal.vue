<!--
  * 字典 value 弹窗
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-06-08 21:50:41 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" :title="form.dictValueId ? '편집' : '추가'" ok-text="확인" cancel-text="취소" @ok="onSubmit" @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
      <a-form-item label="코드" name="valueCode">
        <a-input v-model:value="form.valueCode" placeholder="코드를 입력하세요" />
      </a-form-item>
      <a-form-item label="이름" name="valueName">
        <a-input v-model:value="form.valueName" placeholder="이름을 입력하세요" />
      </a-form-item>
      <a-form-item label="정렬 기준" name="sort">
        <a-input-number v-model:value="form.sort" :min="0" :max="1000" />
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
    dictValueId: undefined,
    dictKeyId: undefined,
    sort: 1,
    valueCode: '',
    valueName: '',
    remark: '',
  };
  let form = reactive({ ...formDefault });
  const rules = {
    valueCode: [{ required: true, message: '코드를 입력하세요.' }],
    valueName: [{ required: true, message: '이름을 입력하세요.' }],
    sort: [{ required: true, message: '정렬을 입력하세요.' }],
  };
  // 是否展示
  const visible = ref(false);

  function showModal(rowData, dictKeyId) {
    Object.assign(form, formDefault);
    if (rowData) {
      Object.assign(form, rowData);
    }
    form.dictKeyId = dictKeyId;
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
          if (form.dictValueId) {
            await dictApi.valueEdit(form);
          } else {
            await dictApi.valueAdd(form);
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
        console.log('error', error);
        message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
      });
  }

  defineExpose({
    showModal,
  });
</script>
