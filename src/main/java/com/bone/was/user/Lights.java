package com.bone.was.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // @Convert(converter = StringEncryptConverter.class)
    private String named;

    private Double lat;
    private Double lng;
    private String own;
    private Date dated;
}
