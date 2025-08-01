@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING", "UNNECESSARY_NOT_NULL_ASSERTION")
package uts.sdk.modules.androidUtils
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Base64
import android.view.Gravity
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.dcloud.uniapp.*
import io.dcloud.uniapp.extapi.*
import io.dcloud.uts.*
import io.dcloud.uts.Map
import io.dcloud.uts.Set
import io.dcloud.uts.UTSAndroid
import java.io.IOException
import java.io.OutputStream
import java.lang.System
import java.util.Objects
import java.util.UUID
import kotlin.ByteArray
import kotlin.Int
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
open class MyApiOptions (
    @JsonNotNull
    open var paramA: Boolean = false,
    open var success: ((res: MyApiResult) -> Unit)? = null,
    open var fail: ((res: MyApiFail) -> Unit)? = null,
    open var complete: ((res: Any) -> Unit)? = null,
) : UTSObject()
open class MyApiResult (
    @JsonNotNull
    open var fieldA: Number,
    @JsonNotNull
    open var fieldB: Boolean = false,
    @JsonNotNull
    open var fieldC: String,
) : UTSObject()
typealias MyApiErrorCode = Number
interface MyApiFail : IUniError {
    override var errCode: MyApiErrorCode
}
typealias MyApi = (options: MyApiOptions) -> Unit
typealias MyApiSync = (paramA: Boolean) -> MyApiResult
val UniErrorSubject = "uts-api"
val MyAPIErrors: Map<MyApiErrorCode, String> = Map(_uA(
    _uA(
        9010001,
        "custom error mseeage1"
    ),
    _uA(
        9010002,
        "custom error mseeage2"
    )
))
open class MyApiFailImpl : UniError, MyApiFail {
    constructor(errCode: MyApiErrorCode) : super() {
        this.errSubject = UniErrorSubject
        this.errCode = errCode
        this.errMsg = MyAPIErrors.get(errCode) ?: ""
    }
}
val myApi: MyApi = fun(options: MyApiOptions) {
    if (options.paramA == true) {
        val res = MyApiResult(fieldA = 85, fieldB = true, fieldC = "some message")
        options.success?.invoke(res)
        options.complete?.invoke(res)
    } else {
        val err = MyApiFailImpl(9010001)
        options.fail?.invoke(err)
        options.complete?.invoke(err)
    }
}
val myApiSync: MyApiSync = fun(paramA: Boolean): MyApiResult {
    val res = MyApiResult(fieldA = 85, fieldB = paramA, fieldC = "some message")
    return res
}
var mToast: Toast? = null
var mLastText = ""
var mLastTime: Number = 0
var mLastDuration: Number = 0
val showToast = fun(text: String) {
    if (mToast == null) {
        mToast = Toast.makeText(UTSAndroid.getAppContext()!!!!, text, Toast.LENGTH_LONG)
    } else {
        if (Objects.equals(text, mLastText) && System.currentTimeMillis() - mLastTime < mLastDuration) {
            return
        }
        mToast!!!!.cancel()
        mToast = Toast.makeText(UTSAndroid.getAppContext()!!!!, text, Toast.LENGTH_SHORT)
        mLastDuration = Toast.LENGTH_LONG
        mLastTime = System.currentTimeMillis()
        mLastText = text
    }
    mToast!!!!.setGravity(Gravity.CENTER, 0, 0)
    mToast!!!!.show()
}
val showColorToast = fun(text: String, color: String) {
    var tv = TextView(UTSAndroid.getAppContext()!!!!)
    var b: GradientDrawable = getSoldRadiusBg(Color.parseColor(color), 60)
    tv.setBackgroundDrawable(b as Drawable)
    var r1 = convertHtmlPxToAndroidPx(20).toInt()
    var r2 = convertHtmlPxToAndroidPx(10).toInt()
    tv.setPadding(r1, r2, r1, r2)
    tv.setTextColor(Color.WHITE)
    tv.setTextSize(16.toFloat())
    tv.setText(text)
    if (mToast == null) {
        mToast = Toast(UTSAndroid.getAppContext()!!!!)
        mToast!!!!.setView(tv)
    } else {
        if (Objects.equals(text, mLastText) && System.currentTimeMillis() - mLastTime < mLastDuration) {
            return
        }
        mToast!!!!.cancel()
        mToast = Toast.makeText(UTSAndroid.getAppContext()!!!!, text, Toast.LENGTH_SHORT)
        mToast!!!!.setView(tv)
        mLastDuration = Toast.LENGTH_LONG
        mLastTime = System.currentTimeMillis()
        mLastText = text
    }
    mToast!!!!.setGravity(Gravity.CENTER, 0, 0)
    mToast!!!!.show()
}
val showToastButton = fun(text: String) {
    if (mToast == null) {
        mToast = Toast.makeText(UTSAndroid.getAppContext()!!!!, text, Toast.LENGTH_LONG)
    } else {
        if (Objects.equals(text, mLastText) && System.currentTimeMillis() - mLastTime < mLastDuration) {
            return
        }
        mToast!!!!.cancel()
        mToast = Toast.makeText(UTSAndroid.getAppContext()!!!!, text, Toast.LENGTH_SHORT)
        mLastDuration = Toast.LENGTH_LONG
        mLastTime = System.currentTimeMillis()
        mLastText = text
    }
    mToast!!!!.show()
}
val androidDialog = fun(title: String, msg: String, okText: String, callback: () -> Unit, cancelText: String, callback1: () -> Unit) {
    open class MyOnClickListener : DialogInterface.OnClickListener {
        open var type: Number
        constructor(type: Number){
            this.type = type
        }
        public override fun onClick(dialogInterface: DialogInterface, i: Int) {
            if (this.type == 0) {
                callback()
                dialogInterface.dismiss()
            } else if (this.type == 1) {
                callback1()
                dialogInterface.dismiss()
            }
        }
    }
    var builder: AlertDialog.Builder = AlertDialog.Builder(UTSAndroid.getUniActivity()!!!!)
    if (title != "") {
        builder.setTitle(title)
    }
    builder.setMessage(msg)
    builder.setPositiveButton(okText, MyOnClickListener(0))
    if (cancelText != "") {
        builder.setNegativeButton(cancelText, MyOnClickListener(1))
    }
    builder.create().show()
}
val isHavePermision = fun(pername: String): Boolean {
    return UTSAndroid.checkSystemPermissionGranted(UTSAndroid.getUniActivity()!!, _uA(
        pername
    ))
}
val requestPermison = fun(pername: String, callback: (sth: Boolean) -> Unit) {
    if (isHavePermision(pername)) {
        callback(true)
        return
    }
    UTSAndroid.requestSystemPermission(UTSAndroid.getUniActivity()!!, _uA(
        pername
    ), fun(_: Boolean, p: UTSArray<String>){
        console.log(p)
        callback(true)
    }
    , fun(_: Boolean, p: UTSArray<String>){
        callback(false)
        console.log(p)
    }
    )
}
val requesMoretPermison = fun(pername: UTSArray<String>, callback: (sth: Boolean) -> Unit) {
    var have = true
    run {
        var per: Number = 0
        while(per < pername.length){
            if (!isHavePermision(pername[per])) {
                have = false
            }
            per++
        }
    }
    if (have) {
        callback(true)
        return
    }
    var len = pername.length
    UTSAndroid.requestSystemPermission(UTSAndroid.getUniActivity()!!, pername, fun(_: Boolean, p: UTSArray<String>){
        console.log(p)
        if (p.length == len) {
            callback(true)
        }
    }
    , fun(_: Boolean, p: UTSArray<String>){
        console.log(p)
        callback(false)
    }
    )
}
val saveBase64ToGallery = fun(name: String, base64: String, callback: (sth: Boolean) -> Unit) {
    if (isHavePermision("android.permission.WRITE_EXTERNAL_STORAGE") && isHavePermision("android.permission.READ_MEDIA_IMAGES")) {
        saveBitmap(name, base64, callback)
    } else {
        requesMoretPermison(_uA(
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_MEDIA_IMAGES"
        ), fun(state: Boolean) {
            if (state) {
                saveBitmap(name, base64, callback)
            } else {
                callback(state)
            }
        }
        )
    }
}
val saveBitmap = fun(name: String, base64: String, callback: (sth: Boolean) -> Unit) {
    var decode: ByteArray = Base64.decode(base64, Base64.DEFAULT)
    var bitmapImage: Bitmap = BitmapFactory.decodeByteArray(decode, 0.toInt(), decode.size)
    var contentValues: ContentValues = ContentValues()
    contentValues.put("_display_name", name)
    contentValues.put("mime_type", "image/jpeg")
    var uri: Uri? = null
    uri = UTSAndroid.getAppContext()!!!!.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    try {
        if (uri != null) {
            var outputStream: OutputStream? = null
            outputStream = UTSAndroid.getAppContext()!!!!.getContentResolver()!!!!.openOutputStream(uri!!!!)
            if (outputStream != null) {
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!!!)
            }
            callback(true)
        }
    }
     catch (e: IOException) {
        e.printStackTrace()
        callback(false)
    }
}
open class InputBean (
    open var title: String? = null,
    open var holder: String? = null,
    open var nowInput: String? = null,
    @JsonNotNull
    open var inputType: Number,
    @JsonNotNull
    open var maxLength: Number,
    @JsonNotNull
    open var cancelText: String,
    open var okText: String? = null,
    open var okCallbackClick: ((res: String) -> Unit),
    open var cancelCallbackClick: (() -> Unit),
) : UTSObject()
val showEditDialog = fun(bean: InputBean) {
    var et: EditText = EditText(UTSAndroid.getAppContext()!!!!)
    open class MyOnClickListener : DialogInterface.OnClickListener {
        open var type: Number
        constructor(type: Number){
            this.type = type
        }
        public override fun onClick(dialogInterface: DialogInterface, i: Int) {
            if (this.type == 0) {
                bean!!.okCallbackClick(et.getText().toString())
                dialogInterface.dismiss()
            } else if (this.type == 1) {
                bean!!.cancelCallbackClick()
                dialogInterface.dismiss()
            }
        }
    }
    var builder: AlertDialog.Builder = AlertDialog.Builder(UTSAndroid.getUniActivity()!!!!)
    if (bean.title != null || bean.title != "") {
        builder.setTitle(bean.title)
    }
    builder.setPositiveButton(bean.okText, MyOnClickListener(0))
    if (bean.cancelText != "") {
        builder.setNegativeButton(bean.cancelText, MyOnClickListener(1))
    }
    builder.setView(et)
    builder.create()
    builder.show()
}
val screenShotEnableState = fun(state: Boolean): Unit {
    open class MyRun : Runnable {
        override fun run() {
            if (state) {
                UTSAndroid.getUniActivity()!!!!.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
            } else {
                UTSAndroid.getUniActivity()!!!!.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
    }
    UTSAndroid.getUniActivity()!!!!.runOnUiThread(MyRun())
}
val convertHtmlPxToAndroidPx = fun(htmlPx: Number): Number {
    var resources: Resources = UTSAndroid.getAppContext()!!!!.getResources()
    return Math.round(htmlPx * resources.getDisplayMetrics().density)
}
val showImgDialog = fun() {
    var builder: AlertDialog.Builder = AlertDialog.Builder(UTSAndroid.getUniActivity()!!!!)
    builder.create().show()
}
open class AndroidTTSVoice {
    open var textToSpeech: TextToSpeech
    open var onVoiceStateListener: OnVoiceStateListener? = null
    constructor(callback: (init: Boolean) -> Unit){
        open class MyListener : TextToSpeech.OnInitListener {
            override fun onInit(i: Int): Unit {
                console.log("init  " + i.toString())
                if (i == -1) {
                    callback(false)
                } else {
                    callback(true)
                }
            }
        }
        this.textToSpeech = TextToSpeech(UTSAndroid.getUniActivity()!!!!, MyListener())
        this.textToSpeech.setOnUtteranceProgressListener(MyTTSListener(this))
    }
    public open fun speak(data: String): Number {
        var state = this.textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, Bundle(), UUID.randomUUID().toString())
        return state
    }
    public open fun getVoiceNames(): UTSArray<String> {
        var voices = this.textToSpeech.getVoices()
        var b: UTSArray<String> = _uA()
        for(s in resolveUTSKeyIterator(voices)){
            b.push(s.getName())
        }
        return b
    }
    public open fun setSpeed(num: Number) {
        this.textToSpeech.setSpeechRate(num.toFloat())
    }
    public open fun setVoiceName(name: String): Unit {
        var voices = this.textToSpeech.getVoices()
        var cur: Voice? = null
        for(s in resolveUTSKeyIterator(voices)){
            if (name == s.getName()) {
                cur = s
            }
        }
        this.textToSpeech.setVoice(cur!!!!)
    }
    public open fun stop() {
        this.textToSpeech.stop()
    }
    public open fun shutdown() {
        this.textToSpeech.shutdown()
    }
    public open fun isSpeaking(): Boolean {
        return this.textToSpeech.isSpeaking()
    }
    public open fun listenerVoiceState(callback: (state: Number) -> Unit) {
        open class MyOnVoiceStateListener : OnVoiceStateListener {
            override fun onVoiceState(state: Number) {
                callback(state)
            }
        }
        this.onVoiceStateListener = MyOnVoiceStateListener()
    }
    open fun getEngineName(): String {
        return this.textToSpeech.getDefaultEngine()
    }
}
interface OnVoiceStateListener {
    fun onVoiceState(state: Number)
}
open class MyTTSListener : UtteranceProgressListener {
    open var ts: AndroidTTSVoice
    constructor(t: AndroidTTSVoice){
        this.ts = t
    }
    public override fun onStart(s1: String): Unit {
        ts.onVoiceStateListener!!.onVoiceState(0)
    }
    public override fun onDone(s1: String): Unit {
        ts.onVoiceStateListener!!.onVoiceState(1)
    }
    public override fun onError(s1: String): Unit {
        console.log("onError" + s1)
        ts.onVoiceStateListener!!.onVoiceState(-1)
    }
}
val getSoldRadiusBg = fun(colorSold: Number, radius: Number): GradientDrawable {
    var drawable: GradientDrawable = GradientDrawable()
    drawable.setShape(GradientDrawable.RECTANGLE)
    drawable.setCornerRadius(radius.toFloat())
    drawable.setColor(colorSold.toInt())
    return drawable
}
open class MyApiOptionsJSONObject : UTSJSONObject() {
    open var paramA: Boolean = false
    open var success: UTSCallback? = null
    open var fail: UTSCallback? = null
    open var complete: UTSCallback? = null
}
fun myApiByJs(options: MyApiOptionsJSONObject): Unit {
    return myApi(MyApiOptions(paramA = options.paramA, success = fun(res: MyApiResult): Unit {
        options.success?.invoke(res)
    }
    , fail = fun(res: MyApiFail): Unit {
        options.fail?.invoke(res)
    }
    , complete = fun(res: Any): Unit {
        options.complete?.invoke(res)
    }
    ))
}
fun myApiSyncByJs(paramA: Boolean): MyApiResult {
    return myApiSync(paramA)
}
fun showToastByJs(text: String): Unit {
    return showToast(text)
}
fun showColorToastByJs(text: String, color: String): Unit {
    return showColorToast(text, color)
}
fun showToastButtonByJs(text: String): Unit {
    return showToastButton(text)
}
fun androidDialogByJs(title: String, msg: String, okText: String, callback: UTSCallback, cancelText: String, callback1: UTSCallback): Unit {
    return androidDialog(title, msg, okText, if (callback.fnJS != null) {
        callback.fnJS
    } else {
        callback.fnJS = fun(){
            callback()
        }
        callback.fnJS
    }
     as () -> Unit, cancelText, if (callback1.fnJS != null) {
        callback1.fnJS
    } else {
        callback1.fnJS = fun(){
            callback1()
        }
        callback1.fnJS
    }
     as () -> Unit)
}
fun isHavePermisionByJs(pername: String): Boolean {
    return isHavePermision(pername)
}
fun requestPermisonByJs(pername: String, callback: UTSCallback): Unit {
    return requestPermison(pername, if (callback.fnJS != null) {
        callback.fnJS
    } else {
        callback.fnJS = fun(sth: Boolean){
            callback(sth)
        }
        callback.fnJS
    }
     as (sth: Boolean) -> Unit)
}
fun requesMoretPermisonByJs(pername: UTSArray<String>, callback: UTSCallback): Unit {
    return requesMoretPermison(pername, if (callback.fnJS != null) {
        callback.fnJS
    } else {
        callback.fnJS = fun(sth: Boolean){
            callback(sth)
        }
        callback.fnJS
    }
     as (sth: Boolean) -> Unit)
}
fun saveBase64ToGalleryByJs(name: String, base64: String, callback: UTSCallback): Unit {
    return saveBase64ToGallery(name, base64, if (callback.fnJS != null) {
        callback.fnJS
    } else {
        callback.fnJS = fun(sth: Boolean){
            callback(sth)
        }
        callback.fnJS
    }
     as (sth: Boolean) -> Unit)
}
fun saveBitmapByJs(name: String, base64: String, callback: UTSCallback): Unit {
    return saveBitmap(name, base64, if (callback.fnJS != null) {
        callback.fnJS
    } else {
        callback.fnJS = fun(sth: Boolean){
            callback(sth)
        }
        callback.fnJS
    }
     as (sth: Boolean) -> Unit)
}
fun showEditDialogByJs(bean: InputBean): Unit {
    return showEditDialog(InputBean(title = bean.title, holder = bean.holder, nowInput = bean.nowInput, inputType = bean.inputType, maxLength = bean.maxLength, cancelText = bean.cancelText, okText = bean.okText, okCallbackClick = fun(res: String): Unit {
        bean.okCallbackClick(res)
    }
    , cancelCallbackClick = fun(): Unit {
        bean.cancelCallbackClick()
    }
    ))
}
fun screenShotEnableStateByJs(state: Boolean): Unit {
    return screenShotEnableState(state)
}
fun convertHtmlPxToAndroidPxByJs(htmlPx: Number): Number {
    return convertHtmlPxToAndroidPx(htmlPx)
}
fun showImgDialogByJs(): Unit {
    return showImgDialog()
}
open class AndroidTTSVoiceByJs : AndroidTTSVoice {
    constructor(callback: UTSCallback) : super(if (callback.fnJS != null) {
        callback.fnJS
    } else {
        callback.fnJS = fun(init: Boolean){
            callback(init)
        }
        callback.fnJS
    }
     as (init: Boolean) -> Unit) {}
    public open fun speakByJs(data: String): Number {
        return this.speak(data)
    }
    public open fun getVoiceNamesByJs(): UTSArray<String> {
        return this.getVoiceNames()
    }
    public open fun setSpeedByJs(num: Number) {
        return this.setSpeed(num)
    }
    public open fun setVoiceNameByJs(name: String): Unit {
        return this.setVoiceName(name)
    }
    public open fun stopByJs() {
        return this.stop()
    }
    public open fun shutdownByJs() {
        return this.shutdown()
    }
    public open fun isSpeakingByJs(): Boolean {
        return this.isSpeaking()
    }
    public open fun listenerVoiceStateByJs(callback: UTSCallback) {
        return this.listenerVoiceState(if (callback.fnJS != null) {
            callback.fnJS
        } else {
            callback.fnJS = fun(state: Number){
                callback(state)
            }
            callback.fnJS
        }
         as (state: Number) -> Unit)
    }
    open fun getEngineNameByJs(): String {
        return this.getEngineName()
    }
}
