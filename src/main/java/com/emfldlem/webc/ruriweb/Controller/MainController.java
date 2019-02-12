package com.emfldlem.webc.ruriweb.Controller;


import com.emfldlem.webc.ruriweb.Mail.GmailSend;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Collections;

@RestController
public class MainController {

    GmailSend gmailSend = new GmailSend();

    private static String userEmail = "eiwak@naver.com";


    @GetMapping("/")
    public String index() {

        try {
            File file = new File("C:/dev/txt/ruriweb_hotdeal.txt");
            System.out.println("==================루리웹 스케줄러 시작==================");

            String URL = "http://bbs.ruliweb.com/ps/board/1020";
            Document doc = null;
            doc = Jsoup.connect(URL).get();
            Elements elem = doc.select(".table_body");
            int count = 0;

            /*공지사항 부분 제거*/
            while(count < 5) {
                elem.remove(0);
                count++;
            }

            /*최신 글 상위 5개 가져옴*/
            Collections.reverse(elem);
            for(int i=0; i<25; i++) {
                elem.remove(0);
            }

            for(int i = 0; i<elem.size(); i++) {
                int lastId = readFileId(file);
                //String subject = "루리웹 "+elem.get(i).text();
                String subject = "루리웹  " +  elem.get(i).select(".deco").text();
                String content =  subject + " " + elem.get(i).select(".deco").attr("href");
                //String a = test1.attr("href");
                //Elements test2 = test1.select("a[href]");
                //String test = elem.get(i).select("a[").text();
                int sid = Integer.parseInt(elem.get(i).text().split(" ")[0]);
                if(sid > lastId) {
                    System.out.println("sid =========" + sid);
                    System.out.println("subject =========" + subject);

                    gmailSend.GmailSet(userEmail, subject, content);
                    creatFileId(sid,file);
                }
            }
            System.out.println("==================루리웹 스케줄러 종료==================");



        } catch (IOException e) {
            e.printStackTrace();
        }

        int count = 0;



        return "index";
    }


    @GetMapping("/ruriweb_hotdeal")
   // @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void ruriweb_hotdeal()  throws IOException {
        File file = new File("C:/dev/txt/ruriweb_hotdeal.txt");
        System.out.println("==================루리웹 스케줄러 시작==================");

        String URL = "http://bbs.ruliweb.com/ps/board/1020";
        Document doc = Jsoup.connect(URL).get();
        Elements elem = doc.select(".table_body");
        int count = 0;

        //TODO 나중에 이부분을 없애고 크롤링 자체에서 상위 5개만 가져오도록 구현

        /*공지사항 부분 제거*/
        while(count < 5) {
            elem.remove(0);
            count++;
        }

        /*최신 글 상위 5개 가져옴*/
        Collections.reverse(elem);
        for(int i=0; i<25; i++) {
            elem.remove(0);
        }

        for(int i = 0; i<elem.size(); i++) {
            int lastId = readFileId(file);
            //String subject = "루리웹 "+elem.get(i).text();
            String subject = "루리웹  " +  elem.get(i).select(".deco").text();
            String content =  subject + " " + elem.get(i).select(".deco").attr("href");
            //String a = test1.attr("href");
            //Elements test2 = test1.select("a[href]");
            //String test = elem.get(i).select("a[").text();
            int sid = Integer.parseInt(elem.get(i).text().split(" ")[0]);
            if(sid > lastId) {
                System.out.println("sid =========" + sid);
                System.out.println("subject =========" + subject);

                gmailSend.GmailSet(userEmail, subject, content);
                creatFileId(sid,file);
            }
        }
        System.out.println("==================루리웹 스케줄러 종료==================");
    }

    //@GetMapping("/ppomppu")
    //@Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void ppomppu_ppomppu()  throws IOException {
        File file = new File("C:/dev/txt/ppomppu.txt");

        System.out.println("==================뽐뿌 스케줄러 시작==================");

        String URL = "http://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu";
        Document doc = Jsoup.connect(URL).get();
        Elements elem = doc.select(".list_title");
        Collections.reverse(elem);


        for(int i = 0; i<elem.size(); i++) {
            int lastId = readFileId(file);

            Element elem1 = elem.get(i).parent();
            Element elem2 = elem.get(i).parent().parent().parent().parent().parent().parent().parent().child(0);

            String subject ="뽐뿌  " +  elem1.text();
            String contents = "http://www.ppomppu.co.kr/zboard/"+elem1.attr("href");
            String No = elem2.text();

            int sid = Integer.parseInt(No);
            if(sid > lastId) {
                System.out.println("sid =========" + sid);
                System.out.println("subject =========" + subject);

                gmailSend.GmailSet(userEmail, subject, contents);
                creatFileId(sid,file);
            }

        }

        System.out.println("==================뽐뿌 스케줄러 종료==================");
    }


    private int readFileId(File file) {

        String strText = "";
        int nBuffer;
        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(file));
            while ((nBuffer = buffRead.read()) != -1) {
                strText += (char) nBuffer;
            }
            buffRead.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("====================" + strText);
        return Integer.parseInt(strText);
    }


    @GetMapping("/creatFileId")
    public void creatFileId(int sid, File file) throws IOException {
        String String_sid = String.valueOf(sid);
        if(file.exists() == false) {
            System.out.println("파일 없음");
        }
        else {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
            buffWrite.write(String_sid);
            buffWrite.flush();
            buffWrite.close();
        }
    }
}
