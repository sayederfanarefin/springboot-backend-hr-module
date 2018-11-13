package com.sweetitech.hrm.report.service;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.ztomic.wkhtmltopdf.WkHtmlToPdf;
import com.ztomic.wkhtmltopdf.argument.Argument;
import com.ztomic.wkhtmltopdf.argument.Option;
import com.ztomic.wkhtmltopdf.source.Source;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ztomic.wkhtmltopdf.argument.Option.PageOption.EnableJavascript;

@Service
public class PDFService {

//    public static final String DOWNLOAD_PATH = "/opt/tomcat/webapps/tmp/downloads/"; // for droplet
    public static final String DOWNLOAD_PATH = "tmp/downloads/"; // for mac
    public static final String ZIP_PATH = "/tmp/zip_files";
    public static final String FILE_NAME = "report";
    public static final String SERVER_REPORT_URL = "/report/html";
    public static final String DOWNLOAD_FILE_PATH = DOWNLOAD_PATH + FILE_NAME;
    public static final String ZIPFILE = "/tmp/zip_files/cmed_report.zip";
    public static final String ZIPFILE_NAME = "cmed_report.zip";
    public static final String SRCDIR = "/tmp/downloads";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd-HH";

    public static WkHtmlToPdf initialiseWkHtmlToPdf() {
        WkHtmlToPdf pdf = new WkHtmlToPdf(); // for mac
//        WkHtmlToPdf pdf = new WkHtmlToPdf("/usr/bin/wkhtmltopdf.sh"); // for server

        return pdf;
    }

    public static WkHtmlToPdf initialiseWkHtmlToPdfLandscape() {
        WkHtmlToPdf pdf = new WkHtmlToPdf("-O landscape");

        return pdf;
    }

    public static String getServerAbsolutePath(String requestPath) throws MalformedURLException {
        String URL = getBaseURL() + requestPath;
        //System.out.println(URL);
        return URL;
    }

    public static String getBaseURL() throws MalformedURLException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String baseUrl = "";
        if (request != null) {
            // handle proxy forward
            String scheme = request.getScheme();
            if (request.getHeader("x-forwarded-proto") != null) {
                scheme = request.getHeader("x-forwarded-proto");
            }

            Integer serverPort = request.getServerPort();
            if ((serverPort == 80) || (serverPort == 443)) {
                // No need to add the server port for standard HTTP and HTTPS ports, the scheme will help determine it.
                baseUrl = String.format("%s://%s%s", scheme, request.getServerName(), request.getContextPath());
            } else {
                baseUrl = String.format("%s://%s:%d%s", scheme, request.getServerName(), serverPort, request.getContextPath());
            }
        }

