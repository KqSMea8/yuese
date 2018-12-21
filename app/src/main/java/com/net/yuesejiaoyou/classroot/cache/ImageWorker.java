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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/*import com.example.vliao.interface4.LogDetect;
import com.example.vliao.util.Util;*/











/**
 * This class wraps up completing some arbitrary long running work when loading a bitmap to an
 * ImageView. It handles things like using a memory and disk cache, running the work in a background
 * thread and setting a placeholder image.
 */
public abstract class ImageWorker {
    private static final String TAG = "ImageWorker";
    private static final int FADE_IN_TIME = 200;

    private ImageCache mImageCache;
    private Bitmap mLoadingBitmap;
    private boolean mFadeInBitmap = true;
    private boolean mExitTasksEarly = false;
    protected boolean mPauseWork = false;
    private final Object mPauseWorkLock = new Object();

    protected Resources mResources;
    private Context context;
    
    private LogDetect logDbg = new LogDetect();

    protected ImageWorker(Context context) {
    	this.context = context;
        mResources = context.getResources();
    }

    public String getImageName(String url) {
		String imageName = "";
		if(url != null){
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}
    
    
    /**
     * Load an image specified by the data parameter into an ImageView (override
     * {@link ImageWorker#processBitmap(Object)} to define the processing logic). A memory and disk
     * cache will be used if an {@link ImageCache} has been set using
     * {@link ImageWorker#setImageCache(ImageCache)}. If the image is found in the memory cache, it
     * is set immediately, otherwise an {@link AsyncTask} will be created to asynchronously load the
     * bitmap.
     *
     * @param data The URL of the image to download.
     * @param imageView The ImageView to bind the downloaded image to.
     */
    public void loadImage(String data, ImageView imageView,int w,int h) {
        if (data == null) {
            return;
        }

        
        Bitmap bitmap = null;

        if (mImageCache != null) {
            bitmap = mImageCache.getBitmapFromMemCache(data);
        }
        
        if (bitmap != null) {
            // Bitmap found in memory cache
            imageView.setImageBitmap(bitmap);
        } else if (cancelPotentialWork(data, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mResources, mLoadingBitmap, task);
            imageView.setImageDrawable(asyncDrawable);

            // NOTE: This uses a custom version of AsyncTask that has been pulled from the
            // framework and slightly modified. Refer to the docs at the top of the class
            // for more info on what was changed.
            task.executeOnExecutor(AsyncTask.DUAL_THREAD_EXECUTOR, data,w,h);
        }
    }

    /**
     * Set placeholder bitmap that shows when the the background thread is running.
     *
     * @param bitmap
     */
    public void setLoadingImage(Bitmap bitmap) {
        mLoadingBitmap = bitmap;
    }

    /**
     * Set placeholder bitmap that shows when the the background thread is running.
     *
     * @param resId
     */
    public void setLoadingImage(int resId) {
        mLoadingBitmap = BitmapFactory.decodeResource(mResources, resId);
    }

    /**
     * Sets the {@link ImageCache} object to use with this ImageWorker. Usually you will not need
     * to call this directly, instead use {@link ImageWorker#addImageCache} which will create and
     * add the {@link ImageCache} object in a background thread (to ensure no disk access on the
     * MainActivity/UI thread).
     *
     * @param imageCache
     */
    public void setImageCache(ImageCache imageCache) {
        mImageCache = imageCache;
    }

    /**
     * If set to true, the image will fade-in once it has been loaded by the background thread.
     */
    public void setImageFadeIn(boolean fadeIn) {
        mFadeInBitmap = fadeIn;
    }

    public void setExitTasksEarly(boolean exitTasksEarly) {
        mExitTasksEarly = exitTasksEarly;
    }

    /**
     * Subclasses should override this to define any processing or work that must happen to produce
     * the final bitmap. This will be executed in a background thread and be long running. For
     * example, you could resize a large bitmap here, or pull down an image from the network.
     *
     * @param data The data to identify which image to process, as provided by
     *            {@link ImageWorker#loadImage(Object, ImageView)}
     * @return The processed bitmap
     */
    protected abstract Bitmap processBitmap(Object data,Object w,Object h);
    
    protected abstract Bitmap processBitmapNet(Object data,Object w,Object h);

    /**
     * Cancels any pending work attached to the provided ImageView.
     * @param imageView
     */
    public static void cancelWork(ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if (bitmapWorkerTask != null) {
            bitmapWorkerTask.cancel(true);
            if (BuildConfig.DEBUG) {
                final Object bitmapData = bitmapWorkerTask.data;
                Log.d(TAG, "cancelWork - cancelled work for " + bitmapData);
            }
        }
    }

    /**
     * Returns true if the current work has been canceled or if there was no work in
     * progress on this image view.
     * Returns false if the work in progress deals with the same data. The work is not
     * stopped in that case.
     */
    public static boolean cancelPotentialWork(Object data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final Object bitmapData = bitmapWorkerTask.data;
            if (bitmapData == null || !bitmapData.equals(data)) {
                bitmapWorkerTask.cancel(true);
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "cancelPotentialWork - cancelled work for " + data);
                }
            } else {
                // The same work is already in progress.
                return false;
            }
        }
        return true;
    }

    /**
     * @param imageView Any imageView
     * @return Retrieve the currently active work task (if any) associated with this imageView.
     * null if there is no such task.
     */
    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    /**
     * The actual AsyncTask that will asynchronously process the image.
     */
    //private class BitmapWorkerTask extends android.os.AsyncTask<Object, Void, Bitmap> {
    private class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {
        private Object data;
        private Object width;
        private Object height;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }
        
        public int calculateInSampleSize(BitmapFactory.Options options,
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

        /**
         * Background processing.
         */
        @Override
        protected Bitmap doInBackground(Object... params) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "doInBackground - starting work");
            }

            data = params[0];
            width= params[1];
            height= params[2];
            final String dataString = String.valueOf(data);
            
            String imageName = "";
    		if(data != null){
    			if(dataString.startsWith("http:")) {
    				imageName = Util.getInstance().getExtPath()+"/lanxiaohai/"+getImageName(dataString);
    			} else {
    				imageName = dataString;
    			}
    		}
    		
            Bitmap bitmap = null;

            // Wait here if work is paused and the task is not cancelled
            synchronized (mPauseWorkLock) {
                while (mPauseWork && !isCancelled()) {
                    try {
                        mPauseWorkLock.wait();
                    } catch (InterruptedException e) {}
                }
            }

            // If the image cache is available and this task has not been cancelled by another
            // thread and the ImageView that was originally bound to this task is still bound back
            // to this task and our "exit early" flag is not set then try and fetch the bitmap from
            // the cache
            if (mImageCache != null && !isCancelled() && getAttachedImageView() != null
                    && !mExitTasksEarly) {
            }

            // If the bitmap was not found in the cache and this task has not been cancelled by
            // another thread and the ImageView that was originally bound to this task is still
            // bound back to this task and our "exit early" flag is not set, then call the MainActivity
            // process method (as implemented by a subclass)
            if (bitmap == null && !isCancelled() && getAttachedImageView() != null
                    && !mExitTasksEarly) {
            	//logDbg.sendto(DataType.basicType, "01107", "本地图片：");
            	if(dataString.startsWith("http:")) {
            		bitmap = processBitmapNet(imageName,width,height);
            	} else {
            		bitmap = processBitmap(imageName,width,height);
            	}
            } else {
            	//logDbg.sendto(DataType.basicType, "01107", "不满足");
            }
            //logDbg.sendto(DataType.basicType, "01107", "imageName: "+imageName+", bitmap: "+bitmap);
            if (dataString.startsWith("http:") && bitmap == null && !isCancelled() && getAttachedImageView() != null
                    && !mExitTasksEarly) {
            	//logDbg.sendto(DataType.basicType, "01107", "加载网络图片");
				try {
					URL c_url = new URL(dataString);
					InputStream bitmap_data;

					HttpURLConnection conn = (HttpURLConnection)c_url.openConnection();  
			        conn.setRequestMethod("GET");   //设置请求方法为GET  
			        conn.setReadTimeout(5*1000);    //设置请求过时时间为5秒  
					
			        bitmap_data = conn.getInputStream();
					
					//bitmap_data = c_url.openStream();

					//BitmapFactory.Options options = new BitmapFactory.Options(); 
					//options.inSampleSize = 8;
					//Bitmap bitmapin = BitmapFactory.decodeStream(bitmap_data, null, options); 
					
					
			        setBitmapToFile("/lanxiaohai",imageName, bitmap_data);
			        //--------------------------------------------------------------------------
					//Bitmap bitmapin = BitmapFactory.decodeStream(is, outPadding, opts);
					//Bitmap bitmapin = decodeSampledBitmapFromStream(bitmap_data,200,200);
			        // First decode with inJustDecodeBounds=true to check dimensions
			        final BitmapFactory.Options options = new BitmapFactory.Options();
			        options.inJustDecodeBounds = true;
			        BitmapFactory.decodeStream(bitmap_data, null, options);

			        // Calculate inSampleSize
			        options.inSampleSize = calculateInSampleSize(options,/*(int)width*/Integer.parseInt(String.valueOf(width)), /*(int)height*/Integer.parseInt(String.valueOf(height)));

			        // Decode bitmap with inSampleSize set
			        options.inJustDecodeBounds = false;
			        bitmap = BitmapFactory.decodeFile(imageName, options);
			      //--------------------------------------------------------------------------
			        
					
					
					bitmap_data.close();
					bitmap_data=null;
					conn.disconnect();
					conn=null;
					c_url=null;
					Log.d(TAG, "doInBackground - internet work");	
					//context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory()+ "/lanxiaohai"))); 
					//setExitTasksEarly(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			
            }
            
//            if (bitmap == null && !isCancelled() && getAttachedImageView() != null
//                    && !mExitTasksEarly) {
//            	bitmap = processBitmap(imageName,width,height);
//            }
            
           /* */
            
            
            
            // If the bitmap was processed and the image cache is available, then add the processed
            // bitmap to the cache for future use. Note we don't check if the task was cancelled
            // here, if it was, and the thread is still running, we may as well add the processed
            // bitmap to our cache as it might be used again in the future
            if (bitmap != null && mImageCache != null) {
                mImageCache.addBitmapToCache(dataString, bitmap);
            }

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "doInBackground - finished work");
            }

            return bitmap;
        }

        /**
    	 * 将下载好的图片存放到文件中
    	 * @param path 图片路径
    	 * @param mActivity
    	 * @param imageName 图片名字
    	 * @param bitmap 图片
    	 * @return
    	 */
    	private boolean setBitmapToFile(String path/*,Activity mActivity*/,String imageName,InputStream is){
    		File file = null;
    		String real_path = "";
    		try {
    			if(Util.getInstance().hasSDCard()){
    				real_path = Util.getInstance().getExtPath() + (path != null && path.startsWith("/") ? path : "/" + path);
    			}else{
    				//real_path = Util.getInstance().getPackagePath(mActivity) + (path != null && path.startsWith("/") ? path : "/" + path);
    			}
    			file = new File(imageName);
    			if(!file.exists()){
    				File file2 = new File(real_path + "/");
    				file2.mkdirs();
    			}
    			file.createNewFile();
    			FileOutputStream fos = null;
    			if(Util.getInstance().hasSDCard()){
    				fos = new FileOutputStream(file);
    			}else{
    				//fos = mActivity.openFileOutput(imageName, Context.MODE_PRIVATE);
    			}
    			
    			byte[] buf = new byte[1024];
    			int byteCount = 0;
    			//int byteWritten = 0;
    			while((byteCount = is.read(buf,0,1024)) != -1) {
    				fos.write(buf, 0, byteCount);
    				//byteWritten += byteCount;
    			}
//    			if (imageName != null && (imageName.contains(".png") || imageName.contains(".PNG"))){
//    				bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
//    			}
//    			else{
//    				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
//    			}
    			fos.flush();
    			if(fos != null){
    				fos.close();
    			}
//    			bitmap.recycle();
    			return true;
    		} catch (Exception e) {
    			e.printStackTrace();
    			return false;
    		}
    	}
        
        
        
        /**
         * Once the image is processed, associates it to the imageView
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // if cancel was called on this task or the "exit early" flag is set then we're done
            if (isCancelled() || mExitTasksEarly) {
                bitmap = null;
            }

            final ImageView imageView = getAttachedImageView();
            if (bitmap != null && imageView != null) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "onPostExecute - setting bitmap");
                }
                setImageBitmap(imageView, bitmap);
            }
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {
            super.onCancelled(bitmap);
            synchronized (mPauseWorkLock) {
                mPauseWorkLock.notifyAll();
            }
        }

        /**
         * Returns the ImageView associated with this task as long as the ImageView's task still
         * points to this task as well. Returns null otherwise.
         */
        private ImageView getAttachedImageView() {
            final ImageView imageView = imageViewReference.get();
            final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

            if (this == bitmapWorkerTask) {
                return imageView;
            }

            return null;
        }
    }

    /**
     * A custom Drawable that will be attached to the imageView while the work is in progress.
     * Contains a reference to the actual worker task, so that it can be stopped if a new binding is
     * required, and makes sure that only the last started worker process can bind its result,
     * independently of the finish order.
     */
    private static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    /**
     * Called when the processing is complete and the final bitmap should be set on the ImageView.
     *
     * @param imageView
     * @param bitmap
     */
    private void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (mFadeInBitmap) {
            // Transition drawable with a transparent drwabale and the final bitmap
            final TransitionDrawable td =
                    new TransitionDrawable(new Drawable[] {
                            new ColorDrawable(android.R.color.transparent),
                            new BitmapDrawable(mResources, bitmap)
                    });
            /*
            // Set background to loading bitmap
            imageView.setBackgroundDrawable(
                    new BitmapDrawable(mResources, mLoadingBitmap));
            */
            imageView.setImageDrawable(td);
            td.startTransition(FADE_IN_TIME);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void setPauseWork(boolean pauseWork) {
        synchronized (mPauseWorkLock) {
            mPauseWork = pauseWork;
            if (!mPauseWork) {
                mPauseWorkLock.notifyAll();
            }
        }
    }
}
