<!--
  * 系统更新日志
  *
  * @Author:    卓大
  * @Date:      2022-09-26 14:53:50
  * @Copyright  1024创新实验室
-->
<template>
  <!---------- 查询表单form begin ----------->
  <a-form class="smart-query-form" v-privilege="'changeLog:query'">
    <a-row class="smart-query-form-row">
      <a-form-item label="업데이트 유형" class="smart-query-form-item">
        <SmartEnumSelect width="200px" v-model:value="queryForm.type" enumName="CHANGE_LOG_TYPE_ENUM" placeholder="업데이트 유형" />
      </a-form-item>
      <a-form-item label="키워드" class="smart-query-form-item">
        <a-input style="width: 200px" v-model:value="queryForm.keyword" placeholder="키워드" />
      </a-form-item>
      <a-form-item label="릴리스 날짜" class="smart-query-form-item">
        <a-range-picker v-model:value="queryForm.publicDate" :ranges="defaultTimeRanges" style="width: 240px" @change="onChangePublicDate" />
      </a-form-item>
      <a-form-item label="생성 시간" class="smart-query-form-item">
        <a-date-picker valueFormat="YYYY-MM-DD" v-model:value="queryForm.createTime" style="width: 150px" />
      </a-form-item>
      <a-form-item class="smart-query-form-item">
        <a-button type="primary" @click="queryData">
          <template #icon>
            <ReloadOutlined />
          </template>
          문의
        </a-button>
        <a-button @click="resetQuery" class="smart-margin-left10">
          <template #icon>
            <SearchOutlined />
          </template>
          초기화
        </a-button>
      </a-form-item>
    </a-row>
  </a-form>
  <!---------- 查询表单form end ----------->

  <a-card size="small" :bordered="false" :hoverable="true">
    <!---------- 表格操作行 begin ----------->
    <a-row class="smart-table-btn-block">
      <div class="smart-table-operate-block">
        <a-button @click="showForm" type="primary" size="small" v-privilege="'changeLog:add'">
          <template #icon>
            <PlusOutlined />
          </template>
          신규 추가
        </a-button>
        <a-button @click="confirmBatchDelete" type="danger" size="small" :disabled="selectedRowKeyList.length == 0" v-privilege="'changeLog:batchDelete'">
          <template #icon>
            <DeleteOutlined />
          </template>
          일괄 삭제
        </a-button>
      </div>
      <div class="smart-table-setting-block">
        <TableOperator v-model="columns" :tableId="null" :refresh="queryData" />
      </div>
    </a-row>
    <!---------- 表格操作行 end ----------->

    <!---------- 表格 begin ----------->
    <a-table
      size="small"
      :dataSource="tableData"
      :columns="columns"
      rowKey="changeLogId"
      bordered
      :pagination="false"
      :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
    >
      <template #bodyCell="{ text, record, column }">
        <template v-if="column.dataIndex === 'version'">
          <a-button @click="showModal(record)" type="link">{{text}}</a-button>
        </template>
        <template v-if="column.dataIndex === 'type'">
          <a-tag color="success">
            <template #icon>
              <check-circle-outlined />
            </template>
            {{ $smartEnumPlugin.getDescByValue('CHANGE_LOG_TYPE_ENUM', text) }}
          </a-tag>
        </template>
        <template v-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button @click="showForm(record)" type="link" v-privilege="'changeLog:update'">편집</a-button>
            <a-button @click="onDelete(record)" danger type="link" v-privilege="'changeLog:delete'">삭제</a-button>
          </div>
        </template>
      </template>
    </a-table>
    <!---------- 表格 end ----------->

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
        @change="queryData"
        @showSizeChange="queryData"
        :show-total="(total) => `합계: ${total}`"
      />
    </div>

    <ChangeLogForm ref="formRef" @reloadList="queryData" />

    <ChangeLogModal ref="modalRef" />
  </a-card>
