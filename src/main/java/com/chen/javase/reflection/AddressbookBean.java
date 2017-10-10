package com.chen.javase.reflection;

import java.util.Date;

public class AddressbookBean {

    private String id;    //地址薄ID
    private String name;    //名称
    private String account;
    private String petName;    //昵称
    private String department;    //部门
    private String jobTitle;    //职位
    private String emails;        //邮箱
    private String email;        //邮箱
    private String phones;    //联系号码              //openorg中的逻辑case when p.isHidePhone=1 then null else  concat(ifnull(p.phones,''),',',ifnull(p.phone,''))  end phones,
    private String phone;    //联系号码            //t_bd_person.phone
    private String photoUrl;    //头像
    private String photoId;
    private String firstPinyinCode;    //
    private String firstPinyin;
    private String fullPinyin;    //全拼
    private String fullPinyinCode;
    private Date xtCreateTime;
    private Date xtUpdateTime;
    private int xtStatus;
    private String easPersonId;
    private String kdWeiboUserId;
    private int isPartJob;
	private Date xtOpenDate;
    private int favFlag;
    private String no;
    private String eid;
    private String orgId;
    private String orgNum;//部门num
    private String wbUserId;
    private String wbNetworkId;
    private String openId;
    private String oId;
    private String eName;
    private int isAdmin;
    private String organization;
    private Object parttimejob;
    private String departOrgId;   //部门对应的组织id
    private String departOrgName; //部门对应的组织名称
    private int status ;
    private Object contact;	//联系人
    private boolean isHidePhoneOrAccount;
    private int orgUserType;//是否组织负责人
    
    private int partnerType=0;//0内部，1，外部，2，内部兼职外部

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getFirstPinyinCode() {
		return firstPinyinCode;
	}

	public void setFirstPinyinCode(String firstPinyinCode) {
		this.firstPinyinCode = firstPinyinCode;
	}

	public String getFirstPinyin() {
		return firstPinyin;
	}

	public void setFirstPinyin(String firstPinyin) {
		this.firstPinyin = firstPinyin;
	}

	public String getFullPinyin() {
		return fullPinyin;
	}

	public void setFullPinyin(String fullPinyin) {
		this.fullPinyin = fullPinyin;
	}

	public String getFullPinyinCode() {
		return fullPinyinCode;
	}

	public void setFullPinyinCode(String fullPinyinCode) {
		this.fullPinyinCode = fullPinyinCode;
	}

	public Date getXtCreateTime() {
		return xtCreateTime;
	}

	public void setXtCreateTime(Date xtCreateTime) {
		this.xtCreateTime = xtCreateTime;
	}

	public Date getXtUpdateTime() {
		return xtUpdateTime;
	}

	public void setXtUpdateTime(Date xtUpdateTime) {
		this.xtUpdateTime = xtUpdateTime;
	}

	public int getXtStatus() {
		return xtStatus;
	}

	public void setXtStatus(int xtStatus) {
		this.xtStatus = xtStatus;
	}

	public String getEasPersonId() {
		return easPersonId;
	}

	public void setEasPersonId(String easPersonId) {
		this.easPersonId = easPersonId;
	}

	public String getKdWeiboUserId() {
		return kdWeiboUserId;
	}

	public void setKdWeiboUserId(String kdWeiboUserId) {
		this.kdWeiboUserId = kdWeiboUserId;
	}

	public int getIsPartJob() {
		return isPartJob;
	}

	public void setIsPartJob(int isPartJob) {
		this.isPartJob = isPartJob;
	}

	public Date getXtOpenDate() {
		return xtOpenDate;
	}

	public void setXtOpenDate(Date xtOpenDate) {
		this.xtOpenDate = xtOpenDate;
	}

	public int getFavFlag() {
		return favFlag;
	}

	public void setFavFlag(int favFlag) {
		this.favFlag = favFlag;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgNum() {
		return orgNum;
	}

	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}

	public String getWbUserId() {
		return wbUserId;
	}

	public void setWbUserId(String wbUserId) {
		this.wbUserId = wbUserId;
	}

	public String getWbNetworkId() {
		return wbNetworkId;
	}

	public void setWbNetworkId(String wbNetworkId) {
		this.wbNetworkId = wbNetworkId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Object getParttimejob() {
		return parttimejob;
	}

	public void setParttimejob(Object parttimejob) {
		this.parttimejob = parttimejob;
	}

	public String getDepartOrgId() {
		return departOrgId;
	}

	public void setDepartOrgId(String departOrgId) {
		this.departOrgId = departOrgId;
	}

	public String getDepartOrgName() {
		return departOrgName;
	}

	public void setDepartOrgName(String departOrgName) {
		this.departOrgName = departOrgName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getContact() {
		return contact;
	}

	public void setContact(Object contact) {
		this.contact = contact;
	}

	public boolean isHidePhoneOrAccount() {
		return isHidePhoneOrAccount;
	}

	public void setHidePhoneOrAccount(boolean isHidePhoneOrAccount) {
		this.isHidePhoneOrAccount = isHidePhoneOrAccount;
	}

	public int getOrgUserType() {
		return orgUserType;
	}

	public void setOrgUserType(int orgUserType) {
		this.orgUserType = orgUserType;
	}

	public int getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(int partnerType) {
		this.partnerType = partnerType;
	}

    
}
