package com.cafe24.dbdlstjq930.seopstagram.Controller;

import com.cafe24.dbdlstjq930.seopstagram.Config.JwtTokenUtil;
import com.cafe24.dbdlstjq930.seopstagram.DTO.FeedModel;
import com.cafe24.dbdlstjq930.seopstagram.Entity.FollowEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PostEntity;
import com.cafe24.dbdlstjq930.seopstagram.Service.FollowService;
import com.cafe24.dbdlstjq930.seopstagram.Service.MemberService;
import com.cafe24.dbdlstjq930.seopstagram.Service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.session.WebSessionStore;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    private JwtTokenUtil util;
    private MemberService memberService;
    private PostService postsService;
    private FollowService followingsService;

    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request) throws Exception {
        String token = request.getCookies() != null ? URLDecoder.decode(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue(), "UTF-8") : null;
        MemberEntity memberEntity = memberService.findByusername(util.getUsernameFromToken(token.substring(7)));
        List<FeedModel> boards = new ArrayList<>();
        List<PostEntity> followingBoard = new ArrayList<>();
        if(followingsService.findByStartId(memberEntity) != null){
            for(FollowEntity e : followingsService.findByStartId(memberEntity)){
                followingBoard = postsService.boards(e.getTargetid().getMemberPK());
                MemberEntity memberEntity1 = memberService.findBymemberPK(e.getTargetid().getMemberPK());
                if(followingBoard != null){
                    for(PostEntity t : followingBoard){
                        FeedModel feedModel = new FeedModel();
                        feedModel.setPostsEntity(t);
                        feedModel.setUsernick(memberEntity1.getUsernick());
                        feedModel.setUserprofileimg(memberEntity1.getUserprofileimg());
                        //여기에 댓글 넣는것도 넣어야함
                        boards.add(feedModel);
                    }
                }
            }
        }
        model.addAttribute("boards",boards);
        return "index";
    }
}
