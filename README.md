# About this project
This project complete look up table by android render script which accelerate lut by gpu. write in kotlin and renderscript,
you can simple import this project if you are developing picture filter app, cube file parse also available in this project.

# Key files in this project
## lut.rs file
jut six lines code function accomplish lut algorithm

```
uchar4 RS_KERNEL lutFilter(uchar4 in,uint32_t x, uint32_t y) {
   uchar4 out = in;
   uchar4 lutPoint = rsGetElementAt_uchar4(mLut, in.r * width / 256 , in.g * width / 256 + in.b * width / 256 * width);
   out.rgb = lutPoint.rgb;
   return out;
}
```
## FasterLut.kt
###provide both lut filter and cube file parse function.
this project assume you provide some format lut filter bitmap with 64 width and 64 * 64 height, with width grow the R channel
from 0 to 255 and height grow the G channel form 0 to 255 in evevry rectangle. with rectangle count up on y dimension the B chanel from 0 to 255.

this  will do every thing
```
val fasterLut = FasterLut()
val result = fasterLut.fasterLut(yourBitmap, yourFilter, context)
```
Done!

# Some example
below are screenshot of the demo app

## Lut file
<img src="https://github.com/KePeng1019/android_renderscript_lut/blob/master/app/src/main/res/drawable/lut_standard.png" height="1280">

## Filter result

<img src="https://github.com/KePeng1019/android_renderscript_lut/blob/master/app/picturesample/2.png" width="220">
<img src="https://github.com/KePeng1019/android_renderscript_lut/blob/master/app/picturesample/3.png" width="220">
<img src="https://github.com/KePeng1019/android_renderscript_lut/blob/master/app/picturesample/4.png" width="220">
<img src="https://github.com/KePeng1019/android_renderscript_lut/blob/master/app/picturesample/5.png" width="220">

