package com.cafe24.dbdlstjq930.seopstagram.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDTO {
    private String username;
    private String password;
}