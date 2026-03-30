package in.co.rays.project_3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author Rajendra Negi
 * 
 * 
 *
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	public HostelModelInt getHostelModel() {

		HostelModelInt hostelModel = (HostelModelInt) modelCache.get("hostelModel");

		if (hostelModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				hostelModel = new HostelModelHibImp();
			}

			if ("JDBC".equals(DATABASE)) {
				hostelModel = new HostelModelJDBCImpl();
			}

			modelCache.put("hostelModel", hostelModel);
		}

		return hostelModel;
	}

	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}

	public ShiftModelInt getShiftModel() {

		ShiftModelInt shiftModel = (ShiftModelInt) modelCache.get("shiftModel");

		if (shiftModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				shiftModel = new ShiftModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				shiftModel = new ShiftModelJDBCImpl();
			}

			modelCache.put("shiftModel", shiftModel);
		}

		return shiftModel;
	}

	public BankModelInt getBankModel() {

		BankModelInt bankModel = (BankModelInt) modelCache.get("bankModel");

		if (bankModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				bankModel = new BankModelHibImp();
			}

			if ("JDBC".equals(DATABASE)) {
				bankModel = new BankModelJDBCImpl();
			}

			modelCache.put("bankModel", bankModel);
		}

		return bankModel;
	}

	public InventoryModelInt getInventoryModel() {

		InventoryModelInt inventoryModel = (InventoryModelInt) modelCache.get("inventoryModel");

		if (inventoryModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				inventoryModel = new InventoryModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				inventoryModel = new InventoryModelJDBCImpl();
			}

			modelCache.put("inventoryModel", inventoryModel);
		}

		return inventoryModel;
	}

	public DispatchModelInt getDispatchModel() {

		DispatchModelInt dispatchModel = (DispatchModelInt) modelCache.get("dispatchModel");

		if (dispatchModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				dispatchModel = new DispatchModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				dispatchModel = new DispatchModelJDBCImpl();
			}

			modelCache.put("dispatchModel", dispatchModel);
		}

		return dispatchModel;
	}

	public BookModelInt getBookModel() {

		BookModelInt bookModel = (BookModelInt) modelCache.get("bookModel");

		if (bookModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				bookModel = new BookModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				bookModel = new BookModelJDBCImpl();
			}

			modelCache.put("bookModel", bookModel);
		}

		return bookModel;
	}

	public HolidayModelInt getHolidayModel() {

		HolidayModelInt holidayModel = (HolidayModelInt) modelCache.get("holidayModel");

		if (holidayModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				holidayModel = new HolidayModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				holidayModel = new HolidayModelJDBCImpl();
			}

			modelCache.put("holidayModel", holidayModel);
		}

		return holidayModel;

	}

	public ComplaintModelInt getComplaintModel() {

		ComplaintModelInt complaintModel = (ComplaintModelInt) modelCache.get("complaintModel");

		if (complaintModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				complaintModel = new ComplaintModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				complaintModel = new ComplaintModelJDBCImpl();
			}

			modelCache.put("complaintModel", complaintModel);
		}

		return complaintModel;
	}

	public ResultModelInt getResultModel() {

		ResultModelInt resultModel = (ResultModelInt) modelCache.get("resultModel");

		if (resultModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				resultModel = new ResultModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				resultModel = new ResultModelJDBCImpl();
			}

			modelCache.put("resultModel", resultModel);
		}

		return resultModel;

	}

	public UploadHistoryModelInt getUploadHistoryModel() {

		UploadHistoryModelInt uploadHistoryModel = (UploadHistoryModelInt) modelCache.get("uploadHistoryModel");

		if (uploadHistoryModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				uploadHistoryModel = new UploadHistoryModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				uploadHistoryModel = new UploadHistoryModelJDBCImpl();
			}

			modelCache.put("uploadHistoryModel", uploadHistoryModel);
		}

		return uploadHistoryModel;

	}

	public SubscriptionPlanModelInt getSubscriptionPlanModel() {

		SubscriptionPlanModelInt subscriptionPlanModel = (SubscriptionPlanModelInt) modelCache
				.get("subscriptionPlanModel");

		if (subscriptionPlanModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				subscriptionPlanModel = new SubscriptionPlanModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				subscriptionPlanModel = new SubscriptionPlanModelJDBCImpl();
			}

			modelCache.put("subscriptionPlanModel", subscriptionPlanModel);
		}

		return subscriptionPlanModel;
	}

	public PhotographerModelInt getPhotographerModel() {

		PhotographerModelInt photographerModel = (PhotographerModelInt) modelCache.get("photographerModel");

		if (photographerModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				photographerModel = (PhotographerModelInt) new PhotographerModelHibImpl();
			}

			if ("JDBC".equals(DATABASE)) {
				photographerModel = (PhotographerModelInt) new PhotographerModelJDBCImpl();
			}

			modelCache.put("photographerModel", photographerModel);
		}

		return photographerModel;
	}

	public BranchManagerModelInt getBranchManagerModel() {

		BranchManagerModelInt branchManagerModel = (BranchManagerModelInt) modelCache.get("branchManagerModel");

		if (branchManagerModel == null) {

			if ("Hibernate".equals(DATABASE)) {
				branchManagerModel = (BranchManagerModelInt) new BranchManagerModelHibImp();
			}

			if ("JDBC".equals(DATABASE)) {
				branchManagerModel = (BranchManagerModelInt) new BranchManagerModelJDBCImpl();
			}

			modelCache.put("branchManagerModel", branchManagerModel);
		}

		return branchManagerModel;

	}
}
