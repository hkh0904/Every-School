package com.everyschool.schoolservice.api.app.service.schoolapply;

import com.everyschool.schoolservice.domain.schoolapply.repository.SchoolApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SchoolApplyAppService {

    private final SchoolApplyRepository schoolApplyRepository;


}
