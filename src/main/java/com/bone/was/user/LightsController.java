package com.bone.was.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lights")
public class LightsController {
    @Autowired
    private LightsDaoService service;

    @GetMapping("/all")
    public List<Lights> retrieveAllLights(){
        return service.findAll();
    }

    @PostMapping("/all")
    public List<Lights> retrieveAllLights(@RequestBody Lights location){
        List<Lights> lights = service.findAll();

    }

    @GetMapping("/iftest")
    public List<Lights> selectedLights() {
        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();


        for (Lights lis : lights) {
            if ((lis.getLat() < 35.179) && (lis.getLat() >= 35.1788)) {
                if ((lis.getLng() < 126.933) && (lis.getLng() >= 126.930)) {
                    ret.add(lis);
                }
            }
        }
        return ret;
    }

    @GetMapping("/lights/loc/{lat}/{lng}")
    public List<Lights> selectedLightsFromME(@PathVariable double lat, @PathVariable double lng){
        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();

        // 오차 0.001 약 100m
        for (Lights lis : lights) {
            if ((lis.getLat() < lat + 0.001) && (lis.getLat() >= lat - 0.001)) {
                if ((lis.getLng() < lng + 0.001) && (lis.getLng() >= lng - 0.001)) {
                    ret.add(lis);
                }
            }
        }
        return ret;
    }


    @GetMapping("/lights/loc/{slat}/{slng}/{elat}/{elng}")
    public List<Lights> selectedLightsFromME(@PathVariable double slat, @PathVariable double slng, @PathVariable double elat, @PathVariable double elng){
        List<Lights> lights = service.findAll();
        List<Lights> ret = new ArrayList<>();

        double smalllat, biglat, smalllng, biglng = 0;
        if (slat >=elat){
            biglat = slat;
            smalllat = elat;
        }else{
            biglat = elat;
            smalllat = slat;
        }
        if(slng >= elng){
            biglng = slng;
            smalllng = elng;
        }else{
            biglng = elng;
            smalllng = slng;
        }

        // 오차 0.001 약 100m
        for (Lights lis : lights) {
            if ((lis.getLat() < biglat ) && (lis.getLat() >= smalllat )) {
                if ((lis.getLng() < biglng ) && (lis.getLng() >= smalllng )) {
                    ret.add(lis);
                }
            }
        }

        //오름차순
        int idx = 0;
        double distance[] = new double[ret.size()];
        for (Lights l : ret) {
            double k = (l.getLat()-slat)*(l.getLat()-slat) + (l.getLng()-slng)*(l.getLng()-slng);
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
