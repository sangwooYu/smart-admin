<!--
  * 企业 员工
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-15 20:15:49
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <div>
    <div class="header">
      <div>
        关键字：
        <a-input style="width: 250px" v-model:value="queryForm.keywords" placeholder="이름/휴대폰 번호/로그인 계정" />
        <a-button class="button-style" type="primary" @click="queryEmployee">검색</a-button>
        <a-button class="button-style" type="default" @click="resetQueryEmployee">초기화</a-button>
      </div>

      <div>
        <a-button class="button-style" type="primary" @click="addEmployee" v-privilege="'enterprise:addEmployee'"> 직원 추가 </a-button>
        <a-button class="button-style" type="primary" danger @click="batchDelete" v-privilege="'enterprise:deleteEmployee'"> 일괄 제거 </a-button>
      </div>
    </div>
    <a-table
      :loading="tableLoading"
      :dataSource="tableData"
      :columns="columns"
      :pagination="false"
      rowKey="employeeId"
      :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
      size="small"
      bordered
    >
      <template #bodyCell="{ text, record, index, column }">
        <template v-if="column.dataIndex === 'disabledFlag'">
          <a-tag :color="text ? 'error' : 'processing'">{{ text ? 'Disable' : 'Enable' }}</a-tag>
        </template>
        <template v-else-if="column.dataIndex === 'gender'">
          <span>{{ $smartEnumPlugin.getDescByValue('GENDER_ENUM', text) }}</span>
        </template>
        <template v-if="column.dataIndex === 'operate'">
          <a @click="deleteEmployee(record.employeeId)" v-privilege="'enterprise:deleteEmployee'">제거</a>
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
        @change="queryEmployee"
        @showSizeChange="queryEmployee"
        :show-total="showTableTotal"
      />
    </div>
    <EmployeeTableSelectModal ref="selectEmployeeModal" @selectData="selectData" />
  </div>
</template>
<script setup lang="ts">
  import EmployeeTableSelectModal from '/@/components/system/employee-table-select-modal/index.vue';

  import { message, Modal } from 'ant-design-vue';
  import _ from 'lodash';
  import { computed, reactive, ref, watch } from 'vue';
  import { enterpriseApi } from '/@/api/business/oa/enterprise-api';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { PAGE_SIZE, PAGE_SIZE_OPTIONS, showTableTotal } from '/@/constants/common-const';
  import { smartSentry } from '/@/lib/smart-sentry';

  const props = defineProps({
    enterpriseId: {
      type: Number,
      default: null,
    },
  });

  const columns = reactive([
    {
      title: '이름',
      dataIndex: 'actualName',
    },
    {
      title: '휴대폰 번호',
      dataIndex: 'phone',
      width: 120,
    },
    {
      title: '로그인 계정',
      dataIndex: 'loginName',
    },
    {
      title: '기업',
      dataIndex: 'enterpriseName',
      ellipsis: true,
    },
    {
      title: '섹터',
      dataIndex: 'departmentName',
      ellipsis: true,
    },
    {
      title: '상태',
      dataIndex: 'disabledFlag',
      width: 80,
    },
    {
      title: '운영',
      dataIndex: 'operate',
      width: 60,
    },
  ]);

  // --------------------------- 查询 ---------------------------

  const defaultQueryForm = {
    pageNum: 1,
    pageSize: PAGE_SIZE,
    enterpriseId: undefined,
    keywords: undefined,
  };
  // 查询表单
  const queryForm = reactive({ ...defaultQueryForm });
  const total = ref(0);
  const tableData = ref([]);
  const tableLoading = ref(false);

  function resetQueryEmployee() {
    queryForm.keywords = '';
    queryEmployee();
  }

  async function queryEmployee() {
    try {
      tableLoading.value = true;
      queryForm.enterpriseId = props.enterpriseId;
      let res = await enterpriseApi.queryPageEmployeeList(queryForm);
      tableData.value = res.data.list;
      total.value = res.data.total;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  async function selectData(list) {
    if (_.isEmpty(list)) {
      message.warning('직원을 선택해 주세요.');
      return;
    }
    SmartLoading.show();
    try {
      let params = {
        employeeIdList: list,
        enterpriseId: props.enterpriseId,
      };
      await enterpriseApi.addEmployee(params);
      message.success('추가 성공');
      await queryEmployee();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }

  // --------------------------- 添加员工 ---------------------------

  // 添加员工
  const selectEmployeeModal = ref();
  async function addEmployee() {
    let res = await enterpriseApi.employeeList([props.enterpriseId]);
    let selectedIdList = res.data.map((e) => e.employeeId) || [];
    selectEmployeeModal.value.showModal(selectedIdList);
  }

  // --------------------------- 删除 ---------------------------

  // 删除员工方法
  async function deleteEmployee(employeeId) {
    Modal.confirm({
      title: '팁',
      content: '이 비즈니스에 속한 직원을 삭제하시겠습니까?',
      okText: '결정',
      okType: 'danger',
      async onOk() {
        SmartLoading.show();
        try {
          let param = {
            employeeIdList: [employeeId],
            enterpriseId: props.enterpriseId,
          };
          await enterpriseApi.deleteEmployee(param);
          message.success('제거 성공');
          await queryEmployee();
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

  // 批量删除
  const selectedRowKeyList = ref([]);
  const hasSelected = computed(() => selectedRowKeyList.value.length > 0);
  function onSelectChange(selectedRowKeys) {
    selectedRowKeyList.value = selectedRowKeys;
  }

  // 批量移除
  function batchDelete() {
    if (!hasSelected.value) {
      message.warning('삭제할 직원을 선택하세요.');
      return;
    }
    Modal.confirm({
      title: '팁',
      content: '이 비즈니스에 속한 직원을 삭제하시겠습니까?',
      okText: '결정',
      okType: 'danger',
      async onOk() {
        SmartLoading.show();
        try {
          let params = {
            employeeIdList: selectedRowKeyList.value,
            enterpriseId: props.enterpriseId,
          };
          await enterpriseApi.deleteEmployee(params);
          message.success('제거 성공');
          selectedRowKeyList.value = [];
          await queryEmployee();
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

  watch(
    () => props.enterpriseId,
    (e) => {
      if (e) {
        queryEmployee();
      }
    },
    { immediate: true }
  );
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
