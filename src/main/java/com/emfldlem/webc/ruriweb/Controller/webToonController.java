package com.emfldlem.webc.ruriweb.Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@RestController
public class webToonController {

    @GetMapping("/webtoon")
    public void webToon() throws IOException {
        int count = 1;
        while (count < 500) {

            String path = "C:\\webtoon\\신의탑\\"+count; //폴더 경로
            File Folder = new File(path);

            // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
            if (!Folder.exists()) {
                try{
                    Folder.mkdir(); //폴더 생성합니다.
                    System.out.println("폴더가 생성되었습니다.");

                    String URL = "https://comic.naver.com/webtoon/detail.nhn?titleId=183559&no="+count;
                    Document doc = Jsoup.connect(URL).get();
                    Elements elem = doc.select("#comic_view_area img[alt~=comic]");
                    BufferedImage TotalBufferedImage = ImageIO.read(new File("C:\\webtoon\\init.jpg"));

                    System.out.println("ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
                    System.out.println(elem.size());
                    System.out.println("ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");

                    for (int i = 0; i < elem.size(); i++) {
                        String alt = elem.get(i).attr("alt");
                        if (alt.equals("comic content")) {
                            String imgUrl = elem.get(i).attr("src");
                            String fileName = "naver_" + i;
                            String ext = imgUrl.substring(imgUrl.lastIndexOf('.') + 1);

                            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
                            System.out.println(imgUrl);
                            System.out.println(i);
                            System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");

                            java.net.URL url = new URL(imgUrl);
                            URLConnection uc = url.openConnection();
                            uc.addRequestProperty("User-Agent",
                                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

                            BufferedImage currentImage = ImageIO.read(uc.getInputStream());

                            int width = TotalBufferedImage.getWidth();
                            int height = TotalBufferedImage.getHeight() + currentImage.getHeight();


                            BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                            Graphics2D graphics = (Graphics2D) mergedImage.getGraphics();
                            graphics.setBackground(Color.WHITE);
                            graphics.drawImage(TotalBufferedImage, 0, 0, null);
                            graphics.drawImage(currentImage, 0, TotalBufferedImage.getHeight(), null);


                            TotalBufferedImage = mergedImage;

                            if (i == elem.size() / 2) {
                                System.out.println("==========================================================================================================");
                                System.out.println(i);
                                System.out.println("==========================================================================================================");
                                ImageIO.write(TotalBufferedImage, "jpg", new File(path+"\\" + "total1" + ".jpg"));
                                TotalBufferedImage = ImageIO.read(new File("C:\\webtoon\\init.jpg"));
                            }
                            if (i == elem.size() - 1) {
                                System.out.println("==========================================================================================================");
                                System.out.println(i);
                                System.out.println("==========================================================================================================");
                                ImageIO.write(TotalBufferedImage, "jpg", new File(path+"\\" + "total2" + ".jpg"));
                            }


                            //ImageIO.write(mergedImage, ext, new File("C:\\webtoon\\"+fileName+".jpg"));
                        }
                    }

                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }else {
                System.out.println("이미 폴더가 생성되어 있습니다.");
            }

            count++;
        }


    }

    @GetMapping("filetest")
    public void filetest() throws IOException {

        List<String> sendCCs = new ArrayList<>();

        int i = sendCCs.size();

        System.out.println(i);


    }


}
