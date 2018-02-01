package com.peng.ke.fasterlookuptable

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.renderscript.Allocation
import android.renderscript.RenderScript
import java.io.*

class FasterLut {

    private val unScaleOption: BitmapFactory.Options by lazy {
        BitmapFactory.Options().apply {
            inScaled = false
        }
    }

    /**
     * apply look up table by android render script
     * @param src input bitmap
     * @param tableId your look up table resource
     * @param context
     * @return bitmap after apply filter
     */

    fun fasterLut(src: Bitmap, tableId: Int, context: Context): Bitmap {
        val table = BitmapFactory.decodeResource(context.resources, tableId, unScaleOption)
        val result = Bitmap.createBitmap(src.width, src.height, src.config)
        val rs = RenderScript.create(context)
        val tableRs = Allocation.createFromBitmap(rs, table)

        val inAllocation = Allocation.createFromBitmap(rs, src)
        val outAllocation = Allocation.createFromBitmap(rs, result)

        val lutScript = ScriptC_lut(rs)

        lutScript._mLut = tableRs
        lutScript._width = table.width.toLong()
        lutScript.forEach_lutFilter(inAllocation, outAllocation)
        outAllocation.copyTo(result)
        rs.destroy()
        return result
    }

    /**
     * if you have .cube file, you can parse the cube file to look up table bitmap
     * @param sourceId your cube file id
     * @return look up table bitmap format
     */

    fun parserCuber(sourceId: Int, ctx: Context): Bitmap? {
        try {
            val resultBitmap = Bitmap.createBitmap(64, 64 * 64, Bitmap.Config.ARGB_8888)
            val pixels = IntArray(64 * 64 * 64)
            val inputStream = ctx.resources.openRawResource(sourceId)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var lineContent = reader.readLine()
            var widthIndex = 0
            var heightIndex = 0
            while ((lineContent != null)) {
                val lineNumbers = lineContent.split(" ")
                val red = lineNumbers[0].toFloat() * 255
                val green = lineNumbers[1].toFloat() * 255
                val blue = lineNumbers[2].toFloat() * 255
                pixels[widthIndex + heightIndex * 64] = Color.argb(255, red.toInt(), green.toInt(), blue.toInt())
                widthIndex++
                if (widthIndex >= 64) {
                    widthIndex -= 64
                    heightIndex++
                }
                lineContent = reader.readLine()
            }
            reader.close()
            inputStream.close()
            resultBitmap.setPixels(pixels, 0, resultBitmap.width, 0, 0, resultBitmap.width, resultBitmap.height)
            return resultBitmap
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}