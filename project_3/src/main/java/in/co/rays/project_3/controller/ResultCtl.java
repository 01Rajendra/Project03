package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ResultDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ResultModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * result functionality controller.to perform add,delete and update operation
 * 
 * @author Rajendra Negi
 *
 */
@WebServlet(urlPatterns = { "/ctl/ResultCtl" })
public class ResultCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ResultCtl.class);

	// 🔥 validate only your fields
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("resultCode"))) {
			request.setAttribute("resultCode", PropertyReader.getValue("error.require", "Result Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("studentName"))) {
			request.setAttribute("studentName", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("marks"))) {
			request.setAttribute("marks", PropertyReader.getValue("error.require", "Marks"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("resultStatus"))) {
			request.setAttribute("resultStatus", PropertyReader.getValue("error.require", "Result Status"));
			pass = false;
		}

		return pass;
	}

	// 🔥 populate only your DTO fields
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ResultDTO dto = new ResultDTO();

		dto.setId(DataUtility.getLong(request.getParameter("resultId")));
		dto.setResultCode(DataUtility.getString(request.getParameter("resultCode")));
		dto.setStudentName(DataUtility.getString(request.getParameter("studentName")));
		dto.setMarks(DataUtility.getInt(request.getParameter("marks")));
		dto.setResultStatus(DataUtility.getString(request.getParameter("resultStatus")));

		populateBean(dto, request);

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("ResultCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		ResultModelInt model = ModelFactory.getInstance().getResultModel();

		long id = DataUtility.getLong(request.getParameter("resultId"));

		if (id > 0 || op != null) {
			ResultDTO dto = null;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		ResultModelInt model = ModelFactory.getInstance().getResultModel();

		long id = DataUtility.getLong(request.getParameter("resultId"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ResultDTO dto = (ResultDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {

					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Result Code already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Result Code already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ResultDTO dto = (ResultDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.RESULT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.RESULT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.RESULT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("ResultCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.RESULT_VIEW;
	}
}