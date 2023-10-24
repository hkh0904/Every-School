package com.everyschool.userservice.api.service.codedetail;

import com.everyschool.userservice.api.controller.codedetail.respnse.CodeDetailResponse;
import com.everyschool.userservice.api.controller.codedetail.respnse.CodeResponse;
import com.everyschool.userservice.domain.codedetail.repository.CodeDetailQueryRepository;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CodeDetailQueryService {

    private final CodeDetailQueryRepository codeDetailQueryRepository;
    private final CodeGroupRepository codeGroupRepository;

    public CodeResponse searchCodeDetails(int groupId) {
        Optional<CodeGroup> findCodeGroup = codeGroupRepository.findById(groupId);
        if (findCodeGroup.isEmpty()) {
            throw new NoSuchElementException();
        }
        CodeGroup codeGroup = findCodeGroup.get();

        List<CodeDetailResponse> responses = codeDetailQueryRepository.findByGroupId(groupId);

        return CodeResponse.builder()
            .groupId(codeGroup.getId())
            .groupName(codeGroup.getGroupName())
            .codes(responses)
            .build();
    }
}
