<!--
  * 菜单 表单 树形下拉框
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-06-12 20:11:39
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <a-tree-select
    :value="props.value"
    :treeData="treeData"
    :fieldNames="{ label: 'menuName', key: 'menuId', value: 'menuId' }"
    show-search
    style="width: 100%"
    :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
    placeholder="메뉴를 선택하세요."
    allow-clear
    tree-default-expand-all
    treeNodeFilterProp="menuName"
    @change="treeSelectChange"
  />
</template>
<script setup lang="ts">
  import { onMounted, ref, watch } from 'vue';
  import { menuApi } from '/@/api/system/menu/menu-api';
  import _ from 'lodash';

  const props = defineProps({
    value: Number,
  });

  let treeData = ref([]);
  async function queryMenuTree() {
    let res = await menuApi.queryMenuTree(true);
    treeData.value = res.data;
  }

  onMounted(queryMenuTree);

  const emit = defineEmits(['update:value']);
  function treeSelectChange(e) {
    emit('update:value', e);
  }

  defineExpose({
    queryMenuTree,
  });
</script>
