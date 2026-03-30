package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HolidayDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.HolidayModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/HolidayCtl" })

public class HolidayCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(HolidayCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("HolidayCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("holidayCode"))) {
			request.setAttribute("holidayCode", PropertyReader.getValue("error.require", "Holiday Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("holidayName"))) {
			request.setAttribute("holidayName", PropertyReader.getValue("error.require", "Holiday Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("holidayDate"))) {
			request.setAttribute("holidayDate", PropertyReader.getValue("error.require", "Holiday Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("holidayType"))) {
			request.setAttribute("holidayType", PropertyReader.getValue("error.require", "Holiday Type"));
			pass = false;
		}

		log.debug("HolidayCtl Method validate Ended");

		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		HolidayDTO dto = new HolidayDTO();

		dto.setHolidayId(DataUtility.getLong(request.getParameter("holidayId")));

		dto.setHolidayCode(DataUtility.getString(request.getParameter("holidayCode")));
		dto.setHolidayName(DataUtility.getString(request.getParameter("holidayName")));
		dto.setHolidayDate(DataUtility.getDate(request.getParameter("holidayDate")));
		dto.setHolidayType(DataUtility.getString(request.getParameter("holidayType")));

		populateBean(dto, request);

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("holidayId"));

		HolidayModelInt model = ModelFactory.getInstance().getHolidayModel();

		if (id > 0 || op != null) {

			HolidayDTO dto;

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("holidayId"));

		HolidayModelInt model = ModelFactory.getInstance().getHolidayModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			HolidayDTO dto = (HolidayDTO) populateDTO(request);

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

						ServletUtility.setErrorMessage("Holiday already exists", request);

					}

				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Holiday already exists", request);

			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			HolidayDTO dto = (HolidayDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.HOLIDAY_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOLIDAY_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOLIDAY_CTL, request, response);

			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("HolidayCtl Method doPost Ended");

	}

	@Override
	protected String getView() {

		return ORSView.HOLIDAY_VIEW;

	}

}