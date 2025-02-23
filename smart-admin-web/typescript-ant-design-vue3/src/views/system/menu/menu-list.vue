<!--
  * 菜单列表
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-06-12 20:11:39
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <div>
    <a-form class="smart-query-form">
      <a-row class="smart-query-form-row">
        <a-form-item label="키워드" class="smart-query-form-item">
          <a-input style="width: 300px" v-model:value="queryForm.keywords" placeholder="메뉴 이름 / 라우팅 주소 / 구성 요소 경로 / 권한 문자열" />
        </a-form-item>

        <a-form-item label="유형" class="smart-query-form-item">
          <SmartEnumSelect width="120px" v-model:value="queryForm.menuType" placeholder="유형을 선택하세요." enum-name="MENU_TYPE_ENUM" />
        </a-form-item>

        <a-form-item label="사용 금지" class="smart-query-form-item">
          <SmartEnumSelect width="120px" enum-name="FLAG_NUMBER_ENUM" v-model:value="queryForm.disabledFlag" />
        </a-form-item>

        <a-form-item class="smart-query-form-item smart-margin-left10">
          <a-button type="primary" @click="query">
            <template #icon>
              <ReloadOutlined />
            </template>
            문의
          </a-button>

          <a-button @click="resetQuery">
            <template #icon>
              <SearchOutlined />
            </template>
            초기화
          </a-button>
          <a-button class="smart-margin-left20" @click="moreQueryConditionFlag = !moreQueryConditionFlag">
            <template #icon>
              <MoreOutlined />
            </template>
            {{ moreQueryConditionFlag ? '가리기' : '펼치기' }}
          </a-button>
        </a-form-item>
      </a-row>

      <a-row class="smart-query-form-row" v-show="moreQueryConditionFlag">
        <a-form-item label="외부 링크" class="smart-query-form-item">
          <SmartEnumSelect width="120px" enum-name="FLAG_NUMBER_ENUM" v-model:value="queryForm.frameFlag" />
        </a-form-item>

        <a-form-item label="캐시" class="smart-query-form-item">
          <SmartEnumSelect width="120px" enum-name="FLAG_NUMBER_ENUM" v-model:value="queryForm.cacheFlag" />
        </a-form-item>

        <a-form-item label="표시" class="smart-query-form-item">
          <SmartEnumSelect width="120px" enum-name="FLAG_NUMBER_ENUM" v-model:value="queryForm.visibleFlag" />
        </a-form-item>
      </a-row>
    </a-form>

    <a-card size="small" :bordered="false" :hoverable="true">
      <a-row class="smart-table-btn-block">
        <div class="smart-table-operate-block">
          <a-button v-privilege="'system:menu:add'" type="primary" size="small" @click="showDrawer">
            <template #icon>
              <PlusOutlined />
            </template>
            메뉴 추가
          </a-button>

          <a-button v-privilege="'system:menu:batch:delete'" type="primary" danger size="small" @click="batchDelete" :disabled="!hasSelected">
            <template #icon>
              <DeleteOutlined />
            </template>
            일괄 삭제
          </a-button>
        </div>
        <div class="smart-table-setting-block">
            <TableOperator v-model="columns" :tableId="TABLE_ID_CONST.SYSTEM.MENU" :refresh="query" />
        </div>
      </a-row>

      <a-table
        :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        size="small"
        :defaultExpandAllRows="true"
        :dataSource="tableData"
        bordered
        :columns="columns"
        :loading="tableLoading"
        rowKey="menuId"
        :pagination="false"
      >
        <template #bodyCell="{ text, record, column }">
          <template v-if="column.dataIndex === 'menuType'">
            <a-tag :color="menuTypeColorArray[text]">{{ $smartEnumPlugin.getDescByValue('MENU_TYPE_ENUM', text) }}</a-tag>
          </template>

          <template v-if="column.dataIndex === 'component'">
            <span>{{ record.frameFlag ? record.frameUrl : record.component }}</span>
          </template>

          <template v-if="column.dataIndex === 'frameFlag'">
            <span>{{ $smartEnumPlugin.getDescByValue('FLAG_NUMBER_ENUM', text) }}</span>
          </template>

          <template v-if="column.dataIndex === 'permsType'">
            <span>{{ $smartEnumPlugin.getDescByValue('MENU_PERMS_TYPE_ENUM', text) }}</span>
          </template>

          <template v-if="column.dataIndex === 'cacheFlag'">
            <span>{{ $smartEnumPlugin.getDescByValue('FLAG_NUMBER_ENUM', text) }}</span>
          </template>

          <template v-if="column.dataIndex === 'visibleFlag'">
            <span>{{ $smartEnumPlugin.getDescByValue('FLAG_NUMBER_ENUM', text) }}</span>
          </template>

          <template v-if="column.dataIndex === 'disabledFlag'">
            <span>{{ $smartEnumPlugin.getDescByValue('FLAG_NUMBER_ENUM', text) }}</span>
          </template>

          <template v-if="column.dataIndex === 'icon'">
            <component :is="$antIcons[text]" />
          </template>

          <template v-if="column.dataIndex === 'operate'">
            <div class="smart-table-operate">
              <a-button v-privilege="'system:menu:update'" type="link" size="small" @click="showDrawer(record)">편집기</a-button>
              <a-button v-privilege="'system:menu:delete'" danger type="link" @click="singleDelete(record)">삭제</a-button>
            </div>
          </template>
        </template>
      </a-table>
    </a-card>

    <MenuOperateModal ref="menuOperateModal" @reloadList="query" />
  </div>
