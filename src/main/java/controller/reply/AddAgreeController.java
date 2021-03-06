package controller.reply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.Agree;
import model.service.AgreeManager;

public class AddAgreeController implements Controller{
	private static final Logger logger = LoggerFactory.getLogger(AddAgreeController.class);
	AgreeManager aMan = AgreeManager.getInstance();
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("in AgreeController");
		
		int postNum = Integer.parseInt(request.getParameter("postNum"));
		String agree = request.getParameter("agree");
		
		Agree a = null;
		
		try {
			
			if(aMan.findAgree(postNum) == null) {
				a = aMan.create(postNum);
			}		
			
			if (agree.equals("agree")) {
				logger.debug("agree!!");
				aMan.addAgree(postNum);
				
			}
			else {
				logger.debug("disagree!!");
				aMan.addDisAgree(postNum);
			}
			
			a = aMan.findAgree(postNum);
			request.setAttribute("agree", request.getParameter("agree"));
			
			request.setAttribute("agr", a.getAgree());
			request.setAttribute("disagr", a.getDisagree());
			
			System.out.println("agree " + a.getAgree());
			System.out.println("disagree " + a.getDisagree());
			
			return "redirect:/post/view?postNum=" +postNum;
		
		} catch (Exception e) {
			request.setAttribute("createAgreeFailed", true);
			request.setAttribute("exception", e);
			request.setAttribute("agree", a);
			
			return "redirect:/post/view?postNum=" + postNum;

		}
	}

}
