package com.cafe24.dbdlstjq930.seopstagram.Repository;

import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.MemberPK;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.PostPK;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, PostPK> {
    List<PostEntity> findAllByMemberEntity_MemberPK(MemberPK memberPK);
    int countByMemberEntity_MemberPK(MemberPK memberPK);
}
