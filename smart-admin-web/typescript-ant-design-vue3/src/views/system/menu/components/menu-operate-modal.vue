<!--
  * 菜单 表单弹窗
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-06-12 20:11:39
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <a-drawer
    :body-style="{ paddingBottom: '80px' }"
    :maskClosable="true"
    :title="form.menuId ? '편집' : '추가'"
    :visible="visible"
    :width="550"
    @close="onClose"
  >
    <a-form ref="formRef" :labelCol="{ span: labelColSpan }" :labelWrap="true" :model="form" :rules="rules">
      <a-form-item label="메뉴 유형" name="menuType">
        <a-radio-group v-model:value="form.menuType" button-style="solid">
          <a-radio-button v-for="item in MENU_TYPE_ENUM" :key="item.value" :value="item.value">
            {{ item.desc }}
          </a-radio-button>
        </a-radio-group>
      </a-form-item>
      <a-form-item :label="form.menuType === MENU_TYPE_ENUM.CATALOG.value ? '상위 디렉토리' : '상위 메뉴'">
        <MenuTreeSelect ref="parentMenuTreeSelect" v-model:value="form.parentId" />
      </a-form-item>
      <!--      目录 菜单 start   -->
      <template v-if="form.menuType == MENU_TYPE_ENUM.CATALOG.value || form.menuType == MENU_TYPE_ENUM.MENU.value">
        <a-form-item label="메뉴 이름" name="menuName">
          <a-input v-model:value="form.menuName" placeholder="메뉴 이름을 입력하세요." />
        </a-form-item>
        <a-form-item label="메뉴 아이콘" name="icon">
          <IconSelect @updateIcon="selectIcon">
            <template #iconSelect>
              <a-input v-model:value="form.icon" placeholder="메뉴 아이콘을 입력하세요." style="width: 200px" />
              <component :is="$antIcons[form.icon]" class="smart-margin-left15" style="font-size: 20px" />
            </template>
          </IconSelect>
        </a-form-item>
        <a-form-item v-if="form.menuType == MENU_TYPE_ENUM.MENU.value" label="라우팅 주소" name="path">
          <a-input v-model:value="form.path" placeholder="라우팅 주소를 입력하세요." />
        </a-form-item>
        <a-form-item label="정렬 기준" name="sort">
          <a-input-number v-model:value="form.sort" :min="0" placeholder="정렬기준을 입력하세요." style="width: 100%" />
          <h6 style="color: #ababab">값이 작을수록 우선 순위가 높아집니다.</h6>
        </a-form-item>
        <template v-if="form.menuType == MENU_TYPE_ENUM.MENU.value">
          <a-form-item v-if="form.frameFlag" label="외부 링크 주소" name="frameUrl">
            <a-input v-model:value="form.frameUrl" placeholder="외부 링크 주소를 입력하세요." />
          </a-form-item>
          <a-form-item v-else label="组件地址" name="component">
            <a-input v-model:value="form.component" placeholder="기본적으로 컴포넌트 주소는 시작 부분으로 입력하세요./@/views" />
          </a-form-item>
        </template>

        <a-form-item v-if="form.menuType == MENU_TYPE_ENUM.MENU.value" label="캐시 여부" name="cacheFlag">
          <a-switch v-model:checked="form.cacheFlag" checked-children="캐싱 켜기" un-checked-children="캐시 없음" />
        </a-form-item>
        <a-form-item v-if="form.menuType == MENU_TYPE_ENUM.MENU.value" label="외부 링크 여부" name="frameFlag">
          <a-switch v-model:checked="form.frameFlag" checked-children="외부 링크입니다." un-checked-children="외부 링크가 아닙니다." />
        </a-form-item>
        <a-form-item label="상태 표시" name="frameFlag">
          <a-switch v-model:checked="form.visibleFlag" checked-children="표시" un-checked-children="표시되지 않음" />
        </a-form-item>
        <a-form-item label="비활성화 상태" name="frameFlag">
          <a-switch v-model:checked="form.disabledFlag" checked-children="사용" un-checked-children="사용 금지" />
        </a-form-item>
      </template>
      <!--      目录 菜单 end   -->
      <!--      按钮 start   -->
      <template v-if="form.menuType == MENU_TYPE_ENUM.POINTS.value">
        <a-form-item label="함수 포인트 이름" name="menuName">
          <a-input v-model:value="form.menuName" placeholder="함수 포인트의 이름을 입력하세요." />
        </a-form-item>
        <a-form-item label="함수 포인트 연결 메뉴">
          <MenuTreeSelect ref="contextMenuTreeSelect" v-model:value="form.contextMenuId" />
        </a-form-item>
        <a-form-item label="기능 포인트 상태" name="frameFlag">
          <a-switch v-model:checked="form.disabledFlag" checked-children="사용" un-checked-children="사용 금지" />
        </a-form-item>
        <a-form-item label="권한 유형" name="permsType">
          <a-radio-group v-model:value="form.permsType" >
            <a-radio v-for="item in MENU_PERMS_TYPE_ENUM" :key="item.value" :value="item.value">
              {{ item.desc }}
            </a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item :label="form.permsType === MENU_PERMS_TYPE_ENUM.SPRING_SECURITY.value ? '권한 문자' : '프런트엔드 권한 문자'" name="webPerms">
          <a-input v-model:value="form.webPerms" placeholder="권한 문자를 입력하세요." />
        </a-form-item>
        <a-form-item label="권한 URL" name="apiPermsList" v-if="form.permsType === MENU_PERMS_TYPE_ENUM.URL.value">
          <a-select v-model:value="form.apiPermsList" mode="multiple" placeholder="인터페이스 권한을 선택하세요." style="width: 100%">
            <a-select-option v-for="item in allUrlData" :key="item.name">{{ item.url }} </a-select-option>
          </a-select>
        </a-form-item>
      </template>
      <!--      按钮 end   -->
    </a-form>
    <div class="footer">
      <a-button style="margin-right: 8px" @click="onClose">취소</a-button>
      <a-button style="margin-right: 8px" type="primary" @click="onSubmit(false)">제출하기</a-button>
      <a-button v-if="!form.menuId" type="primary" @click="onSubmit(true)">제출하고 다음 </a-button>
    </div>
  </a-drawer>
