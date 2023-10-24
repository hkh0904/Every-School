package com.everyschool.userservice.api.service.codegroup;

import com.everyschool.userservice.api.controller.codegroup.response.CodeGroupResponse;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CodeGroupQueryService {

    private final CodeGroupQueryRepository codeGroupQueryRepository;

    public List<CodeGroupResponse> searchCodeGroups() {
        return codeGroupQueryRepository.findAll();
    }
}
