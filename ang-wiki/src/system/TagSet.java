package system;

public class TagSet {
	private String tagName;
	private int tagCnt;
	
	TagSet(String tagName, int tagCnt) {
		this.tagName = tagName;
		this.tagCnt = tagCnt;
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public int getTagCnt() {
		return tagCnt;
	}
	public void setTagCnt(int tagCnt) {
		this.tagCnt = tagCnt;
	}	
}
