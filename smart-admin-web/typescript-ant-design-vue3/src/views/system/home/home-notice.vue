
<template>
  <default-home-card extra="자세히 보기" icon="SoundTwoTone" title="공지 사항" @extraClick="onMore">
    <a-spin :spinning="loading">
    <div class="content-wrapper">
      <a-empty v-if="$lodash.isEmpty(data)" />
      <ul v-else>
        <li v-for="(item, index) in data" :key="index" :class="[item.viewFlag ? 'read' : 'un-read']">
          <a-tooltip placement="top">
            <template #title>
              <span>{{ item.title }}</span>
            </template>
            <a class="content" @click="toDetail(item.noticeId)">
              <a-badge :status="item.viewFlag ? 'default' : 'error'" />
              {{ item.title }}
            </a>
          </a-tooltip>
          <span class="time"> {{ item.publishDate }}</span>
        </li>
      </ul>
    </div>
    </a-spin>
  </default-home-card>
</template>
<script setup lang="ts">
  import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { noticeApi } from '/@/api/business/oa/notice-api';
import { smartSentry } from '/@/lib/smart-sentry';
import DefaultHomeCard from '/@/views/system/home/components/default-home-card.vue';

  const props = defineProps({
    noticeTypeId: {
      type: Number,
      default: 1,
    },
  });

  const queryForm = {
    noticeTypeId: props.noticeTypeId,
    pageNum: 1,
    pageSize: 6,
    searchCount: false,
  };

  let data = ref([]);

  const loading = ref(false);
  // 문의 목록
  async function queryNoticeList() {
    try {
      loading.value = true;
      const result = await noticeApi.queryEmployeeNotice(queryForm);
      data.value = result.data.list;
    } catch (err) {
      smartSentry.captureError(err);
    } finally {
      loading.value = false;
    }
  }

  onMounted(() => {
    queryNoticeList();
  });

  // 자세히 보기
  function onMore() {
    router.push({
      path: '/oa/notice/notice-employee-list',
    });
  }

  // 세부 정보 입력
  const router = useRouter();
  function toDetail(noticeId: any) {
    router.push({
      path: '/oa/notice/notice-employee-detail',
      query: { noticeId },
    });
  }
</script>
<style lang="less" scoped>
  @read-color: #666;
  .content-wrapper{
    height: 150px;
    overflow-y: hidden;
    overflow-x: hidden;
  }
  ul li {
    display: flex;
    justify-content: space-between;
    margin-bottom: 4px;

    .content {
      white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
      word-break: break-all;
      margin-right: 5px;
    }

    .time {
      flex-shrink: 0;
      color: @read-color;
      min-width: 75px;
    }
  }

  ul li :hover {
    color: @primary-color;
  }

  .un-read a {
    color: @text-color;
  }

  .read a {
    color: @read-color;
  }
</style>
