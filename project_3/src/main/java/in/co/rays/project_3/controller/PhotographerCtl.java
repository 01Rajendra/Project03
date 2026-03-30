package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PhotographerDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PhotographerModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Photographer Controller (Add, Update, Delete)
 * 
 * @author Rajendra Negi
 */
@WebServlet(urlPatterns = { "/ctl/PhotographerCtl" })
public class PhotographerCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(PhotographerCtl.class);

	// ===================== VALIDATE =====================
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("photographerName"))) {
			request.setAttribute("photographerName", PropertyReader.getValue("error.require", "Photographer Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("eventType"))) {
			request.setAttribute("eventType", PropertyReader.getValue("error.require", "Event Type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("charges"))) {
			request.setAttribute("charges", PropertyReader.getValue("error.require", "Charges"));
			pass = false;
		}

		return pass;
	}

	// ===================== POPULATE DTO =====================
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		PhotographerDTO dto = new PhotographerDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setPhotographerId(DataUtility.getLong(request.getParameter("photographerId")));
		dto.setPhotographerName(DataUtility.getString(request.getParameter("photographerName")));
		dto.setEventType(DataUtility.getString(request.getParameter("eventType")));
		dto.setCharges(DataUtility.getDouble(request.getParameter("charges")));

		populateBean(dto, request);

		return dto;
	}

	// ===================== DO GET =====================
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("PhotographerCtl doGet Start");

		PhotographerModelInt model = ModelFactory.getInstance().getPhotographerModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				PhotographerDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ===================== DO POST =====================
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("PhotographerCtl doPost Start");

		String op = DataUtility.getString(request.getParameter("operation"));

		PhotographerModelInt model = ModelFactory.getInstance().getPhotographerModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PhotographerDTO dto = (PhotographerDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data successfully updated", request);
				} else {
					try {
						model.add(dto);
						ServletUtility.setSuccessMessage("Data successfully saved", request);
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Photographer already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Photographer already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			PhotographerDTO dto = (PhotographerDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.PHOTOGRAPHER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PHOTOGRAPHER_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PHOTOGRAPHER_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("PhotographerCtl doPost End");
	}

	// ===================== VIEW =====================
	@Override
	protected String getView() {
		return ORSView.PHOTOGRAPHER_VIEW;
	}
}