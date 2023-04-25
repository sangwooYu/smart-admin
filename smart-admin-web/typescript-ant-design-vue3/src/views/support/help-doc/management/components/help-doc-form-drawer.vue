<!--
  * 帮助文档表单
  * 
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-drawer
    :title="formData.helpDocId ? '编辑' : '新建'"
    :visible="visibleFlag"
    :width="1000"
    :footerStyle="{ textAlign: 'right' }"
    @close="onClose"
    :destroyOnClose="true"
  >
    <a-form ref="formRef" :model="formData" :rules="formRules" :label-col="{ span: 3 }" :wrapper-col="{ span: 20 }">
      <a-form-item label="제목" name="title">
        <a-input v-model:value="formData.title" placeholder="제목을 입력하세요." />
      </a-form-item>
      <a-form-item label="카탈로그" name="helpDocCatalogId">
        <HelpDocCatalogTreeSelect v-model:value="formData.helpDocCatalogId" style="width: 100%" />
      </a-form-item>
      <a-form-item label="작성자" name="author">
        <a-input v-model:value="formData.author" placeholder="작성자를 입력하세요." />
      </a-form-item>
      <a-form-item label="정렬 기준" name="sort">
        <a-input-number v-model:value="formData.sort" placeholder="값이 작을수록 우선 순위가 높아집니다." />(값이 작을수록 순위가 높습니다.)
      </a-form-item>
      <a-form-item label="관련 메뉴">
        <MenuTreeSelect v-model:value="formData.relationIdList" ref="menuTreeSelect" />
      </a-form-item>
      <a-form-item label="게시판 콘텐츠" name="contentHtml">
        <Wangeditor ref="contentRef" :modelValue="formData.contentHtml" :height="300" />
      </a-form-item>
      <a-form-item label="부록">
        <Upload
          :defaultFileList="defaultFileList"
          :maxUploadSize="10"
          :folder="FILE_FOLDER_TYPE_ENUM.HELP_DOC.value"
          buttonText="첨부 파일 업로드"
          listType="text"
          extraMsg="최대 10개의 첨부 파일 업로드"
          @change="changeAttachment"
        />
      </a-form-item>
    </a-form>

    <template #footer>
      <a-space>
        <a-button @click="onClose">취소</a-button>
        <a-button type="primary" @click="onSubmit">저장</a-button>
      </a-space>
    </template>
  </a-drawer>
</template>

<script setup lang="ts">
  import { reactive, ref, nextTick } from 'vue';
  import { message, Modal } from 'ant-design-vue';
  import lodash from 'lodash';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { FILE_FOLDER_TYPE_ENUM } from '/@/constants/support/file-const';
  import { helpDocApi } from '/@/api/support/help-doc/help-doc-api';
  import Wangeditor from '/@/components/framework/wangeditor/index.vue';
  import Upload from '/@/components/support/file-upload/index.vue';
  import HelpDocCatalogTreeSelect from './help-doc-catalog-tree-select.vue';
  import MenuTreeSelect from '/@/components/system/menu-tree-select/index.vue';
  import _ from 'lodash';
import { smartSentry } from '/@/lib/smart-sentry';

  const emits = defineEmits(['reloadList']);

  // ------------------ 显示，关闭 ------------------
  // 显示
  const visibleFlag = ref(false);
  function showModal(helpDocId) {
    Object.assign(formData, defaultFormData);
    defaultFileList.value = [];
    if (helpDocId) {
      getDetail(helpDocId);
    }

    visibleFlag.value = true;
    nextTick(() => {
      formRef.value.clearValidate();
    });
  }

  // 关闭
  function onClose() {
    visibleFlag.value = false;
  }

  // ------------------ 表单 ------------------

  const formRef = ref();
  const contentRef = ref();
  const noticeFormVisibleModal = ref();

  const defaultFormData = {
    helpDocId: undefined,
    helpDocCatalogId: undefined,
    title: undefined, // 标题
    author: undefined, // 作者
    sort: 0, // 排序
    attachment: [], // 附件
    relationIdList: [], //关联id集合
    contentHtml: '', // html内容
    contentText: '', // 纯文本内容
  };

  const formData = reactive({ ...defaultFormData });

  const formRules = {
    title: [{ required: true, message: '입력하세요' }],
    helpDocCatalogId: [{ required: true, message: '카탈로그를 선택하세요.' }],
    author: [{ required: true, message: '작성자를 입력하세요.' }],
    sort: [{ required: true, message: '정렬을 입력하세요.' }],
    contentHtml: [{ required: true, message: '콘텐츠를 입력하세요.' }],
  };

  // 查询详情
  async function getDetail(helpDocId) {
    try {
      SmartLoading.show();
      const result = await helpDocApi.getDetail(helpDocId);
      const attachment = result.data.attachment;
      if (!lodash.isEmpty(attachment)) {
        defaultFileList.value = attachment;
      } else {
        defaultFileList.value = [];
      }
      Object.assign(formData, result.data);
      formData.relationIdList = result.data.relationList ? result.data.relationList.map((e) => e.relationId) : [];
    } catch (err) {
      smartSentry.captureError(err);
    } finally {
      SmartLoading.hide();
    }
  }

  // 点击确定，验证表单
  async function onSubmit() {
    try {
      formData.contentHtml = contentRef.value.getHtml();
      formData.contentText = contentRef.value.getText();
      await formRef.value.validateFields();
      save();
    } catch (err) {
      message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
    }
  }

  // 新建、编辑API
  const menuTreeSelect = ref();

  async function save() {
    try {
      SmartLoading.show();
      let param = _.cloneDeep(formData);
      let relationList = menuTreeSelect.value.getMenuListByIdList(formData.relationIdList);
      param.relationList = relationList.map((e) => Object.assign({}, { relationId: e.menuId, relationName: e.menuName }));

      if (param.helpDocId) {
        await helpDocApi.update(param);
      } else {
        await helpDocApi.add(param);
      }
      message.success('저장 성공');
      emits('reloadList');
      onClose();
    } catch (err) {
      smartSentry.captureError(err);
    } finally {
      SmartLoading.hide();
    }
  }

  // ----------------------- 上传附件 ----------------------------
  // 已上传的附件列表
  const defaultFileList = ref([]);
  function changeAttachment(fileList) {
    defaultFileList.value = fileList;
    formData.attachment = lodash.isEmpty(fileList) ? [] : fileList;
  }

  // ----------------------- 以下是暴露的方法内容 ------------------------
  defineExpose({
    showModal,
  });
</script>

<style lang="less" scoped>
  .visible-list {
    display: flex;
    flex-wrap: wrap;
    .visible-item {
      padding-top: 8px;
    }
  }
</style>
