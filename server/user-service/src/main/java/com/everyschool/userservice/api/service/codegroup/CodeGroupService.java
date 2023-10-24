package com.everyschool.userservice.api.service.codegroup;

import com.everyschool.userservice.api.controller.codegroup.response.CreateCodeGroupResponse;
import com.everyschool.userservice.api.controller.codegroup.response.RemoveCodeGroupResponse;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupQueryRepository;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CodeGroupService {

    private final CodeGroupRepository codeGroupRepository;
    private final CodeGroupQueryRepository codeGroupQueryRepository;

    public CreateCodeGroupResponse createCodeGroup(String groupName) {
        boolean isExist = codeGroupQueryRepository.existGroupName(groupName);
        if (isExist) {
            throw new DuplicateException("이미 사용 중인 그룹 이름입니다.");
        }

        CodeGroup codeGroup = CodeGroup.builder()
            .groupName(groupName)
            .build();
        CodeGroup savedCodeGroup = codeGroupRepository.save(codeGroup);

        return CreateCodeGroupResponse.of(savedCodeGroup);
    }

    public RemoveCodeGroupResponse removeCodeGroup(Integer groupId) {
        Optional<CodeGroup> findCodeGroup = codeGroupRepository.findById(groupId);
        if (findCodeGroup.isEmpty()) {
            throw new NoSuchElementException();
        }

        CodeGroup group = findCodeGroup.get();
        group.remove();

        return RemoveCodeGroupResponse.of(group);
    }
}
