<!--
  * 商品列表
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <!---------- 查询表单form begin ----------->
  <a-form class="smart-query-form">
    <a-row class="smart-query-form-row" v-privilege="'goods:query'">
      <a-form-item label="제품 카테고리" class="smart-query-form-item">
        <category-tree
          width="150px"
          v-model:value="queryForm.categoryId"
          placeholder="제품 카테고리를 선택하세요."
          :categoryType="CATEGORY_TYPE_ENUM.GOODS.value"
        />
      </a-form-item>

      <a-form-item label="제품 이름" class="smart-query-form-item">
        <a-input style="width: 200px" v-model:value="queryForm.searchWord" placeholder="제품 이름" />
      </a-form-item>

      <a-form-item label="원산지" name="place" class="smart-query-form-item">
        <DictSelect key-code="GODOS_PLACE" v-model:value="queryForm.place" width="120px" />
      </a-form-item>

      <a-form-item label="제품 상태" name="goodsStatus" class="smart-query-form-item">
        <SmartEnumSelect enum-name="GOODS_STATUS_ENUM" v-model:value="queryForm.goodsStatus" width="160px" />
      </a-form-item>

      <a-form-item label="빠른 심사" class="smart-query-form-item">
        <a-radio-group v-model:value="queryForm.shelvesFlag" @change="queryData">
          <a-radio-button :value="undefined">모두</a-radio-button>
          <a-radio-button :value="true">선반으로 이동</a-radio-button>
          <a-radio-button :value="false">내려놓기</a-radio-button>
        </a-radio-group>
      </a-form-item>

      <a-form-item class="smart-query-form-item">
        <a-button type="primary" @click="queryData" v-privilege="'goods:query'">
          <template #icon>
            <ReloadOutlined />
          </template>
          문의
        </a-button>
        <a-button @click="resetQuery" class="smart-margin-left10" v-privilege="'goods:query'">
          <template #icon>
            <SearchOutlined />
          </template>
          초기화
        </a-button>
      </a-form-item>
    </a-row>
  </a-form>
  <!---------- 查询表单form end ----------->

  <a-card size="small" :bordered="false" :hoverable="true">
    <!---------- 表格操作行 begin ----------->
    <a-row class="smart-table-btn-block">
      <div class="smart-table-operate-block">
        <a-button @click="addGoods" type="primary" size="small" v-privilege="'goods:add'">
          <template #icon>
            <PlusOutlined />
          </template>
          신규 추가
        </a-button>

        <a-button @click="confirmBatchDelete" type="danger" size="small" :disabled="selectedRowKeyList.length == 0" v-privilege="'goods:batchDelete'">
          <template #icon>
            <DeleteOutlined />
          </template>
          일괄 삭제
        </a-button>
      </div>
      <div class="smart-table-setting-block">
        <TableOperator v-model="columns" :tableId="TABLE_ID_CONST.BUSINESS.ERP.GOODS" :refresh="queryData" />
      </div>
    </a-row>
    <!---------- 表格操作行 end ----------->

    <a-table
      size="small"
      :dataSource="tableData"
      :columns="columns"
      rowKey="goodsId"
      bordered
      :pagination="false"
      :row-selection="{ selectedRowKeys: selectedRowKeyList, onChange: onSelectChange }"
    >
      <template #bodyCell="{ text, record, column }">
        <template v-if="column.dataIndex === 'place'">
          <span>{{ text && text.length > 0 ? text[0].valueName : '' }}</span>
        </template>
        <template v-if="column.dataIndex === 'goodsStatus'">
          <span>{{ $smartEnumPlugin.getDescByValue('GOODS_STATUS_ENUM', text) }}</span>
        </template>
        <template v-if="column.dataIndex === 'shelvesFlag'">
          <span>{{ text ? '선반에 있음' : '선반에서 제외됨' }}</span>
        </template>
        <template v-if="column.dataIndex === 'action'">
          <div class="smart-table-operate">
            <a-button @click="addGoods(record)" type="link" v-privilege="'goods:update'">편집기</a-button>
            <a-button @click="deleteGoods(record)" danger type="link" v-privilege="'goods:delete'">삭제</a-button>
          </div>
        </template>
      </template>
    </a-table>

    <div class="smart-query-table-page">
      <a-pagination
        showSizeChanger
        showQuickJumper
        show-less-items
        :pageSizeOptions="PAGE_SIZE_OPTIONS"
        :defaultPageSize="queryForm.pageSize"
        v-model:current="queryForm.pageNum"
        v-model:pageSize="queryForm.pageSize"
        :total="total"
        @change="queryData"
        @showSizeChange="queryData"
        :show-total="(total) => `합계: ${total}`"
      />
    </div>

    <GoodsFormModal ref="formModal" @reloadList="queryData" />
  </a-card>
