package com.cafe24.dbdlstjq930.seopstagram.DTO;

import com.cafe24.dbdlstjq930.seopstagram.Entity.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedModel {
    private PostEntity postsEntity;
    private String userprofileimg;
    private String usernick;
}