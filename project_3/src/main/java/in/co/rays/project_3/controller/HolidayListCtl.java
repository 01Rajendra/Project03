package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.HolidayDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.HolidayModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "HolidayListCtl", urlPatterns = { "/ctl/HolidayListCtl" })

public class HolidayListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(HolidayListCtl.class);

	protected void preload(HttpServletRequest request) {

		HolidayModelInt model = ModelFactory.getInstance().getHolidayModel();

		try {

			List list = model.list();

			request.setAttribute("holidayList", list);

		} catch (Exception e) {

		}

	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		HolidayDTO dto = new HolidayDTO();

		dto.setHolidayId(DataUtility.getLong(request.getParameter("holidayId")));

		populateBean(dto, request);

		return dto;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("HolidayListCtl doGet Start");

		List list = null;
		List next = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		HolidayDTO dto = (HolidayDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		HolidayModelInt model = ModelFactory.getInstance().getHolidayModel();

		try {

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record found ", request);

			}

			if (next == null || next.size() == 0) {

				request.setAttribute("nextListSize", 0);

			} else {

				request.setAttribute("nextListSize", next.size());

			}

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(e, request, response);

			return;

		}

		log.debug("HolidayListCtl doGet End");

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("HolidayListCtl doPost Start");

		List list = null;
		List next = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));

		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		HolidayDTO dto = (HolidayDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		HolidayModelInt model = ModelFactory.getInstance().getHolidayModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {

					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {

					pageNo++;

				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {

					pageNo--;

				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.HOLIDAY_CTL, request, response);

				return;

			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.HOLIDAY_LIST_CTL, request, response);

				return;

			} else if (OP_BACK.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.HOLIDAY_LIST_CTL, request, response);

				return;

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				pageNo = 1;

				if (ids != null && ids.length > 0) {

					HolidayDTO deleteBean = new HolidayDTO();

					for (String id : ids) {

						deleteBean.setHolidayId(DataUtility.getLong(id));

						model.delete(deleteBean);

					}

					ServletUtility.setSuccessMessage("Data Deleted Successfully", request);

				} else {

					ServletUtility.setErrorMessage("Select at least one record", request);

				}

			}

			dto = (HolidayDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);

			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setDto(dto, request);

			ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record found ", request);

			}

			if (next == null || next.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {

				request.setAttribute("nextListSize", 0);

			} else {

				request.setAttribute("nextListSize", next.size());

			}

			ServletUtility.setPageNo(pageNo, request);

			ServletUtility.setPageSize(pageSize, request);

			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			log.error(e);

			ServletUtility.handleException(e, request, response);

			return;

		}

		log.debug("HolidayListCtl doPost End");

	}

	@Override
	protected String getView() {

		return ORSView.HOLIDAY_LIST_VIEW;

	}

}