package system;

import java.sql.Timestamp;

public class Posts implements Cloneable{
	private int id;
	private String title;
	private String writer;
	private String content;
	private String tags;
	private Timestamp writetime;
	private String moder;
	private Timestamp modtime;
	private	int modcnt;
	public Posts(){}
	public Posts(int id, String title, String writer, String tags, Timestamp writetime
			, String moder , Timestamp modtime, int modcnt ){
		setId(id);
		setTitle(title);
		setWriter(writer);
		setTags(tags);
		setWritetime(writetime);
		setModer(moder);
		setModtime(modtime);
		setModcnt(modcnt);
	}
	
	@Override
	public Posts clone(){
		try{
			final Posts result = (Posts) super.clone();
			return result;			
		}catch (Exception e){
			throw new AssertionError();
		}
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getWritetime() {
		return writetime;
	}
	public void setWritetime(Timestamp writetime) {
		this.writetime = writetime;
	}


	public Timestamp getModtime() {
		return modtime;
	}


	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}


	public int getModcnt() {
		return modcnt;
	}


	public void setModcnt(int modcnt) {
		this.modcnt = modcnt;
	}


	public String getModer() {
		return moder;
	}


	public void setModer(String moder) {
		this.moder = moder;
	}
}