        return baseUrl;
    }

    public static String generateDailyAttendanceByUserReport(String SPECIFIC_PATH, Long userId, LeaveCountDTO dto) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);
            String date = sdf.format(dto.getFromDate());
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?userId=" + userId + "&fromDate=" +  date);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-dailyAttendanceByUser" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateMonthlyAttendanceByCompanyAndDepartmentReport(String SPECIFIC_PATH,
                                                                               Long companyId,
                                                                               Long departmentId,
                                                                               Integer month,
                                                                               Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId
                    + "&departmentId=" +  departmentId
                    + "&month=" + month
                    + "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyAttendanceByCompanyAndDepartment" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateMonthlyAttendance(String SPECIFIC_PATH,
                                                   Long companyId,
                                                   Long departmentId,
                                                   Long officeCodeId,
                                                   Integer month,
                                                   Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId
                    + "&departmentId=" +  departmentId
                    + "&officeCodeId=" + officeCodeId
                    + "&month=" + month
                    + "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyAttendanceSummary" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateEmployeeList(String SPECIFIC_PATH,
                                              Long companyId,
                                              Long departmentId,
                                              Long officeCodeId) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        pdf.addArguments(Argument.from(Option.GlobalOption.Orientation, "Landscape"));

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId
                    + "&departmentId=" +  departmentId
                    + "&officeCodeId=" + officeCodeId);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-employeeList" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateBlankAllowanceForm(String SPECIFIC_PATH,
                                                    Long userId,
                                                    Integer month,
                                                    Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?userId=" + userId
                    + "&month=" +  month
                    + "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-blankAllowanceForm" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateDailyAttendanceByCompanyReport(String SPECIFIC_PATH,
                                                                Long companyId,
                                                                Long departmentId,
                                                                Long officeCodeId,
                                                                LeaveCountDTO dto) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);
            String date = sdf.format(dto.getFromDate());
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId
                    + "&departmentId=" + departmentId
                    + "&officeCodeId=" + officeCodeId
                    + "&fromDate=" +  date);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-dailyAttendanceSummary" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateMonthlyAttendanceByUserReport(String SPECIFIC_PATH, Long userId, Integer month, Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?userId=" + userId + "&month=" +  month + "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyAttendanceByUser" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateMonthlyAllowanceByUserReport(String SPECIFIC_PATH,
                                                              Long userId,
                                                              Integer month,
                                                              Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?userId=" + userId
                    + "&month=" +  month
                    + "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyAllowanceByUser" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateMonthlyLeaveByUserReport(String SPECIFIC_PATH, Long userId, Integer month, Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?userId=" + userId + "&month=" +  month + "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyLeaveByUser" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateYearlyLeaveByCompanyReport(String SPECIFIC_PATH,
                                                            Long companyId,
                                                            Long departmentId,
                                                            Long officeCodeId,
                                                            Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId +
                    "&departmentId=" +  departmentId +
                    "&officeCodeId=" + officeCodeId +
                    "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-yearlyLeaveByCompany" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateMonthlyLeaveByCompanyReport(String SPECIFIC_PATH,
                                                             Long companyId,
                                                             Long departmentId,
                                                             Long officeCodeId,
                                                             Integer month,
                                                             Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId +
                    "&departmentId=" +  departmentId +
                    "&officeCodeId=" + officeCodeId +
                    "&month=" + month +
                    "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyLeaveByCompany" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateYearlyByCategoryTypeAndDepartment(String SPECIFIC_PATH,
                                                                   Long companyId,
                                                                   String department,
                                                                   Long officeCodeId,
                                                                   Long typeId,
                                                                   Integer year,
                                                                   String category) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId +
                    "&department=" +  department +
                    "&officeCodeId=" + officeCodeId +
                    "&typeId=" + typeId +
                    "&year=" + year +
                    "&category=" + category);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-yearlyCommissionSummary" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateYearlyBonusByTypeAndDepartment(String SPECIFIC_PATH,
                                                                Long companyId,
                                                                Long departmentId,
                                                                Long officeCodeId,
                                                                Long typeId,
                                                                Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId +
                    "&departmentId=" +  departmentId +
                    "&officeCodeId=" + officeCodeId +
                    "&typeId=" + typeId +
                    "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-yearlyBonusSummary" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateYearlyEarnedLeaveByCompanyReport(String SPECIFIC_PATH,
                                                                  Long companyId,
                                                                  Long departmentId,
                                                                  Long officeCodeId,
                                                                  Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId +
                    "&departmentId=" +  departmentId +
                    "&officeCodeId=" + officeCodeId +
                    "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-yearlyEarnedLeaveByCompany" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateAllTenders(String SPECIFIC_PATH) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        pdf.addArguments(Argument.from(Option.GlobalOption.Orientation, "Landscape"));

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-yearlyEarnedLeaveByCompany" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generatePayrollForUser(String SPECIFIC_PATH,
                                                Long userId,
                                                Integer month,
                                                Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?userId=" + userId +
                    "&month=" +  month +
                    "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyPayrollByUser" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generatePayrollByCompany(String SPECIFIC_PATH,
                                                  Long companyId,
                                                  Long departmentId,
                                                  Long officeCodeId,
                                                  Integer month,
                                                  Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId +
                    "&departmentId=" +  departmentId +
                    "&officeCodeId=" +  officeCodeId +
                    "&month=" +  month +
                    "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyPayrollByCompany" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generateYearlyTourReportByUser(String SPECIFIC_PATH,
                                                        Long userId,
                                                        Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?userId=" + userId +
                    "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-yearlyToursByUser" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

    public static String generatePayrollByCompany(String SPECIFIC_PATH,
                                                  Long companyId,
                                                  Integer month,
                                                  Integer year) {
        WkHtmlToPdf pdf = initialiseWkHtmlToPdf();

        try {
            String path = getServerAbsolutePath(SERVER_REPORT_URL
                    + SPECIFIC_PATH + "?companyId=" + companyId +
                    "&month=" +  month +
                    "&year=" + year);
            pdf.addSources(Source.fromUrl(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String downloadPath = DOWNLOAD_FILE_PATH + "-monthlyApprovedToursByCompany" + ".pdf";

        pdf.addArguments(
                Argument.from(EnableJavascript));

        // Save the PDF
        File file = new File(downloadPath);

        System.out.println("Directory status: " + file.exists() + " " + file.isDirectory());
        System.out.println(downloadPath);

        try {
            pdf.save(Paths.get(downloadPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadPath;
    }

}
