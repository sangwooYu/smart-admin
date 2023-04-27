<template>
  <a-dropdown class="header-trigger">
    <div class="wrapper">
      <a-avatar style="margin: 0 5px" :size="24" id="smartAdminAvatar">
        {{ avatarName }}
      </a-avatar>
      <span class="name">{{ actualName }}</span>
    </div>
    <template #overlay>
      <a-menu :class="['avatar-menu']">
        <a-menu-item @click="onRefresh">
          <span>권한 새로 고침</span>
        </a-menu-item>
        <a-menu-item @click="showUpdatePwdModal">
          <span>비밀번호 변경</span>
        </a-menu-item>
        <a-menu-item @click="onLogout">
          <span>로그아웃</span>
        </a-menu-item>
      </a-menu>
    </template>
  </a-dropdown>
  <HeaderResetPassword ref="resetPasswordRef" />
</template>
<script setup lang="ts">
  import { computed, ref, onMounted } from 'vue';
  import { loginApi } from '/@/api/system/login/login-api.js';
  import { useUserStore } from '/@/store/modules/system/user';
  import { clearAllCoolies } from '/@/utils/cookie-util';
  import { localClear } from '/@/utils/local-util';
  import { smartSentry } from '/@/lib/smart-sentry';
  import HeaderResetPassword from './header-reset-password-modal/index.vue';

  // 头像背景颜色
  const AVATAR_BACKGROUND_COLOR_ARRAY = ['#87d068', '#00B853', '#f56a00', '#1890ff'];

  //监听退出登录方法
  async function onLogout() {
    localClear();
    clearAllCoolies();
    useUserStore().logout();
    try {
      await loginApi.logout();
    } catch (e) {
      smartSentry.captureError(e);
    } finally {
      location.reload();
    }
  }

  // 刷新用户信息（包含用户基础信息、权限信息等等）
  async function onRefresh() {
    await loginApi.refresh();
    location.reload();
  }

  // ------------------------ 修改密码 ------------------------
  const resetPasswordRef = ref();

  function showUpdatePwdModal() {
    resetPasswordRef.value.showModal();
  }

  // ------------------------ 以下是 头像和姓名 相关 ------------------------

  const avatarName = ref('');
  const actualName = computed(() => useUserStore().actualName);
  // 更新头像信息
  function updateAvatar() {
    if (useUserStore().actualName) {
      avatarName.value = useUserStore().actualName.substr(0, 1);
      const avatar = document.getElementById('smartAdminAvatar');
      if (avatar) {
        avatar.style.backgroundColor = AVATAR_BACKGROUND_COLOR_ARRAY[hashcode(avatarName.value) % 4];
      }
    }
  }

  /**
   * 색상은 고정 문자열의 해시를 계산하여 선택되며, 로그인할 때마다 동일한 색상이 적용됩니다.
   */
  function hashcode(str) {
    let hash = 1,
      i,
      chr;
    if (str.length === 0) return hash;
    for (i = 0; i < str.length; i++) {
      chr = str.charCodeAt(i);
      hash = (hash << 5) - hash + chr;
      hash |= 0; // Convert to 32bit integer
    }
    return hash;
  }

  onMounted(updateAvatar);
</script>
<style lang="less" scoped>
  .wrapper {
    cursor: pointer;
    display: flex;
    align-items: center;
  }
  .header-trigger {
    height: @header-user-height;
    line-height: @header-user-height;

    .avatar {
      vertical-align: middle;
    }

    .name {
      margin-left: 5px;
      font-weight: 500;
    }
  }
</style>
