package com.dili.formation8.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Map;

/**
 * 使用了google zxing作为二维码生成工具
 */
public class ZxingUtils {
    private static Log log = LogFactory.getLog(ZxingUtils.class);

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    private static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    /** 将内容contents生成长宽均为width的图片，图片路径由imgPath指定
     */
    public static File getQRCodeImge(String contents, int width, String imgPath) {
        return getQRCodeImge(contents, width, width, imgPath);
    }

    /** 将内容contents生成长为width，宽为width的图片，图片路径由imgPath指定
     */
    public static File getQRCodeImge(String contents, int width, int height, String imgPath) {
        try {
            Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

            File imageFile = new File(imgPath);
            writeToFile(bitMatrix, "png", imageFile);

            return imageFile;

        } catch (Exception e) {
            log.error("create QR code error!", e);
            return null;
        }
    }


    /**
     * 用于页面输出QR code
     * 例:
     * MatrixUtil.writeToStream(MatrixUtil.toQrCodeMatrix(str, width, height), fmt == null ? "jpg" : fmt, getResponse().getOutputStream());
     * getResponse().getOutputStream().flush();
     *
     * @param matrix
     * @param format
     * @param stream
     * @throws IOException
     */
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if(!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static BitMatrix toQrCodeMatrix(String str, Integer width, Integer height) {
        try {
            Hashtable e = new Hashtable();
            e.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = (new MultiFormatWriter()).encode(str, BarcodeFormat.QR_CODE, width.intValue(), height.intValue(), e);
            return bitMatrix;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String decode(File file) {
        try {
            if(file != null && file.exists()) {
                BufferedImage image = ImageIO.read(file);
                BufferedImageLuminanceSource e = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(e));
                Hashtable hints = new Hashtable();
                hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
                Result result = (new MultiFormatReader()).decode(bitmap, hints);
                return result.getText();
            } else {
                throw new Exception(" File not found:" + file.getPath());
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }
}
