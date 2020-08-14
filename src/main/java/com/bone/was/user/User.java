package com.bone.was.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


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

    //@Convert(converter= StringEncryptConverter.class)
    private String AuthKey;

    //@CreationTimestamp
    //private LocalDateTime joinDate;
    private Date joinDate;

}
