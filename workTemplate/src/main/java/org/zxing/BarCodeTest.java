package org.zxing;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class BarCodeTest {
    @Test
    public void generalBarCode() throws Exception {
        BufferedImage image = BarCodeUtils.getBarCodeWithWords("123456788910123124125151",
                "123456788910123124125151",
                null,
                null,
                300,
                100);
        ImageIO.write(image, "jpg", new File("barCode.jpg"));
    }

}
