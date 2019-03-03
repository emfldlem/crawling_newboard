package com.emfldlem.webc.ruriweb.Controller;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;


import java.io.IOException;
import java.util.Collections;



@Controller
public class webController {


    @GetMapping("/")
    public String index() {
        return "main";
    }

    @GetMapping("/taps")
    public String taps() {
        return "taps";
    }



    @PostMapping("ruriwebDataCall")
    public @ResponseBody
    JSONObject ruriwebDataCall() {

        /* json 객체를 담을 json 배열  */
        JSONArray jsonArray = new JSONArray();
        /* 최종 객체를 담을 json */
        JSONObject finalJsonObject = new JSONObject();

        try {
           // File file = new File("C:/dev/txt/ruriweb_hotdeal.txt");
            String URL = "http://bbs.ruliweb.com/ps/board/1020";
            Document doc = null;
            doc = Jsoup.connect(URL).get();
            Elements elem = doc.select(".table_body");
            int count = 0;

            /*공지사항 부분 제거*/
            while (count < 2) {
                elem.remove(0);
                count++;
            }

            /*최신 글 상위 5개 가져옴*/
            Collections.reverse(elem);
            elem.subList(0, 3).clear();

            for (Element anElem : elem) {
                /*새로운 json 객체에 각 각의 요소를 담기*/
                JSONObject tempJsonObject = new JSONObject();
                //int lastId = readFileId(file);
                String subject = anElem.select(".deco").text();
                String link = anElem.select(".deco").attr("href");
                int sid = Integer.parseInt(anElem.text().split(" ")[0]);

                System.out.println("sid =========" + sid);
                System.out.println("subject =========" + subject);
                tempJsonObject.put("sid", sid);
                tempJsonObject.put("subject", subject);
                tempJsonObject.put("link", link);
                jsonArray.add(tempJsonObject);

                /*if (sid > lastId) {

                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finalJsonObject.put("ruriweb", jsonArray);
        System.out.println(finalJsonObject);
        return finalJsonObject;
    }

    @PostMapping("ppomppuDataCall")
    public @ResponseBody
    JSONObject ppomppuDataCall() {
        /* json 객체를 담을 json 배열  */
        JSONArray jsonArray = new JSONArray();
        /* 최종 객체를 담을 json */
        JSONObject finalJsonObject = new JSONObject();

        try {
            //TODO 추후 DB가 구축된다면 DB에서 값을 가져오자
            //File file = new File("C:/dev/txt/ppomppu.txt");
            String URL = "http://www.ppomppu.co.kr/zboard/zboard.php?id=ppomppu";
            Document doc = Jsoup.connect(URL).get();
            Elements elem = doc.select(".list_title");
            Collections.reverse(elem);

            for (Element anElem : elem) {
                /*새로운 JSON 객체 */
                JSONObject tempJsonObject = new JSONObject();

               // int lastId = readFileId(file);

                Element elem1 = anElem.parent();
                Element elem2 = anElem.parent().parent().parent().parent().parent().parent().parent().child(0);

                String subject = elem1.text();
                String link = "http://www.ppomppu.co.kr/zboard/" + elem1.attr("href");
                String No = elem2.text();


                int sid = Integer.parseInt(No);

                System.out.println("sid =========" + sid);
                System.out.println("subject =========" + subject);
                tempJsonObject.put("sid", sid);
                tempJsonObject.put("subject", subject);
                tempJsonObject.put("link", link);
                jsonArray.add(tempJsonObject);

                /*if (sid > lastId) {
                    System.out.println("sid =========" + sid);
                    System.out.println("subject =========" + subject);
                    tempJsonObject.put("sid", sid);
                    tempJsonObject.put("subject", subject);
                    tempJsonObject.put("link", link);
                    jsonArray.add(tempJsonObject);
                    //creatFileId(sid,file);
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        finalJsonObject.put("ppompu", jsonArray);
        System.out.println(finalJsonObject);
        return finalJsonObject;

    }
}
