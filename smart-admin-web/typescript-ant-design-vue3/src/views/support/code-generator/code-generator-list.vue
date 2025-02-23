<!--
  * 代码生成 列表
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-06-08 21:50:41 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <div>
    <a-form class="smart-query-form">
      <a-row class="smart-query-form-row">
        <a-form-item label="테이블 이름" class="smart-query-form-item">
          <a-input style="width: 300px" v-model:value="queryForm.tableNameKeywords" placeholder="테이블 이름 키워드를 입력하세요." />
        </a-form-item>

        <a-form-item class="smart-query-form-item smart-margin-left10">
          <a-button type="primary" @click="ajaxQuery">
            <template #icon>
              <ReloadOutlined />
            </template>
            문의
          </a-button>
          <a-button @click="resetQuery">
            <template #icon>
              <SearchOutlined />
            </template>
            초기화
          </a-button>
        </a-form-item>
      </a-row>
    </a-form>

    <a-card size="small" :bordered="false" :hoverable="true">
      <a-row justify="end">
        <TableOperator class="smart-margin-bottom5" v-model="columns" :tableId="TABLE_ID_CONST.SUPPORT.CONFIG" :refresh="ajaxQuery" />
      </a-row>

      <a-table size="small" :loading="tableLoading" bordered :dataSource="tableData" :columns="columns" rowKey="configId" :pagination="false">
        <template #bodyCell="{ record, index, column }">
          <template v-if="column.dataIndex === 'seq'">
            {{ index + 1 }}
          </template>
          <template v-if="column.dataIndex === 'action'">
            <div class="smart-table-operate">
              <a-button @click="showConfig(record)" type="link">코드 구성</a-button>
              <a-button @click="showPreview(record)" type="link">코드 미리보기</a-button>
              <a-button @click="download(record)" type="link">코드 다운로드</a-button>
            </div>
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
          @change="ajaxQuery"
          @showSizeChange="ajaxQuery"
          :show-total="(total) => `합계: ${total}`"
        />
      </div>
    </a-card>

    <CodeGeneratorTableConfigForm ref="codeGeneratorTableConfigFormRef" />
    <CodeGeneratorPreviewModal ref="codeGeneratorPreviewModalRef" />
  </div>
</template>
<script setup lang="ts">
  import { onMounted, reactive, ref, nextTick } from 'vue';
  import { codeGeneratorApi } from '/@/api/support/code-generator/code-generator-api';
  import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { smartSentry } from '/@/lib/smart-sentry';
  import CodeGeneratorTableConfigForm from './components/form/code-generator-table-config-form.vue';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';
  import CodeGeneratorPreviewModal from './components/preview/code-generator-preview-modal.vue';

  const columns = ref([
    {
      title: '일련 번호',
      width: 50,
      dataIndex: 'seq',
    },
    {
      title: '테이블 이름',
      dataIndex: 'tableName',
    },
    {
      title: '비고',
      dataIndex: 'tableComment',
      ellipsis: true,
    },
    {
      title: '코드 구성',
      dataIndex: 'configTime',
      width: 150,
    },
    {
      title: '테이블 생성 시간',
      dataIndex: 'createTime',
      width: 150,
    },
    {
      title: '테이블 수정 시간',
      dataIndex: 'updateTime',
      width: 150,
    },

    {
      title: '운영',
      dataIndex: 'action',
      fixed: 'right',
      width: 210,
    },
  ]);

  // ---------------- 查询数据 -----------------------

  const queryFormState = {
    configKey: '',
    pageNum: 1,
    pageSize: 10,
    tableNameKeywords: undefined,
  };
  const queryForm = reactive({ ...queryFormState });

  const tableLoading = ref(false);
  const tableData = ref([]);
  const total = ref(0);

  function resetQuery() {
    Object.assign(queryForm, queryFormState);
    ajaxQuery();
  }
  async function ajaxQuery() {
    try {
      tableLoading.value = true;
      let responseModel = await codeGeneratorApi.queryTableList(queryForm);
      const list = responseModel.data.list;
      total.value = responseModel.data.total;
      tableData.value = list;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  // ------------------------- 表单操作 弹窗 ------------------------------

  const codeGeneratorTableConfigFormRef = ref();
  function showConfig(rowData) {
    codeGeneratorTableConfigFormRef.value.showModal(rowData);
  }

  // ------------------------- 预览 弹窗 ------------------------------

  const codeGeneratorPreviewModalRef = ref();
  function showPreview(rowData) {
    codeGeneratorPreviewModalRef.value.showModal(rowData);
  }

  // ------------------------- 下载 ------------------------------
  
  function download(rowData) {
    codeGeneratorApi.downloadCode(rowData.tableName);
  }

  onMounted(ajaxQuery);
</script>
