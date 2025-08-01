<template>
  <div class="page-container">
    <div class="outer-box">
      <!-- 内层蓝色矩形 (显示时间) -->
      <div class="inner-box">
        <div class="date">{{ currentDate }}</div>
        <div class="time">{{ currentTime }}</div>
      </div>
      
      <!-- 底部提示文本 - 添加单独点击事件 -->
      <div class="reminder" @click="handleReminderClick">请保持安静，切勿喧哗</div>
    </div>

    <!-- 浮动配置框（使用遮罩层和居中定位） -->
    <div v-if="showConfigBox" class="config-overlay" @click.self="showConfigBox = false">
      <div class="config-popup">
        <div class="config-content">
          <div class="config-item">
            <label>服务器地址：</label>
            <input v-model="serverAddress" type="text" placeholder="输入服务器IP" />
          </div>
          <div class="config-item">
            <label>本机IP地址：</label>
            <input v-model="localAddress" type="text" placeholder="输入本机IP" />
          </div>
		  
		   <!-- 新增确认按钮 -->
			<div class="action-buttons">
			  <button class="confirm-btn" @click="saveConfig">确认</button>
			</div>
		  
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';

// 日期时间相关
const currentDate = ref('');
const currentTime = ref('');

// 配置框相关
const showConfigBox = ref(false);
const clickCount = ref(0);
const serverAddress = ref('');
const localAddress = ref('');

// 更新时间函数
const updateTime = () => {
  const now = new Date();
  
  // 格式化日期
  currentDate.value = now.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  }).replace(/\//g, '年').replace(/\//g, '月') + '日';
  
  // 格式化时间
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');
  currentTime.value = `${hours}:${minutes}:${seconds}`;
};

// 单独处理reminder的点击事件
const handleReminderClick = () => {
  clickCount.value++;
  if (clickCount.value >= 5) {
    showConfigBox.value = true;
    clickCount.value = 0; // 重置计数
  }
};

// 保存配置到本地缓存
const saveConfig = () => {
  // 创建配置对象
  const config = {
    serverAddress: serverAddress.value,
    localAddress: localAddress.value
  };
  
  try {
    // 使用uni-app的存储API保存到本地
    uni.setStorageSync('serverConfig', config);
    
    // 保存成功后关闭配置框
    setTimeout(() => {
      uni.showToast({
        title: '配置已保存',
        icon: 'success',
        duration: 2000
      });
      showConfigBox.value = false;
    }, 300);
  } catch (e) {
    console.error('保存配置失败:', e);
    uni.showToast({
      title: '保存失败',
      icon: 'error',
      duration: 2000
    });
  }
};


// 定时器ID
let timer = null;

onMounted(() => {
  updateTime();
  timer = setInterval(updateTime, 1000);
});

onBeforeUnmount(() => {
  if (timer) clearInterval(timer);
});
</script>

<style scoped>
	
	/* 新增按钮样式 */
	.action-buttons {
	  display: flex;
	  justify-content: flex-end;
	  margin-top: 10px;
	}
	
	.confirm-btn {
	  background-color: #4CAF50;
	  color: white;
	  border: none;
	  border-radius: 4px;
	  cursor: pointer;
	  font-size: 1em;
	  font-weight: bold;
	  transition: background-color 0.3s;
	}
	
/* 基础样式 */
.page-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: white;
  padding: 20px;
  box-sizing: border-box;
}

.outer-box {
  width: 80%;
  max-width: 600px;
  aspect-ratio: 16/9;
  background-color: #4CAF50; /* 绿色背景 */
  position: relative;
  display: flex;
  flex-direction: column;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 添加轻微阴影替代边框 */
  border-radius: 8px; /* 添加圆角 */
  overflow: hidden; /* 确保内层圆角正确显示 */
}

.inner-box {
  flex: 1;
  background-color: #2196F3; /* 蓝色背景 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.date {
  color: white;
  font-weight: bold;
  font-size: 2.5vw;
  margin-bottom: 10px;
}

.time {
  color: white;
  font-weight: bold;
  font-size: 4vw;
  letter-spacing: 1px;
}

.reminder {
  color: white;
  font-weight: normal;
  padding: 20px;
  font-size: 2vw;
  text-align: center;
  background-color: #4CAF50;
  cursor: pointer;
}

/* 浮动配置框样式 */
.config-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明遮罩 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.config-popup {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  max-width: 90%;
  width: 400px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.25); /* 增强阴影效果 */
  position: relative;
}

.config-content {
  padding: 10px;
}

.config-item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.config-item label {
  min-width: 120px;
  text-align: right;
  padding-right: 15px;
  font-weight: bold;
}

.config-item input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd; /* 使用浅色边框 */
  border-radius: 4px;
  font-size: 1.1em;
  transition: border-color 0.3s;
}

.config-item input:focus {
  border-color: #2196F3; /* 聚焦时显示蓝色边框 */
  outline: none;
  box-shadow: 0 0 0 2px rgba(33, 150, 243, 0.2);
}

/* 响应式处理 */
@media (max-width: 768px) {
  .date {
    font-size: 5vw;
  }
  
  .time {
    font-size: 7vw;
  }
  
  .reminder {
    font-size: 4vw;
  }
  
  .config-popup {
    width: 90%;
  }

  .config-item {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .config-item label {
    width: 100%;
    text-align: left;
    margin-bottom: 5px;
    padding-right: 0;
  }
}
</style>