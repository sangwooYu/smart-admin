<!--
  * 缓存
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-06-08 21:50:41
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <a-card size="small" :bordered="false" :hoverable="true">
    <a-alert>
      <template v-slot:message>
        <h4>캐시 소개:</h4>
      </template>
      <template v-slot:description>
        <pre>
소개: SmartAdmin은 SpringCache를 사용하여 캐시를 관리하며, SpringCache의 다양한 구현이 있으며, 이 프로젝트의 기본값은 카페인입니다.
Caffeine:
- Caffeine은 가장 빠른 캐시로 알려진 고성능 Java 캐싱 라이브러리인 캐시 동시성 처리량을 크게 향상시키기 위해 최신 Java 8 [StampedLock] 낙관적 잠금 기술을 사용하는 인프로세스 캐싱 프레임워크입니다.
기타:
- 분산, 클러스터링 및 기타 애플리케이션 구현의 경우 Redis, CouchBase 등으로 변경할 수 있습니다.
</pre
        >
      </template>
    </a-alert>

    <a-table size="small" bordered class="smart-margin-top10" :dataSource="tableData" :columns="columns" rowKey="tag" :pagination="false" >
      <template #bodyCell="{ record, column }">
        <template v-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button @click="remove(record.key)" v-privilege="'support:cache:delete'" type="link">캐시클리어</a-button>
            <a-button @click="getAllKeys(record.key)" v-privilege="'support:cache:keys'" type="link">모든 키 가져오기</a-button>
          </div>
        </template>
      </template>
    </a-table>
  </a-card>
</template>
<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import { onMounted, reactive, ref, h } from 'vue';
  import { cacheApi } from '/@/api/support/cache/cache-api';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { Modal } from 'ant-design-vue';
  import _ from 'lodash';
  import { smartSentry } from '/@/lib/smart-sentry';

  //------------------------ 删除 ---------------------

  async function remove(key) {
    try {
      await cacheApi.remove(key);
      message.success('성공적으로 삭제됨');
      ajaxQuery();
    } catch (e) {
      smartSentry.captureError(e);
    }
  }

  //------------------------ 获取所有key ---------------------
  async function getAllKeys(cacheName) {
    SmartLoading.show();
    try {
      let res = await cacheApi.getKeys(cacheName);
      SmartLoading.hide();
      Modal.info({
        title: '모든 키:' + cacheName,
        content: h('div', {}, [h('p', _.join(res.data, ' , '))]),
        onOk() {
          ajaxQuery();
        },
      });
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }

  //------------------------ 表格渲染 ---------------------

  const columns = reactive([
    {
      title: 'Key',
      dataIndex: 'key',
    },
    {
      title: '운영',
      dataIndex: 'action',
      fixed: 'right',
      width: 160,
    },
  ]);

  const tableLoading = ref(false);
  const tableData = ref([]);

  async function ajaxQuery() {
    try {
      tableLoading.value = true;
      let res = await cacheApi.getAllCacheNames();
      tableData.value = res.data.map((e) => Object.assign({}, { key: e }));
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  onMounted(ajaxQuery);
</script>
