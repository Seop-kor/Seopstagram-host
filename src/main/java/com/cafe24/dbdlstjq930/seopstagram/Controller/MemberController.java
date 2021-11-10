package com.cafe24.dbdlstjq930.seopstagram.Controller;

import com.cafe24.dbdlstjq930.seopstagram.Config.JwtTokenUtil;
import com.cafe24.dbdlstjq930.seopstagram.DTO.JwtTokenDTO;
import com.cafe24.dbdlstjq930.seopstagram.DTO.MemberDTO;
import com.cafe24.dbdlstjq930.seopstagram.Service.JwtUserDetailsService;
import com.cafe24.dbdlstjq930.seopstagram.Service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class MemberController {
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;
    private MemberService memberService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @PostMapping("/login.action")
//    public String loginaction(HttpSession session, JwtTokenDTO jwtTokenDTO, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception{
//        MemberDTO memberDTO = new MemberDTO();
//        memberDTO.setUserid(jwtTokenDTO.getUsername());
//        memberDTO.setUserpass(jwtTokenDTO.getPassword());
//        if(!memberService.membercheck(memberDTO)){
//            return "redirect:/login";
//        }
//        String username = jwtTokenDTO.getUsername();
//        authenticate(username, jwtTokenDTO.getPassword());
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        final String token = jwtTokenUtil.generateToken(userDetails);
////        session.setAttribute("Authorization", "Bearer " + token);
////        redirectAttributes.addAttribute("Authorization", "Bearer " + token);
//        WebUtils.setSessionAttribute(request, "Authorization", "Bearer " + token);
//        return "redirect:/"; //forward였음
//    }
    @PostMapping("/login.action")
    public @ResponseBody String loginactiont(JwtTokenDTO jwtTokenDTO, HttpServletRequest request) throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserid(jwtTokenDTO.getUsername());
        memberDTO.setUserpass(jwtTokenDTO.getPassword());
        if(!memberService.membercheck(memberDTO)){
            return "false";
        }
        String username = jwtTokenDTO.getUsername();
        authenticate(username, jwtTokenDTO.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @GetMapping("/signup")
    public String signup(){
        return "register";
    }

    @PostMapping("/signup.action")
    public String signupaction(MemberDTO memberDTO) throws Exception{
        memberService.register(memberDTO);
        return "redirect:/login";
    }

    @GetMapping("/idcheck")
    public @ResponseBody
    boolean check(@RequestParam(value = "id")String id) throws Exception{
        return memberService.countByuserid(id);
    }

    private void authenticate(String username, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw new Exception("user_Disabled", e);
        }catch (BadCredentialsException e){
            throw new Exception("invalid_credentials",e);
        }
    }
}
