
<template>
  <a-form class="smart-query-form" v-privilege="'enterprise:query'">
    <a-row class="smart-query-form-row">
      <a-form-item label="키워드" class="smart-query-form-item">
        <a-input style="width: 300px" v-model:value="queryForm.keywords" placeholder="업체명/연락처/연락처 번호" />
      </a-form-item>

      <a-form-item label="생성 시간" class="smart-query-form-item">
        <a-space direction="vertical" :size="12">
          <a-range-picker v-model:value="searchDate" @change="dateChange" />
        </a-space>
      </a-form-item>

      <a-form-item class="smart-query-form-item smart-margin-left10">
        <a-button type="primary" @click="ajaxQuery">
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
      </a-form-item>
    </a-row>
  </a-form>

  <a-card size="small" :bordered="false" :hoverable="true">
    <a-row class="smart-table-btn-block">
      <div class="smart-table-operate-block">
        <a-button @click="add()" v-privilege="'enterprise:add'" type="primary" size="small">
          <template #icon>
            <PlusOutlined />
          </template>
          신규 비즈니스
        </a-button>
      </div>
      <div class="smart-table-setting-block">
        <TableOperator v-model="columns" :tableId="TABLE_ID_CONST.BUSINESS.OA.ENTERPRISE" :refresh="ajaxQuery" />
      </div>
    </a-row>

    <a-table
      :scroll="{ x: 1300 }"
      size="small"
      :dataSource="tableData"
      :columns="columns"
      rowKey="enterpriseId"
      :pagination="false"
      :loading="tableLoading"
      bordered
    >
      <template #bodyCell="{ column, record, text }">
        <template v-if="column.dataIndex === 'disabledFlag'">
          {{ text ? '사용 금지' : '사용' }}
        </template>
        <template v-if="column.dataIndex === 'enterpriseName'">
          <a @click="detail(record.enterpriseId)">{{ record.enterpriseName }}</a>
        </template>
        <template v-if="column.dataIndex === 'type'">
          <span>{{ $smartEnumPlugin.getDescByValue('ENTERPRISE_TYPE_ENUM', text) }}</span>
        </template>
        <template v-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button @click="update(record.enterpriseId)" v-privilege="'enterprise:edit'" type="link">편집</a-button>
            <a-button @click="confirmDelete(record.enterpriseId)" danger v-privilege="'enterprise:delete'" type="link">삭제</a-button>
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
    <EnterpriseOperate ref="operateRef" @refresh="ajaxQuery" />
  </a-card>
</template>
<script setup lang="ts">
  import { reactive, ref, onMounted } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { enterpriseApi } from '/@/api/business/oa/enterprise-api';
  import { PAGE_SIZE, PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import { useRouter } from 'vue-router';
  import EnterpriseOperate from './components/enterprise-operate-modal.vue';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';

  // --------------------------- 企业表格 列 ---------------------------

  const columns = ref([
    {
      title: '회사 이름',
      dataIndex: 'enterpriseName',
      minWidth: 180,
      ellipsis: true,
    },
    {
      title: '통합 소셜 신용 코드',
      dataIndex: 'unifiedSocialCreditCode',
      minWidth: 170,
      ellipsis: true,
    },
    {
      title: '비즈니스 유형',
      dataIndex: 'type',
      width: 100,
    },
    {
      title: '담당자',
      width: 100,
      dataIndex: 'contact',
      ellipsis: true,
    },
    {
      title: '연락처 전화',
      width: 120,
      dataIndex: 'contactPhone',
      ellipsis: true,
    },
    {
      title: '이메일',
      minWidth: 100,
      dataIndex: 'email',
      ellipsis: true,
    },
    {
      title: '상태',
      width: 50,
      dataIndex: 'disabledFlag',
    },
    {
      title: '작성자',
      width: 60,
      dataIndex: 'createUserName',
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
      width: 100,
    },
  ]);

  // --------------------------- 查询 ---------------------------

  const queryFormState = {
    keywords: '',
    endTime: null,
    startTime: null,
    pageNum: 1,
    pageSize: PAGE_SIZE,
    searchCount: true,
  };
  const queryForm = reactive({ ...queryFormState });
  const tableLoading = ref(false);
  const tableData = ref([]);
  const total = ref(0);

  // 日期选择
  let searchDate = ref();

  function dateChange(dates, dateStrings) {
    queryForm.startTime = dateStrings[0];
    queryForm.endTime = dateStrings[1];
  }

  function resetQuery() {
    searchDate.value = [];
    Object.assign(queryForm, queryFormState);
    ajaxQuery();
  }

  async function ajaxQuery() {
    try {
      tableLoading.value = true;
      let responseModel = await enterpriseApi.pageQuery(queryForm);
      const list = responseModel.data.list;
      total.value = responseModel.data.total;
      tableData.value = list;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  // --------------------------- 删除 ---------------------------

  function confirmDelete(enterpriseId) {
    Modal.confirm({
      title: '정말 삭제하시겠습니까?',
      content: '삭제 후에는 정보를 복구할 수 없습니다.',
      okText: '삭제',
      okType: 'danger',
      onOk() {
        del(enterpriseId);
      },
      cancelText: '취소',
      onCancel() {},
    });
  }

  async function del(enterpriseId) {
    try {
      SmartLoading.show();
      await enterpriseApi.delete(enterpriseId);
      message.success('성공적으로 삭제됨');
      ajaxQuery();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }

  // --------------------------- 增加、修改、详情 ---------------------------

  let router = useRouter();
  const operateRef = ref();
  function add() {
    operateRef.value.showModal();
  }

  function update(enterpriseId) {
    operateRef.value.showModal(enterpriseId);
  }

  function detail(enterpriseId) {
    router.push({ path: '/oa/enterprise/enterprise-detail', query: { enterpriseId: enterpriseId } });
  }

  onMounted(ajaxQuery);
</script>
