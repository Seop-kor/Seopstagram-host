package com.cafe24.dbdlstjq930.seopstagram.Controller;

import com.cafe24.dbdlstjq930.seopstagram.Config.JwtTokenUtil;
import com.cafe24.dbdlstjq930.seopstagram.DTO.MemberDTO;
import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Entity.PostEntity;
import com.cafe24.dbdlstjq930.seopstagram.Service.FileUploadDownService;
import com.cafe24.dbdlstjq930.seopstagram.Service.FollowService;
import com.cafe24.dbdlstjq930.seopstagram.Service.MemberService;
import com.cafe24.dbdlstjq930.seopstagram.Service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProfileController {
    private MemberService memberService;
    private PostService postsService;
    private JwtTokenUtil util;
    private FileUploadDownService fileUploadDownService;
    private FollowService followService;

    @RequestMapping("/profile")
    public ModelAndView profile(HttpSession session, HttpServletRequest request) throws Exception {
//        String token = WebUtils.getSessionAttribute(request, "Authorization").toString();
        String token = request.getCookies() != null ? URLDecoder.decode(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue(), "UTF-8") : null;
        MemberEntity memberEntity = memberService.findByusername(util.getUsernameFromToken(token.substring(7)));
        List<PostEntity> postsEntities = postsService.boards(memberEntity.getMemberPK());
        int board_count = postsService.countByMemberEntity(memberEntity.getMemberPK());
        int follower_count = followService.countBytargetid(memberEntity);
        int following_count = followService.countByStartid(memberEntity);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profileimg", memberEntity.getUserprofileimg());
        modelAndView.addObject("usernick",memberEntity.getUsernick());
        modelAndView.addObject("board_count",board_count);
        modelAndView.addObject("follower_count",follower_count);
        modelAndView.addObject("following_count",following_count);
        modelAndView.addObject("boards",postsEntities);
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @GetMapping("/logoutgg")
    public String logout(HttpSession session, HttpServletRequest request) throws Exception{
//        session.removeAttribute("Authorization");
        request.getSession().removeAttribute("Authorization");
        return "redirect:/";
    }
    @GetMapping("/profile_change")
    public String change(){
        return "profile_change";
    }

    @PostMapping("/profile_change.action")
    public String profile_change(MemberDTO memberDTO, HttpSession session, HttpServletRequest request) throws Exception{
        String token = request.getCookies() != null ? URLDecoder.decode(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue(), "UTF-8") : null;
        memberService.memberchange(memberDTO, util.getUsernameFromToken(token.substring(7)));
        return "redirect:/profile";
    }

    @PostMapping("/profileimg.action")
    public @ResponseBody
    boolean imgaction(@RequestParam(value = "file") MultipartFile file) throws Exception{
        String fileName = fileUploadDownService.storeFile(file, "profile");

//        fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
        return true;
    }
}
