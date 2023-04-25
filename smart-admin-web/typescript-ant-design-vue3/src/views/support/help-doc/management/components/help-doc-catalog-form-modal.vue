<!--
  * 目录表单
  * 
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal v-model:visible="visible" :title="formState.helpDocCatalogId ? '카탈로그 편집' : '카탈로그 추가'" @ok="handleOk" destroyOnClose>
    <a-form ref="formRef" :model="formState" :rules="rules" layout="vertical">
      <a-form-item label="이전 카탈로그" name="parentId" v-if="formState.parentId != 0">
        <HelpDocCatalogTreeSelect ref="helpDocCatalogTreeSelect" v-model:value="formState.parentId" :defaultValueFlag="false" width="100%" />
      </a-form-item>
      <a-form-item label="카탈로그 이름" name="name">
        <a-input v-model:value.trim="formState.name" placeholder="카탈로그 이름을 입력하세요." />
      </a-form-item>
      <a-form-item label="목차 정렬(값이 작을수록 높습니다!)" name="sort">
        <a-input-number style="width: 100%" v-model:value="formState.sort" :min="0" placeholder="카탈로그 이름을 입력하세요." />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import message from 'ant-design-vue/lib/message';
  import { reactive, ref } from 'vue';
  import { helpDocCatalogApi } from '/@/api/support/help-doc/help-doc-catalog-api';
  import HelpDocCatalogTreeSelect from './help-doc-catalog-tree-select.vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
import { smartSentry } from '/@/lib/smart-sentry';

  // ----------------------- 对外暴漏 ---------------------

  defineExpose({
    showModal,
  });

  // ----------------------- modal 的显示与隐藏 ---------------------
  const emits = defineEmits(['refresh']);

  const visible = ref(false);
  function showModal(data) {
    visible.value = true;
    updateFormData(data);
  }
  function closeModal() {
    visible.value = false;
    resetFormData();
  }

  // ----------------------- form 表单操作 ---------------------
  const formRef = ref();
  const helpDocCatalogTreeSelect = ref();
  const defaultHelpDocCatalogForm = {
    helpDocCatalogId: undefined,
    name: undefined,
    parentId: undefined,
    sort: 0,
  };
  const employeeSelect = ref();

  let formState = reactive({
    ...defaultHelpDocCatalogForm,
  });
  // 表单校验规则
  const rules = {
    parentId: [{ required: true, message: '상위 디렉터리는 비어 있을 수 없습니다.' }],
    name: [
      { required: true, message: '디렉토리 이름은 비워둘 수 없습니다.' },
      { max: 50, message: '디렉토리 이름은 20자를 초과할 수 없습니다.', trigger: 'blur' },
    ],
  };
  // 更新表单数据
  function updateFormData(data) {
    Object.assign(formState, defaultHelpDocCatalogForm);
    if (data) {
      Object.assign(formState, data);
    }
    visible.value = true;
  }
  // 重置表单数据
  function resetFormData() {
    Object.assign(formState, defaultHelpDocCatalogForm);
  }

  async function handleOk() {
    try {
      await formRef.value.validate();
      if (formState.helpDocCatalogId) {
        updateHelpDocCatalog();
      } else {
        addHelpDocCatalog();
      }
    } catch (error) {
      message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
    }
  }

  // ----------------------- form 表单  ajax 操作 ---------------------
  //添加目录ajax请求
  async function addHelpDocCatalog() {
    SmartLoading.show();
    try {
      await helpDocCatalogApi.add(formState);
      emits('refresh');
      closeModal();
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }

  //更新目录ajax请求
  async function updateHelpDocCatalog() {
    SmartLoading.show();
    try {
      if (formState.parentId == formState.helpDocCatalogId) {
        message.warning('상급 메뉴는 그 자체로 존재할 수 없습니다.');
        return;
      }
      await helpDocCatalogApi.update(formState);
      emits('refresh');
      closeModal();
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }
</script>
