<!--
  * 字典 value 弹窗
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-06-08 21:50:41 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-drawer :width="800" :visible="visible" :body-style="{ paddingBottom: '80px' }" title="사전 값" @close="onClose">
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

    <a-card size="small" :bordered="false">
      <a-row class="smart-table-btn-block">
        <div class="smart-table-operate-block">
          <a-button @click="addOrUpdateValue" type="primary" size="small">
            <template #icon>
              <PlusOutlined />
            </template>
            신규생성
          </a-button>

          <a-button @click="confirmBatchDelete" type="danger" size="small" :disabled="selectedRowKeyList.length == 0">
            <template #icon>
              <DeleteOutlined />
            </template>
            일괄 삭제
          </a-button>
        </div>
        <div class="smart-table-setting-block"></div>
      </a-row>

      <a-table
        size="small"
        :dataSource="tableData"
        :columns="columns"
        rowKey="dictValueId"
        :pagination="false"
        :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
        bordered
      >
        <template #bodyCell="{ record, column }">
          <template v-if="column.dataIndex === 'action'">
            <a-button @click="addOrUpdateValue(record)" type="link">편집기</a-button>
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
          :show-total="(total) => `합계:${total}`"
        />
      </div>
    </a-card>
    <DictValueOperateModal ref="operateModal" @reloadList="ajaxQuery" />
  </a-drawer>
</template>
<script setup lang="ts">
  import { reactive, ref } from 'vue';
  import DictValueOperateModal from './dict-value-operate-modal.vue';
  import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { dictApi } from '/@/api/support/dict/dict-api';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { Modal } from 'ant-design-vue';
  import { message } from 'ant-design-vue';
import { smartSentry } from '/@/lib/smart-sentry';

  // 是否展示抽屉
  const visible = ref(false);
  const dictKeyId = ref(undefined);

  function showModal(keyId) {
    dictKeyId.value = keyId;
    visible.value = true;
    ajaxQuery();
  }

  function onClose() {
    visible.value = false;
    dictKeyId.value = undefined;
  }

  const columns = reactive([
    {
      title: 'ID',
      width: 80,
      dataIndex: 'dictValueId',
    },
    {
      title: '코드',
      dataIndex: 'valueCode',
    },
    {
      title: '이름',
      dataIndex: 'valueName',
    },
    {
      title: '정렬 기준',
      width: 80,
      dataIndex: 'sort',
    },
    {
      title: '비고',
      dataIndex: 'remark',
    },
    {
      title: '운영',
      dataIndex: 'action',
      fixed: 'right',
    },
  ]);

  // ----------------------- 表格 查询 ------------------------

  const queryFormState = {
    dictKeyId: undefined,
    searchWord: '',
    pageNum: 1,
    pageSize: 10,
  };
  const queryForm = reactive({ ...queryFormState });
  const selectedRowKeyList = ref([]);
  const tableLoading = ref(false);
  const tableData = ref([]);
  const total = ref(0);

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
      queryForm.dictKeyId = dictKeyId.value;
      let responseModel = await dictApi.valueQuery(queryForm);
      const list = responseModel.data.list;
      total.value = responseModel.data.total;
      tableData.value = list;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  // ----------------------- 批量 删除 ------------------------

  function confirmBatchDelete() {
    Modal.confirm({
      title: '팁',
      content: '선택한 값을 삭제하시겠습니까?',
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
      await dictApi.valueDelete(selectedRowKeyList.value);
      message.success('성공적으로 삭제됨');
      ajaxQuery();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  };

  // ----------------------- 弹窗表单操作 ------------------------

  const operateModal = ref();
  function addOrUpdateValue(rowData) {
    operateModal.value.showModal(rowData, dictKeyId.value);
  }

  defineExpose({
    showModal,
  });
</script>
