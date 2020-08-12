package com.bone.was.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    private Double slat;
    private Double slng;
    private Double dlat;
    private Double dlng;
}
