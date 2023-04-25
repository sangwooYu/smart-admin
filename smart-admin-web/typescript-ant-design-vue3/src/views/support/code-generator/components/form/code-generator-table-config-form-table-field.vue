<!--
  * 代码生成 列表
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-22 21:50:41 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-table size="small" bordered  :scroll="{ x: 1000 }" class="smart-margin-top10" :dataSource="tableData" :columns="columns" rowKey="columnName" :pagination="false">
    <template #bodyCell="{ text, record, index, column }">
      <template v-if="column.dataIndex === 'no'">
        {{ index + 1 }}
      </template>

      <template v-if="column.dataIndex === 'showFlag'">
        <a-checkbox v-model:checked="record.showFlag" />
      </template>

      <template v-if="column.dataIndex === 'fieldName'">
        <a-input v-model:value="record.fieldName" />
      </template>

      <template v-if="column.dataIndex === 'label'">
        <a-input v-model:value="record.label" />
      </template>

      <template v-if="column.dataIndex === 'width'">
        <a-input-number v-model:value="record.width" />
      </template>

      <template v-if="column.dataIndex === 'ellipsisFlag'">
        <a-switch v-model:checked="record.ellipsisFlag" checked-children="자동 생략" un-checked-children="라인 피드 디스플레이" />
      </template>
    </template>
  </a-table>
</template>

<script setup lang="ts">
  import { inject, ref } from 'vue';
import { convertLowerCamel } from '/@/utils/str-util';

  //------------------------ 全局数据 ---------------------
  const tableInfo = inject('tableInfo');

  //------------------------ 表格渲染 ---------------------

  const columns = ref([
    {
      title: '일련 번호',
      dataIndex: 'no',
      width: 60,
    },
    {
      title: '리스팅',
      dataIndex: 'columnName',
      width: 120,
      ellipsis: true,
    },
    {
      title: '열 설명',
      dataIndex: 'columnComment',
      width: 120,
      ellipsis: true,
    },
    {
      title: '표시',
      dataIndex: 'showFlag',
      width: 50,
    },
    {
      title: '필드 명사',
      dataIndex: 'label',
      width: 120,
    },
    {
      title: '필드 이름 지정',
      dataIndex: 'fieldName',
      width: 120,
    },

    {
      title: '너비',
      dataIndex: 'width',
      width: 80,
    },
    {
      title: 'ellipsis',
      dataIndex: 'ellipsisFlag',
      width: 100,
    },
  ]);

  const tableData = ref([]);

  // -------------------  表格数据 -------------------
  //初始化设置数据
  function setData(tableColumns, config) {
    let fields = [];

    for (let column of tableColumns) {
      let configField = getConfigField(config.tableFields, column.columnName);

      let field = {
        columnName: column.columnName,
        columnComment: column.columnComment,
        dataType: column.dataType,
        //表单
        showFlag: configField ? configField.showFlag : true,
        label: configField ? configField.label : column.columnComment,
        fieldName: configField ? configField.fieldName : convertLowerCamel(column.columnName),
        width: configField ? configField.width : null,
        ellipsisFlag: configField ? configField.ellipsisFlag : true,
      };
      fields.push(field);
    }

    tableData.value = fields;
  }

  // 获取配置过的字段信息
  function getConfigField(configFields, columnName) {
    if (!configFields) {
      return null;
    }

    let result = configFields.filter((e) => e.columnName === columnName);
    return result && result.length > 0 ? result[0] : null;
  }

  // 获取表单数据
  function getTaleFieldsForm() {
    return tableData.value.map((e) => {
      return {
        columnName: e.columnName,
        label: e.label,
        fieldName: e.fieldName,
        showFlag: e.showFlag,
        width: e.width,
        ellipsisFlag: e.ellipsisFlag,
      };
    });
  }

  defineExpose({
    setData,
    getTaleFieldsForm,
  });
</script>
