<!--
  * 代码生成 配置信息
  * 
  * @Author:    1024创新实验室-主任：卓大 
  * @Date:      2022-09-22 21:50:41 
  * @Wechat:    zhuda1024 
  * @Email:     lab1024@163.com 
  * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012 
-->
<template>
  <a-row type="flex">
    <a-col flex="350px">
      <a-form ref="formRef" :model="formData" :rules="formRules" :label-col="{ span: 5 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="표"> {{ tableInfo.tableName }} </a-form-item>
        <a-form-item label="테이블 비고"> {{ tableInfo.tableComment }} </a-form-item>
        <a-form-item label="단어 이름 지정" name="moduleName">
          <a-input v-model:value="formData.moduleName" placeholder="단어 이름을 입력하세요. " />
        </a-form-item>
        <a-form-item label="Java 패키지 이름" name="javaPackageName">
          <a-input v-model:value="formData.javaPackageName" placeholder="Java 패키지 이름을 입력하세요." />
        </a-form-item>
        <a-form-item label="참고 정보" name="description">
          <a-input v-model:value="formData.description" placeholder="메모 정보를 입력하세요. " />
        </a-form-item>
        <a-form-item label="프론트엔드 작성자" name="frontAuthor">
          <a-input v-model:value="formData.frontAuthor" placeholder="프론트엔드 작성자를 입력하세요." />
        </a-form-item>
        <a-form-item label="프런트 엔드 시간" name="frontDate">
          <a-date-picker
            style="width: 100%"
            show-time
            format="YYYY-MM-DD HH:mm:ss"
            v-model:value="formData.frontDate"
            placeholder="프런트엔드 시간을 입력하세요."
          />
        </a-form-item>
        <a-form-item label="백엔드 작성자" name="backendAuthor">
          <a-input v-model:value="formData.backendAuthor" placeholder="백엔드 작성자를 입력하세요." />
        </a-form-item>
        <a-form-item label="백엔드 시간" name="backendDate">
          <a-date-picker
            style="width: 100%"
            show-time
            format="YYYY-MM-DD HH:mm:ss"
            v-model:value="formData.backendDate"
            placeholder="백엔드 시간을 입력하세요."
          />
        </a-form-item>
        <a-form-item label="저작권 정보" name="copyright">
          <a-input v-model:value="formData.copyright" placeholder="저작권 정보를 입력하세요." />
        </a-form-item>
      </a-form>
    </a-col>
    <a-col flex="auto" style="height: 100vh; overflow-y: scroll">
      <a-tabs v-model:activeKey="activeKey" size="small">
        <a-tab-pane key="1" tab="프런트엔드 파일 이름 지정">
          <div class="preview-title">프런트엔드 파일 이름</div>
          <div class="preview-block">
            <div v-for="item in frontNameList" :key="item">
              {{ item }}
            </div>
          </div>
          <div class="preview-title">프런트엔드 Vue 파일 주석</div>
          <div>
            <pre class="preview-block">
&lt;!--
  * {{ formData.description }}
  * 
  * @Author:     {{ formData.frontAuthor }}
  * @Date:       {{ formData.frontDate }}
  * @Copyright   {{ formData.copyright }}
--&gt;</pre
            >
          </div>
          <div class="preview-title">프런트엔드 Js 파일 주석</div>
          <div>
            <pre class="preview-block">
/*
 * {{ formData.description }}
 *
 * @Author:     {{ formData.frontAuthor }}
 * @Date:       {{ formData.frontDate }}
 * @Copyright   {{ formData.copyright }}
 */
              </pre
            >
          </div>
        </a-tab-pane>
        <a-tab-pane key="2" tab="백엔드 파일 이름 지정">
          <div>
            <div class="preview-title">백엔드 - 4개의 코드 레이어:</div>
            <div class="preview-block">
              <div v-for="item in backendMvcNameList" :key="item">
                {{ item }}
              </div>
            </div>
            <div class="preview-title">JavaBean 코드:</div>
            <div class="preview-block">
              <div v-for="item in backendJavaBeanNameList" :key="item">
                {{ item }}
              </div>
            </div>
            <div class="preview-title">상수 코드:</div>
            <div class="preview-block">
              <div v-for="item in backendConstNameList" :key="item">
                {{ item }}
              </div>
            </div>
            <div class="preview-title">참고:</div>
            <pre class="preview-block">
