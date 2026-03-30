package in.co.rays.project_3.controller;

/**
 * ORS View Provide Loose Coupling
 * 
 * @author Rajendra Negi
 */
public interface ORSView {

	public String APP_CONTEXT = "/project_3";
	public String PAGE_FOLDER = "/jsp";

	// Views
	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";
	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView404.jsp";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";
	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";

	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";

	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";

	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";

	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";

	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";

	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";

	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";

	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";

	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";

	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";
	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";

	public String HOSTEL_VIEW = PAGE_FOLDER + "/HostelView.jsp";
	public String HOSTEL_LIST_VIEW = PAGE_FOLDER + "/HostelListView.jsp";

	// Controllers
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";

	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";

	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";

	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";

	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";

	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";

	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";

	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";

	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";

	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";

	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";

	public String HOSTEL_CTL = APP_CONTEXT + "/ctl/HostelCtl";
	public String HOSTEL_LIST_CTL = APP_CONTEXT + "/ctl/HostelListCtl";

	// Extra Modules
	public String SHIFT_CTL = APP_CONTEXT + "/ctl/ShiftCtl";
	public String SHIFT_LIST_CTL = APP_CONTEXT + "/ctl/ShiftListCtl";
	public String SHIFT_VIEW = PAGE_FOLDER + "/ShiftView.jsp";
	public String SHIFT_LIST_VIEW = PAGE_FOLDER + "/ShiftListView.jsp";

	public String BANK_CTL = APP_CONTEXT + "/ctl/BankCtl";
	public String BANK_LIST_CTL = APP_CONTEXT + "/ctl/BankListCtl";
	public String BANK_VIEW = PAGE_FOLDER + "/BankView.jsp";
	public String BANK_LIST_VIEW = PAGE_FOLDER + "/BankListView.jsp";

	public String INVENTORY_CTL = APP_CONTEXT + "/ctl/InventoryCtl";
	public String INVENTORY_LIST_CTL = APP_CONTEXT + "/ctl/InventoryListCtl";
	public String INVENTORY_VIEW = PAGE_FOLDER + "/InventoryView.jsp";
	public String INVENTORY_LIST_VIEW = PAGE_FOLDER + "/InventoryListView.jsp";

	public String DISPATCH_CTL = APP_CONTEXT + "/ctl/DispatchCtl";
	public String DISPATCH_LIST_CTL = APP_CONTEXT + "/ctl/DispatchListCtl";
	public String DISPATCH_VIEW = PAGE_FOLDER + "/DispatchView.jsp";
	public String DISPATCH_LIST_VIEW = PAGE_FOLDER + "/DispatchListView.jsp";

	public String BOOK_CTL = APP_CONTEXT + "/ctl/BookCtl";
	public String BOOK_LIST_CTL = APP_CONTEXT + "/ctl/BookListCtl";
	public String BOOK_VIEW = PAGE_FOLDER + "/BookView.jsp";
	public String BOOK_LIST_VIEW = PAGE_FOLDER + "/BookListView.jsp";

	public String HOLIDAY_CTL = APP_CONTEXT + "/ctl/HolidayCtl";
	public String HOLIDAY_LIST_CTL = APP_CONTEXT + "/ctl/HolidayListCtl";
	public String HOLIDAY_VIEW = PAGE_FOLDER + "/HolidayView.jsp";
	public String HOLIDAY_LIST_VIEW = PAGE_FOLDER + "/HolidayListView.jsp";

	public String COMPLAINT_CTL = APP_CONTEXT + "/ctl/ComplaintCtl";
	public String COMPLAINT_LIST_CTL = APP_CONTEXT + "/ctl/ComplaintListCtl";
	public String COMPLAINT_VIEW = PAGE_FOLDER + "/ComplaintView.jsp";
	public String COMPLAINT_LIST_VIEW = PAGE_FOLDER + "/ComplaintListView.jsp";

	public String RESULT_CTL = APP_CONTEXT + "/ctl/ResultCtl";
	public String RESULT_LIST_CTL = APP_CONTEXT + "/ctl/ResultListCtl";
	public String RESULT_VIEW = PAGE_FOLDER + "/ResultView.jsp";
	public String RESULT_LIST_VIEW = PAGE_FOLDER + "/ResultListView.jsp";

	public String UPLOADHISTORY_CTL = APP_CONTEXT + "/ctl/UploadHistoryCtl";
	public String UPLOADHISTORY_LIST_CTL = APP_CONTEXT + "/ctl/UploadHistoryListCtl";
	public String UPLOADHISTORY_VIEW = PAGE_FOLDER + "/UploadHistoryView.jsp";
	public String UPLOADHISTORY_LIST_VIEW = PAGE_FOLDER + "/UploadHistoryListView.jsp";

	public String APPROVAL_CODE_CTL = APP_CONTEXT + "/ctl/ApprovalCodeCtl";
	public String APPROVAL_CODE_LIST_CTL = APP_CONTEXT + "/ctl/ApprovalCodeListCtl";
	public String APPROVAL_CODE_VIEW = PAGE_FOLDER + "/ApprovalCodeView.jsp";
	public String APPROVAL_CODE_LIST_VIEW = PAGE_FOLDER + "/ApprovalCodeListView.jsp";

	public String SUBSCRIPTION_PLAN_CTL = APP_CONTEXT + "/ctl/SubscriptionPlanCtl";
	public String SUBSCRIPTION_PLAN_LIST_CTL = APP_CONTEXT + "/ctl/SubscriptionPlanListCtl";
	public String SUBSCRIPTION_PLAN_VIEW = PAGE_FOLDER + "/SubscriptionPlanView.jsp";
	public String SUBSCRIPTION_PLAN_LIST_VIEW = PAGE_FOLDER + "/SubscriptionPlanListView.jsp";

	public String PHOTOGRAPHER_CTL = APP_CONTEXT + "/ctl/PhotographerCtl";
	public String PHOTOGRAPHER_LIST_CTL = APP_CONTEXT + "/ctl/PhotographerListCtl";
	public String PHOTOGRAPHER_LIST_VIEW = PAGE_FOLDER + "/PhotographerListView.jsp";
	public String PHOTOGRAPHER_VIEW = PAGE_FOLDER + "/PhotographerView.jsp";

	public String BRANCH_MANAGER_CTL = APP_CONTEXT + "/ctl/BranchManagerCtl";
	public String BRANCH_MANAGER_LIST_CTL = APP_CONTEXT + "/ctl/BranchManagerListCtl";
	public String BRANCH_MANAGER_LIST_VIEW = PAGE_FOLDER + "/BranchManagerListView.jsp";
	public String BRANCH_MANAGER_VIEW = PAGE_FOLDER + "/BranchManagerView.jsp";
}