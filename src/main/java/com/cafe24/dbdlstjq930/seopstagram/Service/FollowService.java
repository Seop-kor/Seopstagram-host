package com.cafe24.dbdlstjq930.seopstagram.Service;

import com.cafe24.dbdlstjq930.seopstagram.DTO.FollowDTO;
import com.cafe24.dbdlstjq930.seopstagram.DTO.MemberDTO;
import com.cafe24.dbdlstjq930.seopstagram.Entity.FollowEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Repository.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FollowService {
    private FollowRepository repository;

//    public List<FollowEntity> findByMemberPK(MemberEntity memberEntity){
//        return repository.findAllByStartid(memberEntity);
//    }
    public List<FollowEntity> findByStartId(MemberEntity memberEntity){
        return repository.findAllByStartid(memberEntity);
    }

    public int countByStartid(MemberEntity startid){
        return repository.countAllByStartid(startid);
    }

    public int countBytargetid(MemberEntity targetid){
        return repository.countAllByTargetid(targetid);
    }

    public void following(FollowDTO followDTO, int memberid){
        followDTO.setStartid(memberid);
        repository.save(followDTO.toEntity());
    }

    public int countAllByStartidAndTargetid(MemberEntity startid, MemberEntity targetid){
        return repository.countAllByStartidEqualsAndTargetidEquals(startid, targetid);
    }

    @Transactional
    public void unfollow(FollowDTO targetid, MemberEntity startid){
        MemberDTO target = new MemberDTO();
        target.setMemberid(targetid.getTargetid());
        repository.deleteByStartidEqualsAndTargetidEquals(startid, target.toEntity());
    }
}
