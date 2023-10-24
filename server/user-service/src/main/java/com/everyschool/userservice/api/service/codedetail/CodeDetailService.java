package com.everyschool.userservice.api.service.codedetail;

import com.everyschool.userservice.api.controller.codedetail.respnse.CreateCodeDetailResponse;
import com.everyschool.userservice.api.service.user.exception.DuplicateException;
import com.everyschool.userservice.domain.codedetail.CodeDetail;
import com.everyschool.userservice.domain.codedetail.repository.CodeDetailQueryRepository;
import com.everyschool.userservice.domain.codedetail.repository.CodeDetailRepository;
import com.everyschool.userservice.domain.codegroup.CodeGroup;
import com.everyschool.userservice.domain.codegroup.repository.CodeGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CodeDetailService {

    private final CodeDetailRepository codeDetailRepository;
    private final CodeDetailQueryRepository codeDetailQueryRepository;
    private final CodeGroupRepository codeGroupRepository;

    public CreateCodeDetailResponse createCodeDetail(int groupId, String codeName) {
        boolean isExist = codeDetailQueryRepository.existCodeName(groupId, codeName);
        if (isExist) {
            throw new DuplicateException("이미 사용 중인 코드 이름입니다.");
        }

        Optional<CodeGroup> findCodeGroup = codeGroupRepository.findById(groupId);
        if (findCodeGroup.isEmpty()) {
            throw new NoSuchElementException();
        }
        CodeGroup codeGroup = findCodeGroup.get();

        CodeDetail codeDetail = CodeDetail.builder()
            .codeName(codeName)
            .group(codeGroup)
            .build();
        CodeDetail savedCodeDetail = codeDetailRepository.save(codeDetail);

        return CreateCodeDetailResponse.of(savedCodeDetail);
    }
}
