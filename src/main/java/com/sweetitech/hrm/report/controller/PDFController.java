package com.sweetitech.hrm.report.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.repository.TypeOfBonusRepository;
import com.sweetitech.hrm.service.implementation.*;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.Log;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.serialised.UserListSerialised;
import com.sweetitech.hrm.report.domain.MyAllowance;
import com.sweetitech.hrm.service.mapping.AttendanceResponseMapper;

@Controller
@RequestMapping("/report/html")
public class PDFController {

	@Value("${base.url.image}")
	private String BASE_LOCATION;

	private LogServiceImpl logService;
	private CompanyServiceImpl companyService;
	private DepartmentServiceImpl departmentService;
	private UserServiceImpl userService;
	private OfficeCodeServiceImpl officeCodeService;
	private AllowanceServiceImpl allowanceService;
	private LeaveServiceImpl leaveService;
	private CommissionServiceImpl commissionService;
	private TypeOfCommissionServiceImpl typeOfCommissionService;
	private BonusServiceImpl bonusService;
	private TypeOfBonusServiceImpl typeOfBonusService;
	private TenderServiceImpl tenderService;
	private PayrollServiceImpl payrollService;
	private TourServiceImpl tourService;

	@Autowired
	private AttendanceResponseMapper attendanceResponseMapper;

	@Autowired
	private UserSmallResponseMapper userSmallResponseMapper;

	@Autowired
	public PDFController(LogServiceImpl logService,
                         CompanyServiceImpl companyService,
                         DepartmentServiceImpl departmentService,
                         UserServiceImpl userService,
                         OfficeCodeServiceImpl officeCodeService,
                         AllowanceServiceImpl allowanceService,
                         LeaveServiceImpl leaveService,
                         CommissionServiceImpl commissionService,
                         TypeOfCommissionServiceImpl typeOfCommissionService,
                         BonusServiceImpl bonusService,
                         TypeOfBonusServiceImpl typeOfBonusService,
                         TenderServiceImpl tenderService,
						 PayrollServiceImpl payrollService,
						 TourServiceImpl tourService) {
		this.logService = logService;
		this.companyService = companyService;
		this.departmentService = departmentService;
		this.userService = userService;
		this.officeCodeService = officeCodeService;
		this.allowanceService = allowanceService;
		this.leaveService = leaveService;
		this.commissionService = commissionService;
		this.typeOfCommissionService = typeOfCommissionService;
		this.bonusService = bonusService;
		this.typeOfBonusService = typeOfBonusService;
		this.tenderService = tenderService;
		this.payrollService = payrollService;
		this.tourService = tourService;
	}

	@RequestMapping(value = "/dailyAttendanceByUser", method = RequestMethod.GET)
	public String dailyAttendanceByUser(@RequestParam String userId, @RequestParam String fromDate, Model model)
			throws Exception {
		if (userId == null || fromDate == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		List<AttendanceResponseDTO> dtos = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);
		Date date = sdf.parse(fromDate);
		List<Log> logs = logService.readAllByUserAndDay(Long.parseLong(userId), date);

		for (Log log : logs) {
			dtos.add(attendanceResponseMapper.map(log));
		}

		AttendanceDayTrackDTO dayTrackDTO = logService.countLateAndEarly(Long.parseLong(userId), date);

		Company company = companyService.read(userService.read(Long.parseLong(userId)).getCompany().getId());

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("dayTrackDTO", dayTrackDTO);
		model.addAttribute("attendanceResponse", dtos);
		return "DailyAttendanceByUser";
	}

