<!--
  * 帮助文档 列表
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <a-form class="smart-query-form" v-privilege="'helpDoc:query'">
    <a-row class="smart-query-form-row">
      <a-form-item label="키워드" class="smart-query-form-item">
        <a-input style="width: 300px" v-model:value="queryForm.keywords" placeholder="제목, 작성자" />
      </a-form-item>

      <a-form-item label="생성 시간" class="smart-query-form-item">
        <a-range-picker v-model:value="createDate" @change="createDateChange" style="width: 220px" />
      </a-form-item>

      <a-form-item class="smart-query-form-item smart-margin-left10">
        <a-button-group>
          <a-button type="primary" @click="onSearch">
            <template #icon>
              <SearchOutlined />
            </template>
            문의
          </a-button>
          <a-button @click="onReload">
            <template #icon>
              <ReloadOutlined />
            </template>
            초기화
          </a-button>
        </a-button-group>
      </a-form-item>
    </a-row>
  </a-form>

  <a-card size="small" :bordered="false">
    <a-row class="smart-table-btn-block">
      <div class="smart-table-operate-block">
        <a-button type="primary" size="small" @click="addOrUpdate()" v-privilege="'helpDoc:add'">
          <template #icon>
            <PlusOutlined />
          </template>
          신규 추가
        </a-button>
      </div>
      <div class="smart-table-setting-block">
        <TableOperator v-model="tableColumns" :tableId="TABLE_ID_CONST.SUPPORT.HELP_DOC" :refresh="queryHelpDocList" />
      </div>
    </a-row>

    <a-table
      rowKey="helpDocId"
      :columns="tableColumns"
      :scroll="{ x: 1000 }"
      :dataSource="tableData"
      :pagination="false"
      :loading="tableLoading"
      size="small"
      bordered
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'title'">
          <router-link tag="a" target="_blank" :to="{ path: '/help-doc/detail', query: { helpDocId: record.helpDocId } }">{{
            record.title
          }}</router-link>
        </template>
        <template v-else-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button type="link" @click="addOrUpdate(record.helpDocId)" v-privilege="'helpDoc:update'">편집기</a-button>
            <a-button type="link" danger @click="onDelete(record.helpDocId)" v-privilege="'helpDoc:delete'">삭제</a-button>
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
        @change="queryHelpDocList"
        @showSizeChange="queryHelpDocList"
        :show-total="(total) => `합계: ${total}`"
      />
    </div>
  </a-card>

  <HelpDocFormDrawer ref="helpDocFormDrawerRef" @reloadList="queryHelpDocList" />
</template>

<script setup lang="ts">
  import { message, Modal } from 'ant-design-vue';
  import { onMounted, reactive, ref, watch } from 'vue';
  import HelpDocFormDrawer from './help-doc-form-drawer.vue';
  import { helpDocApi } from '/@/api/support/help-doc/help-doc-api';
  import { PAGE_SIZE, PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';

  const props = defineProps({
    // 目录id
    helpDocCatalogId: Number,
  });

  const queryFormState = {
    helpDocCatalogId: props.helpDocCatalogId, //目录
    keywords: '', //标题、作者
    createTimeBegin: null, //创建-开始时间
    createTimeEnd: null, //创建-截止时间
    pageNum: 1,
    pageSize: PAGE_SIZE,
  };
  const queryForm = reactive({ ...queryFormState });

  const tableColumns = ref([
    {
      title: `제목`,
      dataIndex: 'title',
      ellipsis: true,
    },
    {
      title: '카탈로그',
      dataIndex: 'helpDocCatalogName',
      width: 120,
      ellipsis: true,
    },
    {
      title: `작성자`,
      dataIndex: 'author',
      width: 110,
      ellipsis: true,
    },
    {
      title: '정렬 기준',
      dataIndex: 'sort',
      width: 90,
    },
    {
      title: '페이지 조회수',
      dataIndex: 'pageViewCount',
      width: 90,
    },
    {
      title: '사용자 보기',
      dataIndex: 'userViewCount',
      width: 90,
    },
    {
      title: '생성 시간',
      dataIndex: 'createTime',
      width: 150,
    },
    {
      title: '운영',
      dataIndex: 'action',
      fixed: 'right',
      width: 90,
    },
  ]);

  // ------------------ 查询相关 ------------------

  const tableData = ref([]);
  const total = ref(0);
  const tableLoading = ref(false);

  onMounted(() => {
    queryHelpDocList();
  });

  // 查询列表
  async function queryHelpDocList() {
    try {
      tableLoading.value = true;
      const result = await helpDocApi.query(queryForm);
      tableData.value = result.data.list;
      total.value = result.data.total;
    } catch (err) {
      smartSentry.captureError(err);
    } finally {
      tableLoading.value = false;
    }
  }

  // 点击查询
  function onSearch() {
    queryForm.pageNum = 1;
    queryHelpDocList();
  }

  // 点击重置
  function onReload() {
    Object.assign(queryForm, queryFormState);
    publishDate.value = [];
    createDate.value = [];
    queryHelpDocList();
  }

  // 发布日期选择
  const publishDate = ref([]);
  function publishDateChange(dates, dateStrings) {
    queryForm.publishTimeBegin = dateStrings[0];
    queryForm.publishTimeEnd = dateStrings[1];
  }
  // 创建日期选择
  const createDate = ref([]);
  function createDateChange(dates, dateStrings) {
    queryForm.createTimeBegin = dateStrings[0];
    queryForm.createTimeEnd = dateStrings[1];
  }

  // ------------------ 新建、编辑 ------------------

  // 新建、编辑
  const helpDocFormDrawerRef = ref();
  function addOrUpdate(helpDocId) {
    helpDocFormDrawerRef.value.showModal(helpDocId);
  }

  // ------------------ 删除 ------------------

  // 删除
  function onDelete(helpDocId) {
    Modal.confirm({
      title: '팁',
      content: '이 데이터의 삭제를 확인하시겠습니까?',
      onOk() {
        deleteHelpDoc(helpDocId);
      },
    });
  }

  // 删除API
  async function deleteHelpDoc(helpDocId) {
    try {
      tableLoading.value = true;
      await helpDocApi.delete(helpDocId);
      message.success('성공적으로 삭제됨');
      queryHelpDocList();
    } catch (err) {
      smartSentry.captureError(err);
    } finally {
      tableLoading.value = false;
    }
  }

  watch(
    () => props.helpDocCatalogId,
    () => {
      queryForm.helpDocCatalogId = props.helpDocCatalogId;
      onSearch();
    },
    { immediate: true }
  );
</script>

<style lang="less" scoped></style>
