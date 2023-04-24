<!--
  * 企业 发票 表单
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-15 20:15:49
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" :title="form.invoiceId ? '편집' : '추가'" ok-text="확인" cancel-text="취소" @ok="onSubmit" @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
      <a-form-item label="송장 수취인" name="invoiceHeads">
        <a-input v-model:value="form.invoiceHeads" placeholder="지불할 청구서를 다음 주소로 입력하세요." />
      </a-form-item>
      <a-form-item label="납세자 식별 번호" name="taxpayerIdentificationNumber">
        <a-input v-model:value="form.taxpayerIdentificationNumber" placeholder="납세자 식별 번호를 입력하세요." />
      </a-form-item>
      <a-form-item label="은행 계좌 번호" name="accountNumber">
        <a-input v-model:value="form.accountNumber" placeholder="은행 계좌 번호를 입력하세요." />
      </a-form-item>
      <a-form-item label="계좌 개설 은행" name="bankName">
        <a-input v-model:value="form.bankName" placeholder="계좌 개설 은행을 입력하세요." />
      </a-form-item>
      <a-form-item label="상태 활성화" name="disabledFlag">
        <a-switch v-model:checked="enabledChecked" @change="enabledCheckedChange" />
      </a-form-item>
      <a-form-item label="비고" name="remark">
        <a-textarea v-model:value="form.remark" :rows="2" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import { ref, reactive } from 'vue';
  import { message } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { invoiceApi } from '/@/api/business/oa/invoice-api';
import { smartSentry } from '/@/lib/smart-sentry';

  const props = defineProps({
    enterpriseId: {
      type: Number,
      default: null,
    },
  });
  // emit
  const emit = defineEmits(['reloadList']);

  // --------------------- modal 显示与隐藏 ---------------------
  // 표시 또는 표시 안 함
  const visible = ref(false);
  const enabledChecked = ref(true);

  function enabledCheckedChange(checked) {
    form.disabledFlag = !checked;
  }

  function showModal(rowData) {
    Object.assign(form, formDefault);
    if (rowData) {
      Object.assign(form, rowData);
      enabledChecked.value = !rowData.disabledFlag;
    }
    form.enterpriseId = props.enterpriseId;
    visible.value = true;
  }

  function onClose() {
    formRef.value.resetFields();
    Object.assign(form, formDefault);
    visible.value = false;
  }

  // --------------------- 表单 ---------------------

  //  组件
  const formRef = ref();

  const formDefault = {
    invoiceId: undefined,
    enterpriseId: undefined,
    bankName: '',
    accountNumber: '',
    invoiceHeads: '',
    taxpayerIdentificationNumber: '',
    disabledFlag: false,
    remark: '',
  };
  let form = reactive({ ...formDefault });
  const rules = {
    invoiceHeads: [{ required: true, message: '지불할 청구서를 다음 주소로 입력하세요.' }],
    taxpayerIdentificationNumber: [{ required: true, message: '납세자 식별 번호를 입력하세요.' }],
    accountNumber: [{ required: true, message: '은행 계좌 번호를 입력하세요.' }],
    bankName: [{ required: true, message: '계좌 은행을 입력하세요.' }],
  };

  function onSubmit() {
    formRef.value
      .validate()
      .then(async () => {
        SmartLoading.show();
        try {
          if (form.invoiceId) {
            await invoiceApi.update(form);
          } else {
            await invoiceApi.create(form);
          }
          message.success(`${form.invoiceId ? '수정 사항' : '추가'}성공`);
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
