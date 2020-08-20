package com.bone.was.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lights {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String named;
    private Double lat;
    private Double lng;
    private String own;
    private LocalDate dated;
}
