<!--
  *  客服人员弹窗 
  *
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-06 20:40:16 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" width="600px" title="고객 서비스 문의" :closable="false" :maskClosable="true">
    <a-row><div style="margin-left: 180px">담당자 이메일 : sw.yu@iskra.world </div> </a-row>
<!--    <br />-->
<!--    <div class="app-qr-box">-->
<!--      <div class="app-qr">-->
<!--        <img :src="zhuoda" />-->
<!--        <span class="qr-desc strong"> qr테스트 </span>-->
<!--        <span class="qr-desc"> qr테스트 </span>-->
<!--      </div>-->
<!--      <div class="app-qr">-->
<!--        <img :src="xiaozhen" />-->
<!--        <span class="qr-desc strong"> qr테스트 </span>-->
<!--        <span class="qr-desc"> qr테스트 </span>-->
<!--      </div>-->
<!--      <div class="app-qr">-->
<!--        <img :src="lab1024" />-->
<!--        <span class="qr-desc strong"> qr테스트 </span>-->
<!--        <span class="qr-desc"> Qr테스트 </span>-->
<!--      </div>-->
<!--    </div>-->
    <template #footer>
      <a-button type="primary" @click="hide">알겠습니다.</a-button>
    </template>
  </a-modal>
</template>
<script setup lang="ts">
  import { ref, reactive, nextTick, computed } from 'vue';
  import zhuoda from '/@/assets/images/1024lab/zhuoda-wechat.jpg';
  import lab1024 from '/@/assets/images/1024lab/1024lab-gzh.jpg';
  import xiaozhen from '/@/assets/images/1024lab/xiaozhen-gzh.jpg';

  defineExpose({
    show,
  });

  const visible = ref(false);
  function show() {
    visible.value = true;
  }
  function hide() {
    visible.value = false;
  }
</script>
<style lang="less" scoped>
  .app-qr-box {
    display: flex;
    height: 170px;
    align-items: center;
    justify-content: space-around;
    .app-qr {
      display: flex;
      align-items: center;
      width: 33%;
      justify-content: center;
      flex-direction: column;
      > img {
        width: 100%;
        max-width: 150px;
        height: 100%;
        max-height: 150px;
      }
      .strong {
        font-weight: 600;
      }
      .qr-desc {
        display: flex;
        align-items: center;
        font-size: 12px;
        text-align: center;
        overflow-x: hidden;
        > img {
          width: 15px;
          height: 18px;
          margin-right: 9px;
        }
      }
    }
  }

  .ant-carousel :deep(.slick-slide) {
    text-align: center;
    height: 120px;
    line-height: 120px;
    width: 120px;
    background: #364d79;
    overflow: hidden;
  }

  .ant-carousel :deep(.slick-slide h3) {
    color: #fff;
  }
</style>
