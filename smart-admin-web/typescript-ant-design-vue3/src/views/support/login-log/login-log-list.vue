<!--
  * 登录、登出 日志
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-06-02 20:23:08 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-form class="smart-query-form" v-privilege="'loginLog:query'">
    <a-row class="smart-query-form-row">
      <a-form-item label="사용자 이름" class="smart-query-form-item">
        <a-input style="width: 300px" v-model:value="queryForm.userName" placeholder="사용자 이름" />
      </a-form-item>

      <a-form-item label="사용자 IP" class="smart-query-form-item">
        <a-input style="width: 120px" v-model:value="queryForm.ip" placeholder="IP" />
      </a-form-item>

      <a-form-item label="시간" class="smart-query-form-item">
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

  <a-card size="small" :bordered="false" :hoverable="true">
    <a-row justify="end">
      <TableOperator class="smart-margin-bottom5" v-model="columns" :tableId="TABLE_ID_CONST.SUPPORT.LOGIN_LOG" :refresh="ajaxQuery" />
    </a-row>
    <a-table size="small" :dataSource="tableData" :columns="columns" bordered rowKey="loginLogId" :pagination="false" :loading="tableLoading">
      <template #bodyCell="{ text, record, column }">
        <template v-if="column.dataIndex === 'loginResult'">
          <template v-if="text === LOGIN_RESULT_ENUM.LOGIN_SUCCESS.value">
            <a-tag color="success">로그인 성공</a-tag>
          </template>
          <template v-if="text === LOGIN_RESULT_ENUM.LOGIN_FAIL.value">
            <a-tag color="error">로그인 실패</a-tag>
          </template>
          <template v-if="text === LOGIN_RESULT_ENUM.LOGIN_OUT.value">
            <a-tag color="processing">로그아웃</a-tag>
          </template>
        </template>

        <template v-if="column.dataIndex === 'userAgent'">
          <div>{{ record.browser }} / {{ record.os }} / {{ record.device }}</div>
        </template>

        <template v-if="column.dataIndex === 'userType'">
          <span>{{ $smartEnumPlugin.getDescByValue('USER_TYPE_ENUM', text) }}</span>
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
</template>
<script setup lang="ts">
  import { onMounted, reactive, ref } from 'vue';
  import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { defaultTimeRanges } from '/@/lib/default-time-ranges';
  import uaparser from 'ua-parser-js';
  import { LOGIN_RESULT_ENUM } from '/@/constants/support/login-log-const';
  import { loginLogApi } from '/@/api/support/login-log/login-log-api';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';

  const columns = ref([
    {
      title: '사용자 ID',
      dataIndex: 'userId',
      width: 70,
    },
    {
      title: '사용자 이름',
      dataIndex: 'userName',
      ellipsis: true,
    },
    {
      title: '유형',
      dataIndex: 'userType',
      width: 50,
      ellipsis: true,
    },
    {
      title: 'IP',
      dataIndex: 'loginIp',
      ellipsis: true,
    },
    {
      title: '장비 정보',
      dataIndex: 'userAgent',
      ellipsis: true,
    },
    {
      title: '결과',
      dataIndex: 'loginResult',
      ellipsis: true,
    },
    {
      title: '비고',
      dataIndex: 'remark',
      ellipsis: true,
    },
    {
      title: '시간',
      dataIndex: 'createTime',
      width: 150,
    },
  ]);

  const queryFormState = {
    userName: '',
    ip: '',
    startDate: undefined,
    endDate: undefined,
    pageNum: 1,
    pageSize: 10,
  };
  const queryForm = reactive({ ...queryFormState });
  const createDateRange = ref([]);
  const defaultChooseTimeRange = defaultTimeRanges;
  // 时间变动
  function changeCreateDate(dates, dateStrings) {
    queryForm.startDate = dateStrings[0];
    queryForm.endDate = dateStrings[1];
  }

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
      let responseModel = await loginLogApi.queryList(queryForm);

      for (const e of responseModel.data.list) {
        if (!e.userAgent) {
          continue;
        }
        let ua = uaparser(e.userAgent);
        e.browser = ua.browser.name;
        e.os = ua.os.name;
        e.device = ua.device.vendor ? ua.device.vendor + ua.device.model : '';
      }

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
