<!--
  * reload 结果
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" title="reload결과 목록" width="60%" :footer="null" @cancel="onClose">
    <a-button type="primary" @click="ajaxQuery" size="small">
      <template #icon>
        <ReloadOutlined />
      </template>
      刷新
    </a-button>
    <a-table :scroll="{ y: 350 }" size="small" bordered rowKey="id" class="smart-margin-top10" :dataSource="tableData" :columns="columns">
      <template #bodyCell="{ text, column }">
        <template v-if="column.dataIndex === 'result'">
          <a-tag :color="text ? 'success' : 'error'">{{ text ? '성공' : '실패' }}</a-tag>
        </template>
      </template>
      <template #expandedRowRender="{ record }">
        <pre style="margin: 0; font-size: 12px">
          {{ record.exception }}
        </pre>
      </template>
    </a-table>
  </a-modal>
</template>
<script setup lang="ts">
  import { reactive, ref } from 'vue';
  import { reloadApi } from '/@/api/support/reload/reload-api';
import { smartSentry } from '/@/lib/smart-sentry';
  defineExpose({
    showModal,
  });

  // ----------------------- 表单 隐藏 与 显示 ------------------------
  // 是否展示
  const visible = ref(false);

  function showModal(tag) {
    queryTag = tag;
    ajaxQuery();
    visible.value = true;
  }

  function onClose() {
    visible.value = false;
  }

  //------------------------ 表格查询 ---------------------
  let queryTag = '';
  const tableLoading = ref(false);
  const tableData = ref([]);

  async function ajaxQuery() {
    try {
      tableLoading.value = true;
      let res = await reloadApi.queryReloadResult(queryTag);
      let count = 1;
      for (const item of res.data) {
        item.id = count++;
      }
      tableData.value = res.data;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  //------------------------ 表格列 ---------------------

  const columns = reactive([
    {
      title: '태그',
      dataIndex: 'tag',
    },
    {
      title: '매개변수',
      dataIndex: 'args',
    },
    {
      title: '실행 결과',
      dataIndex: 'result',
    },
    {
      title: '예외',
      dataIndex: 'exception',
      ellipsis: true,
    },
    {
      title: '생성 시간',
      dataIndex: 'createTime',
    },
  ]);
</script>
