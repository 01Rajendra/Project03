package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BranchManagerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BranchManagerModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/BranchManagerCtl" })
public class BranchManagerCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BranchManagerCtl.class);

	// VALIDATION
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("managerName"))) {
			request.setAttribute("managerName", PropertyReader.getValue("error.require", "Manager Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("branchName"))) {
			request.setAttribute("branchName", PropertyReader.getValue("error.require", "Branch Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", PropertyReader.getValue("error.require", "Contact Number"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", "Invalid Mobile Number");
			pass = false;
		}

		return pass;
	}

	// POPULATE DTO
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BranchManagerDTO dto = new BranchManagerDTO();

		dto.setManagerId(DataUtility.getLong(request.getParameter("id")));
		dto.setManagerName(DataUtility.getString(request.getParameter("managerName")));
		dto.setBranchName(DataUtility.getString(request.getParameter("branchName")));
		dto.setContactNumber(DataUtility.getString(request.getParameter("contactNumber")));

		populateBean(dto, request);

		return dto;
	}

	// DO GET
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		BranchManagerModelInt model = ModelFactory.getInstance().getBranchManagerModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				BranchManagerDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// DO POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		BranchManagerModelInt model = ModelFactory.getInstance().getBranchManagerModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BranchManagerDTO dto = (BranchManagerDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data updated successfully", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Data saved successfully", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Manager already exists", request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BranchManagerDTO dto = (BranchManagerDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.BRANCH_MANAGER_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BRANCH_MANAGER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BRANCH_MANAGER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	// VIEW
	protected String getView() {
		return ORSView.BRANCH_MANAGER_VIEW;
	}
}