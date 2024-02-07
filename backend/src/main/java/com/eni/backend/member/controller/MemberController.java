package com.eni.backend.member.controller;

import com.eni.backend.common.exception.CustomBadRequestException;
import com.eni.backend.common.response.BaseSuccessResponse;
import com.eni.backend.member.dto.request.PutCoinRequest;
import com.eni.backend.member.dto.request.PutRewardRequest;
import com.eni.backend.member.dto.request.PutLanguageRequest;
import com.eni.backend.member.dto.request.PutNicknameRequest;
import com.eni.backend.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.eni.backend.common.response.BaseResponseStatus.BAD_REQUEST;
import static com.eni.backend.common.response.BaseResponseStatus.SUCCESS;
import static com.eni.backend.common.util.BindingResultUtils.getErrorMessages;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("")
    public BaseSuccessResponse<?> getList() {
        log.info("MemberController.getList");

        return BaseSuccessResponse.of(SUCCESS, memberService.getList());
    }

    @PutMapping("/nickname")
    public BaseSuccessResponse<?> putNickname(Authentication authentication, @RequestBody @Valid PutNicknameRequest putNicknameRequest, BindingResult bindingResult) {
        log.info("MemberController.nickname.put");

        // validation 오류
        if (bindingResult.hasErrors()) {
            throw new CustomBadRequestException(BAD_REQUEST, getErrorMessages(bindingResult));
        }

        return BaseSuccessResponse.of(SUCCESS, memberService.putNickname(authentication, putNicknameRequest));
    }

    @PutMapping("/lang")
    public BaseSuccessResponse<?> putLanguage(Authentication authentication, @RequestBody @Valid PutLanguageRequest putLanguageRequest, BindingResult bindingResult) {
        log.info("MemberController.language.put");

        // validation 오류
        if (bindingResult.hasErrors()) {
            throw new CustomBadRequestException(BAD_REQUEST, getErrorMessages(bindingResult));
        }

        return BaseSuccessResponse.of(SUCCESS, memberService.putLanguage(authentication, putLanguageRequest));
    }

    @PutMapping("/coin/add")
    public BaseSuccessResponse<?> putCoinAdd(Authentication authentication, @RequestBody @Valid PutCoinRequest putCoinRequest, BindingResult bindingResult) {
        log.info("MemberController.coin.add");

        // validation 오류
        if (bindingResult.hasErrors()) {
            throw new CustomBadRequestException(BAD_REQUEST, getErrorMessages(bindingResult));
        }

        return BaseSuccessResponse.of(SUCCESS, memberService.putCoin(authentication, putCoinRequest, true));
    }

    @PutMapping("/coin/sub")
    public BaseSuccessResponse<?> putCoinSub(Authentication authentication, @RequestBody @Valid PutCoinRequest putCoinRequest, BindingResult bindingResult) {
        log.info("MemberController.coin.sub");

        // validation 오류
        if (bindingResult.hasErrors()) {
            throw new CustomBadRequestException(BAD_REQUEST, getErrorMessages(bindingResult));
        }

        return BaseSuccessResponse.of(SUCCESS, memberService.putCoin(authentication, putCoinRequest, false));
    }

    @PutMapping("/reward")
    public BaseSuccessResponse<?> putExp(Authentication authentication, @RequestBody @Valid PutRewardRequest putRewardRequest, BindingResult bindingResult) {
        log.info("MemberController.reward");

        // validation 오류
        if (bindingResult.hasErrors()) {
            throw new CustomBadRequestException(BAD_REQUEST, getErrorMessages(bindingResult));
        }

        return BaseSuccessResponse.of(SUCCESS, memberService.putReward(authentication, putRewardRequest));
    }
}