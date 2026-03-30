package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DispatchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.DispatchModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/DispatchCtl" })
public class DispatchCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(DispatchCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("DispatchCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("dispatchId"))) {
			request.setAttribute("dispatchId", PropertyReader.getValue("error.require", "Dispatch Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dispatchDate"))) {
			request.setAttribute("dispatchDate", PropertyReader.getValue("error.require", "Dispatch Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courierName"))) {
			request.setAttribute("courierName", PropertyReader.getValue("error.require", "Courier Name"));
			pass = false;
		}

		log.debug("DispatchCtl Method validate Ended");

		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		DispatchDTO dto = new DispatchDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setDispatchId(DataUtility.getLong(request.getParameter("dispatchId")));
		dto.setDispatchDate(DataUtility.getDate(request.getParameter("dispatchDate")));
		dto.setStatus(DataUtility.getString(request.getParameter("status")));
		dto.setCourierName(DataUtility.getString(request.getParameter("courierName")));

		populateBean(dto, request);

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		DispatchModelInt model = ModelFactory.getInstance().getDispatchModel();

		if (id > 0 || op != null) {

			DispatchDTO dto;

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

		long id = DataUtility.getLong(request.getParameter("id"));

		DispatchModelInt model = ModelFactory.getInstance().getDispatchModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			DispatchDTO dto = (DispatchDTO) populateDTO(request);

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

						ServletUtility.setErrorMessage("Dispatch already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Dispatch already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			DispatchDTO dto = (DispatchDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.DISPATCH_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DISPATCH_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.DISPATCH_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("DispatchCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.DISPATCH_VIEW;
	}

}