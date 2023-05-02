<!--
  * 数据 字典
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-06-08 21:50:41
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <a-form class="smart-query-form">
    <a-row class="smart-query-form-row">
      <a-form-item label="키워드" class="smart-query-form-item">
        <a-input style="width: 300px" v-model:value="queryForm.searchWord" placeholder="키워드" />
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
    <a-row class="smart-table-btn-block">
      <div class="smart-table-operate-block">
        <a-button @click="addOrUpdateKey" v-privilege="'support:dict:add'" type="primary" size="small">
          <template #icon>
            <PlusOutlined />
          </template>
          신규 추가
        </a-button>

        <a-button @click="confirmBatchDelete" v-privilege="'support:dict:batch:delete'" type="danger" size="small" :disabled="selectedRowKeyList.length == 0">
          <template #icon>
            <DeleteOutlined />
          </template>
          일괄 삭제
        </a-button>

        <a-button @click="cacheRefresh" v-privilege="'support:dict:refresh'" type="primary" size="small">
          <template #icon>
            <cloud-sync-outlined />
          </template>
          캐시 새로 고침
        </a-button>
      </div>
      <div class="smart-table-setting-block">
        <TableOperator class="smart-margin-bottom5" v-model="columns" :tableId="TABLE_ID_CONST.SUPPORT.DICT" :refresh="ajaxQuery" />
      </div>
    </a-row>

    <a-table
      size="small"
      :dataSource="tableData"
      :columns="columns"
      :loading="tableLoading"
      rowKey="dictKeyId"
      :pagination="false"
      bordered
      :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
    >
      <template #bodyCell="{ record, column }">
        <template v-if="column.dataIndex === 'keyCode'">
          <a @click="showValueList(record.dictKeyId)">{{ record.keyCode }}</a>
        </template>
        <template v-else-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button @click="addOrUpdateKey(record)" v-privilege="'support:dict:update'" type="link">편집기</a-button>
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

    <DictKeyOperateModal ref="operateModal" @reloadList="ajaxQuery" />
    <!-- 值列表 -->
    <DictValueModal ref="dictValueModal" />
  </a-card>
</template>
<script setup lang="ts">
  import DictKeyOperateModal from './components/dict-key-operate-modal.vue';
  import DictValueModal from './components/dict-value-modal.vue';
  import { reactive, ref, onMounted } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { dictApi } from '/@/api/support/dict/dict-api';
  import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';

  const columns = ref([
    {
      title: 'ID',
      width: 90,
      dataIndex: 'dictKeyId',
    },
    {
      title: '코딩',
      dataIndex: 'keyCode',
    },
    {
      title: '이름',
      dataIndex: 'keyName',
    },
    {
      title: '비고',
      dataIndex: 'remark',
    },
    {
      title: '운영',
      dataIndex: 'action',
      fixed: 'right',
      width: 50,
    },
  ]);

  // ---------------- 查询数据 -----------------

  const queryFormState = {
    searchWord: '',
    pageNum: 1,
    pageSize: 10,
  };
  const queryForm = reactive({ ...queryFormState });
  const tableLoading = ref(false);
  const selectedRowKeyList = ref([]);
  const tableData = ref([]);
  const total = ref(0);
  const operateModal = ref();
  const dictValueModal = ref();

  // 显示操作记录弹窗
  function showValueList(dictKeyId) {
    dictValueModal.value.showModal(dictKeyId);
  }

  function onSelectChange(selectedRowKeys) {
    selectedRowKeyList.value = selectedRowKeys;
  }

  function resetQuery() {
    Object.assign(queryForm, queryFormState);
    ajaxQuery();
  }
  async function ajaxQuery() {
    try {
      tableLoading.value = true;
      let responseModel = await dictApi.keyQuery(queryForm);
      const list = responseModel.data.list;
      total.value = responseModel.data.total;
      tableData.value = list;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  // ---------------- 刷新缓存 -----------------

  async function cacheRefresh() {
    try {
      SmartLoading.show();
      await dictApi.cacheRefresh();
      message.success('캐시 플러시 성공');
      ajaxQuery();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }

  // ---------------- 批量 删除 -----------------

  function confirmBatchDelete() {
    Modal.confirm({
      title: '팁',
      content: '선택한 키를 삭제하시겠습니까?',
      okText: '삭제',
      okType: 'danger',
      onOk() {
        batchDelete();
      },
      cancelText: '취소',
      onCancel() {},
    });
  }

  const batchDelete = async () => {
    try {
      SmartLoading.show();
      await dictApi.keyDelete(selectedRowKeyList.value);
      message.success('성공적으로 삭제됨');
      ajaxQuery();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  };

  // ---------------- 添加/更新 -----------------

  function addOrUpdateKey(rowData) {
    operateModal.value.showModal(rowData);
  }

  onMounted(ajaxQuery);
</script>
