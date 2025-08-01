<template>
	<view class="field">
	  <text class="input_text"  :class="{'show':text.length>0}"> {{hint}}</text>
	   <input class="nor_input" v-model="text" :disabled="disabled"  :inputmode="inputmode" type="text"  :password="password"   @input="onInput">
	</view>
</template>
<script>
	export default {
	props:{
		hint:{
			type:String,
			
		},
		inputmode:{
			type:String,
			default:"text"
		},
		password:{
			type:Boolean,
			default:false
		},
		disabled:{
			type:Boolean,
			default:false
		},
		// datas:{
		// 	type:String,
		// 	default:""
		// }
	},
		
		data() {
			return{
				text:"",
			}
		},
		methods: {
			onInput(val){
				this.text=val.detail.value;
				this.$emit("input",val);
			},
			
			setInputText(data){
				console.log("setInputText")
				this.text=data;
			}
			
			
		}
	}
	
	
</script>
<style>
	
	.content{
		display: flex;
		flex-direction: column;
	}
.field{
	display: flex;
	align-items: center;

}
	
.input_text {
  font-size: 40rpx;
  color: #BBBBBB;
  position: absolute;
  font-weight: 500;
	padding-bottom: 15rpx;
  transition: all 0.2s linear;
}

.nor_input {
  font-size: 40rpx;
  font-weight: 500;
 border-bottom-color: #BBBBBB;
  border-bottom-style: double;
  padding-bottom: 15rpx;
  border-width: 2rpx;
  width: 100%;
}

.show {
  color: #BBBBBB;
  font-size: 30rpx;
  font-weight: 500;
  padding-bottom: 15rpx;
  margin-top: -95rpx;
}

</style>
