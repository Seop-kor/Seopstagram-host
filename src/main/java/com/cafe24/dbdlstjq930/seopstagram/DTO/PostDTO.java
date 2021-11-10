package com.cafe24.dbdlstjq930.seopstagram.DTO;

import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDTO {
    private int memberid;
    private int postid;
    private String postimg;
    private String content;
    private String hashtag;

    public PostEntity toEntity(){
        return PostEntity.builder().memberEntity(MemberEntity.builder().memberid(memberid).build())
                .postid(postid).postimg(postimg).content(content).hashtag(hashtag).build();
    }
}
