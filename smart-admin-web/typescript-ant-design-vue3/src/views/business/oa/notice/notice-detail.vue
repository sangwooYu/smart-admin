<!--
  * 通知  详情
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-08-21 19:52:43 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-card style="margin-bottom: 15px" size="small">
    <a-descriptions :title="noticeDetail.title" :columns="4" size="small">
      <template #extra>
        <a-button v-if="!noticeDetail.publishFlag" type="primary" size="small" @click="onEdit">편집</a-button>
      </template>
      <a-descriptions-item label="분류">{{ noticeDetail.noticeTypeName }}</a-descriptions-item>
      <a-descriptions-item label="문서 번호">{{ noticeDetail.documentNumber }}</a-descriptions-item>
      <a-descriptions-item label="출처">{{ noticeDetail.source }}</a-descriptions-item>
      <a-descriptions-item label="작성자">{{ noticeDetail.author }}</a-descriptions-item>
      <a-descriptions-item label="페이지 조회수">{{ noticeDetail.pageViewCount }}</a-descriptions-item>
      <a-descriptions-item label="사용자 보기">{{ noticeDetail.userViewCount }}</a-descriptions-item>
      <a-descriptions-item label="생성 시간">{{ noticeDetail.createTime }}</a-descriptions-item>
      <a-descriptions-item label="릴리스 날짜">{{ noticeDetail.publishTime }}</a-descriptions-item>
      <a-descriptions-item label="시간 제한 릴리스">{{ noticeDetail.publishFlag ? 'Published' : '게시 예정' }}</a-descriptions-item>
      <a-descriptions-item label="상태 삭제">{{ noticeDetail.deletedFlag ? '삭제됨' : '삭제되지 않음' }}</a-descriptions-item>
      <a-descriptions-item v-if="!$lodash.isEmpty(noticeDetail.attachmentFile)" label="附件">
        <div class="file-list">
          <a class="file-item" v-for="item in noticeDetail.attachmentFile" :key="item.fileId" @click="onPrevFile(item)">{{ item.fileName }}</a>
        </div>
      </a-descriptions-item>
      <a-descriptions-item label="가시 범위" :span="2">
        <template v-if="noticeDetail.allVisibleFlag">全部可见</template>
        <div class="visible-list">
          <div class="visible-item" v-for="item in noticeDetail.visibleRangeList" :key="item.dataId">
            {{ item.dataName }}
          </div>
        </div>
      </a-descriptions-item>
    </a-descriptions>
  </a-card>

  <a-card size="small">
    <a-tabs v-model:activeKey="activeKey" size="small">
      <a-tab-pane :key="1" tab="콘텐츠">
        <div class="content-html" v-html="noticeDetail.contentHtml"></div>
      </a-tab-pane>
      <a-tab-pane :key="2" tab="기록 보기" force-render>
        <NoticeViewRecordList ref="noticeViewRecordList" :noticeId="route.query.noticeId" />
      </a-tab-pane>
      <a-tab-pane :key="3" tab="작업 로그" />
    </a-tabs>
  </a-card>

  <!-- 编辑 -->
  <NoticeFormDrawer ref="noticeFormDrawerRef" @reloadList="queryNoticeDetail" />

  <!-- 预览附件 -->
  <FilePreviewModal ref="filePreviewRef" />
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import NoticeFormDrawer from './components/notice-form-drawer.vue';
import NoticeViewRecordList from './components/notice-view-record-list.vue';
import { noticeApi } from '/@/api/business/oa/notice-api';
import { SmartLoading } from '/@/components/framework/smart-loading';
import FilePreviewModal from '/@/components/support/file-preview-modal/index.vue';
import { smartSentry } from '/@/lib/smart-sentry';

const route = useRoute();

const props = defineProps({
  newsType: {
    type: Number,
  },
});

const activeKey = ref(1);

const noticeDetail = ref({});
const noticeViewRecordList = ref();

onMounted(() => {
  if (route.query.noticeId) {
    queryNoticeDetail();
    noticeViewRecordList.value.onSearch();
  }
});

// 查询详情
async function queryNoticeDetail() {
  try {
    SmartLoading.show();
    const result = await noticeApi.getUpdateNoticeInfo(route.query.noticeId);
    noticeDetail.value = result.data;
  } catch (err) {
    smartSentry.captureError(err);
  } finally {
    SmartLoading.hide();
  }
}

// 点击编辑
const noticeFormDrawerRef = ref();
function onEdit() {
  noticeFormDrawerRef.value.showModal(noticeDetail.value.noticeId);
}

// 预览附件
const filePreviewRef = ref();
function onPrevFile(fileItem) {
  filePreviewRef.value.showPreview(fileItem);
}
</script>

<style lang="less" scoped>
:deep(.ant-descriptions-item-content) {
  flex: 1;
  overflow: hidden;
}
.file-list {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  .file-item {
    display: block;
    margin-right: 10px;
  }
}
.visible-list {
  display: flex;
  flex-wrap: wrap;
  .visible-item {
    margin-right: 10px;
    color: #666;
  }
}
.content-html {
  img {
    max-width: 100%;
  }
}
</style>
