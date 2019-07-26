package onem.cjq.rss.domain;

/**
 * 请保证成员变量顺序和数据库中的顺序是一致的，这样才能方便查询
 * @author cjq
 *
 */
public class Feed {
	private int id;
	private String webRawTitleName;
	private String webRawDesc;
	private String webPath;
	private String webEncode;
	private String webGlobalReg;
	private String webItemReg;
	private String composeTitleReg;
	private String composeLinkReg;
	private String composeContentReg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWebRawTitleName() {
		return webRawTitleName;
	}
	public void setWebRawTitleName(String webRawTitleName) {
		this.webRawTitleName = webRawTitleName;
	}
	public String getWebRawDesc() {
		return webRawDesc;
	}
	public void setWebRawDesc(String webRawDesc) {
		this.webRawDesc = webRawDesc;
	}
	public String getWebPath() {
		return webPath;
	}
	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}
	public String getWebEncode() {
		return webEncode;
	}
	public void setWebEncode(String webEncode) {
		this.webEncode = webEncode;
	}
	public String getWebGlobalReg() {
		return webGlobalReg;
	}
	public void setWebGlobalReg(String webGlobalReg) {
		this.webGlobalReg = webGlobalReg;
	}
	public String getWebItemReg() {
		return webItemReg;
	}
	public void setWebItemReg(String webItemReg) {
		this.webItemReg = webItemReg;
	}
	public String getComposeTitleReg() {
		return composeTitleReg;
	}
	public void setComposeTitleReg(String composeTitleReg) {
		this.composeTitleReg = composeTitleReg;
	}
	public String getComposeLinkReg() {
		return composeLinkReg;
	}
	public void setComposeLinkReg(String composeLinkReg) {
		this.composeLinkReg = composeLinkReg;
	}
	public String getComposeContentReg() {
		return composeContentReg;
	}
	public void setComposeContentReg(String composeContentReg) {
		this.composeContentReg = composeContentReg;
	}
	@Override
	public String toString() {
		return "Feed [id=" + id + ", webRawTitleName=" + webRawTitleName + ", webRawDesc=" + webRawDesc + ", webPath="
				+ webPath + ", webEncode=" + webEncode + ", webGlobalReg=" + webGlobalReg + ", webItemReg=" + webItemReg
				+ ", composeTitleReg=" + composeTitleReg + ", composeLinkReg=" + composeLinkReg + ", composeContentReg="
				+ composeContentReg + "]";
	}
}
