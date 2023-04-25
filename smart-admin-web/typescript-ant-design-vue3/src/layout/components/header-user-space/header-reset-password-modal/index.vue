<!--
  * 修改密码
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-06 20:02:01 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" title="비밀번호 변경" ok-text="OK" cancel-text="취소" @ok="updatePwd" @cancel="cancelModal">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }">
      <a-form-item label="기존 비밀번호" name="oldPassword">
        <a-input v-model:value.trim="form.oldPassword" type="password" placeholder="기존 비밀번호를 입력하세요" />
      </a-form-item>
      <a-form-item label="새 비밀번호" name="newPassword" :help="tips">
        <a-input v-model:value.trim="form.newPassword" type="password" placeholder="새 비밀번호를 입력하세요" />
      </a-form-item>
      <a-form-item label="비밀번호 확인" name="confirmPwd" :help="tips">
        <a-input v-model:value.trim="form.confirmPwd" type="password" placeholder="비밀번호 확인" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import { ref, reactive } from 'vue';
  import { message } from 'ant-design-vue';
  import { employeeApi } from '/@/api/system/employee/employee-api';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { smartSentry } from '/@/lib/smart-sentry';

  const visible = ref(false);
  const formRef = ref();
  const tips = '문자(대소문자 구분 없음) + 숫자 조합, 6~15자리'; //校验规则
  const reg = /(^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$)/; //校验正则

  const rules = {
    oldPassword: [{ required: true, message: '기존 비밀번호를 입력하세요.' }],
    newPassword: [{ type: 'string',  pattern: reg, message: '잘못된 비밀번호 형식' }],
    confirmPwd: [{ type: 'string',  pattern: reg, message: '확인 비밀번호를 입력하세요.' }],
  };

  const formDefault = {
    oldPassword: '',
    newPassword: '',
  };
  let form = reactive({
    ...formDefault,
  });

  async function updatePwd() {
    formRef.value
      .validate()
      .then(async () => {
        if (form.newPassword != form.confirmPwd) {
          message.error('새 비밀번호가 확인 비밀번호와 일치하지 않습니다.');
          return;
        }
        SmartLoading.show();
        try {
          await employeeApi.updateEmployeePassword(form);
          message.success('수정 성공');
          visible.value = false;
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

  function showModal() {
    visible.value = true;
  }

  function cancelModal() {
    visible.value = false;
  }

  defineExpose({ showModal });
</script>
