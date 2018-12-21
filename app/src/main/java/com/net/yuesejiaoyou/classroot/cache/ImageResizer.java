/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.net.yuesejiaoyou.classroot.cache;

import java.io.File;
import java.io.FileDescriptor;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;


/**
 * A simple subclass of {@link ImageWorker} that resizes images from resources given a target width
 * and height. Useful for when the input images might be too large to simply load directly into
 * memory.
 */
public class ImageResizer extends ImageWorker {
    protected int mImageWidth;
    protected int mImageHeight;
    //protected ImageSearchUtil mImageSearcher;
    private ContentResolver resolver = null;
    

    /**
     * Initialize providing a single target image size (used for both width and height);
     *
     * @param context
     * @param imageWidth
     * @param imageHeight
     */
    public ImageResizer(Context context, int imageWidth, int imageHeight) {
        super(context);
        resolver = context.getContentResolver();
        setImageSize(imageWidth, imageHeight);
    }

    /**
     * Initialize providing a single target image size (used for both width and height);
     *
     * @param context
     * @param imageSize
     */
    public ImageResizer(Context context, int imageSize) {
        super(context);
        resolver = context.getContentResolver();
        setImageSize(imageSize);
    }

    /**
     * Set the target image width and height.
     *
     * @param width
     * @param height
     */
    public void setImageSize(int width, int height) {
        mImageWidth = width;
        mImageHeight = height;
    }
    
    /*public void setImageSearcher(ImageSearchUtil imageSearcher)
    {
        mImageSearcher = imageSearcher;
    }*/

    /**
     * Set the target image size (width and height will be the same).
     *
     * @param size
     */
    public void setImageSize(int size) {
        setImageSize(size, size);
    }
    
    @SuppressWarnings("static-access")
	@Override
    protected Bitmap processBitmap(Object data,Object w,Object h)
    {
        /*if (null != mImageSearcher && null != data)
        {
            if (data instanceof MediaInfo)
            {
                MediaInfo info = (MediaInfo)data;
                Bitmap thumb = mImageSearcher.getImageThumbnail2(info);

                return thumb;
            }
        }*/
    	//String path="/lanxiaohai";
    	Bitmap bitmap = null;
		if(data != null){
			File file = null;
			//String real_path = "";
			try {
				/*if(Util.getInstance().hasSDCard()){
					real_path = Util.getInstance().getExtPath() + (path != null && path.startsWith("/") ? path : "/" + path);
				}else{
					//real_path = Util.getInstance().getPackagePath(mActivity) + (path != null && path.startsWith("/") ? path : "/" + path);
				}
				file = new File(real_path, String.valueOf(data));*/
				file = new File(String.valueOf(data));
				if(file.exists()){
					//bitmap=decodeSampledBitmapFromFile(real_path+"/"+String.valueOf(data),mImageWidth,mImageHeight);
					
					//LogDetect.sendto(DataType.basicType, "01107", "存在");
					
//					Bitmap a = BitmapFactory.decodeStream(new FileInputStream(file));
					
					//bitmap=centerSquareScaleBitmap(a,(int)w);
					
//					int width = a.getWidth();    
//				    int height = a.getHeight();    
//					if(width<=(int)w || height<=(int)h){
//						bitmap=a;
//					}else{
//						bitmap=a.createScaledBitmap(a, (int)w, (int)h, true);
//					}
					
					//BitmapFactory.Options options = new BitmapFactory.Options(); 
	                //options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一 
	                // bitmap = BitmapFactory.decodeFile(real_path+"/"+imageName, options); 
//					if(!a.isRecycled() && a!=bitmap){
//						a.recycle();
//						System.gc();
//					}
					// --------------------------------------
//					BitmapFactory.Options options = new BitmapFactory.Options();
//					options.inJustDecodeBounds = true;
//					BitmapFactory.decodeFile(String.valueOf(data),options);
//					options.inJustDecodeBounds = false;
//					int width = options.outWidth;
//					int height = options.outHeight;
//					int xSample = Math.round((float)width/200);
//					int ySample = Math.round((float)height/200);
//					int sample = xSample>ySample?ySample:xSample;
//					options.inSampleSize = sample;
//					bitmap = BitmapFactory.decodeFile(String.valueOf(data),options);
					
					bitmap = getBitmap(resolver,String.valueOf(data));
				} else {
					//LogDetect.sendto(DataType.basicType, "01107", "不存在");
				}
				
				//bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
			} catch (Exception e) {
				e.printStackTrace();
				bitmap = null;
			}
		}
		return bitmap;
    	
    	
        //return null;
    }
    
