package com.cafe24.dbdlstjq930.seopstagram.DTO;

import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO {
    private int memberid;
    private String userid;
    private String userpass;
    private String usernick;
    private String userprofileimg;

    public MemberEntity toEntity(){
        return MemberEntity.builder().memberid(memberid).userid(userid).userpass(userpass)
                .usernick(usernick).userprofileimg(userprofileimg).build();
    }
}
