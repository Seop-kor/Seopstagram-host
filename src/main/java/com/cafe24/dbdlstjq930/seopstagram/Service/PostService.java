package com.cafe24.dbdlstjq930.seopstagram.Service;

import com.cafe24.dbdlstjq930.seopstagram.DTO.PostDTO;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PK.MemberPK;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PostEntity;
import com.cafe24.dbdlstjq930.seopstagram.Repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private PostRepository repository;

    public List<PostEntity> boards(MemberPK memberid){
        return repository.findAllByMemberEntity_MemberPK(memberid);
    }

    public void newpost(PostDTO postsDTO){
        repository.save(postsDTO.toEntity());
    }

    public int countByMemberEntity(MemberPK memberPK){
        return repository.countByMemberEntity_MemberPK(memberPK);
    }
}
