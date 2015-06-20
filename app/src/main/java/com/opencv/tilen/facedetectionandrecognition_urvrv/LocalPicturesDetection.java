package com.opencv.tilen.facedetectionandrecognition_urvrv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Tilen on 10.6.2015.
 */
public class LocalPicturesDetection {
    private Mat localPicture;
    private Context mContext;
    public LocalPicturesDetection(Context context,int resourceId) throws IOException {
        this.mContext = context;
       /* localPicture = new Mat();
        Bitmap bMap= BitmapFactory.decodeResource(mContext.getResources(), R.drawable.test_image_0);
        Utils.bitmapToMat(bMap, localPicture);*/

        localPicture = Utils.loadResource(context, resourceId);
        Global.TestDebug("LocalPicturesDetection.LocalPicturesDetection: localPicture " + localPicture.cols());


    }

    public Mat getlocalPicture() {
        return localPicture;
    }
    public static Bitmap matToBitmap(Mat inputPicture)
    {
        Mat convertedPicture = new Mat();
        Global.TestDebug("test : " +inputPicture.cols());
        Imgproc.cvtColor(inputPicture, convertedPicture, Imgproc.COLOR_RGB2BGRA);
        Bitmap bitmapPicture = Bitmap.createBitmap(inputPicture.cols(), inputPicture.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(convertedPicture, bitmapPicture);
        return bitmapPicture;
    }

    public static Mat bitmapToMat(Bitmap inputPicture)
    {
        Mat matPicture = new Mat();
        Utils.bitmapToMat(inputPicture, matPicture);
        Imgproc.cvtColor(matPicture, matPicture, Imgproc.COLOR_RGB2BGRA);
        return matPicture;
    }

    public static void saveBitmaps(Mat[] faceImages, Context mContext)
    {
        File cacheDir = mContext.getCacheDir();
        File file;
        FileOutputStream out;
        Bitmap bitmapPicture;
        for(int i = 0; i < faceImages.length;i++) {
            file = new File(cacheDir, "faceImage" + i);
            bitmapPicture = LocalPicturesDetection.matToBitmap(faceImages[i]);
            try {
                out = new FileOutputStream(file);
                bitmapPicture.compress(
                        Bitmap.CompressFormat.PNG,
                        100, out);
                out.flush();
                out.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap[] loadBitmaps(int numberOfImages, Context mContext)
    {
        File cacheDir = mContext.getCacheDir();
        File file;
        FileInputStream fis;
        Bitmap[] faceImages = new Bitmap[numberOfImages];
        for(int i= 0; i < numberOfImages; i++) {
            file = new File(cacheDir, "faceImage" + i);
            fis = null;
            try {
                fis = new FileInputStream(file);
                faceImages[i] = BitmapFactory.decodeStream(fis);
                file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return faceImages;
    }
}
