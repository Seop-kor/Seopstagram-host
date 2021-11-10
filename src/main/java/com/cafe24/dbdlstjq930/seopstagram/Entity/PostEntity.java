package com.cafe24.dbdlstjq930.seopstagram.Entity;

import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.PostPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "post")
public class PostEntity {
    @EmbeddedId
    private PostPK postsPK;

    @ManyToOne(targetEntity = MemberEntity.class)
    @JoinColumn(name = "MEM_id")
    private MemberEntity memberEntity;

    @Column(nullable = false, name = "POST_post_img")
    private String postimg;

    @Column(nullable = false, name = "POST_content")
    private String content;

    @Column(nullable = false, name = "POST_hashtag")
    private String hashtag;

    @Builder
    public PostEntity(int postid, MemberEntity memberEntity, String postimg, String content, String hashtag){
        this.postsPK = new PostPK(postid);
        this.memberEntity = memberEntity;
        this.postimg = postimg;
        this.content = content;
        this.hashtag = hashtag;
    }
}
