package system;

import java.sql.*;
import java.time.LocalDateTime;

abstract class Mgr {
	abstract void setPost(Posts post); // 만든 객체를 넣기
	abstract Posts getPost(); // 가진 객체가 없을 경우 새 객체를 만든후 저장, 리턴
	abstract boolean readPost(String title, int rev);
	abstract boolean modPost(String title, String content, String moder);	// 항상 읽어온 후 실행해야됨 - 읽어온 Posts 를 수정함
	abstract Timestamp getModtime(String title, int rev); // 해당 rev 의 수정 시간
	
	abstract boolean writePost(String title, String writer,String content, String tags);		
	abstract boolean delPost(String title);
}

public class PostMgr extends Mgr {
	private DBConnectionMgr pool;
	private Posts post;
	
	public PostMgr(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Error : "+e);
		}
		this.post = new Posts();
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
		
		/* 새 글 내용 저장 시작 		!!!! 글내용 파일에 저장하던 옛날 코드 !!!!
		String filename = "./post/"+title+".post";
		File folder = new File("post");
		if(!folder.exists()) 
			folder.mkdirs();
		
		try (DataOutputStream dos = new DataOutputStream
				(new BufferedOutputStream(new FileOutputStream(filename)))){
			dos.writeUTF(content);
			dos.flush();
		}catch(IOException e){
			System.out.println("IOEx : " + e);
		}
		!!!!!!!지난 코드 !!!!!!! 새 글 내용 저장 끝  */
		
		
		//* DB 저장 시작  *//		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try{
			con = pool.getConnection();
			// 마지막 글 ID를 알아옴 //
			String query = "select max(id) from tblPost";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();				
			if(!rs.wasNull()){
				rs.next();
				postID = rs.getInt(1);
			}
			
			query = "insert into tblPost(id, title, writer, tags, writetime, content)"
					+ " values( ? , ? , ? , ? , ? , ? )";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, ++postID);
			pstmt.setString(2, title);
			pstmt.setString(3, writer);
			pstmt.setString(4, tags);
			pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setString(6, content);
			pstmt.executeUpdate();
			writeflg = true;
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}		
		//* DB 저장 끝  *//
		return writeflg;
	}

	public boolean delPost(String title) {
		boolean delflag = false;
		/* 내용 파일 삭제 시작    !!!!!!!!!!!옛날 코드!!!!!
		String filename = "./post/"+title+".post";
		try {
			File fl = new File(filename);
			fl.delete();
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}		
		//* 내용 파일 삭제 끝  */
		
		//* DB 삭제 시작  *//
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = pool.getConnection();
			String query = "delete from tblPost where title = ? ";
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
			post.setTags(rs.getString(4));
			post.setWritetime(rs.getTimestamp(5));
			post.setModtime(rs.getTimestamp(6));
			post.setModcnt(rs.getInt(7));
			post.setContent(rs.getString(8));
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}

		
		
		/* 글 내용 읽기 시작   !!!!!!!옛날코드
		  
		String revSuffix = "";
		if(rev != 0) {
			revSuffix = "_"+String.valueOf(rev);
			post.setModtime(this.getModtime(title, rev));
		}

		String filename = "./post/"+title+revSuffix+".post";
		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)))){
			while(dis.available() > 0){
				post.setContent(dis.readUTF());
			}
			readflg = true;
		}catch(Exception e){
			System.out.println("Ex : "+e);
		}
		글 내용 읽기 끝 */	
		
		return readflg;
	}
	
	public boolean modPost(String title, String content, String moder){
		if(post == null) return false;
		Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
		boolean modflg = false;
		String prevContent = null;
		
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
			query = "select content from tblPost where title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			rs = pstmt.executeQuery();
			rs.next();
			prevContent = rs.getString(1);
			pstmt.close();
			rs.close();
			
			// 최근 변경 시간을 등록합니다
			query = "update tblPost set modtime = ? , modcnt = ? , content = ? where title = ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setInt(2, modCount+1);
			pstmt.setString(3, content);
			pstmt.setString(4, title);
			pstmt.executeUpdate();
			
			//* mods DB 에 등록합니다   *//		
			query = "insert into tblPostMods(title, modcnt, moder, content)"
					+ " values( ? , ? , ? , ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setInt(2, modCount+1);
			pstmt.setString(3, moder);
			pstmt.setString(4, prevContent);
			pstmt.executeUpdate();	
		}catch(SQLException ex){
			System.out.println(new Exception().getStackTrace()[0].getMethodName()+"\n"+"SQLEx : "+ex);
		}catch(Exception e){
			System.out.println("Exception : " + e);
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}		
		//* DB 작업 끝  *//
		
			
		/* 옛날코드 파일관리
		String prevfilename = "./post/"+title+"_"+String.valueOf(modCount+1)+".post";
		String newfilename = "./post/"+title+".post";
		
		File newFile = new File(newfilename);
		File prevFile = new File(prevfilename);
		newFile.renameTo(prevFile);
		
			
		try (DataOutputStream dos = new DataOutputStream
				(new BufferedOutputStream(new FileOutputStream(newfilename)))){			
			dos.writeUTF(content);
			dos.flush();
			modflg = true;
		}catch(IOException e){
			System.out.println("IOEx : " + e);
		}
		*/
		
		return modflg;
	}
		
}
