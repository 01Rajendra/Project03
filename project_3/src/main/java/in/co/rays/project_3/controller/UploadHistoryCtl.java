package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.UploadHistoryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.UploadHistoryModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * UploadHistory functionality controller.to perform add,delete and update
 * operation
 * 
 * @author Rajendra Negi
 *
 */
@WebServlet(urlPatterns = { "/ctl/UploadHistoryCtl" })
public class UploadHistoryCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(UploadHistoryCtl.class);

	
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("uploadCode"))) {
			request.setAttribute("uploadCode", PropertyReader.getValue("error.require", "Upload Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("fileName"))) {
			request.setAttribute("fileName", PropertyReader.getValue("error.require", "File Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("uploadTime"))) {
			request.setAttribute("uploadTime", PropertyReader.getValue("error.require", "Upload Time"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("uploadStatus"))) {
			request.setAttribute("uploadStatus", PropertyReader.getValue("error.require", "Upload Status"));
			pass = false;
		}

		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		UploadHistoryDTO dto = new UploadHistoryDTO();

		dto.setId(DataUtility.getLong(request.getParameter("uploadId")));
		dto.setUploadCode(DataUtility.getString(request.getParameter("uploadCode")));
		dto.setFileName(DataUtility.getString(request.getParameter("fileName")));
		dto.setUploadTime(DataUtility.geTimestamp(request.getParameter("uploadTime")).toLocalDateTime());
		dto.setUploadStatus(DataUtility.getString(request.getParameter("uploadStatus")));

		populateBean(dto, request);

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("UploadHistoryCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		UploadHistoryModelInt model = ModelFactory.getInstance().getUploadHistoryModel();

		long id = DataUtility.getLong(request.getParameter("uploadId"));

		if (id > 0 || op != null) {
			UploadHistoryDTO dto = null;
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

		UploadHistoryModelInt model = ModelFactory.getInstance().getUploadHistoryModel();

		long id = DataUtility.getLong(request.getParameter("uploadId"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			UploadHistoryDTO dto = (UploadHistoryDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Upload Code already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Upload Code already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			UploadHistoryDTO dto = (UploadHistoryDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.UPLOADHISTORY_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.UPLOADHISTORY_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.UPLOADHISTORY_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UploadHistoryCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.UPLOADHISTORY_VIEW;
	}
}