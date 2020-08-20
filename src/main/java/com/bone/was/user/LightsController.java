package com.bone.was.user;

import com.bone.was.location.Destination;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lights")
public class LightsController {
    @Autowired
    private LightsDaoService service;

    // 모든 가로등 정보
    @GetMapping("/all")
    public List<Lights> retrieveAllLights() {
        return service.findAll();
    }

    @PostMapping("/m")
    public JSONObject hashTest(@RequestBody String hash) {
        JSONObject result = new JSONObject();
        JSONObject err = new JSONObject();
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jo = (JSONObject) jsonParser.parse(hash);
            String hashstr = (String) jo.get("hash");
            if (hashstr == null) {
                return (JSONObject) new JSONObject().put("state",400);
            }
            hashstr = hashstr.replace("\n", "");


            if (hashstr.equals("aubvwL2QqTlth5B5zP7iWik4+Rg=")) {
                result.put("state", 200);
                return result;
            } else {
                result.put("state", 400);
                return result;
            }
        } catch (ParseException e) {
            return (JSONObject) new JSONObject().put("state",400);

        }
    }

    // mode 1. 사용자 위치 주변의 가로등 정보
    @PostMapping("/lights")
    public JSONObject myLights(@NotBlank @RequestBody String location1) {
        double tmp_lat = 0.0;
        double tmp_lng = 0.0;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(location1);
            JSONObject jsonObject = (JSONObject) obj;
            tmp_lat = (double) jsonObject.get("lat");
            tmp_lng = (double) jsonObject.get("lng");

        } catch (ParseException e) {
            tmp_lat = 0.0;
            tmp_lng = 0.0;
        }

        // send 주변 lights
        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();
        JSONObject retobj = new JSONObject();

        // 오차 0.001 약 100m => 50m
        for (Lights lis : lights) {
            if ((lis.getLat() < tmp_lat + 0.001) && (lis.getLat() >= tmp_lat - 0.001) && (lis.getLng() < tmp_lng + 0.001) && (lis.getLng() >= tmp_lng - 0.001)) {
                ret.add(lis);
            }
        }

        retobj.put("lights", ret);
        return retobj;
    }

    // mode 1.5 : 상세 목적지 이름, 위도, 경도 받아오기
    @PostMapping("/finddst")
    public JSONObject searchDst(@NotBlank @RequestBody String destination) {
        HttpsURLConnection urlConnection = null;
        String dststr = "";
        String url = "";
        List<Destination> dstlist = new ArrayList<>();
        JSONObject tmp = new JSONObject();
        String line = "";

        JSONParser jsonParser = new JSONParser();
        StringBuilder result = new StringBuilder();

        try {
            JSONObject dstp = (JSONObject) jsonParser.parse(destination);
            dststr = (String) dstp.get("et");

            url = "https://apis.openapi.sk.com/tmap/pois?version=1&format=json&callback=result&appKey=l7xx40fd78a3aba2484eaa6e2546a5eeccc5&searchKeyword=" +
                    URLEncoder.encode(dststr, "UTF-8") +
                    "&resCoordType=WGS84GEO&reqCoordType=WGS84GEO&count=10";


        } catch (ParseException e) {
            return (JSONObject) new JSONObject().put("state", 400);

        } catch (UnsupportedEncodingException e1) {
            return (JSONObject) new JSONObject().put("state", 400);
        }

        try {
            URL urlObj = new URL(url);
            urlConnection = (HttpsURLConnection) urlObj.openConnection();
            if (urlConnection == null) {
                return (JSONObject) new JSONObject().put("state", 400);

            }

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            in.close();
            reader.close();

        } catch (MalformedURLException e1) {
            return (JSONObject) new JSONObject().put("state", 400);

        } catch (IOException e2) {
            return (JSONObject) new JSONObject().put("state", 400);
        }
            try {
            JSONObject jsonobj = (JSONObject) jsonParser.parse(result.toString());
            JSONObject jsoninfo = (JSONObject) jsonobj.get("searchPoiInfo");
            JSONObject jsonpois = (JSONObject) jsoninfo.get("pois");
            JSONArray jsonpoi = (JSONArray) jsonpois.get("poi");

            for (int i = 0; i < jsonpoi.size(); i++) {
                JSONObject one = (JSONObject) jsonpoi.get(i);
                Destination d = new Destination(one.get("name").toString(), Double.parseDouble((String) one.get("noorLat")), Double.parseDouble((String) one.get("noorLon")));
                dstlist.add(d);

            }
        } catch (ParseException e) {
            return (JSONObject) new JSONObject().put("state", 400);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            tmp.put("dst", dstlist);
            return tmp;
        }
    }

    // 동작2. 출발지와 목적지 간에 있는 가로등 리스트를 출발지와 가까운 순으로 오름차순하여 json으로 반환.
    @PostMapping("/pathlights")
    public JSONObject pathLights(@NotBlank @RequestBody String route1) {

        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();
        JSONObject tmp = new JSONObject();
        double slat, slng, dlat, dlng = 0;
        JSONObject dstp = null;
        JSONParser jsonParser = new JSONParser();
        try {
            dstp = (JSONObject) jsonParser.parse(route1);
        } catch (ParseException e) {
            return (JSONObject) new JSONObject().put("state", 400);
        }
        slat = (Double) dstp.get("slat");
        slng = (Double) dstp.get("slng");
        dlat = (Double) dstp.get("dlat");
        dlng = (Double) dstp.get("dlng");

        double smalllat, biglat, smalllng, biglng = 0;

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

        // 범위에 해당하는 가로등 리스트에 저장. (오차 범위 0.001 약 100m)
        // 가로등 5개 반환
        for (Lights lis : lights) {
            if ((lis.getLat() < biglat) && (lis.getLat() >= smalllat) && (lis.getLng() < biglng) && (lis.getLng() >= smalllng)) {
                ret.add(lis);
            }
        }

        List<Lights> ret5 = new ArrayList<>();
        if (ret.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                ret5.add(ret.get(i));
            }
        } else {
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
        tmp.put("lights", ret5);
        return tmp;
    }

}
