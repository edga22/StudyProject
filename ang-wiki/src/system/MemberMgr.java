package system;

import java.sql.*;
import java.util.ArrayList;


public class MemberMgr
{
	private DBConnectionMgr pool;

	public MemberMgr(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Error : "+e);			
		}
	}

	public boolean passCheck(String cust_id, String cust_pass){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean loginflg = false;
        try {
            con = pool.getConnection();
			String query = "select count(*) from tblMember where id = ? and passwd = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,cust_id);
            pstmt.setString(2,cust_pass);
            rs = pstmt.executeQuery();
			rs.next();
            if(rs.getInt(1) > 0) loginflg =true;
        }catch(SQLException ex){
        	System.out.println("SQL Exception : "+ ex);
        }catch(Exception e) {
            System.out.println("Exception : " + e);
        }finally{
             pool.freeConnection(con,pstmt,rs);
        }
        return loginflg;
    }
	
	public boolean signUp(String id, String pw, String nick , String email){
		boolean signflg = false;
		Connection con = null;
        PreparedStatement pstmt = null;
        
        try{
        	con = pool.getConnection();
        	String query = "insert into tblMember(id, passwd, nickname, email)"
        			+ " values( ? , ? , ? , ? )";
        	pstmt = con.prepareStatement(query);
        	pstmt.setString(1, id);
        	pstmt.setString(2, pw);
        	pstmt.setString(3, nick);
        	pstmt.setString(4, email);
        	pstmt.executeUpdate();
        	signflg = true;
        }catch(SQLException ex){
        	System.out.println("SQL Exception : "+ ex);
        }catch(Exception e) {
        	System.out.println("Exception : " + e);
        }finally{
        	pool.freeConnection(con,pstmt);
        }		
		return signflg;
	}
	
	public Members[] getMemberList(){
		ArrayList<Members> result = new ArrayList<>();
		
		
		Members[] out = new Members[result.size()];
		result.toArray(out);
		return out;
	}
	
}
