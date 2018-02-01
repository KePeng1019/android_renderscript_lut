#pragma version(1)
#pragma rs java_package_name(com.peng.ke.fasterlookuptable)
rs_allocation mLut;
uint width = 512;
uint height = 512;
uchar4 RS_KERNEL lutFilter(uchar4 in,uint32_t x, uint32_t y) {
   uchar4 out = in;
   uchar4 lutPoint = rsGetElementAt_uchar4(mLut, in.r * width / 256 , in.g * width / 256 + in.b * width / 256 * width);
   out.rgb = lutPoint.rgb;
   return out;
}