</template>
<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import _ from 'lodash';
  import { computed, nextTick, reactive, ref, watch } from 'vue';
  import MenuTreeSelect from './menu-tree-select.vue';
  import { menuApi } from '/@/api/system/menu/menu-api';
  import IconSelect from '/@/components/framework/icon-select/index.vue';
  import { MENU_DEFAULT_PARENT_ID, MENU_TYPE_ENUM, MENU_PERMS_TYPE_ENUM } from '/@/constants/system/menu-const';
  import { smartSentry } from '/@/lib/smart-sentry';
  import { SmartLoading } from '/@/components/framework/smart-loading';

  // ----------------------- 以下是字段定义 emits props ------------------------
  // emit
  const emit = defineEmits(['reloadList']);

  // ----------------------- 展开、隐藏编辑窗口 ------------------------

  // 是否展示抽屉
  const visible = ref(false);

  const labelColSpan = computed(() => {
    if (form.menuType == MENU_TYPE_ENUM.POINTS.value) {
      return 6;
    }
    return 4;
  });

  watch(visible, (e) => {
    if (e) {
      getAuthUrl();
    }
  });

  const contextMenuTreeSelect = ref();
  const parentMenuTreeSelect = ref();

  //展开编辑窗口
  async function showDrawer(rowData) {
    Object.assign(form, formDefault);
    if (rowData && !_.isEmpty(rowData)) {
      Object.assign(form, rowData);
      if (form.parentId === MENU_DEFAULT_PARENT_ID) {
        form.parentId = null;
      }
    }
    visible.value = true;
    refreshParentAndContext();
  }

  function refreshParentAndContext() {
    nextTick(() => {
      if (contextMenuTreeSelect.value) {
        contextMenuTreeSelect.value.queryMenuTree();
      }
      if (parentMenuTreeSelect.value) {
        parentMenuTreeSelect.value.queryMenuTree();
      }
    });
  }

  // 隐藏窗口
  function onClose() {
    Object.assign(form, formDefault);
    formRef.value.resetFields();
    visible.value = false;
  }

  // ----------------------- 预加载数据 ------------------------

  let allUrlData = ref([]);

  // url数据
  async function getAuthUrl() {
    let res = await menuApi.getAuthUrl();
    allUrlData.value = res.data;
  }

  // ----------------------- form表单相关操作 ------------------------

  const formRef = ref();
  const formDefault = {
    menuId: undefined,
    menuName: undefined,
    menuType: MENU_TYPE_ENUM.CATALOG.value,
    icon: undefined,
    parentId: undefined,
    path: undefined,
    permsType: MENU_PERMS_TYPE_ENUM.SPRING_SECURITY.value,
    webPerms: undefined,
    apiPermsList: undefined,
    sort: undefined,
    visibleFlag: true,
    cacheFlag: false,
    component: undefined,
    contextMenuId: undefined,
    disabledFlag: false,
    frameFlag: false,
    frameUrl: undefined,
  };
  let form = reactive({ ...formDefault });

  function continueResetForm() {
    refreshParentAndContext();
    const menuType = form.menuType;
    const parentId = form.parentId;
    const webPerms = form.webPerms;
    Object.assign(form, formDefault);
    formRef.value.resetFields();
    form.menuType = menuType;
    form.parentId = parentId;
    // 移除最后一个：后面的内容
    if (webPerms && webPerms.lastIndexOf(':')) {
      form.webPerms = webPerms.substring(0, webPerms.lastIndexOf(':') + 1);
    }
  }

  const rules = {
    menuType: [{ required: true, message: '메뉴 유형은 비워둘 수 없습니다.' }],
    permsType: [{ required: true, message: '권한 유형은 비워 둘 수 없습니다.' }],
    menuName: [
      { required: true, message: '메뉴 이름은 비워둘 수 없습니다.' },
      { max: 20, message: '메뉴 이름은 20자를 초과할 수 없습니다.', trigger: 'blur' },
    ],
    frameUrl: [
      { required: true, message: '외부 링크 주소는 비워둘 수 없습니다.' },
      { max: 500, message: '외부 링크 주소는 500자를 넘지 않아야 합니다.', trigger: 'blur' },
    ],
    path: [
      { required: true, message: '라우팅 주소는 비어 있을 수 없습니다.' },
      { max: 100, message: '라우팅 주소는 100자를 초과할 수 없습니다.', trigger: 'blur' },
    ],
    webPerms: [{ required: true, message: '프런트엔드 권한 문자는 비워둘 수 없습니다.' }],
  };

  function validateForm(formRef) {
    return new Promise((resolve) => {
      formRef
        .validate()
        .then(() => {
          resolve(true);
        })
        .catch(() => {
          resolve(false);
        });
    });
  }

  const onSubmit = async (continueFlag) => {
    let validateFormRes = await validateForm(formRef.value);
    if (!validateFormRes) {
      message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
      return;
    }
    SmartLoading.show();
    try {
      let params = _.cloneDeep(form);
      // 若无父级ID 默认设置为0
      if (!params.parentId) {
        params.parentId = 0;
      }
      if (params.menuId) {
        await menuApi.updateMenu(params);
      } else {
        await menuApi.addMenu(params);
      }
      message.success(`${params.menuId ? '수정' : '추가'}성공`);
      if (continueFlag) {
        continueResetForm();
      } else {
        onClose();
      }
      emit('reloadList');
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  };

  function selectIcon(icon) {
    form.icon = icon;
  }

  // ----------------------- 以下是暴露的方法内容 ------------------------
  defineExpose({
    showDrawer,
  });
</script>
<style lang="less" scoped>
  .footer {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #e9e9e9;
    padding: 10px 16px;
    background: #fff;
    text-align: left;
    z-index: 1;
  }
</style>
