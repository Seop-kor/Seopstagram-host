package com.cafe24.dbdlstjq930.seopstagram.DTO;

import com.cafe24.dbdlstjq930.seopstagram.Entity.FollowEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowDTO {
    private int follownum;
    private int startid;
    private int targetid;

    public FollowEntity toEntity(){
        MemberEntity startentity = MemberEntity.builder().memberid(startid).build();
        MemberEntity targetentity = MemberEntity.builder().memberid(targetid).build();
        return FollowEntity.builder().followid(follownum).startid(startentity).targetid(targetentity).build();
    }
}
