package com.slots.rolls

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*

class SlotsView : View {
    private var groupHeight = 0
    private var groupWidth = 0
    private var deltaWidth = 0
    private var deltaHeight = 0
    private lateinit var mRectF1: RectF
    private lateinit var mRectF2: RectF
    private lateinit var mRectF3: RectF
    private lateinit var mRectF4: RectF
    private lateinit var mRectF5: RectF
    private var mCanvas: Canvas? = null
    private lateinit var slot1: Bitmap
    private lateinit var slot2: Bitmap
    private lateinit var slot3: Bitmap
    private lateinit var slot4: Bitmap
    private lateinit var slot5: Bitmap
    private lateinit var mBitmaps: Array<Bitmap>
    private lateinit var mRandBitmaps1: Array<Bitmap>
    private lateinit var mRandBitmaps2: Array<Bitmap>
    private lateinit var mRandBitmaps3: Array<Bitmap>
    private lateinit var mRandBitmaps4: Array<Bitmap>
    private lateinit var mRandBitmaps5: Array<Bitmap>
    private var mPaint: Paint? = null
    private val mCornersRadius = 15f
    private var inc = 0
    private var inc2 = 0
    private var inc3 = 0
    private var inc4 = 0
    private var isRotation = true

    constructor(ctx: Context) : super(ctx) {
        init(ctx)
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        init(ctx)
    }

    private fun init(context: Context) {
        slot1 = BitmapFactory.decodeResource(context.resources, R.drawable.chest)
        slot2 = BitmapFactory.decodeResource(context.resources, R.drawable.crown)
        slot3 = BitmapFactory.decodeResource(context.resources, R.drawable.horseshoe)
        slot4 = BitmapFactory.decodeResource(context.resources, R.drawable.q)
        slot5 = BitmapFactory.decodeResource(context.resources, R.drawable.seven)

        mBitmaps = arrayOf(slot1, slot2, slot3, slot4, slot5)
        mRandBitmaps1 = getRandBitmaps()
        mRandBitmaps2 = getRandBitmaps()
        mRandBitmaps3 = getRandBitmaps()
        mRandBitmaps4 = getRandBitmaps()
        mRandBitmaps5 = getRandBitmaps()
        mPaint = Paint()
        mPaint!!.color = Color.BLACK

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        deltaWidth = 1
        groupHeight = MeasureSpec.getSize(heightMeasureSpec) / 3
        groupWidth = (MeasureSpec.getSize(widthMeasureSpec) - deltaWidth*4) / 5
        deltaHeight = 0

        initRects()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mCanvas = canvas

        canvas!!.drawRoundRect(mRectF1, mCornersRadius, mCornersRadius, mPaint!!)
        canvas.drawRoundRect(mRectF2, mCornersRadius, mCornersRadius, mPaint!!)
        canvas.drawRoundRect(mRectF3, mCornersRadius, mCornersRadius, mPaint!!)
        canvas.drawRoundRect(mRectF4, mCornersRadius, mCornersRadius, mPaint!!)
        canvas.drawRoundRect(mRectF5, mCornersRadius, mCornersRadius, mPaint!!)

        startAnimation()
    }

    private fun initRects() {
        mRectF1 = RectF(0f, deltaHeight.toFloat(), groupWidth.toFloat(), (height - deltaHeight).toFloat())
        mRectF2 = RectF((groupWidth + deltaWidth).toFloat(), deltaHeight.toFloat(), (groupWidth * 2 + deltaWidth).toFloat(), (height - deltaHeight).toFloat())
        mRectF3 = RectF((groupWidth * 2 + deltaWidth*2).toFloat(), deltaHeight.toFloat(), (groupWidth * 3+deltaWidth*2).toFloat(), (height - deltaHeight).toFloat())
        mRectF4 = RectF((groupWidth * 3 + deltaWidth*3).toFloat(), deltaHeight.toFloat(), (groupWidth * 4+deltaWidth*3).toFloat(), (height - deltaHeight).toFloat())
        mRectF5 = RectF((groupWidth * 4 + deltaWidth*4).toFloat(), deltaHeight.toFloat(), (groupWidth * 5 + deltaWidth*4).toFloat(), (height - deltaHeight).toFloat())
    }

    private fun getRandBitmaps(): Array<Bitmap> {
        return arrayOf(getRandomBtm(), getRandomBtm(), getRandomBtm(), getRandomBtm(), getRandomBtm(), getRandomBtm())
    }

    private fun getRandomBtm(): Bitmap {
        val r = Random().nextInt(4)
        return mBitmaps[r]
    }

    private fun startAnimation() {
        val rectDelta = (groupWidth - slot1.width) / 2
        wheel(mCanvas!!, rectDelta, mRandBitmaps1)
        wheel(mCanvas!!, groupWidth + rectDelta + deltaWidth, mRandBitmaps2)
        wheel(mCanvas!!, groupWidth * 2 + rectDelta+ deltaWidth*2, mRandBitmaps3)
        wheel(mCanvas!!, groupWidth * 3 + rectDelta+ deltaWidth*3, mRandBitmaps4)
        wheel(mCanvas!!, groupWidth * 4 + rectDelta+ deltaWidth*4, mRandBitmaps5)
    }

    private fun wheel(canvas: Canvas, width: Int, bitmaps: Array<Bitmap>) {

        canvas.drawBitmap(bitmaps[0], width.toFloat(), (-groupHeight * 3 + inc - deltaHeight).toFloat(), mPaint)
        canvas.drawBitmap(bitmaps[1], width.toFloat(), (-groupHeight * 2 + inc - deltaHeight).toFloat(), mPaint)
        canvas.drawBitmap(bitmaps[2], width.toFloat(), (-groupHeight + inc - deltaHeight).toFloat(), mPaint)

        canvas.drawBitmap(bitmaps[3], width.toFloat(), (inc2 + deltaHeight).toFloat(), mPaint)
        canvas.drawBitmap(bitmaps[4], width.toFloat(), (groupHeight + inc2 + deltaHeight).toFloat(), mPaint)
        canvas.drawBitmap(bitmaps[5], width.toFloat(), (groupHeight * 2 + inc2 + deltaHeight).toFloat(), mPaint)

        if (isRotation) {

            inc += 3
            inc2 += 3
            inc4++

            if (inc3 == 0) {
                if (inc2 >= height) {
                    inc2 = -groupHeight * 3
                    inc3 = 1
                }
            } else {
                if (inc2 >= height) {
                    inc2 = -groupHeight * 3
                }
                if (inc >= 2 * height) {
                    inc = 0
                }

            }

            if (inc4 >= height * 2) {
                isRotation = false
                deltaHeight = (groupHeight - slot4.height) / 2
                getResult()
            }
        }

        postInvalidate(width, -groupHeight * 3, slot1.width + width, height)
    }

    private fun getResult() {
        Log.i("TAG", "getResult: ")
    }

    fun launchAnimation(isStarted: Boolean) {
        isRotation = isStarted
        inc4 = 0
    }
}