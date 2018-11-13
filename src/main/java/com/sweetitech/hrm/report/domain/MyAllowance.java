package com.sweetitech.hrm.report.domain;

import com.sweetitech.hrm.domain.dto.AllowanceListObjectDTO;
import com.sweetitech.hrm.domain.dto.LeaveSummaryDTO;
import com.sweetitech.hrm.domain.dto.OfficeHourDateDTO;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;

public class MyAllowance {

	private AllowanceListObjectDTO myallowanceDay;
	private OfficeHourDateDTO myofficeHour;
	
	private  LeaveSummaryDTO myleaves;
	private TaskResponseDTO myholiday;
	private String colorcode;
	public AllowanceListObjectDTO getMyallowanceDay() {
		return myallowanceDay;
	}
	public void setMyallowanceDay(AllowanceListObjectDTO myallowanceDay) {
		this.myallowanceDay = myallowanceDay;
	}
	public OfficeHourDateDTO getMyofficeHour() {
		return myofficeHour;
	}
	public void setMyofficeHour(OfficeHourDateDTO myofficeHour) {
		this.myofficeHour = myofficeHour;
	}
	public LeaveSummaryDTO getMyleaves() {
		return myleaves;
	}
	public void setMyleaves(LeaveSummaryDTO myleaves) {
		this.myleaves = myleaves;
	}
	public TaskResponseDTO getMyholiday() {
		return myholiday;
	}
	public void setMyholiday(TaskResponseDTO myholiday) {
		this.myholiday = myholiday;
	}
	
	
	
	public String getColorcode() {
		return colorcode;
	}
	public void setColorcode(String colorcode) {
		this.colorcode = colorcode;
	}
	@Override
	public String toString() {
		return "MyAllowance [myallowanceDay=" + myallowanceDay + ", myofficeHour=" + myofficeHour + ", myleaves="
				+ myleaves + ", myholiday=" + myholiday + ", colorcode=" + colorcode + "]";
	}
	
	
	
	
}
