<!--
  * 代码生成 新增和更新
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-22 21:50:41 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-row class="smart-margin-top10">
    <a-col flex="350px">
      <a-form ref="formRef" :model="formData" style="width: 350px" :rules="formRules" :label-col="{ span: 5 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="다음을 지원합니까?" name="isSupportInsertAndUpdate">
          <a-radio-group v-model:value="formData.isSupportInsertAndUpdate" button-style="solid">
            <a-radio-button :value="true">支持</a-radio-button>
            <a-radio-button :value="false">不支持添加、修改</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="페이지 메서드" name="pageType" v-if="formData.isSupportInsertAndUpdate">
          <a-radio-group v-model:value="formData.pageType" button-style="solid">
            <a-radio-button :value="CODE_INSERT_AND_UPDATE_PAGE_ENUM.MODAL.value">{{CODE_INSERT_AND_UPDATE_PAGE_ENUM.MODAL.desc}}</a-radio-button>
            <a-radio-button :value="CODE_INSERT_AND_UPDATE_PAGE_ENUM.DRAWER.value">{{CODE_INSERT_AND_UPDATE_PAGE_ENUM.DRAWER.desc}}</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="페이지 너비" v-show="formData.pageType !== CODE_INSERT_AND_UPDATE_PAGE_ENUM.PAGE.value" name="width" v-if="formData.isSupportInsertAndUpdate">
          <a-input v-model:value="formData.width" placeholder="모달 또는 서랍의 너비 속성 " />
        </a-form-item>
        <a-form-item label="당 행 수" name="countPerLine" v-if="formData.isSupportInsertAndUpdate">
          <a-input-number style="width: 100%" :max="24" v-model:value="formData.countPerLine" placeholder="줄당 번호를 입력하세요." />
        </a-form-item>
      </a-form>
    </a-col>
    <a-col flex="auto" style="height: auto; width: 500px" v-if="formData.isSupportInsertAndUpdate">
      <div class="form-preview">
        <a-row :gutter="20" justify="space-around">
          <a-col class="form-item" :span="spanPerLine" v-for="i of formData.countPerLine" :key="i">
            <div class="gutter-box">필드</div>
          </a-col>
        </a-row>
        <a-row :gutter="20" class="smart-margin-top10" justify="space-around">
          <a-col class="form-item" :span="spanPerLine" v-for="i of formData.countPerLine" :key="i">
            <div class="gutter-box">필드</div>
          </a-col>
        </a-row>
        <a-row :gutter="20" class="smart-margin-top10" justify="space-around">
          <a-col class="form-item" :span="spanPerLine" v-for="i of formData.countPerLine" :key="i">
            <div class="gutter-box">필드</div>
          </a-col>
        </a-row>
      </div>
    </a-col>
  </a-row>

  <a-table size="small" bordered class="smart-margin-top10" :dataSource="tableData" :columns="columns" rowKey="columnName" :pagination="false" v-if="formData.isSupportInsertAndUpdate">
    <template #bodyCell="{ text, record, index, column }">
      <template v-if="column.dataIndex === 'no'">
        {{ index + 1 }}
      </template>

      <template v-if="column.dataIndex === 'columnName'">
        <span>
          <template v-if="record.primaryKeyFlag">
            <a-tag color="#f50" style="line-height: 12px">기본 키</a-tag>
          </template>
          <template v-if="record.autoIncreaseFlag">
            <a-tag color="#f50" style="line-height: 12px">자체 증가</a-tag>
          </template>
          <br />
          {{ text }}
        </span>
      </template>

      <template v-if="column.dataIndex === 'nullableFlag'">
        <a-tag color="error" v-if="text">비어 있지 않음</a-tag>
      </template>

      <template v-if="column.dataIndex === 'required'">
        <a-checkbox v-model:checked="record.requiredFlag" />
      </template>

      <template v-if="column.dataIndex === 'insertFlag'">
        <a-checkbox v-model:checked="record.insertFlag" />
      </template>

      <template v-if="column.dataIndex === 'updateFlag'">
        <a-checkbox v-model:checked="record.updateFlag" />
      </template>

      <template v-if="column.dataIndex === 'frontComponent'">
        <SmartEnumSelect width="100%" enum-name="CODE_FRONT_COMPONENT_ENUM" v-model:value="record.frontComponent" />
      </template>
    </template>
  </a-table>
</template>

