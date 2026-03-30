package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ComplaintDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ComplaintModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ComplaintCtl" })
public class ComplaintCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ComplaintCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ComplaintCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("complaintCode"))) {
			request.setAttribute("complaintCode",
					PropertyReader.getValue("error.require", "Complaint Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("customerName"))) {
			request.setAttribute("customerName",
					PropertyReader.getValue("error.require", "Customer Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("complaintType"))) {
			request.setAttribute("complaintType",
					PropertyReader.getValue("error.require", "Complaint Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("complaintStatus"))) {
			request.setAttribute("complaintStatus",
					PropertyReader.getValue("error.require", "Complaint Status"));
			pass = false;
		}

		log.debug("ComplaintCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		ComplaintDTO dto = new ComplaintDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setComplaintId(DataUtility.getLong(request.getParameter("complaintId")));
		dto.setComplaintCode(DataUtility.getString(request.getParameter("complaintCode")));
		dto.setCustomerName(DataUtility.getString(request.getParameter("customerName")));
		dto.setComplaintType(DataUtility.getString(request.getParameter("complaintType")));
		dto.setComplaintStatus(DataUtility.getString(request.getParameter("complaintStatus")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		ComplaintModelInt model = ModelFactory.getInstance().getComplaintModel();

		if (id > 0 || op != null) {

			ComplaintDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		ComplaintModelInt model = ModelFactory.getInstance().getComplaintModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ComplaintDTO dto = (ComplaintDTO) populateDTO(request);

			try {
				if (id > 0) {

					model.update(dto);
					ServletUtility.setSuccessMessage("Successfully Updated", request);

				} else {

					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Successfully Saved", request);

					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Complaint already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Complaint already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ComplaintDTO dto = (ComplaintDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.COMPLAINT_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COMPLAINT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COMPLAINT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("ComplaintCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.COMPLAINT_VIEW;
	}
}