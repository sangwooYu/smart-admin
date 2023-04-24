<!--
  * 企业 银行 表单
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-15 20:15:49
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" :title="form.bankId ? '편집기' : '추가'" ok-text="확인" cancel-text="취소" @ok="onSubmit"
           @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="계좌 개설 은행" name="bankName">
        <a-input v-model:value="form.bankName" placeholder="은행을 입력하세요."/>
      </a-form-item>
      <a-form-item label="계정 이름" name="accountName">
        <a-input v-model:value="form.accountName" placeholder="계정 이름을 입력하세요."/>
      </a-form-item>
      <a-form-item label="계정 번호" name="accountNumber">
        <a-input v-model:value="form.accountNumber" placeholder="계정 번호를 입력하세요."/>
      </a-form-item>
      <a-form-item label="대중이" name="businessFlag">
        <a-switch v-model:checked="businessFlagChecked" @change="businessFlagCheckedChange"/>
      </a-form-item>
      <a-form-item label="상태 활성화" name="disabledFlag">
        <a-switch v-model:checked="enabledChecked" @change="enabledCheckedChange"/>
      </a-form-item>
      <a-form-item label="비고" name="remark">
        <a-textarea v-model:value="form.remark" :rows="2"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
import {ref, reactive} from 'vue';
import {message} from 'ant-design-vue';
import { SmartLoading } from "/@/components/framework/smart-loading";
import {bankApi} from '/@/api/business/oa/bank-api';
import { smartSentry } from '/@/lib/smart-sentry';

const props = defineProps({
  enterpriseId: {
    type: Number,
    default: null,
  }
});
// emit
const emit = defineEmits(['reloadList']);

// ---------------------- 显示、隐藏 ----------------------
// 是否展示
const visible = ref(false);
function showModal(rowData) {
  Object.assign(form, formDefault);
  if (rowData) {
    Object.assign(form, rowData);
    businessFlagChecked.value = rowData.businessFlag;
    enabledChecked.value = !rowData.disabledFlag;
  }
  form.enterpriseId = props.enterpriseId;
  visible.value = true;
}

function onClose() {
  Object.assign(form, formDefault);
  formRef.value.resetFields();
  visible.value = false;
}

// ---------------------- 表单 ----------------------

//  组件
const formRef = ref();

const formDefault = {
  bankId: undefined,
  enterpriseId: undefined,
  bankName: '',
  accountName: '',
  accountNumber: '',
  businessFlag: false,
  disabledFlag: false,
  remark: '',
};
let form = reactive({...formDefault});
const rules = {
  bankName: [{required: true, message: '은행을 입력하세요.'}],
  accountName: [{required: true, message: '계정 이름을 입력하세요.'}],
  accountNumber: [{required: true, message: '계정 번호를 입력하세요.'}],
};

const businessFlagChecked = ref(false);
const enabledChecked = ref(true);

function businessFlagCheckedChange(checked) {
  form.businessFlag = checked;
}
function enabledCheckedChange(checked) {
  form.disabledFlag = !checked;
}

function onSubmit() {
  formRef.value
      .validate()
      .then(async () => {
        SmartLoading.show();
        try {
          if (form.bankId) {
            await bankApi.update(form);
          } else {
            await bankApi.create(form);
          }
          message.success(`${form.bankId ? '수정 사항' : '추가'}성공`);
          emit('reloadList');
          onClose();
        } catch (error) {
          smartSentry.captureError(error);
        } finally {
          SmartLoading.hide();
        }
      })
      .catch((error) => {
        debugger
        console.log('error', error);
        message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
      });
}

defineExpose({
  showModal,
});
</script>
