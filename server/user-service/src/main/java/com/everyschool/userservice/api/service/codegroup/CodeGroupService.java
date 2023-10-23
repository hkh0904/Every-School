package com.everyschool.userservice.api.service.codegroup;

import com.everyschool.userservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CodeGroupService {

    private final CodeGroupRepository codeGroupRepository;

    public CreateCodeGroupResponse createCodeGroup(String groupName) {
        return null;
    }
}
