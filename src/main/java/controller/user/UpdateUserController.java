package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.service.UserManager;
import model.User;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
 
    	if (request.getMethod().equals("GET")) {	
    		// GET request: 수정 form 요청
    		String updateId = request.getParameter("userId");

    		log.debug("UpdateForm Request : {}", updateId);
    		
    		UserManager manager = UserManager.getInstance();
			User user = manager.findUser(updateId);	// �����Ϸ��� ����� ���� �˻�
			request.setAttribute("user", user);	

			HttpSession session = request.getSession();
			if (UserSessionUtils.isLoginUser(updateId, session) ||
				UserSessionUtils.isLoginUser("admin", session)) {
				// ���� �α����� ����ڰ� ���� ��� ������̰ų� �������� ��� -> ���� ����
				
				return "/user/updateForm.jsp";   // �˻��� ����� ������ update form���� ����     
			}    
			
			// else 로그인 되어있지 않은 상태일 때 
			request.setAttribute("updateFailed", true);
			request.setAttribute("exception", 
					new IllegalStateException("로그인 후 사용 가능한 기능입니다."));            
			return "/user/view.jsp";	// ����� ���� ȭ������ �̵� (forwarding)
	    }	
    	
    	// POST request (ȸ�������� parameter�� ���۵�)
    	User updateUser = new User(
    		request.getParameter("userId"),
    		request.getParameter("password"),
    		request.getParameter("name"),
    		request.getParameter("email"),
    		request.getParameter("birth"),
    		request.getParameter("phone"));

    	log.debug("Update User : {}", updateUser);

		UserManager manager = UserManager.getInstance();
		manager.update(updateUser);			
        return "redirect:/user/list";			
    }
}