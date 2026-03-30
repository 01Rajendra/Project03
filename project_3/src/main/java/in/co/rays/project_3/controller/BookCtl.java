package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BookModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/BookCtl" })

public class BookCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(BookCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("BookCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("bookTitle"))) {
			request.setAttribute("bookTitle", PropertyReader.getValue("error.require", "Book Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("renterName"))) {
			request.setAttribute("renterName", PropertyReader.getValue("error.require", "Renter Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("rentDate"))) {
			request.setAttribute("rentDate", PropertyReader.getValue("error.require", "Rent Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("returnDate"))) {
			request.setAttribute("returnDate", PropertyReader.getValue("error.require", "Return Date"));
			pass = false;
		}

		log.debug("BookCtl Method validate Ended");

		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {

		BookDTO dto = new BookDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setBookTitle(DataUtility.getString(request.getParameter("bookTitle")));
		dto.setRenterName(DataUtility.getString(request.getParameter("renterName")));
		dto.setRentDate(DataUtility.getDate(request.getParameter("rentDate")));
		dto.setReturnDate(DataUtility.getDate(request.getParameter("returnDate")));

		populateBean(dto, request);

		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = request.getParameter("operation");

		long id = DataUtility.getLong(request.getParameter("id"));

		BookModelInt model = ModelFactory.getInstance().getBookModel();

		if (id > 0 || op != null) {

			BookDTO dto;

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

		BookModelInt model = ModelFactory.getInstance().getBookModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BookDTO dto = (BookDTO) populateDTO(request);

			try {

				if (id > 0) {

					model.update(dto);

					ServletUtility.setSuccessMessage("Successfully Updated", request);

				} else {

					try {

						model.add(dto);

						ServletUtility.setSuccessMessage("Successfully Saved", request);

					} catch (ApplicationException e) {

						log.error(e);

						ServletUtility.handleException(e, request, response);

						return;

					} catch (DuplicateRecordException e) {

						ServletUtility.setDto(dto, request);

						ServletUtility.setErrorMessage("Book already exists", request);
					}
				}

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);

				ServletUtility.setErrorMessage("Book already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BookDTO dto = (BookDTO) populateDTO(request);

			try {

				model.delete(dto);

				ServletUtility.redirect(ORSView.BOOK_LIST_CTL, request, response);

				return;

			} catch (ApplicationException e) {

				log.error(e);

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BOOK_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BOOK_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("BookCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.BOOK_VIEW;
	}

}