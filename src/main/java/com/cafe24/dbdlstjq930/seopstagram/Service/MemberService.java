package com.cafe24.dbdlstjq930.seopstagram.Service;

import com.cafe24.dbdlstjq930.seopstagram.DTO.MemberDTO;
import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.MemberPK;
import com.cafe24.dbdlstjq930.seopstagram.Repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@AllArgsConstructor
@Log
public class MemberService {
    private MemberRepository repository;
    private PasswordEncoder passwordEncoder;

    public MemberEntity findByusername(String username){
        return repository.findByUserid(username);
    }

    public MemberEntity findBymemberPK(MemberPK memberid){
        return repository.findByMemberPK(memberid);
    }

    public void register(MemberDTO memberDTO){
        memberDTO.setUserpass(passwordEncoder.encode(memberDTO.getUserpass()));
        memberDTO.setUsernick(memberDTO.getUserid());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/imgs/profile/")
                .path("defaultimage.png")
                .toUriString();
        memberDTO.setUserprofileimg(fileDownloadUri);
        log.info(fileDownloadUri);
        repository.save(memberDTO.toEntity());
    }

    public boolean membercheck(MemberDTO memberDTO){
        MemberEntity memberEntity = repository.findByUserid(memberDTO.getUserid());
        return passwordEncoder.matches(memberDTO.getUserpass(), memberEntity.getUserpass());
    }

    public boolean countByuserid(String id){
        int member = repository.countByUserid(id);
        if(member == 0){
            return true;
        }
        return false;
    }

    public void memberchange(MemberDTO memberDTO, String userid) throws Exception{
        repository.updateprofile(memberDTO.getUserprofileimg(), memberDTO.getUsernick(), userid);
    }

    public MemberEntity userfind(String userid_usernick){
        return repository.userfind(userid_usernick);
    }
}