/**
 * {{ formData.description }}
 *
 * @Author:     {{ formData.backendAuthor }}
 * @Date:       {{ formData.backendDate }}
 * @Copyright   {{ formData.copyright }}
 */
              </pre
            >
          </div>
        </a-tab-pane>
      </a-tabs>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import dayjs from 'dayjs';
  import lodash from 'lodash';
  import { computed, inject, reactive, ref } from 'vue';
  import { convertLowerHyphen, convertUpperCamel } from '/@/utils/str-util';

  const tableInfo = inject('tableInfo');

  const activeKey = ref('1');

  // ------------- 表单 -------------

  const formRef = ref();

  const defaultFormData = {
    moduleName: undefined, // 单词命名
    javaPackageName: undefined, // java包名
    description: undefined, // 注释信息
    frontAuthor: undefined, // 前端作者
    frontDate: undefined, // 前端时间
    backendAuthor: undefined, // 后端作者
    backendDate: undefined, // 后端时间
    copyright: undefined, //版权
  };

  const formData = reactive({ ...defaultFormData });

  const formRules = {
    moduleName: [{ required: true, message: '단어 이름을 입력하세요.' }],
    javaPackageName: [{ required: true, message: '자바 패키지 이름을 입력하세요.' }],
    frontAuthor: [{ required: true, message: '프론트엔드 작성자를 입력하세요.' }],
    frontDate: [{ required: true, message: '프런트엔드 시간을 입력하세요.' }],
    backendAuthor: [{ required: true, message: '백엔드 작성자를 입력하세요.' }],
    backendDate: [{ required: true, message: '백엔드 시간을 입력하세요.' }],
    copyright: [{ required: true, message: '저작권을 입력하세요.' }],
  };

  //初始化设置数据
  function setData(config) {
    //基础信息
    let basic = config.basic;

    //命名
    let removePrefixTableName = tableInfo.tableName;
    if (lodash.startsWith(tableInfo.tableName, 't_')) {
      removePrefixTableName = lodash.trim(removePrefixTableName, '_t');
    }
    formData.moduleName = basic && basic.moduleName ? basic.moduleName : removePrefixTableName;
    formData.moduleName = convertUpperCamel(formData.moduleName);

    //注释
    formData.description = basic && basic.description ? basic.description : tableInfo.tableComment;
    //时间
    formData.frontDate = basic && basic.frontDate ? basic.frontDate : tableInfo.createTime;
    formData.frontDate = dayjs(formData.frontDate);
    formData.backendDate = basic && basic.backendDate ? basic.backendDate : tableInfo.createTime;
    formData.backendDate = dayjs(formData.backendDate);

    //其他字段
    formData.frontAuthor = basic && basic.frontAuthor ? basic.frontAuthor : null;
    formData.javaPackageName = basic && basic.javaPackageName ? basic.javaPackageName : null;
    formData.backendAuthor = basic && basic.backendAuthor ? basic.backendAuthor : null;
    formData.copyright = basic && basic.copyright ? basic.copyright : null;
  }

  // 获取表单数据
  const timeFormat = 'YYYY-MM-DD HH:mm:ss';
  function getBasicForm() {
    return Object.assign({}, formData, {
      frontDate: dayjs(formData.frontDate).format(timeFormat),
      backendDate: dayjs(formData.backendDate).format(timeFormat),
    });
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
    getBasicForm,
    validateForm,
  });

  // ------------- 预览 -------------

  const frontName = computed(() => convertLowerHyphen(formData.moduleName));

  const frontNameList = computed(() => {
    return [
      '요청:' + frontName.value + '-api.js', //
      '상수:' + frontName.value + '-const.js', //
      '리스팅:' + frontName.value + '-list.vue', //
      '양식:' + frontName.value + '-form-modal.vue', //
      '세부 정보:' + frontName.value + '-detail.vue', //
    ];
  });

  const backendMvcNameList = computed(() => {
    return [
      '제어 수준:' + formData.moduleName + 'Controller.java', //
      '운영 수준:' + formData.moduleName + 'Service.java', //
      '중간 레이어:' + formData.moduleName + 'Manager.java', //
      '오래 지속되는 레이어:' + formData.moduleName + 'Dao.java', //
      'SQL 계층: ' + formData.moduleName + 'Mapper.xml', //
    ];
  });
  const backendJavaBeanNameList = computed(() => {
    return [
      '엔티티 카테고리:' + formData.moduleName + 'Entity.java', //
      '성능 카테고리:' + formData.moduleName + 'VO.java', //
      '새로운 추가 카테고리:' + formData.moduleName + 'AddForm.java', //
      '갱신 카테고리:' + formData.moduleName + 'UpdateForm.java', //
      '문의 카테고리:' + formData.moduleName + 'QueryForm.java', //
    ];
  });
  const backendConstNameList = computed(() => {
    return [
      //
      '열거형 클래스:' + formData.moduleName + 'Enum.java', //
      '상수 클래스:' + formData.moduleName + 'Const.java', //
    ];
  });
</script>

<style lang="less" scoped>
  .preview-title {
    font-weight: 600;
    margin: 5px 0px;
  }

  .preview-block {
    font-size: 14px;
    background-color: #f9f9f9;
    padding: 10px 5px;
  }
</style>
