package com.cafe24.dbdlstjq930.seopstagram.Repository;

import com.cafe24.dbdlstjq930.seopstagram.Entity.FollowEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.FollowPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity, FollowPK> {
    List<FollowEntity> findAllByStartid(MemberEntity startid);
    int countAllByStartid(MemberEntity startid);
    int countAllByTargetid(MemberEntity targetid);
    int countAllByStartidEqualsAndTargetidEquals(MemberEntity startid, MemberEntity targetid);
    void deleteByStartidEqualsAndTargetidEquals(MemberEntity startid, MemberEntity targetid);
}
