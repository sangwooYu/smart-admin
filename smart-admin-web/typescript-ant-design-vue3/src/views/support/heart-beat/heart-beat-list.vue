<!--
  * 心跳记录
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-06-02 20:23:08 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-card size="small" :bordered="false" :hoverable="true">
    <a-alert>
      <template v-slot:message>
        <h4>스마트 하트비트 하트비트 서비스 소개:</h4>
      </template>
      <template v-slot:description>
        <pre>
소개: Smart-Heart-Beat는 Java 애플리케이션의 상태 및 기타 정보를 모니터링하는 데 사용되는 하트비트 서비스입니다.
원리: Java 백엔드는 프로젝트가 시작될 때 스레드를 열고 애플리케이션의 IP와 프로세스 번호를 데이터베이스 t_heart_beat_record 테이블에 가끔씩 업데이트합니다.

용도:
1) 각 환경(개발, 테스트, 프로덕션 등)에서 시작된 모든 서비스의 목록을 일괄적으로 확인할 수 있습니다;
2) Java 애플리케이션이 살아 있는지 여부를 감지하기 위해;
3) 특정 비즈니스에서 하나의 서비스만 시작할 수 있는 경우 다른 사람이 서비스를 시작했는지 확인하기 위해;
4) *강조※</pre
        >
      </template>
    </a-alert>

    <a-form class="smart-query-form">
      <a-row class="smart-query-form-row">
        <a-form-item label="핵심 단어" class="smart-query-form-item">
          <a-input style="width: 300px" v-model:value="queryForm.keywords" placeholder="핵심 단어" />
        </a-form-item>

        <a-form-item label="심장 박동 시간" class="smart-query-form-item">
          <a-range-picker @change="changeCreateDate" v-model:value="createDateRange" :ranges="defaultChooseTimeRange" style="width: 240px" />
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
      <a-row justify="end">
        <TableOperator class="smart-margin-bottom5" v-model="columns" :tableId="TABLE_ID_CONST.SUPPORT.HEART_BEAT" :refresh="ajaxQuery" />
      </a-row>
      <a-table
        size="small"
        bordered
        :loading="tableLoading"
        class="smart-margin-top10"
        :dataSource="tableData"
        :columns="columns"
        rowKey="goodsId"
        :pagination="false"
      />
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
          :show-total="(total) => `共${total}条`"
        />
      </div>
  </a-card>
</template>
<script setup lang="ts">
  import { onMounted, reactive, ref } from 'vue';
  import { heartBeatApi } from '/@/api/support/heart-beat/heart-beat-api';
  import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { defaultTimeRanges } from '/@/lib/default-time-ranges';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';

  //------------------------ 时间选择 ---------------------
  const defaultChooseTimeRange = defaultTimeRanges;
  const createDateRange = ref([]);
  // 时间变动
  function changeCreateDate(dates, dateStrings) {
    queryForm.startDate = dateStrings[0];
    queryForm.endDate = dateStrings[1];
  }

  //------------------------ 表格渲染 ---------------------

  const columns = ref([
    {
      title: '프로젝트 경로',
      dataIndex: 'projectPath',
      ellipsis: true,
    },
    {
      title: '서버 IP',
      dataIndex: 'serverIp',
      ellipsis: true,
    },
    {
      title: '프로세스 번호',
      dataIndex: 'processNo',
      width:100
    },
    {
      title: '프로세스 시작 시간',
      dataIndex: 'processStartTime',
      width:150
    },
    {
      title: '현재 심장 박동 시간',
      dataIndex: 'heartBeatTime',
      width:150
    },
  ]);

  const queryFormState = {
    pageNum: 1,
    pageSize: 10,
    keywords: '',
    startDate: undefined,
    endDate: undefined,
  };
  const queryForm = reactive({ ...queryFormState });
  const tableLoading = ref(false);
  const tableData = ref([]);
  const total = ref(0);

  function resetQuery() {
    Object.assign(queryForm, queryFormState);
    createDateRange.value = [];
    ajaxQuery();
  }
  async function ajaxQuery() {
    try {
      tableLoading.value = true;
      let responseModel = await heartBeatApi.queryList(queryForm);
      const list = responseModel.data.list;
      total.value = responseModel.data.total;
      tableData.value = list;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  onMounted(ajaxQuery);
</script>
