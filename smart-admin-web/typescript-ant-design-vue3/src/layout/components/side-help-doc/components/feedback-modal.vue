<!--
  *  意见反馈提交弹窗 
  *
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-06 20:40:16 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-modal :visible="visible" title="피드백" :closable="false" :maskClosable="true" >
    <a-form :labelCol="{ span: 6 }">
      <a-form-item label="불만/제안을 하고 싶습니다:">
        <a-textarea v-model:value="form.feedbackContent" placeholder="만족스럽지 못한 점을 입력해주시면 개선하도록 노력하겠습니다~." :rows="3"/>
      </a-form-item>
      <a-form-item label="피드백 이미지:">
        <Upload
            accept=".jpg,.jpeg,.png,.gif"
            :maxUploadSize="3"
            buttonText="피드백 이미지를 업로드하려면 클릭하세요."
            :default-file-list="form.feedbackAttachment || []"
            listType="picture-card"
            @change="changeAttachment"
            :folder="FILE_FOLDER_TYPE_ENUM.FEEDBACK.value"
        />
      </a-form-item>
    </a-form>
    <template #footer>
      <a-button @click="hide">취소</a-button>
      <a-button type="primary" @click="submit">제출하기</a-button>
    </template>
  </a-modal>
</template>
<script setup lang="ts">
import { reactive, ref } from 'vue';
import { SmartLoading } from '/@/components/framework/smart-loading';
import { feedbackApi } from '/@/api/support/feedback/feedback-api';
import { message } from 'ant-design-vue';
import { FILE_FOLDER_TYPE_ENUM } from '/@/constants/support/file-const';
import Upload from '/@/components/support/file-upload/index.vue';
import { smartSentry } from '/@/lib/smart-sentry';

defineExpose({
  show,
});

const visible = ref(false);

function show () {
  Object.assign(form, formDefault);
  console.log(form)
  visible.value = true;
}

function hide () {
  visible.value = false;
}

const formDefault = {
  feedbackContent:'',
  feedbackAttachment: ''
}
const form = reactive({ ...formDefault });

async function submit () {
  try {
    SmartLoading.show();
    if(!form.feedbackContent){
      message.warn('세부 정보를 입력하세요.');
      return;
    }
    await feedbackApi.addFeedback(form);
    message.success('제출 성공');
    hide();
  } catch (e) {
    smartSentry.captureError(e);
  } finally {
    SmartLoading.hide();
  }
}

function changeAttachment (fileList) {
  form.feedbackAttachment = fileList;
}
</script>
