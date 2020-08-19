package com.bone.was.user;

import com.bone.was.config.JwtTokenProvider;
import com.bone.was.location.Destination;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lights")
public class LightsController {
    @Autowired
    private LightsDaoService service;
    private final JwtTokenProvider jwtTokenProvider;


    @GetMapping("/test")
    public String test(){
        return "Gwi Chuck NoNo";
    }

    @PostMapping("/test1")
    public String test1(){
        return "Gwi Chuck NoNo";
    }

    // 모든 가로등 정보
    @GetMapping("/all")
    public List<Lights> retrieveAllLights(){
        return service.findAll();
    }

    @PostMapping("/m")
    public JSONObject hashTest(@RequestBody String hash) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jo = (JSONObject) jsonParser.parse(hash);
        String hashstr = (String) jo.get("hash");
        hashstr = hashstr.replace("\n","");

        JSONObject result = new JSONObject();

        if( hashstr.equals("a")){
            System.out.println("[dbg] 200 허락");
            result.put("state", 200);
            return result;
        }else{
            System.out.println("[dbg] 400 거부. 종료");
            result.put("state", 400);
            return result;
        }
    }
    // mode 1. 사용자 위치 주변의 가로등 정보
    @PostMapping("/lights") //jsonArraylist
    public JSONObject myLights(@RequestBody String location1) { //List<Lights>
//@RequestHeader(value="authkey") String authkey,
        // no authkey -> return ArrayList
//        if(authkey == null){
//            return new ArrayList<Lights>();
//        }
        Double tmp_lat=null;
        Double tmp_lng=null;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(location1);
            JSONObject jsonObject = (JSONObject) obj;
            tmp_lat = (double)jsonObject.get("lat");
            tmp_lng =  (double)jsonObject.get("lng");

        }catch (Exception e){
            e.printStackTrace();
        }


        System.out.println("[DBG] lat, lng : " + tmp_lat + ", " + tmp_lng);

        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();
        // temp test dbg
        JSONObject tmp = new JSONObject();
        //debug
/*        Location location = location1[0];
        System.out.println("* dgb : mode 1. lights *");
        System.out.println("[dbg] " + location);
        System.out.println("[dbg] " + location.() + ", " + location.getLng());
        // System.out.println("[dbg1] " + authkey);*/

        System.out.println("* dgb : mode 1. lights *");

        System.out.println("[dbg] " + tmp_lat + ", " + tmp_lng);

        //        오차 0.001 약 100m => 50m
        for (Lights lis : lights) {
            if ((lis.getLat() < tmp_lat + 0.001) && (lis.getLat() >= tmp_lat - 0.001)) {
                if ((lis.getLng() < tmp_lng + 0.001) && (lis.getLng() >= tmp_lng - 0.001)) {
                    System.out.println("[DBG] get lights ho~ : " + lis);
                    ret.add(lis);
                }
            }
        }
        System.out.println("[dbg] length " + ret.size());

        // send to json
        tmp.put("lights", ret);
        return tmp; // tmp;// null 포인트 (가로등 없는거처리) - 안드로이드단
    }



