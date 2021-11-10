package com.cafe24.dbdlstjq930.seopstagram.Entity.PK;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Embeddable
public class PostPK implements Serializable {
    @Column(nullable = false, name = "POST_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postid;

    public PostPK(int postid){
        this.postid = postid;
    }
}
