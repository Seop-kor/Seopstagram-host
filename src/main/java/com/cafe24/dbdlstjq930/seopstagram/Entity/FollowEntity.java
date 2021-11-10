package com.cafe24.dbdlstjq930.seopstagram.Entity;

import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.FollowPK;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "follow")
public class FollowEntity {
    @EmbeddedId
    private FollowPK followPK;

    @ManyToOne(targetEntity = MemberEntity.class)
    @JoinColumn(name = "FOL_start_id")
    private MemberEntity startid; //팔로우를 누른 사람 id

    @ManyToOne(targetEntity = MemberEntity.class)
    @JoinColumn(name = "FOL_target_id")
    private MemberEntity targetid; // 그 타겟의 id

    @Builder
    public FollowEntity(int followid, MemberEntity startid, MemberEntity targetid){
        this.followPK = new FollowPK(followid);
        this.startid = startid;
        this.targetid = targetid;
    }
}
