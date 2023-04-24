<!--
  * 通知  管理列表
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-21 19:52:43 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->

<template>
  <a-form class="smart-query-form" v-privilege="'notice:query'">
    <a-row class="smart-query-form-row">
      <a-form-item label="분류" class="smart-query-form-item">
        <a-select v-model:value="queryForm.noticeTypeId" style="width: 100px" :showSearch="true" :allowClear="true" placeholder="분류">
          <a-select-option v-for="item in noticeTypeList" :key="item.noticeTypeId" :value="item.noticeTypeId">
            {{ item.noticeTypeName }}
          </a-select-option>
        </a-select>
      </a-form-item>

      <a-form-item label="핵심 단어" class="smart-query-form-item">
        <a-input style="width: 300px" v-model:value="queryForm.keywords" placeholder="제목, 저자, 출처" />
      </a-form-item>

      <a-form-item label="문서 번호" class="smart-query-form-item">
        <a-input style="width: 150px" v-model:value="queryForm.documentNumber" placeholder="문서 번호" />
      </a-form-item>

      <a-form-item label="작성자" class="smart-query-form-item">
        <a-input style="width: 100px" v-model:value="queryForm.createUserId" placeholder="작성자" />
      </a-form-item>

      <a-form-item label="삭제하기 또는 삭제하지 않기" class="smart-query-form-item">
        <SmartBooleanSelect v-model:value="queryForm.deletedFlag" style="width: 70px" />
      </a-form-item>

      <a-form-item label="릴리스 날짜" class="smart-query-form-item">
        <a-range-picker v-model:value="publishDate" @change="publishDateChange" style="width: 220px" />
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
        <a-button type="primary" size="small" @click="addOrUpdate()" v-privilege="'notice:add'">
          <template #icon>
            <PlusOutlined />
          </template>
          신규 건설
        </a-button>
      </div>
      <div class="smart-table-setting-block">
            <TableOperator v-model="tableColumns" :tableId="TABLE_ID_CONST.BUSINESS.OA.NOTICE" :refresh="queryNoticeList" />
        </div>
    </a-row>

    <a-table
      rowKey="noticeId"
      :columns="tableColumns"
      :dataSource="tableData"
      :scroll="{ x: 1510 }"
      :pagination="false"
      :loading="tableLoading"
      size="small"
      bordered
    >
      <template #bodyCell="{ column, record, text }">
        <template v-if="column.dataIndex === 'title'">
          <a @click="toDetail(record.noticeId)">{{ text }}</a>
        </template>
        <template v-else-if="column.dataIndex === 'allVisibleFlag'"> {{ text ? '모두 표시' : '부분적으로 표시' }} </template>
        <template v-else-if="column.dataIndex === 'publishFlag'">
          {{ text ? 'Published' : '게시 예정' }}
        </template>
        <template v-else-if="column.dataIndex === 'deletedFlag'">
          <a-tag v-show="text" color="error">삭제됨</a-tag>
          <a-tag v-show="!text" color="success">삭제되지 않음</a-tag>
        </template>
        <template v-else-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button type="link" @click="addOrUpdate(record.noticeId)" v-privilege="'notice:edit'">편집기</a-button>
            <a-button type="link" @click="onDelete(record.noticeId)" v-privilege="'notice:delete'" danger>삭제</a-button>
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
        @change="queryNoticeList"
        @showSizeChange="queryNoticeList"
        :show-total="(total) => `합계: ${total}`"
      />
    </div>
  </a-card>

  <NoticeFormDrawer ref="noticeFormDrawer" @reloadList="queryNoticeList" />
</template>

<script setup lang="ts">
  import { reactive, ref, onMounted } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import { useRouter } from 'vue-router';
  import { PAGE_SIZE, PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import SmartBooleanSelect from '/@/components/framework/boolean-select/index.vue';
  import { noticeApi } from '/@/api/business/oa/notice-api';
  import NoticeFormDrawer from './components/notice-form-drawer.vue';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';
  

  const queryFormState = {
    noticeTypeId: undefined, //分类
    keywords: '', //标题、作者、来源
    documentNumber: '', //文号
    createUserId: undefined, //创建人
    deletedFlag: undefined, //删除标识
    createTimeBegin: null, //创建-开始时间
    createTimeEnd: null, //创建-截止时间
    publishTimeBegin: null, //发布-开始时间
    publishTimeEnd: null, //发布-截止时间
    pageNum: 1,
    pageSize: PAGE_SIZE,
  };
  const queryForm = reactive({ ...queryFormState });

  const tableColumns = ref([
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
      title: '분류',
      dataIndex: 'noticeTypeName',
      width: 60,
      ellipsis: true,
    },
    {
      title: `작성자`,
      dataIndex: 'author',
      width: 80,
      ellipsis: true,
    },
    {
      title: `출처`,
      dataIndex: 'source',
      width: 90,
      ellipsis: true,
    },

    {
      title: '가시 범위',
      dataIndex: 'allVisibleFlag',
      width: 90,
      ellipsis: true,
    },
    {
      title: '게시',
      dataIndex: 'publishFlag',
      width: 80,
    },
    {
      title: '삭제',
      dataIndex: 'deletedFlag',
      width: 80,
    },
    {
      title: '릴리스 날짜',
      dataIndex: 'publishTime',
      width: 150,
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
      title: '작성자',
      dataIndex: 'createUserName',
      width: 80,
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

  const tableData = ref([]);
  const total = ref(0);
  const tableLoading = ref(false);

  onMounted(() => {
    queryNoticeTypeList();
    queryNoticeList();
  });

  // 查询列表
  async function queryNoticeList() {
    try {
      tableLoading.value = true;
      const result = await noticeApi.queryNotice(queryForm);
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

  // ------------------ 新建、编辑 ------------------

  // 新建、编辑
  const noticeFormDrawer = ref();
  function addOrUpdate(noticeId) {
    noticeFormDrawer.value.showModal(noticeId);
  }

  // ------------------ 删除 ------------------

  // 删除
  function onDelete(noticeId) {
    Modal.confirm({
      title: '팁',
      content: '이 데이터의 삭제를 확인하시겠습니까?',
      onOk() {
        deleteNotice(noticeId);
      },
    });
  }

  // 删除API
  async function deleteNotice(noticeId) {
    try {
      tableLoading.value = true;
      await noticeApi.deleteNotice(noticeId);
      message.success('성공적으로 삭제됨');
      queryNoticeList();
    } catch (err) {
      smartSentry.captureError(err);
    } finally {
      tableLoading.value = false;
    }
  }

  // ------------------ 详情 ------------------

  // 进入详情
  const router = useRouter();
  function toDetail(noticeId) {
    router.push({
      path: '/oa/notice/notice-detail',
      query: { noticeId },
    });
  }
</script>

<style lang="less" scoped></style>
