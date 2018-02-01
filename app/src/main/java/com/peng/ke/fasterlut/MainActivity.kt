package com.peng.ke.fasterlut

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.peng.ke.fasterlookuptable.FasterLut
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mTables: Array<Int> by lazy {
        arrayOf(R.drawable.lut_black_blue,
                R.drawable.lut_black_pink,
                R.drawable.lut_black_white_1,
                R.drawable.lut_black_white_2,
                R.drawable.lut_color_1,
                R.drawable.lut_color_2,
                R.drawable.lut_standard)
    }

    private val beauty: Bitmap by lazy {
        val options = BitmapFactory.Options()
        options.inScaled = false
        BitmapFactory.decodeResource(resources, R.drawable.beauty, options)
    }

    private var mTableIndex: Int = 0

    private lateinit var mFasterLut: FasterLut

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFasterLut = FasterLut()

        val filteredBitmap = mFasterLut.fasterLut(beauty, mTables[mTableIndex], this)
        filtered.setImageBitmap(filteredBitmap)
        filtered.setOnClickListener {
            mTableIndex = (mTableIndex + 1) % (mTables.size - 1)
            val filterBmp = mFasterLut.fasterLut(beauty, mTables[mTableIndex], this)
            filtered.setImageBitmap(filterBmp)
        }
    }
}
