package system;

import java.sql.*;
import java.util.*;

public class PostSearch {
	private DBConnectionMgr pool;
	
	public PostSearch(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Error : "+e);
		}
	}
	
	public Posts[] getRecentPosts(int count){
		ArrayList<Posts> result = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();
			String query = "select * from tblPost order by id desc limit ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, count);
			rs = pstmt.executeQuery();			
			while(rs.next()){				
				Posts post = new Posts(rs.getInt(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getTimestamp(5),
						null,rs.getTimestamp(6),rs.getInt(7), rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		// 배열 출력을 위한 변환부
		Posts[] out = new Posts[result.size()];
		result.toArray(out);
		return out;
	}
	
	public Posts[] getRecentMods(int count){
		ArrayList<Posts> result = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();
			String query = "select * from tblPost order by modtime desc limit ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, count);
			rs = pstmt.executeQuery();			
			while(!rs.isLast()){
				rs.next();
				Posts post = new Posts(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getTimestamp(5), rs.getString(9), rs.getTimestamp(6),
						rs.getInt(7), rs.getString(8));
				
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		// 배열 출력을 위한 변환부
		int size = result.size();
		Posts[] out = new Posts[size];
		result.toArray(out);
		return out;
	}

	public boolean containsTitle(String title){
		boolean havePost = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();
			String query = "select count(*) from tblPost where title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();			
			rs.next();
			if(rs.getInt(1) == 1) havePost = true;			
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		return havePost;
	}
	
	public Posts[] getTitlePosts(String title){
		ArrayList<Posts> result = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();		
			String query = "select count(*) from tblPost where title LIKE ? order by id desc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%"+title+"%");
			rs = pstmt.executeQuery();	
			rs.next();
			if(rs.getInt(1) < 1){
				pool.freeConnection(con,pstmt,rs);
				return null;
			}
			pstmt.close(); rs.close();
			
			query = "select * from tblPost where title LIKE ? order by id desc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%"+title+"%");
			rs = pstmt.executeQuery();			
			while(rs.next()){				
				Posts post = new Posts(rs.getInt(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getTimestamp(5),
						null,rs.getTimestamp(6),rs.getInt(7),rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		// 배열 출력을 위한 변환부
		Posts[] out = new Posts[result.size()];
		result.toArray(out);
		return out;
	}

	public Posts[] getList(){
		ArrayList<Posts> result = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();
			String query = "select * from tblPost order by id desc";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();			
			while(rs.next()){				
				Posts post = new Posts(rs.getInt(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getTimestamp(5),
						null,rs.getTimestamp(6),rs.getInt(7),rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		// 배열 출력을 위한 변환부
		Posts[] out = new Posts[result.size()];
		result.toArray(out);
		return out;
	}
	
	public Posts[] getColumnPosts(String column, String value){
		ArrayList<Posts> result = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();
			String query = "select * from tblPost where "+ column +" LIKE ? order by id desc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%"+value+"%");
			rs = pstmt.executeQuery();			
			while(rs.next()){				
				Posts post = new Posts(rs.getInt(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getTimestamp(5),
						null,rs.getTimestamp(6),rs.getInt(7), rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		Posts[] out = new Posts[result.size()];
		result.toArray(out);
		return out;
	}
}
