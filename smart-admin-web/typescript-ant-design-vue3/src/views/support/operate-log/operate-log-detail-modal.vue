<!--
  * 操作记录 详情
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-06-02 20:23:08 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" title="세부 정보 요청" width="60%" :footer="null" @cancel="close">
    <div class="info-box">
      <a-row class="smart-margin-top10">
        <a-col :span="16">
          <a-row class="detail-info">
            <a-col :span="12"> 요청 주소: {{ detail.url }}</a-col>
            <a-col :span="12"> 요청 날짜: {{ detail.createTime }}</a-col>
          </a-row>
          <a-row class="detail-info">
            <a-col> 요청 방법: {{ detail.method }}</a-col>
          </a-row>
          <a-row class="detail-info">
            <a-col :span="12"> 사용자 아이디: {{ detail.operateUserId }}</a-col>
            <a-col :span="12"> 사용자 이름: {{ detail.operateUserName }}</a-col>
            <a-col :span="12"> 요청 내용: {{ detail.module }} - {{ detail.content }}</a-col>
          </a-row>
        </a-col>
        <a-col :span="8">
          <p class="detail-right-title">请求状态</p>
          <p :class="['detail-right', detail.successFlag ? 'success' : 'error']">
            {{ detail.successFlag ? '성공' : '실패' }}
          </p>
        </a-col>
      </a-row>
    </div>
    <div class="info-box">
      <h4>요청 매개변수:</h4>
      <JsonViewer :value="detail.param ? JSON.parse(detail.param) : ''" theme="jv-dark" copyable boxed sort />
    </div>
    <div class="info-box">
      <h4>요청 매개변수:</h4>
      <div>
        {{ detail.failReason }}
      </div>
    </div>
  </a-modal>
</template>

<script setup lang="ts">
  import { reactive, ref } from 'vue';
  import { JsonViewer } from 'vue3-json-viewer';
  import { operateLogApi } from '/@/api/support/operate-log/operate-log-api';
import { smartSentry } from '/@/lib/smart-sentry';
  import { SmartLoading } from '/@/components/framework/smart-loading';

  defineExpose({
    show,
  });

  const visible = ref(false);

  function show(operateLogId) {
    visible.value = true;
    clear(detail);
    getDetail(operateLogId);
  }

  const clear = (info) => {
    const keys = Object.keys(info);
    let obj = {};
    keys.forEach((item) => {
      obj[item] = '';
    });
    Object.assign(info, obj);
  };

  function close() {
    visible.value = false;
  }

  let detail = reactive({
    param: '',
    url: '',
  });
  async function getDetail(operateLogId) {
    try {
      SmartLoading.show();
      let res = await operateLogApi.detail(operateLogId);
      detail = Object.assign(detail, res.data);
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }
</script>

<style scoped lang="less">
  .detail-title {
    display: flex;
    align-items: center;
    font-size: 20px;
    font-weight: bold;
  }
  .info-box {
    border-bottom: 1px solid #f0f0f0;
    padding: 10px 8px;
  }
  .detail-info {
    .ant-col {
      line-height: 1.46;
      margin-bottom: 12px;
      padding-right: 5px;
    }
  }
  .detail-right-title {
    text-align: right;
    color: grey;
  }

  :deep(.ant-modal-body) {
    padding: 10px !important;
  }

  .detail-right {
    padding-left: 5px;
    font-size: 20px;
    font-weight: bold;
    text-align: right;
  }

  .success {
    color: @success-color;
  }

  .error {
    color: @error-color;
  }
</style>