</template>
<script setup lang="ts">
  import GoodsFormModal from './components/goods-form-modal.vue';
  import { reactive, ref, onMounted } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { goodsApi } from '/@/api/business/goods/goods-api';
  import { PAGE_SIZE_OPTIONS } from '/@/constants/common-const';
  import CategoryTree from '/@/components/business/category-tree-select/index.vue';
  import { CATEGORY_TYPE_ENUM } from '/@/constants/business/erp/category-const';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';
  import { GOODS_STATUS_ENUM } from '/@/constants/business/erp/goods-const';
  import DictSelect from '/@/components/support/dict-select/index.vue';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';

  // ---------------------------- 表格列 ----------------------------

  const columns = ref([
    {
      title: '제품 카테고리',
      dataIndex: 'categoryName',
    },
    {
      title: '제품 이름',
      dataIndex: 'goodsName',
    },
    {
      title: '제품 상태',
      dataIndex: 'goodsStatus',
    },
    {
      title: '원산지',
      dataIndex: 'place',
    },
    {
      title: '제품 가격',
      dataIndex: 'price',
    },
    {
      title: '선반 상태',
      dataIndex: 'shelvesFlag',
    },
    {
      title: '비고',
      dataIndex: 'remark',
    },
    {
      title: '생성 시간',
      dataIndex: 'createTime',
      width: 150,
    },
    {
      title: '운영',
      dataIndex: 'action',
      fixed: 'right',
      width: 100,
    },
  ]);

  // ---------------------------- 查询数据表单和方法 ----------------------------

  const queryFormState = {
    categoryId: undefined,
    searchWord: '',
    goodsStatus: undefined,
    place: undefined,
    shelvesFlag: undefined,
    goodsType: undefined,
    pageNum: 1,
    pageSize: 10,
  };
  // 查询表单form
  const queryForm = reactive({ ...queryFormState });
  // 表格加载loading
  const tableLoading = ref(false);
  // 表格数据
  const tableData = ref([]);
  // 总数
  const total = ref(0);

  // 重置查询条件
  function resetQuery() {
    let pageSize = queryForm.pageSize;
    Object.assign(queryForm, queryFormState);
    queryForm.pageSize = pageSize;
    queryData();
  }

  // 查询数据
  async function queryData() {
    tableLoading.value = true;
    try {
      let queryResult = await goodsApi.queryGoodsList(queryForm);

      tableData.value = queryResult.data.list;
      total.value = queryResult.data.total;
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  onMounted(queryData);

  // ---------------------------- 添加/修改 ----------------------------
  const formModal = ref();

  function addGoods(goodsData) {
    formModal.value.showDrawer(goodsData);
  }
  // ---------------------------- 单个删除 ----------------------------

  function deleteGoods(goodsData) {
    Modal.confirm({
      title: '팁',
      content: '삭제할 항목이 있는지 확인합니다. 【' + goodsData.goodsName + '】 맞나요?',
      okText: '삭제',
      okType: 'danger',
      onOk() {
        singleDelete(goodsData);
      },
      cancelText: '취소',
      onCancel() {},
    });
  }

  async function singleDelete(goodsData) {
    try {
      SmartLoading.show();
      await goodsApi.deleteGoods(goodsData.goodsId);
      message.success('성공적으로 삭제됨');
      queryData();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }

  // ---------------------------- 批量删除 ----------------------------

  // 选择表格行
  const selectedRowKeyList = ref([]);

  function onSelectChange(selectedRowKeys) {
    selectedRowKeyList.value = selectedRowKeys;
  }

  // 批量删除
  function confirmBatchDelete() {
    Modal.confirm({
      title: '팁',
      content: '선택한 항목을 삭제하시겠습니까?',
      okText: '삭제',
      okType: 'danger',
      onOk() {
        batchDelete();
      },
      cancelText: '취소',
      onCancel() {},
    });
  }

  async function batchDelete() {
    try {
      SmartLoading.show();
      await goodsApi.batchDelete(selectedRowKeyList.value);
      message.success('성공적으로 삭제됨');
      queryData();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      SmartLoading.hide();
    }
  }
</script>
