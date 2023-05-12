package com.dtu.kolgo.dto.kol;

import com.dtu.kolgo.enums.Gender;
import com.dtu.kolgo.model.City;
import com.dtu.kolgo.model.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KolDto {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String role;
    private Integer id;
    private String phone;
    private Gender gender;
    private City city;
    private String addressDetails;
    private List<Field> fields;
    private BigDecimal postPrice;
    private BigDecimal videoPrice;
    private String facebookUrl;
    private String instagramUrl;
    private String tiktokUrl;
    private String youtubeUrl;

}
