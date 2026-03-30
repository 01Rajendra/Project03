package in.co.rays.project_3.controller;

import java.io.IOException;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ShiftDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ShiftModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ShiftCtl" })
public class ShiftCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ShiftCtl.class);

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("shiftCode"))) {
			request.setAttribute("shiftCode", PropertyReader.getValue("error.require", "Shift Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("shiftName"))) {
			request.setAttribute("shiftName", PropertyReader.getValue("error.require", "Shift Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("startTime"))) {
			request.setAttribute("startTime", PropertyReader.getValue("error.require", "Start Time"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("endTime"))) {
			request.setAttribute("endTime", PropertyReader.getValue("error.require", "End Time"));
			pass = false;
		}

		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		ShiftDTO dto = new ShiftDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setShiftCode(DataUtility.getString(request.getParameter("shiftCode")));
		dto.setShiftName(DataUtility.getString(request.getParameter("shiftName")));

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if (startTime != null && !startTime.isEmpty()) {
			dto.setStartTime(LocalTime.parse(startTime));
		}

		if (endTime != null && !endTime.isEmpty()) {
			dto.setEndTime(LocalTime.parse(endTime));
		}

		populateBean(dto, request);

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("ShiftCtl doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		ShiftModelInt model = ModelFactory.getInstance().getShiftModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			try {
				ShiftDTO dto = model.findByPK(id);
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
		ShiftModelInt model = ModelFactory.getInstance().getShiftModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ShiftDTO dto = (ShiftDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Shift Code already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			ShiftDTO dto = (ShiftDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.SHIFT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SHIFT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SHIFT_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("ShiftCtl doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.SHIFT_VIEW;
	}
}