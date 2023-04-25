<!--
  * 系统更新日志
  *
  * @Author:    卓大
  * @Date:      2022-09-26 14:53:50
  * @Copyright  1024创新实验室
-->
<template>
  <a-modal
    :title="form.changeLogId ? '편집' : '추가'"
    width="600px"
    :closable="true"
    :visible="visibleFlag"
    @close="onClose"
    :onCancel="onClose"
    :maskClosable="false"
    :destroyOnClose="true"
  >
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }">
      <a-form-item label="버전" name="version">
        <a-input style="width: 100%" v-model:value="form.version" placeholder="버전" />
      </a-form-item>
      <a-form-item label="업데이트 유형" name="type">
        <SmartEnumSelect width="100%" v-model:value="form.type" enumName="CHANGE_LOG_TYPE_ENUM" placeholder="업데이트 유형" />
      </a-form-item>
      <a-form-item label="게시자" name="publishAuthor">
        <a-input style="width: 100%" v-model:value="form.publishAuthor" placeholder="게시자" />
      </a-form-item>
      <a-form-item label="릴리스 날짜" name="publicDate">
        <a-date-picker valueFormat="YYYY-MM-DD" v-model:value="form.publicDate" style="width: 100%" placeholder="릴리스 날짜" />
      </a-form-item>
      <a-form-item label="링크 이동" name="link">
        <a-input style="width: 100%" v-model:value="form.link" placeholder="링크 이동" />
      </a-form-item>
      <a-form-item label="업데이트" name="content">
        <a-textarea style="width: 100%" :rows="15"  v-model:value="form.content" placeholder="업데이트" />
      </a-form-item>
    </a-form>

    <template #footer>
      <a-space>
        <a-button @click="onClose">취소</a-button>
        <a-button type="primary" @click="onSubmit">저장</a-button>
      </a-space>
    </template>
  </a-modal>
</template>
<script setup lang="ts">
  import { reactive, ref, nextTick } from 'vue';
  import _ from 'lodash';
  import { message } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { changeLogApi } from '/@/api/support/change-log/change-log-api';
  import { smartSentry } from '/@/lib/smart-sentry';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';

  // ------------------------ 事件 ------------------------

  const emits = defineEmits(['reloadList']);

  // ------------------------ 显示与隐藏 ------------------------
  // 是否显示
  const visibleFlag = ref(false);

  function show(rowData) {
    Object.assign(form, formDefault);
    if (rowData && !_.isEmpty(rowData)) {
      Object.assign(form, rowData);
    }
    visibleFlag.value = true;
    nextTick(() => {
      formRef.value.clearValidate();
    });
  }

  function onClose() {
    Object.assign(form, formDefault);
    visibleFlag.value = false;
  }

  // ------------------------ 表单 ------------------------

  // 组件ref
  const formRef = ref();

  const formDefault = {
    changeLogId: undefined,
    version: undefined, //版本
    type: undefined, //更新类型:[1:特大版本功能更新;2:功能更新;3:bug修复]
    publishAuthor: undefined, //发布人
    publicDate: undefined, //发布日期
    content: undefined, //更新内容
    link: undefined, //跳转链接
  };

  let form = reactive({ ...formDefault });

  const rules = {
    version: [{ required: true, message: '필요한 버전' }],
    type: [{ required: true, message: '업데이트 유형:[1:메가 버전 기능 업데이트, 2:기능 업데이트, 3:버그 수정] 필수 입력란' }],
    publishAuthor: [{ required: true, message: '게시자 작성자 필수 필드' }],
    publicDate: [{ required: true, message: '게시 날짜 필수 필드' }],
    content: [{ required: true, message: '업데이트 필수' }],
  };

  // 点击确定，验证表单
  async function onSubmit() {
    try {
      await formRef.value.validateFields();
      save();
    } catch (err) {
      message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
    }
  }

  // 新建、编辑API
  async function save() {
    SmartLoading.show();
    try {
      if (form.changeLogId) {
        await changeLogApi.update(form);
      } else {
        await changeLogApi.add(form);
      }
      message.success('성공적인 작동');
      emits('reloadList');
      onClose();
    } catch (err) {
      smartSentry.captureError(err);
    } finally {
      SmartLoading.hide();
    }
  }

  defineExpose({
    show,
  });
</script>
