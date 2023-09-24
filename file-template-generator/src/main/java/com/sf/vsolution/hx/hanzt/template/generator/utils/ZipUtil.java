package com.sf.vsolution.hx.hanzt.template.generator.utils;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

/**
 * 压缩包工具类
 *
 * @author 01374735
 */
@Slf4j
public class ZipUtil {
    public static final String SUFFIX_7Z = ".7z";

    /**
     * 构建目录
     *
     * @param outputDir 输出目录
     * @param subDir    子目录
     */
    private static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        //子目录不能为空
        if (!(subDir == null || subDir.trim().equals(""))) {
            file = new File(outputDir + File.separator + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.mkdirs();
        }
    }

    /**
     * 对指定的文件进行打包
     *
     * @param fileSet 需打包的文件路径地址
     * @param path    打包文件输出地址
     */
    public static String z7z(Set<String> fileSet, String path) {
        BufferedInputStream instream = null;
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String zipName = String.format("%s_%s%s", "Sheet", UUID.randomUUID(), SUFFIX_7Z);
            String output = path + File.separator + zipName;
            SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File(output));
            SevenZArchiveEntry entry;
            for (String filePath : fileSet) {
                instream = new BufferedInputStream(new FileInputStream(new File(filePath)));
                entry = sevenZOutput.createArchiveEntry(new File(filePath), new File(filePath).getName());
                sevenZOutput.putArchiveEntry(entry);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = instream.read(buffer)) > 0) {
                    sevenZOutput.write(buffer, 0, len);
                }
                instream.close();
                sevenZOutput.closeArchiveEntry();
            }
            sevenZOutput.close();
            return output;
        } catch (Exception e) {
            log.error("压缩打包异常", e);
            return null;
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    log.error("压缩打包关流异常",e);
                }
            }
        }
    }

    public static void Compress7z(String inputFile, String outputFile) throws Exception {
        File input = new File(inputFile);
        if (!input.exists()) {
            throw new Exception(input.getPath() + "待压缩文件不存在");
        }
        SevenZOutputFile out = new SevenZOutputFile(new File(outputFile));

        compress(out, input, null);
        out.close();
    }

    /**
     * @param name 压缩文件名，可以写为null保持默认
     */
    //递归压缩
    public static void compress(SevenZOutputFile out, File input, String name) {
        FileInputStream fos = null;
        BufferedInputStream bis = null;
        try {
            if (name == null) {
                name = input.getName();
            }
            SevenZArchiveEntry entry = null;
            //如果路径为目录（文件夹）
            if (input.isDirectory()) {
                //取出文件夹中的文件（或子文件夹）
                File[] flist = input.listFiles();
                if (flist.length == 0) {
                    //如果文件夹为空，则只需在目的地.7z文件中写入一个目录进入
                    entry = out.createArchiveEntry(input, name + "/");
                    out.putArchiveEntry(entry);
                } else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                {
                    for (int i = 0; i < flist.length; i++) {
                        compress(out, flist[i], name + "/" + flist[i].getName());
                    }
                }
            } else {
                //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入7z文件中
                fos = new FileInputStream(input);
                bis = new BufferedInputStream(fos);
                entry = out.createArchiveEntry(input, name);
                out.putArchiveEntry(entry);
                int len = -1;
                //将源文件写入到7z文件中
                byte[] buf = new byte[1024];
                while ((len = bis.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                out.closeArchiveEntry();
            }
        } catch (Exception e) {
            log.error("7z compress is failure.", e);
        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
