<!--
  * 目录 表单 弹窗
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-21 19:52:43 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" :title="form.categoryId ? '편집' : '추가'" ok-text="확인" cancel-text="취소" @ok="onSubmit" @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
      <a-form-item label="분류 이름" name="categoryName">
        <a-input v-model:value="form.categoryName" placeholder="카테고리 이름을 입력하세요." />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import { ref, reactive } from 'vue';
  import { message } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import _ from 'lodash';
  import { categoryApi } from '/@/api/business/category/category-api';
  import { smartSentry } from '/@/lib/smart-sentry';

  // emit
  const emit = defineEmits('reloadList');

  //  组件
  const formRef = ref();

  // ------------------------------ 显示 、隐藏 ------------------------------

  // 是否展示抽屉
  const visible = ref(false);

  function showModal(categoryType, parentId, rowData) {
    Object.assign(form, formDefault);
    form.categoryType = categoryType;
    form.parentId = parentId;
    if (rowData && !_.isEmpty(rowData)) {
      Object.assign(form, rowData);
    }
    visible.value = true;
  }

  function onClose() {
    Object.assign(form, formDefault);
    visible.value = false;
  }

  // ------------------------------ 表单 ------------------------------

  const formDefault = {
    categoryId: undefined,
    categoryName: '',
    categoryType: 1,
    parentId: undefined,
    disabledFlag: false,
  };
  let form = reactive({ ...formDefault });
  const rules = {
    categoryName: [{ required: true, message: '카테고리 이름을 입력하세요.' }],
  };

  function onSubmit() {
    formRef.value
      .validate()
      .then(async () => {
        SmartLoading.show();
        try {
          if (form.categoryId) {
            await categoryApi.updateCategory(form);
          } else {
            await categoryApi.addCategory(form);
          }
          message.success(`${form.categoryId ? '수정' : '추가'}성공`);
          emit('reloadList', form.parentId);
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
