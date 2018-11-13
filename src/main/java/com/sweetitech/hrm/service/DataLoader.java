package com.sweetitech.hrm.service;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.domain.Role;
import com.sweetitech.hrm.repository.OfficeHourRepository;
import com.sweetitech.hrm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private RoleRepository roleRepository;
    private OfficeHourRepository officeHourRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository, OfficeHourRepository officeHourRepository) {
        this.roleRepository = roleRepository;
        this.officeHourRepository = officeHourRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Load User Roles
        if (roleRepository.findById(1) == null)
            roleRepository.save(new Role(1,"ROLE_SUPER_ADMIN"));
        else if (!roleRepository.findById(1).getName().equals("ROLE_SUPER_ADMIN")) {
            Role oldAdminDate = roleRepository.findById(1);
            Role role = new Role();
            role.setId(oldAdminDate.getId());
            role.setName("ROLE_SUPER_ADMIN");
            roleRepository.save(role);
        }

        if (roleRepository.findById(2) == null)
            roleRepository.save(new Role(2,"ROLE_ADMIN_1"));
        else if (!roleRepository.findById(2).getName().equals("ROLE_ADMIN_1")) {
            Role oldAdminDate = roleRepository.findById(2);
            Role role = new Role();
            role.setId(oldAdminDate.getId());
            role.setName("ROLE_ADMIN_1");
            roleRepository.save(role);
        }

        if (roleRepository.findById(3) == null)
            roleRepository.save(new Role(3,"ROLE_ADMIN_2"));
        else if (!roleRepository.findById(3).getName().equals("ROLE_ADMIN_2")) {
            Role oldAdminDate = roleRepository.findById(3);
            Role role = new Role();
            role.setId(oldAdminDate.getId());
            role.setName("ROLE_ADMIN_2");
            roleRepository.save(role);
        }

        if (roleRepository.findById(4) == null)
            roleRepository.save(new Role(4,"ROLE_EMPLOYEE"));
        else if (!roleRepository.findById(4).getName().equals("ROLE_EMPLOYEE")) {
            Role oldAdminDate = roleRepository.findById(4);
            Role role = new Role();
            role.setId(oldAdminDate.getId());
            role.setName("ROLE_EMPLOYEE");
            roleRepository.save(role);
        }

        // Load days of week
        if (officeHourRepository.getFirstByDayNumber(1) == null) {
            officeHourRepository.save(new OfficeHour(1, Constants.DaysOfWeek.SUNDAY));
        }
        if (officeHourRepository.getFirstByDayNumber(2) == null) {
            officeHourRepository.save(new OfficeHour(2, Constants.DaysOfWeek.MONDAY));
        }
        if (officeHourRepository.getFirstByDayNumber(3) == null) {
            officeHourRepository.save(new OfficeHour(3, Constants.DaysOfWeek.TUESDAY));
        }
        if (officeHourRepository.getFirstByDayNumber(4) == null) {
            officeHourRepository.save(new OfficeHour(4, Constants.DaysOfWeek.WEDNESDAY));
        }
        if (officeHourRepository.getFirstByDayNumber(5) == null) {
            officeHourRepository.save(new OfficeHour(5, Constants.DaysOfWeek.THURSDAY));
        }
        if (officeHourRepository.getFirstByDayNumber(6) == null) {
            officeHourRepository.save(new OfficeHour(6, Constants.DaysOfWeek.FRIDAY));
        }
        if (officeHourRepository.getFirstByDayNumber(7) == null) {
            officeHourRepository.save(new OfficeHour(7, Constants.DaysOfWeek.SATURDAY));
        }

    }
}