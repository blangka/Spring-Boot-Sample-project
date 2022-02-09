package com.hkmc.sample.api;

import com.hkmc.sample.model.ResJson;
import com.hkmc.sample.model.dto.ResId;
import com.hkmc.sample.model.dto.security.ReqUser;
import com.hkmc.sample.model.dto.security.ResToken;
import com.hkmc.sample.model.dto.security.ResUser;
import com.hkmc.sample.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Api(tags = "인증 API")
public class UserApiController {
    public final UserService userService;

    @ApiOperation(value = "회원가입", notes = "사용자 등록을 합니다.")
    @PostMapping("/signup")
    public ResJson<ResId> signup(@RequestBody ReqUser reqUser) {
        return new ResJson<>(new ResId(userService.signup(reqUser)));
    }

    @ApiOperation(value = "로그인", notes = "로그인하여, 토큰을 생성합니다.")
    @PostMapping("/login")
    public ResJson<ResToken> login(@RequestBody ReqUser request) {
        ResToken result = userService.login(request);
        return new ResJson<>(result);
    }
    /*
    @ApiOperation(value = "토큰 재생성", notes = "토큰 재발행 시간을 통해 다시 토큰을 발급합니다.")
    @PostMapping("/reissue")
    public Header<TokenResponse> reissue(@RequestBody TokenRequest request) {
        return authService.reissue(request);
    }*/
}
