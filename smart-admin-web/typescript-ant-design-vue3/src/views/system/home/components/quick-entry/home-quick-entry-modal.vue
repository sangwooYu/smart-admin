<template>
  <a-modal v-model:visible="visible" title="새로운 바로 가기 포털" @close="onClose">
    <a-form ref="formRef" :model="form" :rules="rules">
      <a-form-item label="아이콘" name="icon">
        <IconSelect @updateIcon="selectIcon">
          <template #iconSelect>
            <a-input v-model:value="form.icon" placeholder="메뉴 아이콘을 입력하세요." style="width: 200px"/>
            <component :is="$antIcons[form.icon]" class="smart-margin-left15" style="font-size: 20px"/>
          </template>
        </IconSelect>
      </a-form-item>
      <a-form-item label="제목" name="title">
        <a-input v-model:value="form.title" placeholder="제목을 입력하세요."/>
      </a-form-item>
      <a-form-item label="경로" name="path">
        <a-input v-model:value="form.path" placeholder="경로를 입력하세요."/>
      </a-form-item>
    </a-form>
    <template #footer>
      <a-button @click="onClose">취소</a-button>
      <a-button type="primary" @click="onSubmit">제출하기</a-button>
    </template>
  </a-modal>
</template>
<script setup lang="ts">
import {reactive, ref} from "vue";
import {message} from "ant-design-vue";
import IconSelect from '/@/components/framework/icon-select/index.vue';
import _ from "lodash";

defineExpose({
  showModal
})

const emit = defineEmits("addQuickEntry");

// 组件ref
const formRef = ref();

const formDefault = {
  icon: undefined,
  title: "",
  path: "",
};
let form = reactive({...formDefault});
const rules = {
  icon: [{required: true, message: "아이콘을 선택하세요."}],
  title: [{required: true, message: "제목은 비워 둘 수 없습니다."}],
  path: [{required: true, message: "경로는 비어 있을 수 없습니다."}],
};

const visible = ref(false);

function showModal() {
  visible.value = true;
}

function selectIcon(icon) {
  form.icon = icon;
}

function onClose() {
  Object.assign(form, formDefault);
  visible.value = false;
}

function onSubmit() {
  formRef.value
      .validate()
      .then(() => {
        emit("addQuickEntry", _.cloneDeep(form));
        onClose();
      })
      .catch((error) => {
        console.log("error", error);
        message.error("파라미터 유효성 검사 오류가 발생했습니다. 양식 데이터를 신중하게 입력하세요!");
      });
}

</script>
<style lang='less' scoped></style>
