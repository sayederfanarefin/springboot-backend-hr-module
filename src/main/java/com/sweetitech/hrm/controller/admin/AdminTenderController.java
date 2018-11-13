package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.domain.dto.TenderDTO;
import com.sweetitech.hrm.domain.dto.TenderResponseDTO;
import com.sweetitech.hrm.domain.dto.TenderSearchDTO;
import com.sweetitech.hrm.service.implementation.TenderServiceImpl;
import com.sweetitech.hrm.service.mapping.TenderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tenders")
public class AdminTenderController {

    private TenderServiceImpl tenderService;

    @Autowired
    private TenderMapper tenderMapper;

    @Autowired
    public AdminTenderController(TenderServiceImpl tenderService) {
        this.tenderService = tenderService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public TenderResponseDTO create(@RequestBody TenderDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.create(tenderMapper.map(dto));
    }

    // Update
    @RequestMapping(value = "/{tenderId}", method = RequestMethod.PUT)
    public TenderResponseDTO update(@PathVariable Long tenderId, @RequestBody TenderDTO dto) throws Exception {
        if (tenderId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.update(tenderId, tenderMapper.map(dto));
    }

    // Get DTO
    @RequestMapping(value = "/{tenderId}", method = RequestMethod.GET)
    public TenderResponseDTO getDTO(@PathVariable Long tenderId) throws Exception {
        if (tenderId == null) {
            throw new EntityNotFoundException("Null tender id found!");
        }

        return tenderService.readDTO(tenderId);
    }

    // Get all
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<TenderResponseDTO> getAll() throws Exception {
        return tenderService.readAll();
    }

    // Delete
    @RequestMapping(value = "/{tenderId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long tenderId) throws Exception {
        if (tenderId == null) {
            throw new EntityNotFoundException("Null tender id found!");
        }

        tenderService.remove(tenderId);
    }

    // Update status
    @RequestMapping(value = "/status/{tenderId}", method = RequestMethod.PUT)
    public TenderResponseDTO updateStatus(@PathVariable Long tenderId,
                                          @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (tenderId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.updateStatus(tenderId, dto.getObject());
    }

    // Update tender security status
    @RequestMapping(value = "/tender-security/{tenderId}", method = RequestMethod.PUT)
    public TenderResponseDTO updateTenderSecurityStatus(@PathVariable Long tenderId,
                                                        @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (tenderId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.updateTenderSecurityStatus(tenderId, dto.getObject());
    }

    // Update tender status
    @RequestMapping(value = "/tender-status/{tenderId}", method = RequestMethod.PUT)
    public TenderResponseDTO updateTenderStatus(@PathVariable Long tenderId,
                                                @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (tenderId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.updateTenderStatus(tenderId, dto.getObject());
    }

    // Search tender by institution name, e-Tender id and MR Code
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<TenderResponseDTO> searchTender(@RequestBody TenderSearchDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.search(dto.getInstitutionName(), dto.geteTenderId(), dto.getMrCode());
    }
}
