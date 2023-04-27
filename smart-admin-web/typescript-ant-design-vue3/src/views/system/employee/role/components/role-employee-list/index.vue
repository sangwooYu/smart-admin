<!--
  * 角色 员工 列表
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-09-12 22:34:00
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
  *
-->
<template>
  <div>
    <div class="header">
      <div>
        핵심 단어：
        <a-input style="width: 250px" v-model:value="queryForm.keywords" placeholder="이름/휴대폰 번호/로그인 계정" />

        <a-button class="button-style" v-if="selectRoleId" type="primary" @click="queryRoleEmployee">검색</a-button>
        <a-button class="button-style" v-if="selectRoleId" type="default" @click="resetQueryRoleEmployee">초기화</a-button>
      </div>

      <div>
        <a-button class="button-style" v-if="selectRoleId" type="primary" @click="addRoleEmployee" v-privilege="'system:role:employee:add'"
          >직원 추가</a-button
        >
        <a-button class="button-style" v-if="selectRoleId" type="primary" danger @click="batchDelete" v-privilege="'system:role:employee:batch:delete'"
          >일괄 제거</a-button
        >
      </div>
    </div>
    <a-table
      :loading="tableLoading"
      :dataSource="tableData"
      :columns="columns"
      :pagination="false"
      :scroll="{ y: 400 }"
      rowKey="employeeId"
      :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
      size="small"
      bordered
    >
      <template #bodyCell="{ text, record, column }">
        <template v-if="column.dataIndex === 'disabledFlag'">
          <a-tag :color="text ? 'error' : 'processing'">{{ text ? '사용 금지' : '사용' }}</a-tag>
        </template>
        <template v-else-if="column.dataIndex === 'gender'">
          <span>{{ $smartEnumPlugin.getDescByValue('GENDER_ENUM', text) }}</span>
        </template>
        <template v-if="column.dataIndex === 'operate'">
          <a @click="deleteEmployeeRole(record.employeeId)" v-privilege="'system:role:employee:delete'">제거</a>
        </template>
      </template>
    </a-table>
    <div class="smart-query-table-page">
      <a-pagination
        showSizeChanger
        showQuickJumper
        show-less-items
        :pageSizeOptions="PAGE_SIZE_OPTIONS"
        :defaultPageSize="queryForm.pageSize"
        v-model:current="queryForm.pageNum"
        v-model:pageSize="queryForm.pageSize"
        :total="total"
        @change="queryRoleEmployee"
        @showSizeChange="queryRoleEmployee"
        :show-total="showTableTotal"
      />
    </div>
    <EmployeeTableSelectModal ref="selectEmployeeModal" @selectData="selectData" />
  </div>
</template>
<script setup lang="ts">
  import { message, Modal } from 'ant-design-vue';
  import _ from 'lodash';
  import { computed, inject, onMounted, reactive, ref, watch } from 'vue';
  import { roleApi } from '/@/api/system/role/role-api';
  import { PAGE_SIZE, showTableTotal, PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import EmployeeTableSelectModal from '/@/components/system/employee-table-select-modal/index.vue';
  import { smartSentry } from '/@/lib/smart-sentry';

  // ----------------------- 以下是字段定义 emits props ---------------------
  let selectRoleId = inject('selectRoleId');

  // ----------------------- 员工列表：显示和搜索 ------------------------
  watch(
    () => selectRoleId.value,
    () => queryRoleEmployee()
  );

  onMounted(queryRoleEmployee);

  const defaultQueryForm = {
    pageNum: 1,
    pageSize: PAGE_SIZE,
    roleId: undefined,
    keywords: undefined,
  };
  // 查询表单
  const queryForm = reactive({ ...defaultQueryForm });
  // 总数
  const total = ref(0);
  // 表格数据
  const tableData = ref([]);
  // 表格loading效果
  const tableLoading = ref(false);

  function resetQueryRoleEmployee() {
    queryForm.keywords = '';
    queryRoleEmployee();
  }

  async function queryRoleEmployee() {
    try {
      tableLoading.value = true;
      queryForm.roleId = selectRoleId.value;
      let res = await roleApi.queryRoleEmployee(queryForm);
      tableData.value = res.data.list;
      total.value = res.data.total;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  const columns = reactive([
    {
      title: '이름',
      dataIndex: 'actualName',
    },
    {
      title: '휴대폰 번호',
      dataIndex: 'phone',
    },
    {
      title: '로그인 계정',
      dataIndex: 'loginName',
    },
    {
      title: '섹터',
      dataIndex: 'departmentName',
    },
    {
      title: '상태',
      dataIndex: 'disabledFlag',
    },
    {
      title: '운영',
      dataIndex: 'operate',
      width: 60,
    },
  ]);

  // ----------------------- 添加成员 ---------------------------------
  const selectEmployeeModal = ref();

  async function addRoleEmployee() {
    let res = await roleApi.getRoleAllEmployee(selectRoleId.value);
    let selectedIdList = res.data.map((e) => e.roleId) || [];
    selectEmployeeModal.value.showModal(selectedIdList);
  }

  async function selectData(list) {
    if (_.isEmpty(list)) {
      message.warning('역할 담당자를 선택하세요.');
      return;
    }
    SmartLoading.show();
    try {
      let params = {
        employeeIdList: list,
        roleId: selectRoleId.value,
      };
      await roleApi.batchAddRoleEmployee(params);
      message.success('추가 성공');
      await queryRoleEmployee();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }

  // ----------------------- 移除成员 ---------------------------------
  // 删除角色成员方法
  async function deleteEmployeeRole(employeeId) {
    Modal.confirm({
      title: '팁',
      content: '역할 멤버를 삭제하시겠습니까?',
      okText: 'OK',
      okType: 'danger',
      async onOk() {
        SmartLoading.show();
        try {
          await roleApi.deleteEmployeeRole(employeeId, selectRoleId.value);
          message.success('제거 성공');
          await queryRoleEmployee();
        } catch (e) {
          smartSentry.captureError(e);
        } finally {
          SmartLoading.hide();
        }
      },
      cancelText: '취소',
      onCancel() {},
    });
  }

  // ----------------------- 批量删除 ---------------------------------

  const selectedRowKeyList = ref([]);
  const hasSelected = computed(() => selectedRowKeyList.value.length > 0);

  function onSelectChange(selectedRowKeys) {
    selectedRowKeyList.value = selectedRowKeys;
  }

  // 批量移除
  function batchDelete() {
    if (!hasSelected.value) {
      message.warning('삭제할 역할 구성원을 선택하세요.');
      return;
    }
    Modal.confirm({
      title: '팁',
      content: '이러한 역할 구성원을 제거하시겠습니까?',
      okText: 'OK',
      okType: 'danger',
      async onOk() {
        SmartLoading.show();
        try {
          let params = {
            employeeIdList: selectedRowKeyList.value,
            roleId: selectRoleId.value,
          };
          await roleApi.batchRemoveRoleEmployee(params);
          message.success('제거 성공');
          selectedRowKeyList.value = [];
          await queryRoleEmployee();
        } catch (e) {
          smartSentry.captureError(e);
        } finally {
          SmartLoading.hide();
        }
      },
      cancelText: '취소',
      onCancel() {},
    });
  }
</script>

<style scoped lang="less">
  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 20px 0;
  }
  .button-style {
    margin: 0 10px;
  }
</style>
