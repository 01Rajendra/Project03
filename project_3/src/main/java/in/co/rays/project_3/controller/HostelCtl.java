package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HostelDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.HostelModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Hostel functionality ctl. To perform add, delete, update operation
 * 
 * @author Rajendra Negi
 * 
 */

@WebServlet(urlPatterns = { "/ctl/HostelCtl" })
public class HostelCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(HostelCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("hostelName"))) {
			request.setAttribute("hostelName", PropertyReader.getValue("error.require", "Hostel Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("capacity"))) {
			request.setAttribute("capacity", PropertyReader.getValue("error.require", "Capacity"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("capacity"))) {
			request.setAttribute("capacity", "Capacity must be numeric");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("numberOfRooms"))) {
			request.setAttribute("numberOfRooms", PropertyReader.getValue("error.require", "No of Rooms"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("numberOfRooms"))) {
			request.setAttribute("numberOfRooms", "No of Rooms must be numeric");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("fees"))) {
			request.setAttribute("fees", PropertyReader.getValue("error.require", "Fees"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		HostelDTO dto = new HostelDTO();

		dto.setHostelName(request.getParameter("hostelName"));
		dto.setCapacity(DataUtility.getInt(request.getParameter("capacity")));
		dto.setNumberOfRooms(DataUtility.getInt(request.getParameter("numberOfRooms")));
		dto.setOccupancy(request.getParameter("occupancy"));
		dto.setRoomType(request.getParameter("roomType"));
		dto.setWashroomStatus(request.getParameter("washroomStatus"));
		dto.setStatus(request.getParameter("status"));
		dto.setFees(request.getParameter("fees"));

		populateBean(dto, request);
		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		HostelModelInt model = ModelFactory.getInstance().getHostelModel();

		if (id > 0 || op != null) {

			try {
				HostelDTO dto = model.findByPK(id);
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

		HostelModelInt model = ModelFactory.getInstance().getHostelModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			HostelDTO dto = (HostelDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Hostel Already Exists", request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOSTEL_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOSTEL_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.HOSTEL_VIEW;
	}

}
