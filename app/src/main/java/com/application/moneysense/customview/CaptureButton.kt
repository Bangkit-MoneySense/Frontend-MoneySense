package com.application.moneysense.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.application.moneysense.R

class CaptureButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var txtColor: Int = 0
    private var enabledBackground: Drawable
    private var disabledBackground: Drawable

    init {
        txtColor = ContextCompat.getColor(context, R.color.light_color)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_active) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_inactive) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        background = if(isEnabled) enabledBackground else disabledBackground
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = if(isEnabled) "Submit" else "Isi Dulu"
    }
}