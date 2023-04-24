<template>
  <default-home-card icon="ProfileTwoTone" title="[테스트]">
    <div class="echarts-box">
      <div class="category-main" id="category-main"></div>
    </div>
  </default-home-card>
</template>
<script setup lang="ts">
  import DefaultHomeCard from '/@/views/system/home/components/default-home-card.vue';
  import * as echarts from 'echarts';
  import { onMounted } from 'vue';

  onMounted(() => {
    init();
  });

  function init() {
    let option = {
      xAxis: {
        type: 'category',
        data: ['월요일', '화요일', '수요일', '목요일', '금요일'],
      },
      yAxis: {
        type: 'value',
      },
      legend: {},
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true,
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow',
        },
      },
      series: [
        {
          name: '우수한 편의성',
          data: [120, 200, 150, 80, 70, 110, 130],
          type: 'bar',
          backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)',
          },
        },
        {
          name: '후커',
          data: [100, 80, 120, 77, 52, 22, 190],
          type: 'bar',
          backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)',
          },
        },
        {
          name: '카이운',
          data: [200, 110, 85, 99, 120, 145, 180],
          type: 'bar',
          backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)',
          },
        },
        {
          name: '첫 새벽',
          data: [80, 70, 90, 110, 200, 44, 80],
          type: 'bar',
          backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)',
          },
        },
      ],
    };
    let chartDom = document.getElementById('category-main');
    if (chartDom) {
      let myChart = echarts.init(chartDom);
      option && myChart.setOption(option);
    }
  }
</script>
<style lang="less" scoped>
  .echarts-box {
    display: flex;
    align-items: center;
    justify-content: center;
    .category-main {
      width: 800px;
      height: 280px;
      background: #fff;
    }
  }
</style>
