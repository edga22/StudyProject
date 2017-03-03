package system;

import java.sql.*;
import java.time.LocalDateTime;

abstract class Mgr {
	abstract void setPost(Posts post); // 만든 객체를 넣기
	abstract Posts getPost(); // 가진 객체가 없을 경우 새 객체를 만든후 저장, 리턴
	abstract boolean readPost(String title, int rev);
	abstract boolean modPost(String title, String content, String moder, String tags);	// 항상 읽어온 후 실행해야됨 - 읽어온 Posts 를 수정함
	abstract Timestamp getModtime(String title, int rev); // 해당 rev 의 수정 시간
	
	abstract boolean writePost(String title, String writer,String content, String tags);		
	abstract boolean delPost(String title);
}

public class PostMgr extends Mgr {
	private DBConnectionMgr pool;
	private Posts post;
	private TagMgr tagmgr;
	
	public PostMgr(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Error : "+e);
		}
		this.post = new Posts();
		this.tagmgr = new TagMgr();
	}
	
	public void setPost(Posts post){
		this.post = post;
	}
	public Posts getPost(){
		if(post == null) this.post = new Posts();
		return this.post;
	}
	
	
	@SuppressWarnings("resource")
	public boolean writePost(String title, String writer,String content,  String tags){
		boolean writeflg = false;
		int postID = 1;
					
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try{
			con = pool.getConnection();
			// 마지막 글 ID를 알아옴  !! SQL에서 AI 를 주면 필요없는 부분 //
			String query = "select max(id) from tblPost";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();				
			if(!rs.wasNull()){
				rs.next();
				postID = rs.getInt(1);
			}
			
			query = "insert into tblPost(id, title, writer, writetime, content)"
					+ " values( ? , ? , ? , ? , ? , ? )";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, ++postID);
			pstmt.setString(2, title);
			pstmt.setString(3, writer);
			pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setString(5, content);
			pstmt.executeUpdate();
			
			pstmt.close();
			rs.close();
			
			tagmgr.submitTagPost(title, tags);			
			writeflg = true;
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}		
				
		return writeflg;
	}

	public boolean delPost(String title) {
		boolean delflag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = pool.getConnection();
			String query = "delete from tblPost where title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.executeUpdate();
			pstmt.close();
			query = "delete from tblPostMods where title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.executeUpdate();
			
			delflag = true;
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}finally{
			pool.freeConnection(con,pstmt);
		}		
		//* DB 삭제 끝  *//
		
		return delflag;
	}
	
	public Timestamp getModtime(String title, int rev){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Timestamp modTime = null;
		String query = "select modtime from tblPostMods where title = ? and modcnt = ? ";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setInt(2, rev);
			rs = pstmt.executeQuery();
			rs.next();
			modTime = rs.getTimestamp(1);			
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		return modTime;
	}

	public boolean readPost(String title, int rev){		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean readflg = false;
					
		String query = "select * from tblPost where title = ? ";
		try {
			con = pool.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			rs.next();
			post.setId(rs.getInt(1));
			post.setTitle(title);
			post.setWriter(rs.getString(3));
			post.setWritetime(rs.getTimestamp(4));
			post.setModtime(rs.getTimestamp(5));
			post.setModcnt(rs.getInt(6));
			post.setContent(rs.getString(7));
			pstmt.close();
			rs.close();
			
			// 구버전 내용 불러오기
			if(rev != 0){
				query = "select * from tblPostMods where title = ? and modcnt = ? ";
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, title);
				pstmt.setInt(2, rev);
				rs = pstmt.executeQuery();
				rs.next();
				post.setContent(rs.getString(5));
				post.setModtime(rs.getTimestamp(3));
				pstmt.close();
				rs.close();
			}
			String tags = tagmgr.getTags(post.getId());
			post.setTags(tags);
			
			readflg = true;
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		
		return readflg;
	}
	
	public boolean modPost(String title, String content, String moder, String tags){
		if(post == null) return false;
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		boolean modflg = false;
		String prevContent = null;
		int id_post = 0;
		
		int modCount = 0;
		
		
		try {
			//* 리비전 count 를 알아오고 Mods에 등록  *//
            con = pool.getConnection();
			String query = "select count(*) from tblPostMods where title = ? ";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, title);
            rs = pstmt.executeQuery();
			rs.next();
			modCount = rs.getInt(1);    
			pstmt.close();
			rs.close();
			
			//예전 글 내용 찾아옴
			query = "select content, id from tblPost where title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			rs.next();
			prevContent = rs.getString(1);
			id_post = rs.getInt(2);
			pstmt.close();
			rs.close();
			
			// 최근 변경 내용을 등록합니다
			query = "update tblPost set modcnt = ? , content = ? , moder = ? where title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, modCount+1);
			pstmt.setString(2, content);
			pstmt.setString(3, moder);
			pstmt.setString(4, title);			
			pstmt.executeUpdate();
			pstmt.close();
			
			// 기존 태그를 삭제합니다
			query = "delete from tblTagPost where id_post = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id_post);
			pstmt.executeUpdate();
			pstmt.close();
			
			tagmgr.submitTagPost(title, tags);
				
			//* mods DB 에 등록합니다   *//		
			query = "insert into tblPostMods(title, modcnt, moder, content)"
					+ " values( ? , ? , ? , ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setInt(2, modCount+1);
			pstmt.setString(3, moder);
			pstmt.setString(4, prevContent);
			pstmt.executeUpdate();	
			
			modflg = true;
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}	
		
		return modflg;
	}
		
}
