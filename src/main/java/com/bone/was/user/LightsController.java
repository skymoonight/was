package com.bone.was.user;

import com.bone.was.config.JwtTokenProvider;
import com.bone.was.location.Location;
import com.bone.was.location.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lights")
public class LightsController {
    @Autowired
    private LightsDaoService service;
    private final JwtTokenProvider jwtTokenProvider;

    // 모든 가로등 정보
    @GetMapping("/all")
    public List<Lights> retrieveAllLights(){
        return service.findAll();
    }

    // mode 1. 사용자 위치 주변의 가로등 정보
    @PostMapping("/lights") //jsonArraylist
    public List<Lights> myLights(@RequestBody Location[] location1) { //List<Lights>
//@RequestHeader(value="authkey") String authkey,
        // no authkey -> return ArrayList
//        if(authkey == null){
//            return new ArrayList<Lights>();
//        }

        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();
        Lights tmp;
        //debug
        Location location = location1[0];
        System.out.println("* dgb : mode 1. lights *");
        System.out.println("[dbg] " + location);
        System.out.println("[dbg] " + location.getLat() + ", " + location.getLng());
        // System.out.println("[dbg1] " + authkey);


        //        오차 0.001 약 100m => 50m
        for (Lights lis : lights) {
            if ((lis.getLat() < location.getLat() + 0.001) && (lis.getLat() >= location.getLat() - 0.001)) {
                if ((lis.getLng() < location.getLng() + 0.001) && (lis.getLng() >= location.getLng() - 0.001)) {
                    ret.add(lis);
                }
            }
        }
        System.out.println("[dbg] length " + ret.size());
        tmp = lights.get(0);
        return ret; // tmp;// null 포인트 (가로등 없는거처리) - 안드로이드단
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

    @PostMapping("/pathlights")
    public List<Lights> pathLights(@RequestBody Route route){
        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();
        double smalllat, biglat, smalllng, biglng = 0;

        // biglat, smalllat, biglng, smallng
        if (route.getSlat() >= route.getDlat()){
            biglat = route.getSlat();
            smalllat = route.getDlat();
        }else{
            biglat = route.getDlat();
            smalllat = route.getSlat();
        }
        if(route.getSlng() >= route.getDlng()){
            biglng = route.getSlng();
            smalllng = route.getDlng();
        }else{
            biglng = route.getDlng();
            smalllng = route.getSlng();
        }

        // 범위에 해당하는 가로등 리스트에 저장. (오차 범위 0.001 약 100m)
        for (Lights lis : lights) {
            if ((lis.getLat() < biglat ) && (lis.getLat() >= smalllat )) {
                if ((lis.getLng() < biglng ) && (lis.getLng() >= smalllng )) {
                    ret.add(lis);
                }
            }
        }

        // 출발지로부터 거리 계산
        int idx = 0;
        double distance[] = new double[ret.size()];
        for (Lights l : ret) {
            double k = (l.getLat()-route.getSlat())*(l.getLat()-route.getSlat()) + (l.getLng()-route.getSlng())*(l.getLng()-route.getSlng());
            distance[idx] = k;
            idx++;
        }

        // 거리값으로 오름차순 sort
        for(int i = 0; i<idx-1; i++) {
            for(int j = i+1; j<idx; j++) {
                if(distance[i] > distance[j]) {
                    double t = distance[i];
                    distance[i] = distance[j];
                    distance[j] = t;

                    //교환
                    Lights x = ret.get(i);
                    ret.set(i, ret.get(j));
                    ret.set(j, x);
                }
            }
        }

        return ret;
    }

}
