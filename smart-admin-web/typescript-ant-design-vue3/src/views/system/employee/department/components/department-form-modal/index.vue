<!--
  * 部门表单 弹窗
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-08 20:46:18
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal v-model:visible="visible" :title="formState.departmentId ? '부서 편집' : '부서 추가'" @ok="handleOk" destroyOnClose>
    <a-form ref="formRef" :model="formState" :rules="rules" layout="vertical">
      <a-form-item label="상급 기관" name="parentId" v-if="formState.parentId != 0">
        <DepartmentTreeSelect ref="departmentTreeSelect" v-model:value="formState.parentId" :defaultValueFlag="false" width="100%" />
      </a-form-item>
      <a-form-item label="부서 이름" name="name">
        <a-input v-model:value.trim="formState.name" placeholder="부서 이름을 입력하세요." />
      </a-form-item>
      <a-form-item label="부서장" name="managerId">
        <EmployeeSelect ref="employeeSelect" placeholder="부서장을 선택해 주세요." width="100%" v-model:value="formState.managerId" :leaveFlag="false" />
      </a-form-item>
      <a-form-item label="섹터 정렬(값이 높을수록 순위가 높아집니다!)" name="sort">
        <a-input-number style="width: 100%" v-model:value="formState.sort" :min="0" placeholder="부서 이름을 입력하세요." />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup lang="ts">
  import message from 'ant-design-vue/lib/message';
  import { reactive, ref } from 'vue';
  import { departmentApi } from '/@/api/system/department/department-api';
  import DepartmentTreeSelect from '/@/components/system/department-tree-select/index.vue';
  import EmployeeSelect from '/@/components/system/employee-select/index.vue';
  import { smartSentry } from '/@/lib/smart-sentry';
  import { SmartLoading } from '/@/components/framework/smart-loading';

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
  const departmentTreeSelect = ref();
  const defaultDepartmentForm = {
    id: undefined,
    managerId: undefined, //部门负责人
    name: undefined,
    parentId: undefined,
    sort: 0,
  };
  const employeeSelect = ref();

  let formState = reactive({
    ...defaultDepartmentForm,
  });
  // 表单校验规则
  const rules = {
    parentId: [{ required: true, message: '上级部门不能为空' }],
    name: [
      { required: true, message: '部门名称不能为空' },
      { max: 50, message: '部门名称不能大于20个字符', trigger: 'blur' },
    ],
    managerId: [{ required: true, message: '部门负责人不能为空' }],
  };
  // 更新表单数据
  function updateFormData(data) {
    Object.assign(formState, defaultDepartmentForm);
    if (data) {
      Object.assign(formState, data);
    }
    visible.value = true;
  }
  // 重置表单数据
  function resetFormData() {
    Object.assign(formState, defaultDepartmentForm);
  }

  async function handleOk() {
    try {
      await formRef.value.validate();
      if (formState.departmentId) {
        updateDepartment();
      } else {
        addDepartment();
      }
    } catch (error) {
      message.error('参数验证错误，请仔细填写表单数据!');
    }
  }

  // ----------------------- form 表单  ajax 操作 ---------------------
  //添加部门ajax请求
  async function addDepartment() {
    SmartLoading.show();
    try {
      await departmentApi.addDepartment(formState);
      emits('refresh');
      closeModal();
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }

  //更新部门ajax请求
  async function updateDepartment() {
    SmartLoading.show();
    try {
      if (formState.parentId == formState.departmentId) {
        message.warning('上级菜单不能为自己');
        return;
      }
      await departmentApi.updateDepartment(formState);
      emits('refresh');
      closeModal();
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }
</script>
