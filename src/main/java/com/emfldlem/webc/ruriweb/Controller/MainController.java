package com.emfldlem.webc.ruriweb.Controller;


import com.emfldlem.webc.ruriweb.Mail.GmailSend;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Collections;

@RestController
public class MainController {

    GmailSend gmailSend = new GmailSend();
    File file = new File("C:/dev/CYWorkspace/ruriweb/id.txt");

    @GetMapping("/web1")
    //@Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void web1()  throws IOException {

        System.out.println("==================스케줄러 시작==================");

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
            int lastId = readFileId();
            //String subject = "루리웹 "+elem.get(i).text();
            String subject = elem.get(i).select(".deco").text();
            String content =  subject + " " + elem.get(i).select(".deco").attr("href");
            //String a = test1.attr("href");
            //Elements test2 = test1.select("a[href]");
            //String test = elem.get(i).select("a[").text();
            int sid = Integer.parseInt(elem.get(i).text().split(" ")[0]);
            if(sid > lastId) {
                System.out.println("sid =========" + sid);
                System.out.println("subject =========" + subject);

                gmailSend.GmailSet("swkim@bsgglobal.com", subject, content);
                creatFileId(sid);
            }

        }

        System.out.println("==================스케줄러 종료==================");
    }


    private int readFileId() {

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
    public void creatFileId(int sid) throws IOException {

        //File file = new File("C:/dev/CYWorkspace/ruriweb/id.txt");
        String String_sid = String.valueOf(sid);
        if(file.exists() == false) {
            System.out.println("파일 없음");
        }
        else {
            /*FileWriter fw = new FileWriter(file, false);

            fw.write(sid);
            fw.flush();
            fw.close();*/


            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
            buffWrite.write(String_sid);
            buffWrite.flush();
            buffWrite.close();

        }

    }

}
