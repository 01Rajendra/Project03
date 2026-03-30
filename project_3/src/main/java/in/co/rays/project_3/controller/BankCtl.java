package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.BankModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;


@WebServlet(urlPatterns = { "/ctl/BankCtl" })
public class BankCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BankCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("BankCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("bank_Name"))) {
			request.setAttribute("bank_Name", PropertyReader.getValue("error.require", "Bank Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("account_NO"))) {
			request.setAttribute("account_NO", PropertyReader.getValue("error.require", "Account No"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("customer_Name"))) {
			request.setAttribute("customer_Name", PropertyReader.getValue("error.require", "Customer Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		log.debug("BankCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BankDTO dto = new BankDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setBank_Name(DataUtility.getString(request.getParameter("bank_Name")));
		dto.setAccount_NO(DataUtility.getString(request.getParameter("account_NO")));
		dto.setCustomer_Name(DataUtility.getString(request.getParameter("customer_Name")));
		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		dto.setAddress(DataUtility.getString(request.getParameter("address")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		BankModelInt model = ModelFactory.getInstance().getBankModel();

		if (id > 0 || op != null) {

			BankDTO dto;
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

		BankModelInt model = ModelFactory.getInstance().getBankModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BankDTO dto = (BankDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Bank already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BankDTO dto = (BankDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANK_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("BankCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.BANK_VIEW;
	}
}