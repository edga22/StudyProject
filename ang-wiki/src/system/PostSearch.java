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
			String query = "select * from tblpost order by id desc limit ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, count);
			rs = pstmt.executeQuery();			
			while(!rs.isLast()){
				rs.next();
				Posts post = new Posts(rs.getInt(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getTimestamp(5),
						null,rs.getTimestamp(6),rs.getInt(7));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println("SQLEx : "+ex);
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
			String query = "select * from tblpostmods order by modtime desc limit ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, count);
			rs = pstmt.executeQuery();			
			while(!rs.isLast()){
				rs.next();
				Posts post = new Posts();
				post.setTitle(rs.getString(1));
				post.setModcnt(rs.getInt(2));
				post.setModtime(rs.getTimestamp(3));	
				post.setModer(rs.getString(4));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println("SQLEx : "+ex);
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

	public Posts[] getTitlePosts(String title){
		ArrayList<Posts> result = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();
			String query = "select * from tblpost where title LIKE ? order by id desc";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%"+title+"%");
			rs = pstmt.executeQuery();			
			while(!rs.isLast()){
				rs.next();
				Posts post = new Posts(rs.getInt(1),rs.getString(2),rs.getString(3)
						,rs.getString(4),rs.getTimestamp(5),
						null,rs.getTimestamp(6),rs.getInt(7));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println("SQLEx : "+ex);
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

}