</template>
<script setup lang="ts">
  import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
  import { message, Modal } from 'ant-design-vue';
  import _ from 'lodash';
  import { computed, createVNode, onMounted, reactive, ref } from 'vue';
  import MenuOperateModal from './components/menu-operate-modal.vue';
  import { buildMenuTableTree, filterMenuByQueryForm } from './menu-data-handler';
  import { columns } from './menu-list-table-columns';
  import { menuApi } from '/@/api/system/menu/menu-api';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { smartSentry } from '/@/lib/smart-sentry';
  import TableOperator from '/@/components/support/table-operator/index.vue';
  import { TABLE_ID_CONST } from '/@/constants/support/table-id-const';

  // ------------------------ 表格渲染 ------------------------
  const menuTypeColorArray = ['red', 'blue', 'orange', 'green'];

  // ------------------------ 查询表单 ------------------------
  const queryFormState = {
    keywords: '',
    menuType: undefined,
    frameFlag: undefined,
    cacheFlag: undefined,
    visibleFlag: undefined,
    disabledFlag: undefined,
  };
  const queryForm = reactive({ ...queryFormState });
  //더 많은 검색 매개변수 확장
  const moreQueryConditionFlag = ref(true);

  // ------------------------ table表格数据和查询方法 ------------------------

  const tableLoading = ref(false);
  const tableData = ref([]);

  function resetQuery() {
    Object.assign(queryForm, queryFormState);
    query();
  }

  onMounted(query);

  async function query() {
    try {
      tableLoading.value = true;
      let responseModel = await menuApi.queryMenu();
      // 过滤搜索条件
      const filtedMenuList = filterMenuByQueryForm(responseModel.data, queryForm);
      // 递归构造树形结构，并付给 TableTree组件
      tableData.value = buildMenuTableTree(filtedMenuList);
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      tableLoading.value = false;
    }
  }

  // -------------- 多选操作 --------------
  const selectedRowKeys = ref([]);
  let selectedRows = [];
  const hasSelected = computed(() => selectedRowKeys.value.length > 0);

  function onSelectChange(keyArray, selectRows) {
    selectedRowKeys.value = keyArray;
    selectedRows = selectRows;
  }

  function singleDelete(record) {
    confirmBatchDelete([record]);
  }

  function batchDelete() {
    confirmBatchDelete(selectedRows);
  }

  function confirmBatchDelete(menuArray) {
    const menuNameArray = menuArray.map((e) => e.menuName);
    Modal.confirm({
      title: '다음 메뉴를 삭제하시겠습니까?',
      icon: createVNode(ExclamationCircleOutlined),
      content: _.join(menuNameArray, '、'),
      okText: '삭제',
      okType: 'danger',
      onOk() {
        console.log('OK');
        const menuIdList = menuArray.map((e) => e.menuId);
        requestBatchDelete(menuIdList);
        selectedRows = [];
      },
      cancelText: '취소',
      onCancel() {},
    });

    async function requestBatchDelete(menuIdList) {
      SmartLoading.show();
      try {
        await menuApi.batchDeleteMenu(menuIdList);
        message.success('성공적으로 삭제되었습니다!');
        query();
      } catch (e) {
        smartSentry.captureError(e);
      } finally {
        SmartLoading.hide();
      }
    }
  }

  // -------------- 添加、修改 右侧抽屉 --------------
  const menuOperateModal = ref();
  function showDrawer(rowData) {
    menuOperateModal.value.showDrawer(rowData);
  }
</script>
