package com.sf.vsolution.hx.hanzt.template.generator.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * @Description:
 * @Author 01415355
 * @Date 2023/5/8 14:47
 */
@Slf4j
public class DrawImageUtil {
    /**
     * 默认字体大小
     */
    private static final int DEFAULT_FONT_SIZE = 9;

    /**
     * 默认图片宽度
     */
    private static final int DEFAULT_PICTURE_WIDTH = 150;

    /**
     * 默认图片高度
     */
    private static final int DEFAULT_PICTURE_HEIGHT = 50;


    private static final String CHARSET = "utf-8";
    public static final String FORMAT_NAME = "JPG";
    /**
     * 二维码尺寸
     */
    private static final int QRCODE_SIZE = 200;
    /**
     * LOGO宽度
     */
    private static final int WIDTH = 60;
    /**
     * LOGO高度
     */
    private static final int HEIGHT = 60;

    public static void generateCode(File file, String code,
                                    String bottomStr,
                                    String topLeftStr,
                                    String topRightStr,
                                    int width,
                                    int height) {
        //定义位图矩阵BitMatrix
        BitMatrix matrix = null;
        try (FileOutputStream outStream = new FileOutputStream(file)) {
            // 使用code_128格式进行编码生成100*25的条形码
            MultiFormatWriter writer = new MultiFormatWriter();
            matrix = writer.encode(code, BarcodeFormat.CODE_128, width, height, null);
            //将位图矩阵BitMatrix保存为图片
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
            // 添加文字到条形码中
            BufferedImage codeWithWords = getCodeWithWords(bufferedImage, bottomStr, topLeftStr, topRightStr, DEFAULT_PICTURE_WIDTH, DEFAULT_PICTURE_HEIGHT,
                    0, 0, 0, 0, 0, 0, DEFAULT_FONT_SIZE);
            ImageIO.write(codeWithWords, "jpg", outStream);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 等比例缩放
        changeImageSize(Arrays.asList(file.getAbsolutePath()), 300, 50);
    }

    public static void changeImageSize(List<String> list, int scaleWidth, int scaleHeight) {
        String tempPath, formatName;
        try {
            for (String filePath : list) {
                // 获取当前文件后缀名
                String[] split = filePath.split("\\.");
                formatName = split[1];
                tempPath = split[0] + FileConstant.IMAGE_TEMP_PREFIX + "." + formatName;
                changeImageSize(filePath, tempPath, scaleWidth, scaleHeight, formatName);
            }
        } catch (Exception e) {
            log.error("changeImage failed,cause by ", e);
        }
    }


    /**
     * @param filePath   需要调整大小文件路径
     * @param tempPath   临时文件路径
     * @param width      等比例缩放宽度
     * @param height     等比例缩放高度
     * @param formatName 文件后缀名
     */
    public static void changeImageSize(String filePath, String tempPath, int width, int height, String formatName) {
        FileOutputStream out = null;
        try {
            // 修改当前图片名称
            File oldFile = new File(filePath);
            File tempFile = new File(tempPath);
//            Files.move(Paths.get(oldFile.getAbsolutePath()), Paths.get(tempPath));
            oldFile.renameTo(tempFile);
            BufferedImage img = ImageIO.read(tempFile);
            // 设置图片缩放比例
            Image _img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            // 创建新的图片
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 创建画布
            Graphics2D graphics = image.createGraphics();
            // 图片写入画布
            graphics.drawImage(_img, 0, 0, null);
            // 释放资源
            graphics.dispose();
            // 将图片写出到文件中
            out = new FileOutputStream(filePath);
            ImageIO.write(image, formatName, out);
            out.close();
            // 删除临时文件
            if (tempFile.exists()) {
                tempFile.delete();
            }
        } catch (Exception e) {
            log.error("changeImage failed,cause by ", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("changeImage close stream failed");
                }
            }
        }
    }