</template>
<script setup lang="ts">
  import { reactive, ref, onMounted } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { changeLogApi } from '/@/api/support/change-log/change-log-api';
  import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import DictSelect from '/@/components/support/dict-select/index.vue';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';
  import { defaultTimeRanges } from '/@/lib/default-time-ranges';
  import ChangeLogModal from './change-log-modal.vue';
  import ChangeLogForm from './change-log-form.vue';
  // ---------------------------- 表格列 ----------------------------

  const columns = ref([
    {
      title: '버전',
      dataIndex: 'version',
      ellipsis: true,
    },
    {
      title: '업데이트 유형',
      dataIndex: 'type',
      ellipsis: true,
    },
    {
      title: '게시자',
      dataIndex: 'publishAuthor',
      ellipsis: true,
    },
    {
      title: '릴리스 날짜',
      dataIndex: 'publicDate',
      ellipsis: true,
    },
    {
      title: '업데이트',
      dataIndex: 'content',
      ellipsis: true,
    },
    {
      title: '링크 이동',
      dataIndex: 'link',
      ellipsis: true,
    },
    {
      title: '생성 시간',
      dataIndex: 'createTime',
      ellipsis: true,
    },
    {
      title: '업데이트 시간',
      dataIndex: 'updateTime',
      ellipsis: true,
    },
    {
      title: '운영',
      dataIndex: 'action',
      fixed: 'right',
      width: 90,
    },
  ]);

  // ---------------------------- 查询数据表单和方法 ----------------------------

  const queryFormState = {
    type: undefined, //更新类型:[1:特大版本功能更新;2:功能更新;3:bug修复]
    keyword: undefined, //关键字
    publicDate: [], //发布日期
    publicDateBegin: undefined, //发布日期 开始
    publicDateEnd: undefined, //发布日期 结束
    createTime: undefined, //创建时间
    link: undefined, //跳转链接
    pageNum: 1,
    pageSize: 10,
  };
  // 查询表单form
  const queryForm = reactive({ ...queryFormState });
  // 表格加载loading
  const tableLoading = ref(false);
  // 表格数据
  const tableData = ref([]);
  // 总数
  const total = ref(0);

  // 重置查询条件
  function resetQuery() {
    let pageSize = queryForm.pageSize;
    Object.assign(queryForm, queryFormState);
    queryForm.pageSize = pageSize;
    queryData();
  }

  // 查询数据
  async function queryData() {
    tableLoading.value = true;
    try {
      let queryResult = await changeLogApi.queryPage(queryForm);
      tableData.value = queryResult.data.list;
      total.value = queryResult.data.total;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  function onChangePublicDate(dates, dateStrings) {
    queryForm.publicDateBegin = dateStrings[0];
    queryForm.publicDateEnd = dateStrings[1];
  }

  onMounted(queryData);

  // ---------------------------- 查看 ----------------------------
  const modalRef = ref();

  function showModal(data) {
    modalRef.value.show(data);
  }

  // ---------------------------- 添加/修改 ----------------------------
  const formRef = ref();

  function showForm(data) {
    formRef.value.show(data);
  }

  // ---------------------------- 单个删除 ----------------------------
  //确认删除
  function onDelete(data) {
    Modal.confirm({
      title: '팁',
      content: '선택 항목을 제거하시겠습니까?',
      okText: 'OK',
      okType: 'danger',
      onOk() {
        requestDelete(data);
      },
      cancelText: '취소',
      onCancel() {},
    });
  }

  //请求删除
  async function requestDelete(data) {
    SmartLoading.show();
    try {
      let deleteForm = {
        goodsIdList: selectedRowKeyList.value,
      };
      await changeLogApi.delete(data.changeLogId);
      message.success('성공적으로 삭제됨');
      queryData();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }

  // ---------------------------- 批量删除 ----------------------------

  // 选择表格行
  const selectedRowKeyList = ref([]);

  function onSelectChange(selectedRowKeys) {
    selectedRowKeyList.value = selectedRowKeys;
  }

  // 批量删除
  function confirmBatchDelete() {
    Modal.confirm({
      title: '팁',
      content: '이 데이터를 일괄 삭제하시겠습니까?',
      okText: 'OK',
      okType: 'danger',
      onOk() {
        requestBatchDelete();
      },
      cancelText: '취소',
      onCancel() {},
    });
  }

  //请求批量删除
  async function requestBatchDelete() {
    try {
      SmartLoading.show();
      await changeLogApi.batchDelete(selectedRowKeyList.value);
      message.success('성공적으로 삭제됨');
      queryData();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }
</script>
