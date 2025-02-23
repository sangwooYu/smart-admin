<!--
  * 通知  详情 （员工列表）
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-21 19:52:43 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-form class="smart-query-form">
    <a-row class="smart-query-form-row">
      <a-form-item label="분류" class="smart-query-form-item">
        <a-select v-model:value="queryForm.noticeTypeId" style="width: 100px" :showSearch="true" :allowClear="true">
          <a-select-option v-for="item in noticeTypeList" :key="item.noticeTypeId" :value="item.noticeTypeId">
            {{ item.noticeTypeName }}
          </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="키워드" class="smart-query-form-item">
        <a-input style="width: 300px" v-model:value="queryForm.keywords" placeholder="제목, 작성자, 출처, 문서 번호" />
      </a-form-item>

      <a-form-item label="릴리스 날짜" class="smart-query-form-item">
        <a-range-picker v-model:value="publishDate" @change="publishDateChange" style="width: 220px" />
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
    <a-tabs @change="changeNotViewFlag" size="small">
      <a-tab-pane :key="0" tab="모두" />
      <a-tab-pane :key="1" tab="읽지 않음" />
    </a-tabs>

    <a-table
      rowKey="noticeId"
      :columns="tableColumns"
      :dataSource="tableData"
      :scroll="{ x: 1500 }"
      :pagination="false"
      :loading="tableLoading"
      bordered
      size="small"
    >
      <template #bodyCell="{ column, record, text }">
        <template v-if="column.dataIndex === 'title'">
          <span v-show="record.viewFlag">
            <a @click="toDetail(record.noticeId)" style="color: #666">【{{ record.noticeTypeName }}】{{ text }}（읽기）</a>
          </span>
          <span v-show="!record.viewFlag">
            <a @click="toDetail(record.noticeId)"
              >【{{ record.noticeTypeName }}】{{ text }}
              <span style="color: red">（읽지 않음）</span>
            </a>
          </span>
        </template>
        <template v-if="column.dataIndex === 'pageViewCount'"> {{ record.userViewCount }}인 / {{ record.pageViewCount }}횟수 </template>
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
        @change="queryNoticeList"
        @showSizeChange="queryNoticeList"
        :show-total="(total) => `합계: ${total}`"
      />
    </div>
  </a-card>
</template>

<script setup lang="ts">
  import { reactive, ref, onMounted } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import { useRouter } from 'vue-router';
  import { PAGE_SIZE, PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import SmartBooleanSelect from '/@/components/framework/boolean-select/index.vue';
  import { noticeApi } from '/@/api/business/oa/notice-api';
import { smartSentry } from '/@/lib/smart-sentry';

  const tableColumns = reactive([
    {
      title: `제목`,
      dataIndex: 'title',
      width: 300,
      ellipsis: true,
    },

    {
      title: `문서 번호`,
      dataIndex: 'documentNumber',
      width: 100,
      ellipsis: true,
    },

    {
      title: `작성자`,
      dataIndex: 'author',
      width: 40,
      ellipsis: true,
    },
    {
      title: `출처`,
      dataIndex: 'source',
      width: 90,
      ellipsis: true,
    },
    {
      title: '릴리스 날짜',
      dataIndex: 'publishTime',
      width: 140,
    },
    {
      title: '사용자/페이지 조회수',
      dataIndex: 'pageViewCount',
      width: 90,
    },
  ]);

  // ------------------ 通知分类 ------------------

  // 查询分类列表
  const noticeTypeList = ref([]);
  async function queryNoticeTypeList() {
    try {
      const result = await noticeApi.getAllNoticeTypeList();
      noticeTypeList.value = result.data;
    } catch (err) {
      smartSentry.captureError(err);
    }
  }

  // ------------------ 查询相关 ------------------

  const queryFormState = {
    noticeTypeId: undefined, //分类
    keywords: '', //标题、作者、来源
    publishTimeBegin: null, //发布-开始时间
    publishTimeEnd: null, //发布-截止时间
    notViewFlag: false, //未读
    pageNum: 1,
    pageSize: PAGE_SIZE,
  };
  const queryForm = reactive({ ...queryFormState });

  const tableData = ref([]);
  const total = ref(0);
  const tableLoading = ref(false);

  onMounted(() => {
    queryNoticeTypeList();
    queryNoticeList();
  });

  function changeNotViewFlag(value) {
    queryForm.notViewFlag = value === 0 ? null : true;
    onSearch();
  }

  // 查询列表
  async function queryNoticeList() {
    try {
      tableLoading.value = true;
      const result = await noticeApi.queryEmployeeNotice(queryForm);
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
    queryNoticeList();
  }

  // 点击重置
  function onReload() {
    Object.assign(queryForm, queryFormState);
    publishDate.value = [];
    createDate.value = [];
    queryNoticeList();
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

  // ------------------ 详情 ------------------

  // 进入详情
  const router = useRouter();
  function toDetail(noticeId) {
    router.push({
      path: '/oa/notice/notice-employee-detail',
      query: { noticeId },
    });
  }
</script>

<style lang="less" scoped></style>
