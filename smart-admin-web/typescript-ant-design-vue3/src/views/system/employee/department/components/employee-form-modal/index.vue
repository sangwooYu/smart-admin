<!--
  *  员工 表单 弹窗
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-08 20:46:18
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-drawer
    :title="form.employeeId ? '편집' : '추가'"
    :width="600"
    :visible="visible"
    :body-style="{ paddingBottom: '80px' }"
    @close="onClose"
    destroyOnClose
  >
    <a-form ref="formRef" :model="form" :rules="rules" layout="vertical">
      <a-form-item label="성명" name="actualName">
        <a-input v-model:value.trim="form.actualName" placeholder="성명을 입력하세요" />
      </a-form-item>
      <a-form-item label="핸드폰 번호" name="phone">
        <a-input v-model:value.trim="form.phone" placeholder="핸드폰 번호를 입력하세요" />
      </a-form-item>
      <a-form-item label="섹터" name="departmentId">
        <DepartmentTreeSelect ref="departmentTreeSelect" width="100%" :init="false" v-model:value="form.departmentId" />
      </a-form-item>
      <a-form-item label="로그인 이름" name="loginName">
        <a-input v-model:value.trim="form.loginName" placeholder="로그인 이름을 입력하세요." />
        <p class="hint">초기 비밀번호 기본값: 무작위</p>
      </a-form-item>
      <a-form-item label="성별" name="gender">
        <smart-enum-select style="width: 100%" v-model:value="form.gender" placeholder="성별을 선택해 주세요." enum-name="GENDER_ENUM" />
      </a-form-item>
      <a-form-item label="상태" name="disabledFlag">
        <a-select v-model:value="form.disabledFlag" placeholder="상태를 선택하세요.">
          <a-select-option :value="0">사용</a-select-option>
          <a-select-option :value="1">사용 금지</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="역할" name="roleIdList">
        <a-select mode="multiple" v-model:value="form.roleIdList" optionFilterProp="title" placeholder="역할을 선택해 주세요.">
          <a-select-option v-for="item in roleList" :key="item.roleId" :title="item.roleName">{{ item.roleName }}</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
    <div class="footer">
      <a-button style="margin-right: 8px" @click="onClose">취소</a-button>
      <a-button type="primary" style="margin-right: 8px" @click="onSubmit(false)">저장</a-button>
      <a-button v-if="!form.employeeId" type="primary" @click="onSubmit(true)">저장하고 계속 추가</a-button>
    </div>
  </a-drawer>
</template>
<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import _ from 'lodash';
  import { nextTick, reactive, ref } from 'vue';
  import { employeeApi } from '/@/api/system/employee/employee-api';
  import { roleApi } from '/@/api/system/role/role-api';
  import DepartmentTreeSelect from '/@/components/system/department-tree-select/index.vue';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';
  import { GENDER_ENUM } from '/@/constants/common-const';
  import { regular } from '/@/constants/regular-const';
  import { SmartLoading } from '/@/components/framework/smart-loading';
import { smartSentry } from '/@/lib/smart-sentry';
  // ----------------------- 以下是字段定义 emits props ---------------------
  const departmentTreeSelect = ref();
  // emit
  const emit = defineEmits(['refresh', 'show-account']);

  // ----------------------- 显示/隐藏 ---------------------

  const visible = ref(false); // 是否展示抽屉
  // 隐藏
  function onClose() {
    reset();
    visible.value = false;
  }
  // 显示
  async function showDrawer(rowData) {
    Object.assign(form, formDefault);
    if (rowData && !_.isEmpty(rowData)) {
      Object.assign(form, rowData);
    }
    visible.value = true;
    nextTick(() => {
      queryAllRole();
    });
  }

  // ----------------------- 表单显示 ---------------------

  const roleList = ref([]); //角色列表
  async function queryAllRole() {
    let res = await roleApi.queryAll();
    roleList.value = res.data;
  }

  const formRef = ref(); // 组件ref
  const formDefault = {
    id: undefined,
    actualName: undefined,
    departmentId: undefined,
    disabledFlag: 0,
    leaveFlag: 0,
    gender: GENDER_ENUM.MAN.value,
    loginName: undefined,
    phone: undefined,
    roleIdList: undefined,
  };

  let form = reactive(_.cloneDeep(formDefault));
  function reset() {
    Object.assign(form, formDefault);
    formRef.value.resetFields();
  }

  // ----------------------- 表单提交 ---------------------
  // 表单规则
  const rules = {
    actualName: [
      { required: true, message: '이름은 비워 둘 수 없습니다.' },
      { max: 30, message: '이름은 30자를 초과할 수 없습니다.', trigger: 'blur' },
    ],
    phone: [
      { required: true, message: '휴대폰 번호는 비워 둘 수 없습니다.' },
      { pattern: regular.phone, message: '올바른 휴대폰 번호를 입력하세요.', trigger: 'blur' },
    ],
    loginName: [
      { required: true, message: '로그인 계정은 비어 있을 수 없습니다.' },
      { max: 30, message: '로그인 계정은 30자를 초과할 수 없습니다.', trigger: 'blur' },
    ],
    gender: [{ required: true, message: '성별은 비워 둘 수 없습니다.' }],
    departmentId: [{ required: true, message: '부서는 비어 있을 수 없습니다.' }],
    disabledFlag: [{ required: true, message: '상태는 비어있을 수 없습니다.' }],
    leaveFlag: [{ required: true, message: '서비스 중 상태는 비어 있을 수 없습니다.' }],
  };

  // 校验表单
  function validateForm(formRef) {
    return new Promise((resolve) => {
      formRef
        .validate()
        .then(() => {
          resolve(true);
        })
        .catch(() => {
          resolve(false);
        });
    });
  }

  // 提交数据
  async function onSubmit(keepAdding) {
    let validateFormRes = await validateForm(formRef.value);
    if (!validateFormRes) {
      message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
      return;
    }
    SmartLoading.show();
    if (form.employeeId) {
      await updateEmployee(keepAdding);
    } else {
      await addEmployee(keepAdding);
    }
  }

  async function addEmployee(keepAdding) {
    try {
      let { data } = await employeeApi.addEmployee(form);
      message.success('추가 성공');
      emit('show-account', form.loginName, data);
      if (keepAdding) {
        reset();
      } else {
        onClose();
      }
      emit('refresh');
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }
  async function updateEmployee(keepAdding) {
    try {
      let result = await employeeApi.updateEmployee(form);
      message.success('업데이트 성공');
      if (keepAdding) {
        reset();
      } else {
        onClose();
      }
      emit('refresh');
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }

  // ----------------------- 以下是暴露的方法内容 ----------------------------
  defineExpose({
    showDrawer,
  });
</script>
<style scoped lang="less">
  .footer {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e9e9e9;
    padding: 10px 16px;
    background: #fff;
    text-align: right;
    z-index: 1;
  }
  .hint {
    margin-top: 5px;
    color: #bfbfbf;
  }
</style>
