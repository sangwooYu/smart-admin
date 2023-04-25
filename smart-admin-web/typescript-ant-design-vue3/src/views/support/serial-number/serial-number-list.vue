<!--
  * 单号
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <a-card size="small" :bordered="false" :hoverable="true">
    <a-alert>
      <template v-slot:message>
        <h4>일련번호 단일 번호 생성기 소개:</h4>
      </template>
      <template v-slot:description>
        <pre>
소개: SerialNumber는 주문 번호, 계약 번호, 구매 주문 번호 등 다양한 날짜와 규칙에 따라 일련의 특수 주문 번호를 생성할 수 있는 함수입니다.
원리: 내부 구현에는 세 가지가 있습니다: 1) 인메모리 잠금 기반(분산 및 클러스터링은 지원되지 않음), 2) redis 잠금 기반, 3) 업데이트용 Mysql 잠금 기반.
- 레코드의 랜덤 및 쿼리 생성 지원
- 동적 구성 지원
</pre
        >
      </template>
    </a-alert>

    <a-row justify="end">
      <TableOperator class="smart-margin-bottom5 smart-margin-top5" v-model="columns" :tableId="TABLE_ID_CONST.SUPPORT.SERIAL_NUMBER" :refresh="ajaxQuery" />
    </a-row>

    <a-table
      size="small"
      :loading="tableLoading"
      bordered
      class="smart-margin-top10"
      :dataSource="tableData"
      :columns="columns"
      rowKey="tag"
      :pagination="false"
    >
      <template #bodyCell="{ record, column }">
        <template v-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button @click="generate(record)" v-privilege="'support:serial:number:generate'" type="link">생성</a-button>
            <a-button @click="showRecord(record.serialNumberId)" v-privilege="'support:serial:number:record'" type="link">기록 보기</a-button>
          </div>
        </template>
      </template>
    </a-table>
  </a-card>

  <!---生成表单--->
  <SerialNumberGenerateFormModal ref="generateForm" @refresh="ajaxQuery" />
  <!---生成记录--->
  <SerialNumberRecordList ref="recordList" />
</template>
<script setup lang="ts">
  import { onMounted, reactive, ref } from 'vue';
import SerialNumberGenerateFormModal from './serial-number-generate-form-modal.vue';
import SerialNumberRecordList from './serial-number-record-list.vue';
import { serialNumberApi } from '/@/api/support/serial-number/serial-number-api';
import TableOperator from '/@/components/support/table-operator/index.vue';
import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';
import { smartSentry } from '/@/lib/smart-sentry';


  //------------------------ 表格渲染 ---------------------

  const columns = ref([
    {
      title: 'ID',
      dataIndex: 'serialNumberId',
    },
    {
      title: '운영',
      dataIndex: 'businessName',
    },
    {
      title: '형식',
      dataIndex: 'format',
    },
    {
      title: '주기 시간',
      dataIndex: 'ruleType',
    },
    {
      title: '초기 값',
      dataIndex: 'initNumber',
    },
    {
      title: '무작위 증가',
      dataIndex: 'stepRandomRange',
    },
    {
      title: '비고',
      dataIndex: 'remark',
    },
    {
      title: '마지막으로 생성된 주문 번호',
      dataIndex: 'lastNumber',
    },
    {
      title: '마지막으로 생성된 시간',
      dataIndex: 'lastTime',
    },
    {
      title: '운영',
      dataIndex: 'action',
      fixed: 'right',
      width: 140,
    },
  ]);

  const tableLoading = ref(false);
  const tableData = ref([]);

  async function ajaxQuery() {
    try {
      tableLoading.value = true;
      let res = await serialNumberApi.getAll();
      tableData.value = res.data;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  onMounted(ajaxQuery);

  // ------------------------------ 表格操作列： 生成 ------------------------------
  const generateForm = ref();
  function generate(record) {
    generateForm.value.showModal(record);
  }

  // ------------------------------ 表格操作列： 查看结果 ------------------------------

  const recordList = ref();
  function showRecord(serialNumberId) {
    recordList.value.showModal(serialNumberId);
  }
</script>
