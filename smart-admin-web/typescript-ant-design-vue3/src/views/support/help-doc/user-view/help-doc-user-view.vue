<!--
  * 帮助文档详情
  * 
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-card size="small" :bordered="false">
    <div v-if="helpDocDetail">
      <div class="content-header">
        <!--startprint-->
        <div class="content-header-title">
          {{ helpDocDetail.title }}
        </div>
        <div class="content-header-info">
          <span>독자층：{{ helpDocDetail.pageViewCount }}</span>
          <span v-show="helpDocDetail.author">작성자：{{ helpDocDetail.author }}</span>
          <span>에 게시됨：{{ helpDocDetail.createTime }}</span>
          <span>에 수정됨：{{ helpDocDetail.updateTime }}</span>
          <span @click="print">[이 페이지 인쇄]</span>
        </div>
      </div>
      <div class="content-html" v-html="helpDocDetail.contentHtml"></div>
      <!--endprint-->
    </div>
    <a-divider v-if="helpDocDetail.attachment && helpDocDetail.attachment.length > 0" />
    <div v-if="helpDocDetail.attachment && helpDocDetail.attachment.length > 0">부록:<FilePreview :fileList="helpDocDetail.attachment" /></div>
  </a-card>

  <a-card title="읽기 로그" size="small" class="smart-margin-top10" :bordered="false">
    <HelpDocViewRecordList ref="helpDocViewRecordListRef" :helpDocId="route.query.helpDocId" />
  </a-card>

  <!-- 预览附件 -->
  <FilePreview ref="filePreviewRef" />
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import HelpDocViewRecordList from './components/help-doc-view-record-list.vue';
import { helpDocApi } from '/@/api/support/help-doc/help-doc-api';
import { SmartLoading } from '/@/components/framework/smart-loading';
import FilePreview from '/@/components/support/file-preview/index.vue';
import { smartSentry } from '/@/lib/smart-sentry';

const route = useRoute();

const activeKey = ref(1);

const helpDocDetail = ref({});

onMounted(() => {
  if (route.query.helpDocId) {
    queryHelpDocDetail();
  }
});

const helpDocViewRecordListRef = ref();

// 查询详情
async function queryHelpDocDetail() {
  try {
    SmartLoading.show();
    const result = await helpDocApi.view(route.query.helpDocId);
    helpDocDetail.value = result.data;

    helpDocViewRecordListRef.value.onSearch();
  } catch (err) {
    smartSentry.captureError(err);
  } finally {
    SmartLoading.hide();
  }
}

// 预览附件
const filePreviewRef = ref();
function onPrevFile(fileItem) {
  filePreviewRef.value.showPreview(fileItem);
}

// 打印
function print() {
  let bdhtml = window.document.body.innerHTML;
  let sprnstr = '<!--startprint-->'; //必须在页面添加<!--startprint-->和<!--endprint-->而且需要打印的内容必须在它们之间
  let eprnstr = '<!--endprint-->';
  let prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr));
  prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
  let newWin = window.open(''); //新打开一个空窗口
  newWin.document.body.innerHTML = prnhtml;
  newWin.document.close(); //在IE浏览器中使用必须添加这一句
  newWin.focus(); //在IE浏览器中使用必须添加这一句
  newWin.print(); //打印
  newWin.close(); //关闭窗口
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
.content-header {
  .content-header-title {
    margin: 10px 0px;
    font-size: 20px;
    font-weight: bold;
    text-align: center;
  }
  .content-header-info {
    margin: 10px 0px;
    font-size: 14px;
    color: #888;
    text-align: center;
    span {
      margin: 0 10px;
      cursor: pointer;
    }
  }
}
.content-html {
  margin-top: 30px;
  padding: 0 8px;
  line-height: 28px;
    font-size: 14px;
  img {
    max-width: 100%;
  }
}
</style>
