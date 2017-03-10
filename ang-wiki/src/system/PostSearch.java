package system;

import java.sql.*;
import java.util.*;

public class PostSearch {
	private DBConnectionMgr pool;
	private TagMgr tagmgr;
	private PostMgr postmgr;
	
	public PostSearch(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Error : "+e);
		}
		this.tagmgr = new TagMgr();
		this.postmgr = new PostMgr();
	}
	
	private Posts[] _getQueryPosts(String query, int count){
		ArrayList<Posts> result = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, count);
			rs = pstmt.executeQuery();			
			while(rs.next()){				
				Posts post = new Posts();
				post.setId(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setWriter(rs.getString(3));
				post.setWritetime(rs.getTimestamp(4));
				post.setModtime(rs.getTimestamp(5));
				post.setModcnt(rs.getInt(6));
				post.setContent(rs.getString(7));
				post.setModer(rs.getString(8));
				String tags = tagmgr.getTags(post.getId());
				if(tags != null) post.setTags(tags);
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}		
		return result.toArray(new Posts[result.size()]);
	}
	
	public Posts[] getRecentPosts(int count){
		return _getQueryPosts("select * from tblPost order by id desc limit ? ", count);
		/*
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
				Posts post = new Posts();
				post.setId(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setWriter(rs.getString(3));
				post.setWritetime(rs.getTimestamp(4));
				post.setModtime(rs.getTimestamp(5));
				post.setModcnt(rs.getInt(6));
				post.setContent(rs.getString(7));
				post.setModer(rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		return result.toArray(new Posts[result.size()]);
		*/
	}
	
	public Posts[] getNoTagPosts(int count){
		String query = "SELECT * "
				+ "FROM tblPost p "
				+ "WHERE p.id not IN "
				+ "(SELECT distinct p.id "
				+ "FROM tblPost p, tblTagPost tp "
				+ "WHERE p.id = tp.id_post) "
				+ "LIMIT ? ";
		return _getQueryPosts(query, count);
	}
	
	public Posts[] getRecentMods(int count){
		String query = "select * from tblPost WHERE modcnt != 0 order by modtime desc limit ? ";
		return _getQueryPosts(query, count);
		
		/*
		ArrayList<Posts> result = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();
			String query = "select * from tblPost WHERE modcnt != 0 order by modtime desc limit ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, count);
			rs = pstmt.executeQuery();			
			while(!rs.isLast()){
				rs.next();
				Posts post = new Posts();
				post.setId(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setWriter(rs.getString(3));
				post.setWritetime(rs.getTimestamp(4));
				post.setModtime(rs.getTimestamp(5));
				post.setModcnt(rs.getInt(6));
				post.setContent(rs.getString(7));
				post.setModer(rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}		
		
		return result.toArray(new Posts[result.size()]);
		*/
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
				Posts post = new Posts();
				post.setId(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setWriter(rs.getString(3));
				post.setWritetime(rs.getTimestamp(4));
				post.setModtime(rs.getTimestamp(5));
				post.setModcnt(rs.getInt(6));
				post.setContent(rs.getString(7));
				post.setModer(rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
					
		return result.toArray(new Posts[result.size()]);
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
				Posts post = new Posts();
				post.setId(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setWriter(rs.getString(3));
				post.setWritetime(rs.getTimestamp(4));
				post.setModtime(rs.getTimestamp(5));
				post.setModcnt(rs.getInt(6));
				post.setContent(rs.getString(7));
				post.setModer(rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		return result.toArray(new Posts[result.size()]);
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
				Posts post = new Posts();
				post.setId(rs.getInt(1));
				post.setTitle(rs.getString(2));
				post.setWriter(rs.getString(3));
				post.setWritetime(rs.getTimestamp(4));
				post.setModtime(rs.getTimestamp(5));
				post.setModcnt(rs.getInt(6));
				post.setContent(rs.getString(7));
				post.setModer(rs.getString(8));
				result.add(post);
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		return result.toArray(new Posts[result.size()]);
	}
	
	public Posts[] getTagPosts(String tagname){
		ArrayList<Posts> result = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		int id_tag = tagmgr.getTagID(tagname);
		if(id_tag == 0) return null;
		
		try{
			con = pool.getConnection();
			String query = "select id_post from tblTagPost where id_tag = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id_tag);
			rs = pstmt.executeQuery();			
			while(rs.next()){
				result.add(postmgr.getIdPost(rs.getInt(1)));
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		return result.toArray(new Posts[result.size()]);
	}
}
