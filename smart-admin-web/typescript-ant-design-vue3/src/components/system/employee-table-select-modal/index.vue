
<template>
  <a-modal v-model:visible="visible" :width="900" title="직원 선택" @cancel="closeModal" @ok="onSelectEmployee">
    <a-form class="smart-query-form">
      <a-row class="smart-query-form-row">
        <a-form-item label="키워드" class="smart-query-form-item">
          <a-input style="width: 150px" v-model:value="params.keyword" placeholder="키워드" />
        </a-form-item>
        <a-form-item label="섹터" class="smart-query-form-item">
          <DepartmentTreeSelect style="width: 200px" ref="departmentTreeSelect" v-model:value="params.departmentId" />
        </a-form-item>
        <a-form-item label="상태" class="smart-query-form-item">
          <a-select style="width: 120px" v-model:value="params.disabledFlag" placeholder="상태를 선택하세요." allowClear>
            <a-select-option :key="1"> 사용 금지 </a-select-option>
            <a-select-option :key="0"> 사용 </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item class="smart-query-form-item smart-margin-left10">
          <a-button type="primary" @click="queryEmployee">
            <template #icon>
              <SearchOutlined />
            </template>
            문의
          </a-button>
          <a-button @click="reset" class="smart-margin-left10">
            <template #icon>
              <ReloadOutlined />
            </template>
            초기화
          </a-button>
        </a-form-item>
      </a-row>
    </a-form>
    <a-table
      :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
      :loading="tableLoading"
      size="small"
      :columns="columns"
      :data-source="tableData"
      :pagination="false"
      bordered
      rowKey="employeeId"
      :scroll="{ y: 300 }"
    >
      <template #bodyCell="{ text, column }">
        <template v-if="column.dataIndex === 'disabledFlag'">
          <a-tag :color="text ? 'error' : 'processing'">{{ text ? 'Disable' : 'Enable' }}</a-tag>
        </template>

        <template v-if="column.dataIndex === 'gender'">
          <span>{{ $smartEnumPlugin.getDescByValue('GENDER_ENUM', text) }}</span>
        </template>
      </template>
    </a-table>
    <div class="smart-query-table-page">
      <a-pagination
        showSizeChanger
        showQuickJumper
        show-less-items
        :pageSizeOptions="PAGE_SIZE_OPTIONS"
        :defaultPageSize="params.pageSize"
        v-model:current="params.pageNum"
        v-model:pageSize="params.pageSize"
        :total="total"
        @change="queryEmployee"
        @showSizeChange="queryEmployee"
        :show-total="(total) => `합계: ${total}`"
      />
    </div>
  </a-modal>
</template>
<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import { computed, reactive, ref } from 'vue';
  import { employeeApi } from '/@/api/system/employee/employee-api';
  import { PAGE_SIZE, PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import DepartmentTreeSelect from '/@/components/system/department-tree-select/index.vue';
  import { smartSentry } from '/@/lib/smart-sentry';

  // ----------------------- 以下是字段定义 emits props ---------------------
  const emits = defineEmits('selectData');
  defineExpose({
    showModal,
  });

  // ----------------------- modal  显示与隐藏 ---------------------

  const visible = ref(false);
  async function showModal(selectEmployeeId) {
    selectedRowKeyList.value = selectEmployeeId || [];
    visible.value = true;
    queryEmployee();
  }
  function closeModal() {
    Object.assign(params, defaultParams);
    selectedRowKeyList.value = [];
    visible.value = false;
  }
  // ----------------------- 员工查询表单与查询 ---------------------
  const tableLoading = ref(false);
  const departmentTreeSelect = ref();
  const total = ref();

  let defaultParams = {
    departmentId: undefined,
    disabledFlag: undefined,
    employeeIdList: undefined,
    keyword: undefined,
    searchCount: undefined,
    pageNum: 1,
    pageSize: PAGE_SIZE,
    sortItemList: undefined,
  };
  const params = reactive({ ...defaultParams });
  function reset() {
    Object.assign(params, defaultParams);
    queryEmployee();
  }
  async function queryEmployee() {
    tableLoading.value = true;
    try {
      let res = await employeeApi.queryEmployee(params);
      tableData.value = res.data.list;
      total.value = res.data.total;
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      tableLoading.value = false;
    }
  }

  // ----------------------- 员工表格选择 ---------------------
  let selectedRowKeyList = ref([]);
  const hasSelected = computed(() => selectedRowKeyList.value.length > 0);

  function onSelectChange(selectedRowKeys) {
    selectedRowKeyList.value = selectedRowKeys;
  }

  function onSelectEmployee() {
    if (!hasSelected.value) {
      message.warning('역할 담당자를 선택하세요.');
      return;
    }
    emits('selectData', selectedRowKeyList.value);
    closeModal();
  }

  // ----------------------- 员工表格渲染 ---------------------
  const tableData = ref([]);
  //字段
  const columns = [
    {
      title: '성명',
      dataIndex: 'actualName',
    },
    {
      title: '핸드폰번호',
      dataIndex: 'phone',
    },
    {
      title: '성별',
      dataIndex: 'gender',
    },
    {
      title: '로그인이름',
      dataIndex: 'loginName',
    },
    {
      title: '상태',
      dataIndex: 'disabledFlag',
    },
  ];
</script>
