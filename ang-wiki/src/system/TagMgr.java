package system;

import java.sql.*;
import java.util.*;

public class TagMgr {
	private DBConnectionMgr pool;
	
	public TagMgr(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Error : "+e);
		}
	}
	public TagSet[] getTagList(){
		ArrayList<TagSet> resultList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		String query = "";
		try{
			con = pool.getConnection();
			query = "SELECT id_tag, tagname "
					+ "FROM tblTags";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()){
				resultList.add(new TagSet(rs.getString(2), cntTagID(rs.getInt(1))));
			}
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}	
		
		TagSet[] result = new TagSet[resultList.size()];
		resultList.toArray(result);
		return result;
	}
	
	public boolean submitTagPost(String title, String tags){
		boolean flg = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		String query = "";
		
		try{
			con = pool.getConnection();
			if(!tags.equals("")){
				String tagArray[] = tags.split(" ");
				int id_tag, id_post;			
				
				pstmt = con.prepareStatement("select id from tblPost where title = ? ");
				pstmt.setString(1, title);
				rs = pstmt.executeQuery();
				rs.next();
				id_post = rs.getInt(1);
				pstmt.close();
				rs.close();
				
				for(String tag : tagArray){
					query = "select count(tagname) from tblTags where tagname = ? ";
					pstmt = con.prepareStatement(query);
					pstmt.setString(1, tag);
					rs = pstmt.executeQuery();
					rs.next();
					if(rs.getInt(1)<1){
						pstmt.close();
						rs.close();
						
						query = "insert into tblTags (tagname) value ( ? )";
						pstmt = con.prepareStatement(query);
						pstmt.setString(1, tag);
						pstmt.executeUpdate();
						pstmt.close();
					}
					
					pstmt = con.prepareStatement("select id_tag from tblTags where tagname = ? ");
					pstmt.setString(1, tag);
					rs = pstmt.executeQuery();
					rs.next();
					id_tag = rs.getInt(1);
					pstmt.close();
					rs.close();				
					
					pstmt = con.prepareStatement("insert into tblTagPost (id_post, id_tag) value ( ? , ? )");
					pstmt.setInt(1, id_post);
					pstmt.setInt(2, id_tag);
					pstmt.executeUpdate();
				}
			}
			flg = true;
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}	
		
		return flg;		
	}

	public String getTags(int id_post){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		String query = "";		
		String tagString = "";
		try{
			con = pool.getConnection();
			/* select 3회 소스
			ArrayList<Integer> id_tag = new ArrayList<>();
			pstmt = con.prepareStatement("select id_tag from tblTagPost where id_post = ? ");
			pstmt.setInt(1, id_post);
			rs = pstmt.executeQuery();
			while(rs.next()) id_tag.add(rs.getInt(1));
			pstmt.close();
			rs.close();
			if(!(id_tag.size() < 1)) {
				query = "SELECT tagname FROM tblTags WHERE id_tag IN (";
				String temp = "";
				for(int i=0;i<id_tag.size();i++)temp+=", ? ";
				temp = temp.replaceFirst(",", "");
				temp+=")";
				query+=temp;
				pstmt = con.prepareStatement(query);				
				for(int i=1;i<=id_tag.size();i++){	pstmt.setInt(i, id_tag.get(i-1));	}
				rs = pstmt.executeQuery();
				while(rs.next()) tagString+=" "+rs.getString(1);			
				tagString = tagString.substring(1);
				}
			*/
			query = "SELECT t.tagname FROM tblPost p, tblTagPost tp, tblTags t"
					+ " WHERE p.id = tp.id_post AND tp.id_tag = t.id_tag AND p.id = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id_post);
			rs= pstmt.executeQuery();
			while(rs.next()) tagString+=" "+rs.getString(1);
			if(tagString.equals("")) tagString = null;
			else tagString = tagString.substring(1);
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}	
		
		return tagString;
	}
	
	public int getTagID(String tagname){
		int id_tag = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();		
			String query = "select id_tag from tblTags where tagname = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, tagname);
			rs = pstmt.executeQuery();	
			if(rs.next()) id_tag = rs.getInt(1);
			else id_tag = 0;
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		return id_tag;
	}
	
	public int cntTagID(int id_tag){
		int cntTag = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		
		try{
			con = pool.getConnection();		
			String query = "select count(*) from tblTagPost where id_tag = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id_tag);
			rs = pstmt.executeQuery();	
			if(rs.next()) cntTag = rs.getInt(1);
			
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		return cntTag;
	}

	
}
