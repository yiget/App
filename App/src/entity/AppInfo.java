package entity;

import java.sql.Date;

/**
 * 基础信息表
 * @author Administrator
 *
 */
public class AppInfo {
	private int id;//主键
	private String softwareName;//软件名称
	private String APKName;//APK名称
	private String supportROM;//支持ROM
	private String interfaceLanguage;//界面语言
	private double softwareSize;//软件大小
	private Date updateDate;//更新日期
	private int devld;//开发者Id
	private String appInfo;//应用简介
	private int status;//状态
	private Date onSaleDate;//上架时间
	private Date offSaleDate;//下架时间
	private int flatformId;//所属平台
	private int categoryLevel3;//所属三级分类
	private int downloads;//下载量
	private int createdBy;//创建者
	private Date creationDate;//创建时间
	private int modifyBy;//更新者
	private Date modifyDate;//最新更新时间
	private int categoryLevel1;//所属一级分类
	private int categoryLevel2;//所属二级分类
	private String logoPicPath;//LOGO图片url路径
	private String logoLocPath;//LOGO图片的服务器存储路径
	private int versionId;//最新的版本id
	
	private String flatformName; 		//所属平台
	private String categoryLevel1Name;  //一级分类
	private String categoryLevel2Name;  //二级分类
	private String categoryLevel3Name;  //三级分类
	private String statusName;			//App状态
	private String versionNo; 			//版本号
	public String getFlatformName() {
		return flatformName;
	}
	public void setFlatformName(String flatformName) {
		this.flatformName = flatformName;
	}
	public String getCategoryLevel1Name() {
		return categoryLevel1Name;
	}
	public void setCategoryLevel1Name(String categoryLevel1Name) {
		this.categoryLevel1Name = categoryLevel1Name;
	}
	public String getCategoryLevel2Name() {
		return categoryLevel2Name;
	}
	public void setCategoryLevel2Name(String categoryLevel2Name) {
		this.categoryLevel2Name = categoryLevel2Name;
	}
	public String getCategoryLevel3Name() {
		return categoryLevel3Name;
	}
	public void setCategoryLevel3Name(String categoryLevel3Name) {
		this.categoryLevel3Name = categoryLevel3Name;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSoftwareName() {
		return softwareName;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public String getAPKName() {
		return APKName;
	}
	public void setAPKName(String aPKName) {
		APKName = aPKName;
	}
	public String getSupportROM() {
		return supportROM;
	}
	public void setSupportROM(String supportROM) {
		this.supportROM = supportROM;
	}
	public String getInterfaceLanguage() {
		return interfaceLanguage;
	}
	public void setInterfaceLanguage(String interfaceLanguage) {
		this.interfaceLanguage = interfaceLanguage;
	}
	public double getSoftwareSize() {
		return softwareSize;
	}
	public void setSoftwareSize(double softwareSize) {
		this.softwareSize = softwareSize;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getDevld() {
		return devld;
	}
	public void setDevld(int devld) {
		this.devld = devld;
	}
	public String getAppInfo() {
		return appInfo;
	}
	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getOnSaleDate() {
		return onSaleDate;
	}
	public void setOnSaleDate(Date onSaleDate) {
		this.onSaleDate = onSaleDate;
	}
	public Date getOffSaleDate() {
		return offSaleDate;
	}
	public void setOffSaleDate(Date offSaleDate) {
		this.offSaleDate = offSaleDate;
	}
	public int getFlatformId() {
		return flatformId;
	}
	public void setFlatformId(int flatformId) {
		this.flatformId = flatformId;
	}
	public int getCategoryLevel3() {
		return categoryLevel3;
	}
	public void setCategoryLevel3(int categoryLevel3) {
		this.categoryLevel3 = categoryLevel3;
	}
	public int getDownloads() {
		return downloads;
	}
	public void setDownloads(int downloads) {
		this.downloads = downloads;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public int getCategoryLevel1() {
		return categoryLevel1;
	}
	public void setCategoryLevel1(int categoryLevel1) {
		this.categoryLevel1 = categoryLevel1;
	}
	public int getCategoryLevel2() {
		return categoryLevel2;
	}
	public void setCategoryLevel2(int categoryLevel2) {
		this.categoryLevel2 = categoryLevel2;
	}
	public String getLogoPicPath() {
		return logoPicPath;
	}
	public void setLogoPicPath(String logoPicPath) {
		this.logoPicPath = logoPicPath;
	}
	public String getLogoLocPath() {
		return logoLocPath;
	}
	public void setLogoLocPath(String logoLocPath) {
		this.logoLocPath = logoLocPath;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
}
