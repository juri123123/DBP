package model.service;

import java.sql.SQLException;
import java.util.List;

import model.Policy;
import model.Scrap;
import model.User;
import model.dao.PolicyDAO;
import model.dao.ScrapDAO;

public class ScrapManager {

	private static ScrapManager scMan = new ScrapManager();
	private ScrapDAO scrapDAO;

	private ScrapManager() {
		try {
			scrapDAO = new ScrapDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ScrapManager getInstance() {
		return scMan;
	}

	// ��ũ�� �߰�
	public Scrap add(Scrap scrap) throws SQLException {
		return scrapDAO.addScrap(scrap);
	}

	// ��ũ�� ���
	public int cancel(String userId, int policyId) throws SQLException {
		return scrapDAO.cancelScrap(userId, policyId);
	}

	// ����ڰ� ��ũ���� ����Ʈ ��������
	public List<Scrap> getScrapList(String userId) throws SQLException {
		return scrapDAO.getScrapList(userId);
	}

	// 사용자가 스크랩한 리스트 가져오기
	public List<Scrap> getScrapDateList(String userId) throws SQLException {
		return scrapDAO.getScrapDateList(userId);
	}

	// 회원탈퇴
	public int deleteUserAllScrap(String user_id) throws SQLException {
		return scrapDAO.deleteUserAllScrap(user_id);
	}

}