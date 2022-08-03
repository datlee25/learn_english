package com.example.learning_english.controller;

import com.example.learning_english.dto.AccountDto;
import com.example.learning_english.dto.RegisterDto;
import com.example.learning_english.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
=======
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


>>>>>>> be8bf6a (feat: update database code)
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService ;

    @RequestMapping(path ="register",method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto){
        AccountDto accountDto = authenticationService.saveAccount(registerDto);
        return ResponseEntity.ok().body(accountDto);
    }

<<<<<<< HEAD
=======
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest,HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        User user = userService.register(signupRequest);
        return ResponseEntity.ok(user);
    }

    @RequestMapping(path = "/token/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {

        String refresh_token = request.getRefreshToken();
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(refresh_token);

        return refreshTokenService.findByToken(refresh_token)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtils.generateJwtToken(user.getEmail(), user.getUsername());
                    return ResponseEntity.ok(
                            new TokenRefreshResponse(accessToken, refresh_token)
                    );
                }).orElseThrow(() -> new TokenRefreshException(refresh_token,
                        "Refresh token is not in database!"));
    }

    @RequestMapping(path = "/login-google")
    public ResponseEntity<?> loginGoogle(HttpServletRequest request) throws ClientProtocolException, IOException {
        String code = request.getParameter("code");

        String accessToken = googleUtils.getToken(code);
        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
        UserDetails userDetail = googleUtils. buildUser(googlePojo);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
                userDetail.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(googlePojo.getEmail(), googlePojo.getName());

        List<String> roles = userDetail.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        SignupRequest signupRequest = new SignupRequest(googlePojo.getName(), googlePojo.getEmail());

        //LOCK
//        userService.saveUser(signupRequest);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(1);
        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                refreshToken.getToken(),
                googlePojo.getName(),
                googlePojo.getEmail(),
                roles);
        return ResponseEntity.ok(jwtResponse);
    }
>>>>>>> be8bf6a (feat: update database code)
}
