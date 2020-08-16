package com.bone.was.location;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Destination {
    private String name;
    private Double lat;
    private Double lng;
}
