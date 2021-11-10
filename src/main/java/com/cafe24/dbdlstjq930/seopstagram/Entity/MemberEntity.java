package com.cafe24.dbdlstjq930.seopstagram.Entity;

import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.MemberPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class MemberEntity {
    @EmbeddedId
    private MemberPK memberPK;

    @Column(nullable = false, name = "MEM_user_id")
    private String userid;

    @Column(nullable = false, name = "MEM_user_pass")
    private String userpass;

    @Column(name = "MEM_user_nick")
    private String usernick;

    @Column(nullable = false, name = "MEM_user_profile_img")
    private String userprofileimg;

    @Builder
    public MemberEntity(int memberid, String userid, String userpass
            ,String usernick, String userprofileimg){
        this.memberPK = new MemberPK(memberid);
        this.userid = userid;
        this.userpass = userpass;
        this.usernick = usernick;
        this.userprofileimg = userprofileimg;
    }
}
