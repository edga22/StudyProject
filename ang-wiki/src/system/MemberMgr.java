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
			String query = "select count(*) from tblMember where id = ? and passwd = ? ";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,cust_id);
            pstmt.setString(2,cust_pass);
            rs = pstmt.executeQuery();
			rs.next();
            if(rs.getInt(1) > 0) loginflg =true;
        }catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
        return loginflg;
    }
	// 회원 가입 //
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
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt);
		}		
		return signflg;
	}
	
	public Members getMember(String ID, String PW){
		Members result = new Members();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			con = pool.getConnection();
			String query = "select * from tblMember where id = ? and passwd = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, ID);
			pstmt.setString(2, PW);
			rs = pstmt.executeQuery();
			rs.next();
			result.setId(ID);
			result.setNickname(rs.getString(3));
			result.setEmail(rs.getString(4));
			result.setRegisterDate(rs.getTimestamp(5));
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		return result;
	}
	// 모든 맴버의 목록을 얻어옵니다 //
	public Members[] getMemberList(){
		ArrayList<Members> result = new ArrayList<>();
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
        	con = pool.getConnection();
        	String query = "select * from tblMember";
        	pstmt = con.prepareStatement(query);
        	rs = pstmt.executeQuery();        	
        	while(!rs.isLast()){
        		rs.next();
        		Members member = new Members(rs.getString(1), rs.getString(2),
        				rs.getString(3),rs.getString(4),rs.getTimestamp(5));
        		result.add(member);
        	}
        }catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		Members[] out = new Members[result.size()];
		result.toArray(out);
		return out;
	}
	
}
