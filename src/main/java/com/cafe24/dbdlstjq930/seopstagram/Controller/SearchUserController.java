package com.cafe24.dbdlstjq930.seopstagram.Controller;

import com.cafe24.dbdlstjq930.seopstagram.Config.JwtTokenUtil;
import com.cafe24.dbdlstjq930.seopstagram.DTO.FollowDTO;
import com.cafe24.dbdlstjq930.seopstagram.DTO.MemberDTO;
import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PostEntity;
import com.cafe24.dbdlstjq930.seopstagram.Service.FollowService;
import com.cafe24.dbdlstjq930.seopstagram.Service.MemberService;
import com.cafe24.dbdlstjq930.seopstagram.Service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
@Log
public class SearchUserController {
    private MemberService memberService;
    private PostService postsService;
    private JwtTokenUtil util;
    private FollowService followService;

    @GetMapping("/find")
    public ModelAndView find(@RequestParam(value = "usernick")String nick, HttpServletRequest request) throws Exception{
        MemberEntity entity = memberService.userfind(nick);
        List<PostEntity> postsEntities = postsService.boards(entity.getMemberPK());
        int board_count = postsService.countByMemberEntity(entity.getMemberPK());
        int follower_count = followService.countBytargetid(entity);
        int following_count = followService.countByStartid(entity);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userprofileimg",entity.getUserprofileimg());
        modelAndView.addObject("usernick",entity.getUsernick());
        modelAndView.addObject("memberid",entity.getMemberPK().getMemberid());
        modelAndView.addObject("board_count",board_count);
        modelAndView.addObject("follower_count",follower_count);
        modelAndView.addObject("following_count",following_count);
        modelAndView.addObject("boards", postsEntities);
        modelAndView.setViewName("searchuser");
        return modelAndView; //여기 프로필 화면으로 바꿔야함
    }

    @GetMapping("/follow_check")
    public @ResponseBody boolean followCheck(@RequestParam(value = "memberid") String memberid, HttpServletRequest request) throws Exception {
        String token = request.getCookies() != null ? URLDecoder.decode(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue(), "UTF-8") : null;
        String username = util.getUsernameFromToken(token.substring(7));
        MemberEntity startid = memberService.findByusername(username);
        MemberDTO targetid = new MemberDTO();
        targetid.setMemberid(Integer.parseInt(memberid));
        int countFollow = followService.countAllByStartidAndTargetid(startid, targetid.toEntity());
        return countFollow >= 1;
    }

    @PostMapping("/follow")
    public @ResponseBody
    boolean follow(FollowDTO followDTO, HttpServletRequest request) throws Exception{
        String token = request.getCookies() != null ? URLDecoder.decode(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue(), "UTF-8") : null;
        MemberEntity memberEntity = memberService.findByusername(util.getUsernameFromToken(token.substring(7)));
        followService.following(followDTO, memberEntity.getMemberPK().getMemberid());
        return true;
    }

    @PostMapping("/unfollow")
    public @ResponseBody boolean unfollow(FollowDTO followingDTO, HttpServletRequest request) throws Exception{
        String token = request.getCookies() != null ? URLDecoder.decode(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue(), "UTF-8") : null;
        MemberEntity memberEntity = memberService.findByusername(util.getUsernameFromToken(token.substring(7)));
        followService.unfollow(followingDTO, memberEntity);
        return true;
    }
}
