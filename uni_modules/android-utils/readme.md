# android-utils

```
	import {showToast,showToastButton,androidDialog,showColorToast,screenShotEnableState,AndroidTTSVoice} from "@/uni_modules/android-utils"
	 
	
	showToast("test");
	
	showToastButton("test");
	
	androidDialog("标题","消息","确定",function(){
			showToast("单击确定")
	},"取消",function(){
		showToast("单击取消")
		return true;
	})	
	
	showColorToast("这是一个安卓原生吐司","#ff0000")
	
	
	screenShotEnableState(true);// 禁用截屏
	screenShotEnableState(false);// 启用截屏
	
	
```


### 语音对象
#### AndroidTTSVoice
### uniapp
~~~

import {showToast,showToastButton,androidDialog,showColorToast,screenShotEnableState,AndroidTTSVoice} from "@/uni_modules/android-utils"

tts=new AndroidTTSVoice(function(state){
	console.log(state)
	if(state){
		tts.listenerVoiceState(function(b){
			console.log(b)
		})
		tts.speak("语音测试");
	}
});
~~~


### uniappx
~~~

import {showToast,showToastButton,androidDialog,showColorToast,screenShotEnableState,AndroidTTSVoice} from "@/uni_modules/android-utils"

tts=new AndroidTTSVoice(function(state:boolean){
	if(state){
		tts.listenerVoiceState(function(b:boolean){
		})
		tts.speak("语音测试");
	}
});
~~~


## 对象方法
### AndroidTTSVoice 构造方法 
参数1 function  方法  function 参数1 boolean 

#### 播放
#### speak()
参数1 string 播放内容

#### 获取可用语音名称
#### getVoiceNames
retrn string[]

#### 设置语音名称
#### setVoiceName
参数1 string 语音名称


#### 设置语速
#### setSpeed
参数1 number 0-1



#### 停止
#### stop



#### 是否正在播放
#### isSpeaking
return  boolean 是否正在播放




### 监听播放状态 
#### listenerVoiceState
参数1 function 参数1 number  0 开始  1 完成  2 错误



### 开发文档
[UTS 语法](https://uniapp.dcloud.net.cn/tutorial/syntax-uts.html)
[UTS API插件](https://uniapp.dcloud.net.cn/plugin/uts-plugin.html)
[UTS 组件插件](https://uniapp.dcloud.net.cn/plugin/uts-component.html)
[Hello UTS](https://gitcode.net/dcloud/hello-uts)