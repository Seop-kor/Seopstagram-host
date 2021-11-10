package com.cafe24.dbdlstjq930.seopstagram.Repository;

import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.MemberPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<MemberEntity, MemberPK> {
    MemberEntity findByUserid(String username);
    MemberEntity findByMemberPK(MemberPK memberPK);
    MemberEntity findByUsernick(String usernick);
    int countByUserid(String userid);

//    @Query(value = "update member set  = :userprofileimg, usernick = :usernick where userid = :userid", nativeQuery = true)
    @Query(value = "update MemberEntity m set m.userprofileimg=:userprofileimg, m.usernick=:usernick where m.userid=:userid")
    void updateprofile(@Param("userprofileimg")String userprofileimg, @Param("usernick")String usernick, @Param("userid")String userid);

//    @Query(value = "select * from member where userid = :usernick_userid or usernick = :usernick_userid", nativeQuery = true)
    @Query(value = "select m from MemberEntity m where m.userid = :usernick_userid or m.userid = :usernick_userid")
    MemberEntity userfind(@Param("usernick_userid")String usernick_userid);
}
