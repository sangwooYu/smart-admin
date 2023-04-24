<!--
  * 商品表单
  *
  * @Author:    1024创新实验室-主任：卓大
  * @Date:      2022-07-21 21:55:12
  * @Wechat:    zhuda1024
  * @Email:     lab1024@163.com
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
-->
<template>
  <a-drawer :title="form.goodsId ? '편집' : '추가'" :width="500" :visible="visible" :body-style="{ paddingBottom: '80px' }" @close="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 5 }">
      <a-form-item label="제품 카테고리" name="categoryId">
        <CategoryTree v-model:value="form.categoryId" placeholder="제품 카테고리를 선택하세요." :categoryType="CATEGORY_TYPE_ENUM.GOODS.value" />
      </a-form-item>
      <a-form-item label="제품 이름" name="goodsName">
        <a-input v-model:value="form.goodsName" placeholder="제품 이름을 입력하세요." />
      </a-form-item>
      <a-form-item label="제품 상태" name="goodsStatus">
        <SmartEnumSelect enum-name="GOODS_STATUS_ENUM" v-model:value="form.goodsStatus" />
      </a-form-item>
      <a-form-item label="원산지" name="place">
        <DictSelect key-code="GODOS_PLACE" v-model:value="form.place" />
      </a-form-item>
      <a-form-item label="선반 상태" name="shelvesFlag">
        <a-radio-group v-model:value="form.shelvesFlag">
          <a-radio :value="true">上架</a-radio>
          <a-radio :value="false">下架</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="제품 가격" name="price">
        <a-input-number style="width: 100%" placeholder="제품가격을 입력하세요" v-model:value="form.price" :min="0" />
      </a-form-item>
      <a-form-item label="비고" name="remark">
        <a-input style="width: 100%" placeholder="메모를 입력하세요." v-model:value="form.remark" />
      </a-form-item>
    </a-form>
    <div
      :style="{
        position: 'absolute',
        right: 0,
        bottom: 0,
        width: '100%',
        borderTop: '1px solid #e9e9e9',
        padding: '10px 16px',
        background: '#fff',
        textAlign: 'right',
        zIndex: 1,
      }"
    >
      <a-button style="margin-right: 8px" @click="onClose">취소</a-button>
      <a-button type="primary" @click="onSubmit">제출하기</a-button>
    </div>
  </a-drawer>
</template>
<script setup lang="ts">
  import { ref, nextTick, reactive } from 'vue';
  import CategoryTree from '/@/components/business/category-tree-select/index.vue';
  import { CATEGORY_TYPE_ENUM } from '/@/constants/business/erp/category-const';
  import { message } from 'ant-design-vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import { GOODS_STATUS_ENUM } from '/@/constants/business/erp/goods-const';
  import _ from 'lodash';
  import { goodsApi } from '/@/api/business/goods/goods-api';
  import { smartSentry } from '/@/lib/smart-sentry';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';
  import DictSelect from '/@/components/support/dict-select/index.vue';

  // emit
  const emit = defineEmits('reloadList');

  // 组件ref
  const formRef = ref();

  const formDefault = {
    //商品分类
    categoryId: undefined,
    //商品名称
    goodsName: undefined,
    //商品状态
    goodsStatus: GOODS_STATUS_ENUM.APPOINTMENT.value,
    //产地
    place: undefined,
    //商品价格
    price: undefined,
    //上架状态
    shelvesFlag: true,
    //备注
    remark: '',
    //商品id
    goodsId: undefined,
  };
  let form = reactive({ ...formDefault });
  const rules = {
    categoryId: [{ required: true, message: '제품 카테고리를 선택하세요.' }],
    goodsName: [{ required: true, message: '제품 이름은 비워 둘 수 없습니다.' }],
    goodsStatus: [{ required: true, message: '제품 상태는 비어 있을 수 없습니다.' }],
    price: [{ required: true, message: '제품 가격은 비워 둘 수 없습니다.' }],
    place: [{ required: true, message: '출발지는 비어 있을 수 없습니다.' }],
  };
  // 是否展示抽屉
  const visible = ref(false);

  function showDrawer(rowData) {
    Object.assign(form, formDefault);
    if (rowData && !_.isEmpty(rowData)) {
      Object.assign(form, rowData);
    }
    console.log(form);
    visible.value = true;
    nextTick(() => {
      formRef.value.clearValidate();
    });
  }

  function onClose() {
    Object.assign(form, formDefault);
    visible.value = false;
  }

  function onSubmit() {
    formRef.value
      .validate()
      .then(async () => {
        SmartLoading.show();
        try {
          if (form.goodsId) {
            await goodsApi.updateGoods(form);
          } else {
            await goodsApi.addGoods(form);
          }
          message.success(`${form.goodsId ? '수정' : '추가'}성공`);
          onClose();
          emit('reloadList');
        } catch (error) {
          smartSentry.captureError(error);
        } finally {
          SmartLoading.hide();
        }
      })
      .catch((error) => {
        console.log('error', error);
        message.error('파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!');
      });
  }

  defineExpose({
    showDrawer,
  });
</script>
