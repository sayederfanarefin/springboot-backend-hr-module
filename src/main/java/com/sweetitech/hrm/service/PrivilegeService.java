package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Privilege;
import com.sweetitech.hrm.domain.dto.PrivilegeDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PrivilegeService {

    Privilege create(Privilege privilege);

    Privilege update(Long id, Privilege privilege) throws Exception;

    Privilege read(Long id);

    List<Privilege> readAll();

    Page<Privilege> readAll(int page);

    void remove(Long id) throws Exception;

}
