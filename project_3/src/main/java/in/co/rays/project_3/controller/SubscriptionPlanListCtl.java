package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.SubscriptionPlanDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.SubscriptionPlanModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/SubscriptionPlanListCtl" })
public class SubscriptionPlanListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(SubscriptionPlanCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("planName"))) {
			request.setAttribute("planName", PropertyReader.getValue("error.require", "Plan Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "Price"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("validityDays"))) {
			request.setAttribute("validityDays", PropertyReader.getValue("error.require", "Validity Days"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		SubscriptionPlanDTO dto = new SubscriptionPlanDTO();

		dto.setPlanId(DataUtility.getLong(request.getParameter("planId")));
		dto.setPlanName(DataUtility.getString(request.getParameter("planName")));
		dto.setPrice(DataUtility.getLong(request.getParameter("price")));
		dto.setValidityDays(DataUtility.getInt(request.getParameter("validityDays")));

		populateBean(dto, request);

		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		SubscriptionPlanModelInt model = ModelFactory.getInstance().getSubscriptionPlanModel();

		long id = DataUtility.getLong(request.getParameter("planId"));

		if (id > 0) {
			try {
				SubscriptionPlanDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		SubscriptionPlanModelInt model = ModelFactory.getInstance().getSubscriptionPlanModel();

		long id = DataUtility.getLong(request.getParameter("planId"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			SubscriptionPlanDTO dto = (SubscriptionPlanDTO) populateDTO(request);

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
						ServletUtility.setErrorMessage("Plan Name already exists", request);
					}
				}
				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			SubscriptionPlanDTO dto = (SubscriptionPlanDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.SUBSCRIPTION_PLAN_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUBSCRIPTION_PLAN_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.SUBSCRIPTION_PLAN_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SUBSCRIPTION_PLAN_VIEW;
	}
}