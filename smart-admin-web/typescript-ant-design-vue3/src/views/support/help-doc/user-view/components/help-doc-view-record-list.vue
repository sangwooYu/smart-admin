<!--
  * 查看记录
  * 
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <div>
    <a-form class="smart-query-form">
      <a-row class="smart-query-form-row">
        <a-form-item label="키워드" class="smart-query-form-item" style="width: 280px">
          <a-input v-model:value="queryForm.keywords" placeholder="이름/IP/장치" />
        </a-form-item>
        <a-form-item class="smart-query-form-item smart-margin-left10">
          <a-button-group>
            <a-button type="primary" @click="onSearch">
              <template #icon>
                <SearchOutlined />
              </template>
              문의
            </a-button>
            <a-button @click="resetQuery">
              <template #icon>
                <ReloadOutlined />
              </template>
              초기화
            </a-button>
          </a-button-group>
        </a-form-item>
      </a-row>
    </a-form>
    <a-table rowKey="employeeId" :columns="tableColumns" :dataSource="tableData" :pagination="false" :loading="tableLoading" size="small" bordered>
      <template #bodyCell="{ column, record, text }">
        <template v-if="column.dataIndex === 'firstIp'"> {{ text }} ({{ record.firstDevice }}) </template>
        <template v-if="column.dataIndex === 'lastIp'"> {{ text }} ({{ record.lastDevice }}) </template>
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
        @change="queryViewRecord"
        @showSizeChange="queryViewRecord"
        :show-total="(total) => `합계:${total}`"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
  import { reactive, ref } from 'vue';
  import { helpDocApi } from '/@/api/support/help-doc/help-doc-api';
  import { PAGE_SIZE, PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import uaparser from 'ua-parser-js';
  import { smartSentry } from '/@/lib/smart-sentry';

  const props = defineProps({
    helpDocId: {
      type: [Number, String],
    },
  });

  defineExpose({
    onSearch,
  });

  const tableColumns = [
    {
      title: '사용자 이름',
      dataIndex: 'userName',
    },
    {
      title: '시간 보기',
      dataIndex: 'pageViewCount',
      with: 100,
    },
    {
      title: '장비 처음 보기',
      dataIndex: 'firstIp',
    },
    {
      title: '첫 생성 시간',
      dataIndex: 'createTime',
      with: 120,
    },
    {
      title: '마지막으로 본 장비',
      dataIndex: 'lastIp',
    },
    {
      title: '마지막으로 본 시간',
      dataIndex: 'updateTime',
      with: 120,
    },
  ];

  const tableData = ref([]);
  const total = ref(0);
  const tableLoading = ref(false);

  const defaultQueryForm = {
    helpDocId: props.helpDocId,
    keywords: '',
    pageNum: 1,
    pageSize: PAGE_SIZE,
  };

  const queryForm = reactive({ ...defaultQueryForm });

  function buildDeviceInfo(userAgent) {
    if (!userAgent) {
      return '';
    }

    let ua = uaparser(userAgent);
    let browser = ua.browser.name;
    let os = ua.os.name;
    return browser + '/' + os + '/' + (ua.device.vendor ? ua.device.vendor + ua.device.model : '');
  }

  async function queryViewRecord() {
    try {
      tableLoading.value = true;
      const result = await helpDocApi.queryViewRecord(queryForm);

      for (const e of result.data.list) {
        e.firstDevice = buildDeviceInfo(e.firstUserAgent);
        e.lastDevice = buildDeviceInfo(e.lastUserAgent);
      }

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
    queryViewRecord();
  }

  // 点击重置
  function resetQuery() {
    Object.assign(queryForm, defaultQueryForm);
    queryViewRecord();
  }
</script>
<style lang="less" scoped>
  .ant-table.ant-table-small .ant-table-title,
  .ant-table.ant-table-small .ant-table-footer,
  .ant-table.ant-table-small .ant-table-tbody > tr > td,
  .ant-table.ant-table-small tfoot > tr > th,
  .ant-table.ant-table-small tfoot > tr > td {
    padding: 0px 3px !important;
    line-height: 28px;
  }
</style>
