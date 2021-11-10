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
public class FollowPK implements Serializable {
    @Column(nullable = false, name = "FOL_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int followid;

    public FollowPK(int followid){
        this.followid = followid;
    }
}
