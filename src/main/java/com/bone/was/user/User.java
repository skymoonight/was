package com.bone.was.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password","ssn"})
//@JsonFilter("UserInfo")
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String AuthKey;

    //@CreationTimestamp
    //private LocalDateTime joinDate;
    private Date joinDate;

}
