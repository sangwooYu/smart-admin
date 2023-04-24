<template>
  <a-modal :visible="visible" title="추가" :width="700" forceRender ok-text="확인" cancel-text="취소" @ok="onSubmit" @cancel="onClose">
    <a-form ref="formRef" :model="form" :rules="rules" :label-col="{ span: 6 }">
      <a-form-item label="회사 이름" name="enterpriseName">
        <a-input v-model:value="form.enterpriseName" placeholder="비즈니스 이름을 입력하세요." />
      </a-form-item>

      <a-form-item label="기업 로고" name="enterpriseLogo">
        <Upload
          accept=".jpg,.jpeg,.png,.gif"
          :maxUploadSize="1"
          buttonText="기업 로고를 업로드하려면 클릭하세요."
          :default-file-list="form.enterpriseLogo"
          @change="enterpriseLogoChange"
        />
      </a-form-item>

      <a-form-item label="통합 소셜 신용 코드" name="unifiedSocialCreditCode">
        <a-input v-model:value="form.unifiedSocialCreditCode" placeholder="통합 소셜 신용 코드를 입력하세요." />
      </a-form-item>

      <a-form-item label="유형" name="type">
        <SmartEnumSelect width="100%" v-model:value="form.type" placeholder="유형을 선택하세요." enum-name="ENTERPRISE_TYPE_ENUM" />
      </a-form-item>

      <a-form-item label="담당자" name="contact">
        <a-input v-model:value="form.contact" placeholder="담당자를 입력하세요." />
      </a-form-item>
      <a-form-item label="연락처 전화" name="contactPhone">
        <a-input v-model:value="form.contactPhone" placeholder="연락처 전화를 입력하세요." />
      </a-form-item>

      <a-form-item label="거주 도시" name="provinceCityDistrict">
        <AreaCascader type="province_city_district" style="width: 100%" v-model:value="area" placeholder="도시를 선택하세요." @change="changeArea" />
      </a-form-item>
      <a-form-item label="주소 세부 정보" name="address">
        <a-input v-model:value="form.address" placeholder="전체 주소를 입력하세요." />
      </a-form-item>

      <a-form-item label="이메일" name="email">
        <a-input v-model:value="form.email" placeholder="이메일을 입력하세요" />
      </a-form-item>
      <a-form-item label="상태 활성화" name="disabledFlag">
        <a-switch v-model:checked="enabledChecked" @change="enabledCheckedChange" />
      </a-form-item>

      <a-form-item label="비즈니스 라이선스" name="businessLicense">
        <Upload
          accept=".jpg,.jpeg,.png,.gif"
          :maxUploadSize="1"
          buttonText="비즈니스 라이선스를 업로드하려면 클릭하세요."
          :default-file-list="form.businessLicense"
          @change="businessLicenseChange"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
  import { message } from 'ant-design-vue';
  import _ from 'lodash';
  import { nextTick, reactive, ref } from 'vue';
  import { enterpriseApi } from '/@/api/business/oa/enterprise-api';
  import AreaCascader from '/@/components/framework/area-cascader/index.vue';
  import { SmartLoading } from '/@/components/framework/smart-loading';
  import Upload from '/@/components/support/file-upload/index.vue';
  import { regular } from '/@/constants/regular-const';
  import { smartSentry } from '/@/lib/smart-sentry';
  import SmartEnumSelect from '/@/components/framework/smart-enum-select/index.vue';

  defineExpose({
    showModal,
  });
  const emit = defineEmits('refresh');

  // --------------------- modal 显示与隐藏 ---------------------
  // 是否展示
  const visible = ref(false);

  function showModal(enterpriseId) {
    Object.assign(form, formDefault);
    area.value = [];
    if (enterpriseId) {
      detail(enterpriseId);
    }
    visible.value = true;
  }

  function onClose() {
    visible.value = false;
  }

  async function detail(enterpriseId) {
    try {
      let result = await enterpriseApi.detail(enterpriseId);
      let data = result.data;
      Object.assign(form, data);
      nextTick(() => {
        // 省市区不存在，不需要赋值
        if (!data.provinceName) {
          return;
        }
        area.value = [
          {
            value: data.province,
            label: data.provinceName,
          },
          {
            value: data.city,
            label: data.cityName,
          },
          {
            value: data.district,
            label: data.districtName,
          },
        ];
      });
    } catch (error) {
      smartSentry.captureError(error);
    } finally {
      SmartLoading.hide();
    }
  }

  // --------------------- 表单 ---------------------

  //  组件
  const formRef = ref();

  const formDefault = {
    enterpriseId: undefined,
    enterpriseName: undefined,
    unifiedSocialCreditCode: undefined,
    businessLicense: undefined,
    contact: undefined,
    enterpriseLogo:undefined,
    contactPhone: undefined,
    email: undefined,
    province: undefined,
    provinceName: undefined,
    city: undefined,
    cityName: undefined,
    district: undefined,
    districtName: undefined,
    address: undefined,
    disabledFlag: false,
  };
  let form = reactive({ ...formDefault });
  const rules = {
    enterpriseName: [{ required: true, message: '비즈니스 이름을 입력하세요.' }],
    unifiedSocialCreditCode: [{ required: true, message: '통합 소셜 신용 코드를 입력하세요.' }],
    contact: [{ required: true, message: '담당자를 입력하세요.' }],
    contactPhone: [
      { required: true, message: '연락처를 입력하세요.' },
      { pattern: regular.phone, message: '정확한 연락처 전화번호를 입력하세요.', trigger: 'blur' },
    ],
    type: [{ required: true, message: '유형을 선택하세요.' }],
  };

  function onSubmit() {
    formRef.value
      .validate()
      .then(async () => {
        SmartLoading.show();
        try {
          if (form.enterpriseId) {
            await enterpriseApi.update(form);
          } else {
            await enterpriseApi.create(form);
          }
          message.success(`${form.enterpriseId ? '수정 사항' : '추가'}성공`);
          emit('refresh');
          onClose();
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

  // 状态
  const enabledChecked = ref(true);

  function enabledCheckedChange(checked) {
    form.disabledFlag = !checked;
  }

  // 地区
  const area = ref([]);

  function changeArea(value, selectedOptions) {
    Object.assign(form, {
      province: '',
      provinceName: '',
      city: '',
      cityName: '',
      district: '',
      districtName: '',
    });
    if (!_.isEmpty(selectedOptions)) {
      // 地区信息
      form.province = area.value[0].value;
      form.provinceName = area.value[0].label;

      form.city = area.value[1].value;
      form.cityName = area.value[1].label;
      if (area.value[2]) {
        form.district = area.value[2].value;
        form.districtName = area.value[2].label;
      }
    }
  }

  function enterpriseLogoChange(fileList) {
    form.enterpriseLogo = fileList;
  }

  function businessLicenseChange(fileList) {
    form.businessLicense = fileList;
  }
</script>

<style lang="less" scoped>
  .form-width {
    width: 100% !important;
  }

  .footer {
    width: 100%;
    display: flex;
    justify-content: flex-end;
  }

  :deep(.ant-card-body) {
    padding: 10px;
  }
</style>