	@RequestMapping(value = "/monthlyAttendanceByCompanyAndDepartment", method = RequestMethod.GET)
	public String monthlyAttendanceByCompanyAndDepartment(@RequestParam String companyId,
			@RequestParam String departmentId, @RequestParam String month, @RequestParam String year, Model model)
			throws Exception {
		if (companyId == null || departmentId == null || model == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		List<AttendanceMonthlySummaryDTO> dtos = logService.summaryByCompanyDepartmentAndMonth(
				Long.parseLong(companyId), Long.parseLong(departmentId), Integer.parseInt(month),
				Integer.parseInt(year));

		int totalWorkingDays = 0, totalWeekends = 0, totalHolidays = 0;
		if (dtos.size() > 0) {
			totalWorkingDays = dtos.get(0).getTotalWorkingDays();
			totalWeekends = dtos.get(0).getTotalWeekends();
			totalHolidays = dtos.get(0).getTotalHolidays();
		}

		String companyName = "", departmentName = "";
		if (companyService.read(Long.parseLong(companyId)) != null) {
			companyName = companyService.read(Long.parseLong(companyId)).getName();
		}
		if (departmentService.read(Long.parseLong(departmentId)) != null) {
			departmentName = departmentService.read(Long.parseLong(departmentId)).getName();
		}

		Company company = companyService.read(Long.parseLong(companyId));

		System.out.println(BASE_LOCATION);
		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("totalWorkingDays", totalWorkingDays);
		model.addAttribute("totalWeekends", totalWeekends);
		model.addAttribute("totalHolidays", totalHolidays);
		model.addAttribute("monthlySummaryDTO", dtos);
		model.addAttribute("month", DateTimeUtils.getNameOfMonth(Integer.parseInt(month)));
		model.addAttribute("year", year);
		return "MonthlyAttendanceByCompanyAndDepartment";
	}

	@RequestMapping(value = "/monthlyAttendanceSummary", method = RequestMethod.GET)
	public String monthlyAttendanceSummary(@RequestParam String companyId, @RequestParam String departmentId,
			@RequestParam String officeCodeId, @RequestParam String month, @RequestParam String year, Model model)
			throws Exception {
		if (companyId == null || departmentId == null || officeCodeId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		List<AttendanceMonthlySummaryDTO> dtos = logService.summaryByCompanyDepartmentOfficeCodeAndMonth(
				Long.parseLong(companyId), Long.parseLong(departmentId), Long.parseLong(officeCodeId),
				Integer.parseInt(month), Integer.parseInt(year));

		int totalWorkingDays = 0, totalWeekends = 0, totalHolidays = 0;
		if (dtos.size() > 0) {
			totalWorkingDays = dtos.get(0).getTotalWorkingDays();
			totalWeekends = dtos.get(0).getTotalWeekends();
			totalHolidays = dtos.get(0).getTotalHolidays();
		}

		String companyName = "", departmentName = "All", officeCode = "All";
		if (companyService.read(Long.parseLong(companyId)) != null) {
			companyName = companyService.read(Long.parseLong(companyId)).getName();
		}
		if (departmentService.read(Long.parseLong(departmentId)) != null) {
			departmentName = departmentService.read(Long.parseLong(departmentId)).getName();
		}
		if (officeCodeService.read(Long.parseLong(officeCodeId)) != null) {
			officeCode = officeCodeService.readById(Long.parseLong(officeCodeId)).getCode();
		}

		Company company = companyService.read(Long.parseLong(companyId));

		System.out.println(BASE_LOCATION);
		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("officeCode", officeCode);
		model.addAttribute("totalWorkingDays", totalWorkingDays);
		model.addAttribute("totalWeekends", totalWeekends);
		model.addAttribute("totalHolidays", totalHolidays);
		model.addAttribute("monthlySummaryDTO", dtos);
		model.addAttribute("month", DateTimeUtils.getNameOfMonth(Integer.parseInt(month)));
		model.addAttribute("year", year);
		return "MonthlyAttendanceSummary";
	}

	@RequestMapping(value = "/employeeList", method = RequestMethod.GET)
	public String employeeList(@RequestParam String companyId, @RequestParam String departmentId,
			@RequestParam String officeCodeId, Model model) throws Exception {
		if (companyId == null || departmentId == null || officeCodeId == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		List<User> dtos = userService.listActiveUsers(Long.parseLong(companyId), Long.parseLong(departmentId),
				Long.parseLong(officeCodeId));

		List<UserListSerialised> users = new ArrayList<>();

		int totalEmployees = 0;
		if (dtos.size() > 0) {
			totalEmployees = dtos.size();

			int i = 1;

			for (User user : dtos) {
				users.add(new UserListSerialised(i, user));
				i++;
			}
		}

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		String companyName = "", departmentName = "", officeCode = "";
		if (companyService.read(Long.parseLong(companyId)) != null) {
			companyName = companyService.read(Long.parseLong(companyId)).getName();
		}
		if (departmentService.read(Long.parseLong(departmentId)) != null) {
			departmentName = departmentService.read(Long.parseLong(departmentId)).getName();
		}
		if (officeCodeService.read(Long.parseLong(officeCodeId)) != null) {
			officeCode = officeCodeService.readById(Long.parseLong(officeCodeId)).getCode();
		}

		Company company = companyService.read(Long.parseLong(companyId));

		System.out.println(BASE_LOCATION);
		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("officeCode", officeCode);
		model.addAttribute("totalEmployees", totalEmployees);
		model.addAttribute("currentDate", date);
		model.addAttribute("employeeList", users);
		return "EmployeeList";
	}

	@RequestMapping(value = "/blankAllowanceForm", method = RequestMethod.GET)
	public String blankAllowanceForm(@RequestParam Long userId, @RequestParam Integer month, @RequestParam Integer year,
			Model model) throws Exception {
		if (userId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null values found!");
		}

		User user = userService.read(userId);
		if (user == null) {
			throw new EntityNotFoundException("No users with id: " + userId);
		}

		Company company = user.getCompany();

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		Calendar calStart = Calendar.getInstance();
		calStart = DateTimeUtils.getStartOfMonth(month, year);
		Calendar calEnd = Calendar.getInstance();
		calEnd = DateTimeUtils.getEndOfMonth(month, year);

		List<AllowanceListObjectDTO> allowances = new ArrayList<>();

		while (calStart.before(calEnd)) {
			AllowanceListObjectDTO allowance = new AllowanceListObjectDTO();
			allowance.setDayOfMonth(calStart.get(Calendar.DAY_OF_MONTH));
			allowance.setDestination("   ");

			allowances.add(allowance);

			calStart.add(Calendar.DATE, 1);
		}

		System.out.println(BASE_LOCATION);
		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("user", user);
		model.addAttribute("company", company);
		model.addAttribute("dateOfCreation", date);
		model.addAttribute("allowances", allowances);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		return "BlankAllowanceForm";
	}

	@RequestMapping(value = "/dailyAttendanceSummary", method = RequestMethod.GET)
	public String dailyAttendanceSummary(@RequestParam Long companyId, @RequestParam Long departmentId,
			@RequestParam Long officeCodeId, @RequestParam String fromDate, Model model) throws Exception {
		if (companyId == null || departmentId == null || officeCodeId == null || fromDate == null) {
			throw new EntityNotFoundException("Null values found!");
		}

		Company company = companyService.read(companyId);
		if (company == null) {
			throw new EntityNotFoundException("No companies with id: " + companyId);
		}

		String companyName = "", departmentName = "All", officeCode = "All";
		if (companyService.read(companyId) != null) {
			companyName = companyService.read(companyId).getName();
		}
		if (departmentService.read(departmentId) != null) {
			departmentName = departmentService.read(departmentId).getName();
		}
		if (officeCodeService.read(officeCodeId) != null) {
			officeCode = officeCodeService.readById(officeCodeId).getCode();
		}

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);
		Date date = sdf.parse(fromDate);

		AttendanceDailySummaryDTO dto = logService.getAttendanceSummaryByDate(companyId, departmentId, officeCodeId,
				date);

		System.out.println(BASE_LOCATION);
		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("officeCode", officeCode);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("absent", dto.getAbsent());
		model.addAttribute("late", dto.getIsLate());
		model.addAttribute("leftEarly", dto.getLeftEarly());
		model.addAttribute("present", dto.getPresent());
		return "DailyAttendanceSummary";
	}

	@RequestMapping(value = "/monthlyAttendanceByUser", method = RequestMethod.GET)
	public String monthlyAttendanceByUser(@RequestParam Long userId, @RequestParam Integer month,
			@RequestParam Integer year, Model model) throws Exception {
		if (userId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		User user = userService.read(userId);
		if (user == null) {
			throw new EntityNotFoundException("No users with id: " + userId);
		}

		List<AttendanceDayTrackDTO> dtos = new ArrayList<>();
		dtos = logService.readAllByUserAndMonth(userId, month, year);

		Company company = companyService.read(userService.read(userId).getCompany().getId());

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("user", user);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("attendanceResponse", dtos);
		return "MonthlyAttendanceByUser";
	}

	@RequestMapping(value = "/monthlyAllowanceByUser", method = RequestMethod.GET)
	public String generateMonthlyAllowanceByUser(@RequestParam Long userId, @RequestParam Integer month,
			@RequestParam Integer year, Model model) throws Exception {
		if (userId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null values found!");
		}

		User user = userService.read(userId);
		if (user == null) {
			throw new EntityNotFoundException("No users with id: " + userId);
		}

		Company company = user.getCompany();

		AllowanceMonthlyDTO dto = allowanceService.getMonthlyAllowanceSummaryByUser(userId, month, year);

		System.out.println(BASE_LOCATION);
		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("user", user);
		model.addAttribute("company", company);
		model.addAttribute("allowances", dto);
		model.addAttribute("month", month);
		model.addAttribute("year", year);

		/* Done By Tarhib */

		/* Done By Tarhib */
		/* Done By Tarhib */

		
		
		Calendar calendar = Calendar.getInstance();

		
		int date = 1;
		calendar.set(year, month-1, date);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		

		List<MyAllowance> myAllowancesSummary = new ArrayList<MyAllowance>(32);
		// modelAndView.addObject("employee",
		// empService.getSingleEmployeeDetails(userid));
		MyAllowance temp = new MyAllowance();
		temp.setMyallowanceDay(null);
		temp.setMyofficeHour(null);

		for (int i = 0; i < maxDay; i++) {
			myAllowancesSummary.add(i, temp);
		}

		List<OfficeHourDateDTO> officeHoursFromServer = dto.getOfficeHours();

		List<AllowanceListObjectDTO> allowanceSummaryListFromServer = dto.getAllowances();

		List<LeaveSummaryDTO> leaveSummaryListFromServer = dto.getLeaves();

		List<TaskResponseDTO> holidayListFromServer = dto.getHolidays();

		for (OfficeHourDateDTO officehours : officeHoursFromServer) {
			MyAllowance temp1 = new MyAllowance();
			temp1.setMyallowanceDay(null);

			temp1.setMyleaves(null);
			temp1.setMyholiday(null);
			temp1.setMyofficeHour(officehours);
			temp1.setColorcode("weekday");
			myAllowancesSummary.set(officehours.getDayNumber() - 1, temp1);

		}

		for (TaskResponseDTO holidayDetails : holidayListFromServer) {

			MyAllowance temp4 = new MyAllowance();
			temp4.setMyallowanceDay(null);
			temp4.setMyofficeHour(null);
			temp4.setMyleaves(null);
			temp4.setColorcode("holiday");
			temp4.setMyholiday(holidayDetails);

			myAllowancesSummary.set(holidayDetails.getDayNumber() - 1, temp4);

		}

		for (LeaveSummaryDTO leaveSummaryDto : leaveSummaryListFromServer) {
			MyAllowance temp3 = new MyAllowance();
			temp3.setMyallowanceDay(null);
			temp3.setMyofficeHour(null);
			temp3.setMyholiday(null);
			temp3.setColorcode("leave");
			temp3.setMyleaves(leaveSummaryDto);

			myAllowancesSummary.set(leaveSummaryDto.getDayNumber(), temp3);
			System.out.println(myAllowancesSummary.toString());
		}
		double totalSum = 0;
		for (AllowanceListObjectDTO allowanceSummaryDto : allowanceSummaryListFromServer) {
			MyAllowance temp2 = new MyAllowance();

			temp2.setMyofficeHour(null);
			temp2.setMyleaves(null);
			temp2.setMyholiday(null);
			temp2.setMyallowanceDay(allowanceSummaryDto);
			
			
			MyAllowance getAllowance =	myAllowancesSummary.get(allowanceSummaryDto.getDayOfMonth() - 1);
			if(null==getAllowance.getColorcode())
			{
				temp2.setColorcode("holidayweekleave");
			}
			
			
			myAllowancesSummary.set(allowanceSummaryDto.getDayOfMonth() - 1, temp2);
			System.out.println(allowanceSummaryDto.toString());

		}

		model.addAttribute("myAllowancesSummary", myAllowancesSummary);
		//modelAndView.addObject("myAllowancesSummary", myAllowancesSummary);

		/* Done By Tarhib */

		/* Done By Tarhib */
		/* Done By Tarhib */

		return "MonthlyAllowanceByUser";

	}

	@RequestMapping(value = "/monthlyLeaveByUser", method = RequestMethod.GET)
	public String monthlyLeaveByUser(@RequestParam Long userId, @RequestParam Integer month, @RequestParam Integer year,
			Model model) throws Exception {
		if (userId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		User user = userService.read(userId);
		if (user == null) {
			throw new EntityNotFoundException("No users with id: " + userId);
		}

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		List<LeaveSummaryDTO> dtos = new ArrayList<>();
		dtos = leaveService.getAllMonthlyLeaveSummary(userId, year, month);

		LeaveUserSummaryDTO leaveSummary = leaveService.getUserSummary(userId, year);

		Company company = companyService.read(userService.read(userId).getCompany().getId());

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("user", user);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("leaveResponse", dtos);
		model.addAttribute("leaveSummary", leaveSummary);
		model.addAttribute("dateCreated", date);
		return "MonthlyLeaveByUser";
	}

	@RequestMapping(value = "/yearlyLeaveByCompany", method = RequestMethod.GET)
	public String yearlyLeaveByCompany(@RequestParam Long companyId, @RequestParam Long departmentId,
			@RequestParam Long officeCodeId, @RequestParam Integer year, Model model) throws Exception {
		if (companyId == null || departmentId == null || officeCodeId == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		Company company = companyService.read(companyId);
		if (company == null) {
			throw new EntityNotFoundException("No companies with id: " + companyId);
		}

		String companyName = "", departmentName = "All", officeCode = "All";
		if (companyService.read(companyId) != null) {
			companyName = companyService.read(companyId).getName();
		}
		if (departmentService.read(departmentId) != null) {
			departmentName = departmentService.read(departmentId).getName();
		}
		if (officeCodeService.read(officeCodeId) != null) {
			officeCode = officeCodeService.readById(officeCodeId).getCode();
		}

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		List<LeaveUserSummaryDTO> dtos = new ArrayList<>();
		dtos = leaveService.getYearlySummaryByCompany(companyId, departmentId, officeCodeId, year);

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("officeCode", officeCode);
		model.addAttribute("year", year);
		model.addAttribute("leaveResponse", dtos);
		model.addAttribute("dateCreated", date);
		return "YearlyLeaveByCompany";
	}

	@RequestMapping(value = "/monthlyLeaveByCompany", method = RequestMethod.GET)
	public String monthlyLeaveByCompany(@RequestParam Long companyId, @RequestParam Long departmentId,
			@RequestParam Long officeCodeId, @RequestParam Integer month, @RequestParam Integer year, Model model)
			throws Exception {
		if (companyId == null || departmentId == null || officeCodeId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		Company company = companyService.read(companyId);
		if (company == null) {
			throw new EntityNotFoundException("No companies with id: " + companyId);
		}

		String companyName = "", departmentName = "All", officeCode = "All";
		if (companyService.read(companyId) != null) {
			companyName = companyService.read(companyId).getName();
		}
		if (departmentService.read(departmentId) != null) {
			departmentName = departmentService.read(departmentId).getName();
		}
		if (officeCodeService.read(officeCodeId) != null) {
			officeCode = officeCodeService.readById(officeCodeId).getCode();
		}

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		List<LeaveMonthlySummaryDTO> dtos = new ArrayList<>();
		dtos = leaveService.getMonthlyLeaveSummaryForCompany(companyId, departmentId, officeCodeId, month, year);

		List<LeaveMonthlySummaryListDTO> summary = new ArrayList<>();

		if (dtos != null && dtos.size() > 0) {
			for (LeaveMonthlySummaryDTO dto : dtos) {
				summary.add(new LeaveMonthlySummaryListDTO(dto.getUser(), dto.getLeaves().size()));
			}
		}

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("officeCode", officeCode);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("leaveResponse", summary);
		model.addAttribute("dateCreated", date);
		return "MonthlyLeaveByCompany";
	}

    @RequestMapping(value = "/yearlyCommissionSummary", method = RequestMethod.GET)
    public String yearlyCommissionByCategoryTypeAndDepartment(@RequestParam Long companyId,
                                                              @RequestParam String department,
                                                              @RequestParam Long officeCodeId,
                                                              @RequestParam Long typeId,
                                                              @RequestParam Integer year,
                                                              @RequestParam String category,
                                                              Model model) throws Exception {
        if (companyId == null || department == null || officeCodeId == null || typeId == null || year == null || category == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        String companyName = "", departmentName = "All", officeCode = "All", typeOfCommission = "";
        if (companyService.read(companyId) != null) {
            companyName = companyService.read(companyId).getName();
        }

        departmentName = department;

        if (officeCodeService.read(officeCodeId) != null) {
            officeCode = officeCodeService.readById(officeCodeId).getCode();
        }
        if (typeOfCommissionService.read(typeId) != null) {
            typeOfCommission = typeOfCommissionService.read(typeId).getName();
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        date = simpleDateFormat.parse(simpleDateFormat.format(date));

        List<CommissionResponseDTO> dtos = new ArrayList<>();
        dtos = commissionService.generateReportForYearByCompanyDepartmentOfficeCodeTypeAndCategory(companyId,
                department, officeCodeId, typeId, year, category);

        Double totalCommission = (double) 0;

        if (dtos != null && dtos.size() > 0) {
            for (CommissionResponseDTO dto : dtos) {
                switch (category) {
                    case Constants.CommissionCategory.LOCAL:
                        totalCommission += dto.getLocalCommission();
                        break;
                    case Constants.CommissionCategory.FOREIGN:
                        totalCommission += dto.getForeignCommission();
                        break;
                }
            }
        }

        model.addAttribute("base_location", BASE_LOCATION);
        model.addAttribute("company", company);
        model.addAttribute("companyName", companyName);
        model.addAttribute("departmentName", departmentName);
        model.addAttribute("officeCode", officeCode);
        model.addAttribute("category", category);
        model.addAttribute("year", year);
        model.addAttribute("commissionResponse", dtos);
        model.addAttribute("totalCommission", totalCommission);
        model.addAttribute("dateCreated", date);
        model.addAttribute("typeOfCommission", typeOfCommission);
        return "YearlyCommissionByCompany";
    }

	@RequestMapping(value = "/yearlyBonusSummary", method = RequestMethod.GET)
	public String yearlyBonusByTypeAndDepartment(@RequestParam Long companyId,
                                                 @RequestParam Long departmentId,
                                                 @RequestParam Long officeCodeId,
                                                 @RequestParam Long typeId,
                                                 @RequestParam Integer year,
                                                 Model model) throws Exception {
		if (companyId == null || departmentId == null || officeCodeId == null || typeId == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		Company company = companyService.read(companyId);
		if (company == null) {
			throw new EntityNotFoundException("No companies with id: " + companyId);
		}

		String companyName = "", departmentName = "All", officeCode = "All", typeOfBonus = "";
		if (companyService.read(companyId) != null) {
			companyName = companyService.read(companyId).getName();
		}

        if (departmentService.read(departmentId) != null) {
            departmentName = departmentService.read(departmentId).getName();
        }

		if (officeCodeService.read(officeCodeId) != null) {
			officeCode = officeCodeService.readById(officeCodeId).getCode();
		}
		if (typeOfBonusService.read(typeId) != null) {
            typeOfBonus = typeOfBonusService.read(typeId).getName();
		}

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		List<BonusResponseDTO> dtos = new ArrayList<>();
		dtos = bonusService.generateReportForYearByCompanyDepartmentOfficeCodeTypeAndCategory(companyId,
				departmentId, officeCodeId, typeId, year);

		Double totalBonus = (double) 0;

		if (dtos != null && dtos.size() > 0) {
			for (BonusResponseDTO dto : dtos) {
                totalBonus += dto.getAmount();
			}
		}

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("officeCode", officeCode);
		model.addAttribute("year", year);
		model.addAttribute("bonusResponse", dtos);
		model.addAttribute("totalBonus", totalBonus);
		model.addAttribute("dateCreated", date);
		model.addAttribute("typeOfBonus", typeOfBonus);
		return "YearlyBonusByCompany";
	}

    @RequestMapping(value = "/yearlyEarnedLeaveByCompany", method = RequestMethod.GET)
    public String yearlyEarnedLeaveByCompany(@RequestParam Long companyId, @RequestParam Long departmentId,
                                       @RequestParam Long officeCodeId, @RequestParam Integer year, Model model) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        String companyName = "", departmentName = "All", officeCode = "All";
        if (companyService.read(companyId) != null) {
            companyName = companyService.read(companyId).getName();
        }
        if (departmentService.read(departmentId) != null) {
            departmentName = departmentService.read(departmentId).getName();
        }
        if (officeCodeService.read(officeCodeId) != null) {
            officeCode = officeCodeService.readById(officeCodeId).getCode();
        }

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        date = simpleDateFormat.parse(simpleDateFormat.format(date));

        List<EarnedLeaveDTO> dtos = new ArrayList<>();
        dtos = leaveService.getYearlyEarnedLeaveSummaryByCompany(companyId, departmentId, officeCodeId, year);

        model.addAttribute("base_location", BASE_LOCATION);
        model.addAttribute("company", company);
        model.addAttribute("companyName", companyName);
        model.addAttribute("departmentName", departmentName);
        model.addAttribute("officeCode", officeCode);
        model.addAttribute("year", year);
        model.addAttribute("leaveResponse", dtos);
        model.addAttribute("dateCreated", date);
        return "YearlyEarnedLeaveByCompany";
    }

    @RequestMapping(value = "/allTenders", method = RequestMethod.GET)
    public String allTenders(Model model) throws Exception {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        date = simpleDateFormat.parse(simpleDateFormat.format(date));

        List<TenderResponseDTO> dtos = new ArrayList<>();
        dtos = tenderService.readAll();

        int totalTenders = 0;
        if (dtos != null && dtos.size() > 0)
            totalTenders = dtos.size();

        model.addAttribute("base_location", BASE_LOCATION);
        model.addAttribute("tenderResponse", dtos);
        model.addAttribute("dateCreated", date);
        model.addAttribute("totalTenders", totalTenders);
        return "TenderReport";
    }

    @RequestMapping(value = "/expiredTenders", method = RequestMethod.GET)
    public String tendersNearingExpiry(Model model) throws Exception {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        date = simpleDateFormat.parse(simpleDateFormat.format(date));

        List<TenderResponseDTO> dtos = new ArrayList<>();
        dtos = tenderService.readAllNearingExpiry(date);

        int totalTenders = 0;
        if (dtos != null && dtos.size() > 0)
            totalTenders = dtos.size();

        model.addAttribute("base_location", BASE_LOCATION);
        model.addAttribute("tenderResponse", dtos);
        model.addAttribute("dateCreated", date);
        model.addAttribute("totalTenders", totalTenders);
        return "TenderReport";
    }

	@RequestMapping(value = "/monthlyPayrollByUser", method = RequestMethod.GET)
	public String monthlyPayrollByUser(@RequestParam Long userId, @RequestParam Integer month, @RequestParam Integer year,
									 Model model) throws Exception {
		if (userId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		User user = userService.read(userId);
		if (user == null) {
			throw new EntityNotFoundException("No users with id: " + userId);
		}
		UserSmallResponseDTO paidToUser = userSmallResponseMapper.map(user);

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		UserPayrollDTO dto = payrollService.readPayrollForUser(userId, month, year);
		System.out.println(dto);

		Company company = companyService.read(userService.read(userId).getCompany().getId());

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("user", user);
		model.addAttribute("paidToUser", paidToUser);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("dto", dto);
		model.addAttribute("dateCreated", date);
		return "MonthlyPayrollByUser";
	}

	@RequestMapping(value = "/monthlyPayrollByCompany", method = RequestMethod.GET)
	public String monthlyPayrollByCompany(@RequestParam Long companyId,
										  @RequestParam Long departmentId,
										  @RequestParam Long officeCodeId,
										  @RequestParam Integer month,
										  @RequestParam Integer year,
										  Model model) throws Exception {
		if (companyId == null || departmentId == null || officeCodeId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		Company company = companyService.read(companyId);
		if (company == null) {
			throw new EntityNotFoundException("No companies with id: " + companyId);
		}

		String companyName = "", departmentName = "All", officeCode = "All";
		if (companyService.read(companyId) != null) {
			companyName = companyService.read(companyId).getName();
		}

		if (departmentService.read(departmentId) != null) {
			departmentName = departmentService.read(departmentId).getName();
		}

		if (officeCodeService.read(officeCodeId) != null) {
			officeCode = officeCodeService.readById(officeCodeId).getCode();
		}

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		List<UserPayrollListDTO> dtos = new ArrayList<>();
		dtos = payrollService.readAllUserPayrollByMonth(companyId, departmentId, officeCodeId, month, year);

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("departmentName", departmentName);
		model.addAttribute("officeCode", officeCode);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("payrollResponse", dtos);
		model.addAttribute("dateCreated", date);
		return "MonthlyPayrollByCompany";
	}

	@RequestMapping(value = "/yearlyToursByUser", method = RequestMethod.GET)
	public String yearlyToursByUser(@RequestParam Long userId, @RequestParam Integer year,
									   Model model) throws Exception {
		if (userId == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		User user = userService.read(userId);
		if (user == null) {
			throw new EntityNotFoundException("No users with id: " + userId);
		}
		UserSmallResponseDTO userDTO = userSmallResponseMapper.map(user);

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		List<TourResponseDTO> dtos = new ArrayList<>();
		dtos = tourService.readForUser(userId, year);

		Company company = companyService.read(userService.read(userId).getCompany().getId());

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("user", user);
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("year", year);
		model.addAttribute("tours", dtos);
		model.addAttribute("dateCreated", date);
		return "YearlyToursByUser";
	}

	@RequestMapping(value = "/monthlyApprovedToursByCompany", method = RequestMethod.GET)
	public String monthlyApprovedToursByCompany(@RequestParam Long companyId,
												@RequestParam Integer month,
												@RequestParam Integer year,
												Model model) throws Exception {
		if (companyId == null || month == null || year == null) {
			throw new EntityNotFoundException("Null value found!");
		}

		Company company = companyService.read(companyId);
		if (company == null) {
			throw new EntityNotFoundException("No companies with id: " + companyId);
		}

		String companyName = "";
		if (companyService.read(companyId) != null) {
			companyName = companyService.read(companyId).getName();
		}

		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
		date = simpleDateFormat.parse(simpleDateFormat.format(date));

		List<TourResponseDTO> dtos = new ArrayList<>();
		dtos = tourService.readByCompanyAndStatusAndMonth(companyId, Constants.RequestStatus.APPROVED, month, year);

		model.addAttribute("base_location", BASE_LOCATION);
		model.addAttribute("company", company);
		model.addAttribute("companyName", companyName);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("tourResponse", dtos);
		model.addAttribute("dateCreated", date);
		return "MonthlyApprovedToursByCompany";
	}

	@GetMapping("/home")
	public String home() {
		return "index";
	}
}