//    @GetMapping("/iftest")
//    public List<Lights> selectedLights() {
//        List<Lights> lights = service.findAll();
//        List<Lights> ret = new ArrayList<>();
//
//
//        for (Lights lis : lights) {
//            if ((lis.getLat() < 35.179) && (lis.getLat() >= 35.1788)) {
//                if ((lis.getLng() < 126.933) && (lis.getLng() >= 126.930)) {
//                    ret.add(lis);
//                }
//            }
//        }
//        return ret;
//    }

    // mode 1.5 : 상세 목적지 이름, 위도, 경도 받아오기
    @PostMapping("/finddst") //destination == et,   //result is JSONArray
    public JSONObject searchDst(@RequestBody String destination) throws ParseException, UnsupportedEncodingException { // //@PathVariable String destination){
//@RequestHeader(value="authkey") String authkey,
        // no authkey -> return ArrayList
//        if(authkey == null){
//            return new ArrayList<Destination>();
//        }

        // destination Parsing
        JSONParser jsonParser = new JSONParser();
        JSONObject dstp = (JSONObject) jsonParser.parse(destination);
        String dststr = (String) dstp.get("et");

        System.out.println("dfdg_seleuchel : " + dststr);

        List<Destination> dstlist = new ArrayList<>();
        JSONObject tmp = new JSONObject();
        String line = "";
        // path class를 따로 파야할 듯.

        String url = "https://apis.openapi.sk.com/tmap/pois?version=1&format=json&callback=result&appKey=l7xx40fd78a3aba2484eaa6e2546a5eeccc5&searchKeyword=" +
                URLEncoder.encode(dststr,"UTF-8") +
                "&resCoordType=WGS84GEO&reqCoordType=WGS84GEO&count=10"; //10개만 return
        HttpsURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();

        try {
            URL urlObj = new URL(url);
            urlConnection = (HttpsURLConnection) urlObj.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // https
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while ((line = reader.readLine()) != null) {
                result.append(line);
                System.out.println("box nonono : " + line);
            }
        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
            System.out.println("[dbg] printse : " + result);
        }

        JSONObject jsonobj = (JSONObject) jsonParser.parse(result.toString());
        JSONObject jsoninfo = (JSONObject) jsonobj.get("searchPoiInfo");
        JSONObject jsonpois = (JSONObject) jsoninfo.get("pois");
        JSONArray jsonpoi = (JSONArray) jsonpois.get("poi");

        for(int i = 0 ; i < jsonpoi.size(); i++){
            JSONObject one = (JSONObject) jsonpoi.get(i);
            Destination d = new Destination(one.get("name").toString(), Double.parseDouble((String)one.get("noorLat")), Double.parseDouble((String)one.get("noorLon")));
            System.out.println("[Dest result] : " + d.toString());
            dstlist.add(d);

        }

        // format
        tmp.put("dst",dstlist);

        System.out.println("********** 3 ************");
        System.out.println("[dbg] dst : " + dststr);
        System.out.println("[dbg] url : " + url);
        System.out.println("[dbg] print line : " + jsonpoi);
        // System.out.println("[dbg2] " + authkey);
        return tmp;
    }

    // 동작2. 출발지와 목적지 간에 있는 가로등 리스트를 출발지와 가까운 순으로 오름차순하여 json으로 반환.
    @PostMapping("/pathlights")
    public JSONObject pathLights( @RequestBody String route1) throws ParseException {
//@RequestHeader(value="authkey") String authkey,
        // no authkey -> return ArrayList
//        if(authkey == null){
//            return new ArrayList<Lights>();
//        }
        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();
        JSONObject tmp = new JSONObject();
        double slat, slng, dlat, dlng = 0;

        //parse data
        JSONParser jsonParser = new JSONParser();
        JSONObject dstp = (JSONObject) jsonParser.parse(route1);
        slat = (Double) dstp.get("slat");
        slng = (Double) dstp.get("slng");
        dlat = (Double) dstp.get("dlat");
        dlng = (Double) dstp.get("dlng");

        //debug
        System.out.println("* dgb : mode 2. pathlights *");
        System.out.println("[dbg] Start : " + slat + ", " + slng);
        System.out.println("[dbg] End : " + dlat + ", " + dlng);



        double smalllat, biglat, smalllng, biglng = 0;

        // biglat, smalllat, biglng, smallng
        if (slat >= dlat) {
            biglat = slat;
            smalllat = dlat;
        } else {
            biglat = dlat;
            smalllat = slat;
        }
        if (slng >= dlng) {
            biglng = slng;
            smalllng = dlng;
        } else {
            biglng = dlng;
            smalllng = slng;
        }

        System.out.println("[dbg] big : " + biglat + ", " + biglat);


        // 범위에 해당하는 가로등 리스트에 저장. (오차 범위 0.001 약 100m)
        // 가로등 5개 반환
        int cont = 0;
        for (Lights lis : lights) {
            System.out.println("[dbg] lis : " + lis.getLat() + ", " + lis.getLng());
            if ((lis.getLat() < biglat) && (lis.getLat() >= smalllat)) {
                if ((lis.getLng() < biglng) && (lis.getLng() >= smalllng)) {
                    ret.add(lis);
                }
            }
        }

        System.out.println("[dgb] : retsize" + ret.size());


        // for sort
        // ret -> ret5
        List<Lights> ret5 = new ArrayList<>();
        if (ret.size() >= 5){
            for (int i = 0; i < 5; i++)
                ret5.add(ret.get(i));
        }else{
            ret5 = ret;
        }


        // 출발지로부터 거리 계산
        int idx = 0;
        double distance[] = new double[ret5.size()];
        for (Lights l : ret5) {
            double k = (l.getLat() - slat) * (l.getLat() - slat) + (l.getLng() - slng) * (l.getLng() - slng);
            distance[idx] = k;
            idx++;


            // 거리값으로 오름차순 sort
            for (int i = 0; i < idx - 1; i++) {
                for (int j = i + 1; j < idx; j++) {
                    if (distance[i] > distance[j]) {
                        double t = distance[i];
                        distance[i] = distance[j];
                        distance[j] = t;

                        //교환
                        Lights x = ret5.get(i);
                        ret5.set(i, ret5.get(j));
                        ret5.set(j, x);
                    }
                }
            }
        }
        System.out.println("[dbg] final ret5size : " + ret5.size());

        for(int i = 0 ; i < ret5.size(); i++){
            System.out.println("[dbg] final lights >> " + ret5.get(i).getLat() + ", " + ret5.get(i).getLng());
        }
        // System.out.println("[dbg3] " + authkey);
        tmp.put("lights", ret5);

        // return 5 lights.
        return tmp;
    }

}
