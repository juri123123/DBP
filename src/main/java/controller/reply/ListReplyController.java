package controller.reply;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import controller.user.LoginController;
import model.Reply;
import model.service.ReplyManager;

public class ListReplyController  implements Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(ListReplyController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("in ListReplyController");
		
		int postNum = Integer.parseInt(request.getParameter("postNum"));
		logger.debug("ListReplyController postNum: " +postNum);
		
		
		ReplyManager reMan = ReplyManager.getInstance();
		List<Reply> replyList = reMan.findReplyList(postNum);
		
		logger.debug("find replyList");
		
		request.setAttribute("replyList", replyList);
		
		
		logger.debug("replyList:" +replyList+ "\nsetAttribute replyList");
		
		return "/post/postDetail.jsp";
	}
	
	

}
