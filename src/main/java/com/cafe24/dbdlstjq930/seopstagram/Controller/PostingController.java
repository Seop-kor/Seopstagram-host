package com.cafe24.dbdlstjq930.seopstagram.Controller;

import com.cafe24.dbdlstjq930.seopstagram.Config.JwtTokenUtil;
import com.cafe24.dbdlstjq930.seopstagram.DTO.PostDTO;
import com.cafe24.dbdlstjq930.seopstagram.Entity.MemberEntity;
import com.cafe24.dbdlstjq930.seopstagram.Service.FileUploadDownService;
import com.cafe24.dbdlstjq930.seopstagram.Service.MemberService;
import com.cafe24.dbdlstjq930.seopstagram.Service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.Arrays;

@Controller
public class PostingController {
    private JwtTokenUtil util;
    private MemberService memberService;
    private PostService postsService;
    private FileUploadDownService fileUploadDownService;
    private String fileDownloadUri;

    public PostingController(JwtTokenUtil util, MemberService memberService, PostService postsService, FileUploadDownService fileUploadDownService){
        this.util = util;
        this.memberService = memberService;
        this.postsService = postsService;
        this.fileUploadDownService = fileUploadDownService;
    }

    @GetMapping("/post")
    public String post(){
        return "new_post"; //?
    }

    @PostMapping("/post.action")
    public String postaction(HttpSession session, PostDTO postsDTO, HttpServletRequest request) throws Exception{
//        String token = WebUtils.getSessionAttribute(request, "Authorization").toString();
        String token = request.getCookies() != null ? URLDecoder.decode(Arrays.stream(request.getCookies()).filter(s -> s.getName().equals("Authorization")).findFirst().get().getValue(), "UTF-8") : null;
        MemberEntity memberid = memberService.findByusername(util.getUsernameFromToken(token.substring(7)));
        postsDTO.setMemberid(memberid.getMemberPK().getMemberid());
        postsDTO.setPostimg(fileDownloadUri);
        postsService.newpost(postsDTO);
//        return "forward:/";
        return "redirect:/";
    }

    @PostMapping("/post.Imageaction")
    public @ResponseBody
    boolean postimage(@RequestParam(value = "file") MultipartFile file) throws Exception{
        String fileName = fileUploadDownService.storeFile(file, "post");

        fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/imageFile/post/")
                .path(fileName)
                .toUriString();
        return true;
    }
}
