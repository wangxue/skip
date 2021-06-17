package me.wxue.skip

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.os.Build
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import kotlin.math.log

class SkipService : AccessibilityService() {

    val TAG = "wangxue"

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "onCreate: ")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.e(TAG, "onServiceConnected: ")

        val config = AccessibilityServiceInfo()
        //配置监听的事件类型为界面变化|点击事件
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or AccessibilityEvent.TYPE_VIEW_CLICKED
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
        serviceInfo = config
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.e(TAG, "onAccessibilityEvent: ")
        val nodeInfo = event?.source
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            Log.e("wangxue", "onAccessibilityEvent: TYPE_WINDOW_STATE_CHANGED")
            Looper.myQueue().addIdleHandler {
                // 遍历控件树
                searchSkip(nodeInfo)
                return@addIdleHandler false
            }
        }
    }

    override fun onInterrupt() {
        Log.e(TAG, "onInterrupt: ")
        // ignore
    }

    private fun searchSkip(nodeInfo: AccessibilityNodeInfo?) {
        nodeInfo?.apply {
            if (childCount > 0) {
                for (i in 0 until childCount) {
                    searchSkip(getChild(i))
                }
            } else {
                Log.e("wangxue", toString())
                if (text != null && text.contains("跳过")) {
                    Toast.makeText(this@SkipService, "跳过广告", Toast.LENGTH_LONG).show()
                    performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return
                }
            }
        }
        return
    }
}