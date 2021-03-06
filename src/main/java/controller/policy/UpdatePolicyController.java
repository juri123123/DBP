package controller.policy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.DispatcherServlet;
import controller.user.UserSessionUtils;
import model.Policy;
import model.service.PolicyManager;

public class UpdatePolicyController implements Controller {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PolicyManager polMan = PolicyManager.getInstance();
		int policyId = Integer.parseInt(request.getParameter("policyId"));
		if (request.getMethod().equals("GET")) {
			logger.debug("update controller _ get");

			Policy policy = polMan.findPolicy(policyId);

			// admin
			HttpSession session = request.getSession();
			if (UserSessionUtils.isLoginUser("dbpro0102", session)) {
				request.setAttribute("policy", policy);

				return "/policy/policyUpdateForm.jsp";

			} else { // 占쏙옙占쏙옙占쏙옙 占싣댐옙 占쏙옙占�
				logger.debug("update controller _ exce");
				request.setAttribute("updateFailed", true);
				request.setAttribute("exception", new IllegalStateException("정책 수정에 실패하였습니다"));
				return "/policy/list";
			}
		} else { // "POST" -> 占쌉뤄옙(占쏙옙占쏙옙)占쏙옙 占쏙옙책 占쏙옙占쏙옙 parameter占쏙옙 占쏙옙占쏙옙
			logger.debug("update controller _ post");

			Policy updatePol = new Policy(Integer.parseInt(request.getParameter("policyId")),
					request.getParameter("name"), request.getParameter("contents"), request.getParameter("category"),
					request.getParameter("startDate"), request.getParameter("endDate"),
					request.getParameter("policySummary"), request.getParameter("qualificationForApplication"),
					request.getParameter("howToApply"), request.getParameter("local"),
					Integer.parseInt(request.getParameter("startAge")),
					Integer.parseInt(request.getParameter("endAge")), Integer.parseInt(request.getParameter("income")));
			// test
			// System.out.println(request.getParameter("name"));
			polMan.update(updatePol);
			return "redirect:/policy/view?policyId=" + policyId;

		}
	}

}