    @SuppressWarnings("static-access")
	@Override
    public Bitmap processBitmapNet(Object data,Object w,Object h)
    {
        /*if (null != mImageSearcher && null != data)
        {
            if (data instanceof MediaInfo)
            {
                MediaInfo info = (MediaInfo)data;
                Bitmap thumb = mImageSearcher.getImageThumbnail2(info);

                return thumb;
            }
        }*/
    	//String path="/lanxiaohai";
    	Bitmap bitmap = null;
		if(data != null){
			File file = null;
			//String real_path = "";
			try {
				/*if(Util.getInstance().hasSDCard()){
					real_path = Util.getInstance().getExtPath() + (path != null && path.startsWith("/") ? path : "/" + path);
				}else{
					//real_path = Util.getInstance().getPackagePath(mActivity) + (path != null && path.startsWith("/") ? path : "/" + path);
				}
				file = new File(real_path, String.valueOf(data));*/
				file = new File(String.valueOf(data));
				if(file.exists()){
					//bitmap=decodeSampledBitmapFromFile(real_path+"/"+String.valueOf(data),mImageWidth,mImageHeight);
					
					//LogDetect.sendto(DataType.basicType, "01107", "存在");
					
//					Bitmap a = BitmapFactory.decodeStream(new FileInputStream(file));
					
					//bitmap=centerSquareScaleBitmap(a,(int)w);
					
//					int width = a.getWidth();    
//				    int height = a.getHeight();    
//					if(width<=(int)w || height<=(int)h){
//						bitmap=a;
//					}else{
//						bitmap=a.createScaledBitmap(a, (int)w, (int)h, true);
//					}
					
					//BitmapFactory.Options options = new BitmapFactory.Options(); 
	                //options.inSampleSize = 2;//图片宽高都为原来的二分之一，即图片为原来的四分之一 
	                // bitmap = BitmapFactory.decodeFile(real_path+"/"+imageName, options); 
//					if(!a.isRecycled() && a!=bitmap){
//						a.recycle();
//						System.gc();
//					}
					// --------------------------------------
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(String.valueOf(data),options);
					options.inJustDecodeBounds = false;
//					int width = options.outWidth;
//					int height = options.outHeight;
//					int xSample = Math.round(width/(int)w);
//					int ySample = Math.round(height/(int)h);
//					int sample = xSample>ySample?ySample:xSample;
//					options.inSampleSize=sample;
					options.inSampleSize = calculateInSampleSize(options,/*(int)w,(int)h*/Integer.parseInt(String.valueOf(w)),Integer.parseInt(String.valueOf(h)));
					bitmap = BitmapFactory.decodeFile(String.valueOf(data),options);

					//bitmap = getBitmap(resolver,String.valueOf(data));
				} else {
					//LogDetect.sendto(DataType.basicType, "01107", "不存在");
				}
				
				//bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
			} catch (Exception e) {
				e.printStackTrace();
				bitmap = null;
			}
		}
		return bitmap;
    	
    	
        //return null;
    }
    
    public Bitmap getBitmap(ContentResolver cr, String fileName) {
    	Bitmap bitmap = null;
    	BitmapFactory.Options options = new BitmapFactory.Options();
    	options.inDither = false;
    	options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    	//select condition.
    	String whereClause = MediaStore.Images.Media.DATA + " = '" + fileName + "'";
    	//logDbg.sendto(DataType.basicType,"01107","whereClause: "+whereClause+",cr: "+cr);
    	//colection of results.
    	Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new String[] { MediaStore.Images.Media._ID }, whereClause,null, null);
    	//logDbg.sendto(DataType.basicType,"01107","cursor: "+cursor.getCount());
    	if (cursor == null || cursor.getCount() == 0) {
    	if(cursor != null)
    	cursor.close();
    	return null;
    	}
    	cursor.moveToFirst();
    	//image id in image table.
    	String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
    	cursor.close();
    	if (videoId == null) {
    	return null;
    	}
    	long videoIdLong = Long.parseLong(videoId);
    	//via imageid get the bimap type thumbnail in thumbnail table.
    	bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, videoIdLong,Images.Thumbnails.MINI_KIND, options);
    	return bitmap;
    	}

    /**
	 * 工具类方法，裁剪bitmap正方形正中间部分
	 * @param bitmap
	 * @param edgeLength
	 * @return
	 */
	private Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
		if (null == bitmap || edgeLength <= 0) {
			return null;
		}

		Bitmap result = bitmap;
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();

		if (widthOrg > edgeLength && heightOrg > edgeLength) {
			// 压缩到一个最小长度是edgeLength的bitmap
			int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math
					.min(widthOrg, heightOrg));
			int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
			int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
			Bitmap scaledBitmap;

			try {
				scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,
						scaledHeight, true);
			} catch (Exception e) {
				return null;
			}

			// 从图中截取正中间的正方形部分。
			int xTopLeft = (scaledWidth - edgeLength) / 2;
			int yTopLeft = (scaledHeight - edgeLength) / 2;

			try {
				result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft,
						edgeLength, edgeLength);
				scaledBitmap.recycle();
			} catch (Exception e) {
				return null;
			}
		}

		return result;
	}
    
    
    /**
     * Decode and sample down a bitmap from resources to the requested width and height.
     *
     * @param res The resources object containing the image data
     * @param resId The resource id of the image data
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     *         that are equal to or greater than the requested width and height
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * Decode and sample down a bitmap from a file to the requested width and height.
     *
     * @param filename The full path of the file to decode
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     *         that are equal to or greater than the requested width and height
     */
    public static Bitmap decodeSampledBitmapFromFile(String filename,
            int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);

        // Calculate inSampleSize
        //options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inSampleSize=2;
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

    /**
     * Decode and sample down a bitmap from a file input stream to the requested width and height.
     *
     * @param fileDescriptor The file descriptor to read from
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     *         that are equal to or greater than the requested width and height
     */
    public static Bitmap decodeSampledBitmapFromDescriptor(
            FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options An options object with out* params already populated (run through a decode*
     *            method with inJustDecodeBounds==true
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further.
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
}
