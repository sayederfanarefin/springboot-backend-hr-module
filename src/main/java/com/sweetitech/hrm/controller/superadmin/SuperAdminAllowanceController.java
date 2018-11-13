package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.dto.AllowanceDTO;
import com.sweetitech.hrm.service.implementation.AllowanceServiceImpl;
import com.sweetitech.hrm.service.mapping.AllowanceEditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin/allowances")
public class SuperAdminAllowanceController {

    private AllowanceServiceImpl allowanceService;

    @Autowired
    private AllowanceEditMapper allowanceEditMapper;

    @Autowired
    public SuperAdminAllowanceController(AllowanceServiceImpl allowanceService) {
        this.allowanceService = allowanceService;
    }

    // Get All Forwarded by Company
    @RequestMapping(value = "/forwarded/{companyId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllRequestedByCompany(@PathVariable Long companyId,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return allowanceService.readAllRequested(companyId, Constants.RequestStatus.FORWARDED, page);
    }

    // Get All by Status and Company Id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllByStatusAndCompany(@PathVariable Long companyId,
                                                        @RequestParam(value = "status", defaultValue = "requested") String status,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        if (status.equals(Constants.RequestStatus.CANCELLED)) {
            throw new EntityNotFoundException("Access Denied");
        }

        return allowanceService.readAllByStatusAndCompany(companyId, status, page);
    }

    // Get All Forwarded by User
    @RequestMapping(value = "/forwarded/user/{userId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllRequestedByUser(@PathVariable Long userId,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(userId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return allowanceService.readAllRequestedByUserId(userId, Constants.RequestStatus.FORWARDED, page);
    }

    // Get All By Status and User id
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllByStatusAndUser(@PathVariable Long userId,
                                                     @RequestParam(value = "status", defaultValue = "requested") String status,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(userId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        if (status.equals(Constants.RequestStatus.CANCELLED)) {
            throw new EntityNotFoundException("Access Denied");
        }

        return allowanceService.readAllByStatusAndUser(userId, status, page);
    }

    // Edit Requested Allowance
    @RequestMapping(value = "/{allowanceId}/allowance", method = RequestMethod.PUT)
    public Allowance editRequestedAllowance(@PathVariable Long allowanceId,
                                            @RequestBody AllowanceDTO dto) throws Exception {
        if (allowanceId == null) {
            throw new EntityNotFoundException("Null allowance id found!");
        }

        if (dto == null) {
            throw new EntityNotFoundException("Null data found!");
        }

//        if (allowanceService.readStatus(allowanceId).equals(Constants.RequestStatus.APPROVED)
//                || allowanceService.readStatus(allowanceId).equals(Constants.RequestStatus.DECLINED)) {
//            throw new EntityNotFoundException("Allowance cannot be edited now!");
//        }

        // dto.setStatus(Constants.RequestStatus.REQUESTED);

        return allowanceService.update(allowanceId, allowanceEditMapper.map(dto));
    }

}