    /**
     * 获取条形码
     *
     * @param codeImage       条形码图片
     * @param bottomStr       底部文字
     * @param topLeftStr      左上角文字
     * @param topRightStr     右上角文字
     * @param pictureWidth    图片宽度
     * @param pictureHeight   图片高度
     * @param codeOffsetX     条形码宽度
     * @param codeOffsetY     条形码高度
     * @param topLeftOffsetX  左上角文字X轴偏移量
     * @param topLeftOffsetY  左上角文字Y轴偏移量
     * @param topRightOffsetX 右上角文字X轴偏移量
     * @param topRightOffsetY 右上角文字Y轴偏移量
     * @param fontSize        字体大小
     * @return 条形码图片
     */
    public static BufferedImage getCodeWithWords(BufferedImage codeImage,
                                                 String bottomStr,
                                                 String topLeftStr,
                                                 String topRightStr,
                                                 int pictureWidth,
                                                 int pictureHeight,
                                                 int codeOffsetX,
                                                 int codeOffsetY,
                                                 int topLeftOffsetX,
                                                 int topLeftOffsetY,
                                                 int topRightOffsetX,
                                                 int topRightOffsetY,
                                                 int fontSize) {
        BufferedImage picImage = new BufferedImage(pictureWidth, pictureHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = picImage.createGraphics();
        // 抗锯齿
        setGraphics2D(g2d);
        // 设置白色
        setColorWhite(g2d, picImage.getWidth(), picImage.getHeight());

        // 条形码默认居中显示
        int codeStartX = (pictureWidth - codeImage.getWidth()) / 2 + codeOffsetX;
        int codeStartY = (pictureHeight - codeImage.getHeight()) / 2 + codeOffsetY;
        // 画条形码到新的面板
        g2d.drawImage(codeImage, codeStartX, codeStartY, codeImage.getWidth(), codeImage.getHeight(), null);

        // 画文字到新的面板
        g2d.setColor(Color.BLACK);
        // 字体、字型、字号
        g2d.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
        // 文字与条形码之间的间隔
        int wordAndCodeSpacing = 3;

        if (!StringUtils.isEmpty(bottomStr)) {
            // 文字长度
            int strWidth = g2d.getFontMetrics().stringWidth(bottomStr);
            // 文字X轴开始坐标，这里是居中
            int strStartX = codeStartX + (codeImage.getWidth() - strWidth) / 2;
            // 文字Y轴开始坐标
            int strStartY = codeStartY + codeImage.getHeight() + fontSize + wordAndCodeSpacing;
            // 画文字
            g2d.drawString(bottomStr, strStartX, strStartY);
        }

        if (!StringUtils.isEmpty(topLeftStr)) {
            // 文字长度
            int strWidth = g2d.getFontMetrics().stringWidth(topLeftStr);
            // 文字X轴开始坐标
            int strStartX = codeStartX + topLeftOffsetX;
            // 文字Y轴开始坐标
            int strStartY = codeStartY + topLeftOffsetY - wordAndCodeSpacing;
            // 画文字
            g2d.drawString(topLeftStr, strStartX, strStartY);
        }

        if (!StringUtils.isEmpty(topRightStr)) {
            // 文字长度
            int strWidth = g2d.getFontMetrics().stringWidth(topRightStr);
            // 文字X轴开始坐标，这里是居中
            int strStartX = codeStartX + codeImage.getWidth() - strWidth + topRightOffsetX;
            // 文字Y轴开始坐标
            int strStartY = codeStartY + topRightOffsetY - wordAndCodeSpacing;
            // 画文字
            g2d.drawString(topRightStr, strStartX, strStartY);
        }
        g2d.dispose();
        picImage.flush();
        return picImage;
    }


    /**
     * 设置 Graphics2D 属性  （抗锯齿）
     *
     * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private static void setGraphics2D(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
        Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(s);
    }


    /**
     * 设置背景为白色
     *
     * @param g2d Graphics2D提供对几何形状、坐标转换、颜色管理和文本布局更为复杂的控制
     */
    private static void setColorWhite(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.WHITE);
        //填充整个屏幕
        g2d.fillRect(0, 0, width, height);
        //设置笔刷
        g2d.setColor(Color.BLACK);
    }

    public static void createImage(String content, String imgPath, int width, int height) {
        try {
            // 1 生成二维码
            BufferedImage image = createImage(content, null, false);
            // 2 落地到本地文件
            ImageIO.write(image, FORMAT_NAME, new File(imgPath));
            // 3 缩放图片
            DrawImageUtil.changeImageSize(Arrays.asList(imgPath), width, height);
        } catch (Exception e) {
            log.error("create qrCode failed,error:", e);
        }
    }

    public static BufferedImage createImage(String content, String imgPath,
                                            boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        bitMatrix = updateBit(bitMatrix, 4);  //生成新的bitMatrix
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, imgPath, needCompress);
        return image;
    }

    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            log.warn("目标文件不存在:{}", imgPath);
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }


    private static BitMatrix updateBit(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle();   //获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) {   //循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }
}
