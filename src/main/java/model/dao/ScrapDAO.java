package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Policy;
import model.Scrap;
import model.User;

public class ScrapDAO {

	private JDBCUtil jdbcUtil = null;
	
	public ScrapDAO() {			
		jdbcUtil = new JDBCUtil();	
	}
	
	/* 정책 스크랩하기 */
	public Scrap addScrap(Scrap sc) {
		int generatedKey;
		Scrap scrap = null;
		
		String sql = "INSERT INTO Scrap VALUES (?, ?) ";

		Object[] param = new Object[] {sc.getPolicyId(), sc.getUserId()};
		jdbcUtil.setSqlAndParameters(sql, param);
		
		String key[] = {"policyId", "user_Id"};
		
		try {
			jdbcUtil.executeUpdate();
			
			ResultSet rs = jdbcUtil.getGeneratedKeys();
			if(rs.next()) {
				generatedKey = rs.getInt(1);
				scrap.setPolicyId(generatedKey);
			}
			return scrap;				
			
		} catch (Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();		
		}
		return null;
	}
	
	public int cancelScrap(String user_Id) throws SQLException {
		
		String sql = "DELETE Scrap "
				   + "WHERE user_Id=?";
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_Id});
		
		try {
			int result = jdbcUtil.executeUpdate();
			
			return result;
		} catch (Exception e) {
			jdbcUtil.rollback();
			e.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return 0;
	}
	
	/* 사용자가 스크랩한 정책 리스트 불러오기 */
	public List<Scrap> getScrapList(String user_Id) throws SQLException {
		
		String sql = "SELECT s.policyId, s.user_id, p.name, p.category "
				   + "FROM Scrap s, Policy p "
				   + "WHERE s.policyId = p.policyId AND user_Id = ? ";
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {user_Id});
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();					
			List<Scrap> scrapList = new ArrayList<Scrap>();
			
			while (rs.next()) {
				Scrap scrap = new Scrap (	
						rs.getString("user_Id"),
						rs.getInt("policyId"),
						rs.getString("name"), 
						rs.getString("category")
						);
				scrapList.add(scrap);				
			}		
			return scrapList;		
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
		
		return null;
	}
	
	
	
}