<script setup lang="ts">
  import { inject, ref, reactive, computed } from 'vue';
  import { checkExistEnum, getFrontComponent } from '../../code-generator-util';
  import SmartEnumRadio from '/@/components/framework/smart-enum-radio/index.vue';
  import { CODE_INSERT_AND_UPDATE_PAGE_ENUM } from '/@/constants/support/code-generator-const';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';
  import { message } from 'ant-design-vue';
  import { CODE_FRONT_COMPONENT_ENUM } from '/@/constants/support/code-generator-const';

  //------------------------ 全局数据 ---------------------
  const tableInfo = inject('tableInfo');

  // ------------- 表单 -------------

  const formRef = ref();

  const defaultFormData = {
    isSupportInsertAndUpdate: true, // 是否允许增加、删除
    pageType: CODE_INSERT_AND_UPDATE_PAGE_ENUM.MODAL.value, // 类型
    width: undefined, // 宽度
    countPerLine: 1, // 每行数量
  };

  const formData = reactive({ ...defaultFormData });

  const formRules = {
    isSupportInsertAndUpdate: [{ required: true, message: '추가, 삭제 허용 여부를 입력하세요.' }],
    pageType: [{ required: true, message: '페이지 방법을 입력하세요.' }],
    width: [{ required: true, message: '너비를 입력하세요.' }],
    countPerLine: [{ required: true, message: '줄당 번호를 입력하세요.' }],
  };

  // ------------- 预览 -------------

  const spanPerLine = computed(() => {
    return parseInt(20 / formData.countPerLine);
  });

  //------------------------ 表格渲染 ---------------------

  const columns = ref([
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
      title: '열 유형',
      dataIndex: 'dataType',
      width: 100,
      ellipsis: true,
    },
    {
      title: '비어 있지 않음',
      dataIndex: 'nullableFlag',
      width: 60,
    },
    {
      title: '필수 필드',
      dataIndex: 'required',
      width: 35,
    },
    {
      title: '신규',
      dataIndex: 'insertFlag',
      width: 35,
    },
    {
      title: '통신',
      dataIndex: 'updateFlag',
      width: 35,
    },
    {
      title: '프런트엔드 구성 요소',
      dataIndex: 'frontComponent',
      width: 100,
    },
  ]);

  const tableData = ref([]);

  // -------------------  表格数据 -------------------
  //初始化设置数据
  function setData(tableColumns, config) {
    //------------- 更新基础信息 -----------------
    if (config.insertAndUpdate) {
      formData.isSupportInsertAndUpdate = config.insertAndUpdate.isSupportInsertAndUpdate ? config.insertAndUpdate.isSupportInsertAndUpdate : true;
      formData.pageType = config.insertAndUpdate.pageType;
      formData.width = config.insertAndUpdate.width;
      formData.countPerLine = config.insertAndUpdate.countPerLine;
    }

    //------------- 更新字段信息 -----------------
    let insertAndUpdateFields = config.insertAndUpdate && config.insertAndUpdate.fieldList ? config.insertAndUpdate.fieldList : null;
    let fields = [];
    for (let column of tableColumns) {
      let configField = getConfigField(insertAndUpdateFields, column.columnName);

      let field = {
        columnName: column.columnName,
        columnComment: column.columnComment,
        dataType: column.dataType,
        nullableFlag: column.isNullable === 'NO' ? true : false,
        primaryKeyFlag: column.columnKey === 'PRI' ? true : false,
        autoIncreaseFlag: column.extra === 'auto_increment' ? true : false,
      };

      //表单
      field.requiredFlag = configField ? configField.requiredFlag : field.nullableFlag;
      field.insertFlag = configField ? configField.insertFlag : field.nullableFlag;
      field.updateFlag = configField ? configField.updateFlag : false;

      if (configField && configField.frontComponent) {
        field.frontComponent = configField.frontComponent;
      } else {
        field.frontComponent = checkExistEnum(column.columnComment)
          ? CODE_FRONT_COMPONENT_ENUM.ENUM_SELECT.value
          : getFrontComponent(column.dataType);
      }
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
  function getFieldsForm() {
    let fieldList = tableData.value.map((e) => {
      return {
        columnName: e.columnName,
        requiredFlag: e.requiredFlag,
        insertFlag: e.insertFlag,
        updateFlag: e.updateFlag,
        frontComponent: e.frontComponent,
      };
    });

    return {
      isSupportInsertAndUpdate: formData.isSupportInsertAndUpdate,
      pageType: formData.pageType,
      width: formData.width,
      countPerLine: formData.countPerLine,
      fieldList,
    };
  }

  // 校验表单
  function validateForm() {
    return new Promise((resolve, reject) => {
      formRef.value
        .validate()
        .then(() => {
          resolve(true);
        })
        .catch((error) => {
          message.error('기본 명명 양식 매개변수 유효성 검사 오류!');
          reject(error);
        });
    });
  }

  defineExpose({
    setData,
    getFieldsForm,
    validateForm,
  });
</script>

<style scoped lang="less">
  .form-preview {
    background-color: #efefef;
    padding: 15px 20px;
    border: 0;
  }
  .form-item {
    background: #00a0e9;
    padding: 5px 0;
    text-align: center;
    color: white;
  }
</style>
