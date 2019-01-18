package com.duia.english.common.utils;
import com.duia.english.model.UserExamScore;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by xiaochao on 2018/8/1.
 */
public class ImageUtils {

    @Value("${imageServiceRealPath}")
    private String imageServiceRealPath;
    @Value("${host.url}")
    private String hostUrl;

    private static Log logger = LogFactory.getLog(ImageUtils.class);

    /**
     * 根据考试成绩生成分享图
     * @param examScore
     * @param prefixDir
     * @return 返回图片地址
     */
    public  String exportImg(UserExamScore examScore,String prefixDir) {
        String shareFileName = "";
        try {
//          测试环境读取不到文件;
//            String fullPath = ResourceUtils.getFile("classpath:static/image/grade_report.png").getAbsolutePath();
//            String fullPath = Thread.currentThread().getContextClassLoader().getResource(FILE_PATH).getPath();
            InputStream is = this.getClass().getResourceAsStream("/static/image/grade_report.png");
//            InputStream is = new FileInputStream(fullPath);
            BufferedImage buffImg = ImageIO.read(is);
            Graphics2D  g = buffImg.createGraphics();
            //抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //最后一个参数用来设置字体的大小
            Color mycolor = Color.black;//new Color(0, 0, 255);
            g.setColor(mycolor);
            g.setFont(new Font("楷体", Font.BOLD, 27));
            g.drawString(examScore.getRealName(), 180, 335);

            g.setColor(mycolor);
            g.setFont(new Font("楷体", Font.BOLD, 27));
//            g.setFont(loadFont(Font.BOLD, 27));
            g.drawString(examScore.getRealSchool(), 180, 375);

            g.setColor(mycolor);
            g.setFont(new Font("楷体", Font.BOLD, 27));
            g.drawString(examScore.getExamCardNo(), 180, 488);

            g.setColor(mycolor);
            g.setFont(new Font("楷体", Font.BOLD, 27));
            g.drawString("2018年6月", 180, 530);

            g.setColor(mycolor);
            g.setFont(new Font("楷体", Font.BOLD, 22));
            g.drawString(examScore.getAllScore().setScale(0, BigDecimal.ROUND_DOWN).toString(), 300, 625);

            g.setColor(mycolor);
            g.setFont(new Font("楷体", Font.BOLD, 22));
            g.drawString(examScore.getListenScore().setScale(0, BigDecimal.ROUND_DOWN).toString(), 385, 625);

            g.setColor(mycolor);
            g.setFont(new Font("楷体", Font.BOLD, 22));
            g.drawString(examScore.getReadScore().setScale(0, BigDecimal.ROUND_DOWN).toString(), 460, 625);

            g.setColor(mycolor);
            g.setFont(new Font("楷体", Font.BOLD, 22));
            g.drawString(examScore.getOtherScore().setScale(0, BigDecimal.ROUND_DOWN).toString(), 544, 625);

            if(examScore.getOralExamCardNo()!=null){
                g.setColor(mycolor);
                g.setFont(new Font("楷体", Font.BOLD, 27));
                g.drawString(examScore.getOralExamCardNo(), 180, 760);
                if(examScore.getOralGrade()!=null){
                    g.setColor(mycolor);
                    g.setFont(new Font("楷体", Font.BOLD, 27));
                    g.drawString(examScore.getOralGrade(), 570, 765);
                }
                g.setColor(mycolor);
                g.setFont(new Font("楷体", Font.BOLD, 27));
                g.drawString("2018年6月", 180, 798);
            }

            g.dispose();
            OutputStream os;
            String dateDIR = DateFormatUtils.format(new Date(), "yyyyMMdd");
            String path = dateDIR + "/" + UUID.randomUUID().toString() + ".jpg";
            File file = new File(prefixDir + path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            os = new FileOutputStream(prefixDir + path);
            ImageIO.write(buffImg, "jpeg", os);
            shareFileName = "/" + path;
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.error("FileNotFoundException：" + e);
        } catch(Exception e) {
            // TODO Auto-generated catch block
            logger.error("Exception：" + e);
        }
        return shareFileName;
    }

    public static void main(String[] args) {
    }
}
