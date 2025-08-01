<template>
	<view class="content">
		<button type="primary" style="margin: 10rpx;width: 100%;" @click="onClick">点击弹出吐司</button>
		<button type="primary" style="margin: 10rpx;width: 100%;" @click="showDialog">原生对话框</button>
		<button type="primary" style="margin: 10rpx;width: 100%;" @click="colorToast">带颜色吐司</button>
		<button type="primary" style="margin: 10rpx;width: 100%;" @click="screenStateClick(0)">禁止截屏录像</button>
		<button type="primary" style="margin: 10rpx;width: 100%;" @click="screenStateClick(1)">启用截屏录像</button>
		<input  v-model="input_text" style="border-bottom:  #666666 1rpx solid;height: 60rpx;width: 100%;"/>
		<button type="primary" style="margin: 10rpx;width: 100%;" @click="speaks()">播放语音</button>
	</view>
</template>

<script>
	import {showToast,showToastButton,androidDialog,showColorToast,screenShotEnableState,AndroidTTSVoice} from "@/uni_modules/android-utils"
	 var tts;
	export default {
		data() {
			return {
				title: 'Hello',
				input_text:"android原生语音测试"
			}
		},
		onLoad() {
			tts=new AndroidTTSVoice(function(state){
				console.log(state)
				showToast(state+"")
				if(state){
					tts.listenerVoiceState(function(b){
						console.log(b)
					})
				}
			});
		
		},
		methods: {
			onClick(){
				showToast("这是一个安卓原生吐司");
				// showToastButton("这是一个安卓原生吐司")
			},
			
			showDialog(){
				androidDialog("标题","消息","确定",function(){
					showToast("单击确定")
				},"取消",function(){
					showToast("单击取消")
				})
			},
			
			colorToast(){
				showColorToast("这是一个安卓原生吐司","#ff0000")
			},
			imgDialog(){
				showImgDialog();
			},
			inputDialog(){
				var s={
					title: "标题",
					
					okText: "确定",
					cancelText: "取消",
					cancelCallbackClick: function() {
					},
					okCallbackClick: function(res) {
						
					}
				}  ;
				showEditDialog(s);
			},
			
			screenStateClick(state){
				if(state==0){
					screenShotEnableState(true);
				}else{
					screenShotEnableState(false);
				}
			},
			speaks(){
				var state=	tts.speak(this.input_text);
				
				
				
			}
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		width: 100%;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
