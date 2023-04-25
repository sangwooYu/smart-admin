<!--
  * reload
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
        <h4>Smart-Reload 하트비트 서비스 소개：</h4>
      </template>
      <template v-slot:description>
        <pre>
소개: SmartReload는 프로세스를 다시 시작하지 않고도 구성을 동적으로 다시 로드하거나 미리 설정된 특정 설정을 실행할 수 있는 코드입니다.

원리:
- Java 백엔드는 프로젝트가 시작되면 데몬 스레드를 엽니다. 이 데몬 스레드는 몇 초마다 t_smart_item 테이블의 상태를 폴링합니다.
- 상태 식별자]가 [마지막 상태 식별자]와 비교하여 변경되면 사용자 지정 작업을 위해 매개변수가 SmartReload 구현 클래스로 전달됩니다.
용도:
- 메모리의 캐시를 플러시하는 데 사용
- 특정 백도어 코드를 실행하는 데 사용
- Java 핫 로딩을 수행하는 데 사용(클래스 구조가 변경되지 않는 경우)
- 서비스를 다시 시작할 수 없는 기타 애플리케이션
</pre
        >
      </template>
    </a-alert>

    <a-row justify="end">
      <TableOperator class="smart-margin-bottom5 smart-margin-top5" v-model="columns" :tableId="TABLE_ID_CONST.SUPPORT.RELOAD" :refresh="ajaxQuery" />
    </a-row>

    <a-table
      size="small"
      bordered
      class="smart-margin-top10"
      :dataSource="tableData"
      :loading="tableLoading"
      :columns="columns"
      rowKey="tag"
      :pagination="false"
    >
      <template #bodyCell="{ text, record, index, column }">
        <template v-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button @click="doReload(record.tag)" v-privilege="'reload:execute'" type="link">구현</a-button>
            <a-button @click="showResultList(record.tag)" v-privilege="'reload:result'" type="link">결과 보기</a-button>
          </div>
        </template>
      </template>
    </a-table>

    <DoReloadForm @refresh="ajaxQuery" ref="doReloadForm" />
    <ReloadResultList ref="reloadResultList" />
  </a-card>
</template>
<script setup lang="ts">
  import { onMounted, reactive, ref } from 'vue';
  import DoReloadForm from './do-reload-form-modal.vue';
  import ReloadResultList from './reload-result-list.vue';
  import { reloadApi } from '/@/api/support/reload/reload-api';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';

  //------------------------ 表格渲染 ---------------------

  const columns = ref([
    {
      title: '태그',
      dataIndex: 'tag',
      width: 200,
    },
    {
      title: '운영 식별',
      dataIndex: 'identification',
    },
    {
      title: '매개변수',
      dataIndex: 'args',
    },
    {
      title: '업데이트 시간',
      dataIndex: 'updateTime',
      width: 150,
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
      width: 150,
    },
  ]);

  const tableLoading = ref(false);
  const tableData = ref([]);

  async function ajaxQuery() {
    try {
      tableLoading.value = true;
      let res = await reloadApi.queryList();
      tableData.value = res.data;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  onMounted(ajaxQuery);

  // ------------------------------ 表格操作列： 执行 reload ------------------------------
  const doReloadForm = ref();
  function doReload(tag) {
    doReloadForm.value.showModal(tag);
  }

  // ------------------------------ 表格操作列： 查看执行结果 ------------------------------

  const reloadResultList = ref();
  function showResultList(tag) {
    reloadResultList.value.showModal(tag);
  }
</script>
