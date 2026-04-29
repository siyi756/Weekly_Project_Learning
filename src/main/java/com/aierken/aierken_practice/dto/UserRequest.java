package com.aierken.aierken_practice.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
   private String name;
   private String email;
   private String password;
   private String phone;
   private String address;
   
}
