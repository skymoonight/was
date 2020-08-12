package com.bone.was.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lights {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private Double lat;
    private Double lng;
    private String own;
    private Date date;
}
