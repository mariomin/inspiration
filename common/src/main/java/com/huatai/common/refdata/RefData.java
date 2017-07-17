package com.huatai.common.refdata;

import java.sql.Timestamp;
import java.util.BitSet;

import com.huatai.common.data.SingleFinancialData;
import com.huatai.common.type.Currency;

public final class RefData extends SingleFinancialData {
	private static final long serialVersionUID = 1L;
	private String chineseName;
	private String spellCode;
	private String englishName;
	private String industryClassification;
	private Currency currency;
	private double parValue;
	private double outstandingShare;
	private double publicFloatShareQuantity;
	private Integer interestAccrualDate;
	private String crdBuyUnderlying;
	private String crdSellUnderlying;
	private double gageRatio;
	private BitSet status;
	private int qtyUnit;
	private double previousClosingPx;
	private Integer securityType;
	private double highLimitPx;
	private double lowLimitPx;
	private Timestamp created = new Timestamp(System.currentTimeMillis());
	private Timestamp modified = new Timestamp(System.currentTimeMillis());
	
	public RefData() {}
	
	public RefData(String symbol) {
		super(symbol);
	}
	
	public long roundToLots(double qty) {
		int qtyunit = getQtyUnit();
		if (qty > 0)
			return (long) ((qty / qtyunit)) * qtyunit;
		else return 0;
	}
	
	public String getChineseName() {
		return chineseName;
	}
	
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	public String getSpellCode() {
		return spellCode;
	}
	
	public void setSpellCode(String spellCode) {
		this.spellCode = spellCode;
	}
	
	public String getEnglishName() {
		return englishName;
	}
	
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	public String getIndustryClassification() {
		return industryClassification;
	}
	
	public void setIndustryClassification(String industryClassification) {
		this.industryClassification = industryClassification;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public double getParValue() {
		return parValue;
	}
	
	public void setParValue(double parValue) {
		this.parValue = parValue;
	}
	
	public double getOutstandingShare() {
		return outstandingShare;
	}
	
	public void setOutstandingShare(double outstandingShare) {
		this.outstandingShare = outstandingShare;
	}
	
	public double getPublicFloatShareQuantity() {
		return publicFloatShareQuantity;
	}
	
	public void setPublicFloatShareQuantity(double publicFloatShareQuantity) {
		this.publicFloatShareQuantity = publicFloatShareQuantity;
	}
	
	public Integer getInterestAccrualDate() {
		return interestAccrualDate;
	}
	
	public void setInterestAccrualDate(Integer interestAccrualDate) {
		this.interestAccrualDate = interestAccrualDate;
	}
	
	public String getCrdBuyUnderlying() {
		return crdBuyUnderlying;
	}
	
	public void setCrdBuyUnderlying(String crdBuyUnderlying) {
		this.crdBuyUnderlying = crdBuyUnderlying;
	}
	
	public String getCrdSellUnderlying() {
		return crdSellUnderlying;
	}
	
	public void setCrdSellUnderlying(String crdSellUnderlying) {
		this.crdSellUnderlying = crdSellUnderlying;
	}
	
	public double getGageRatio() {
		return gageRatio;
	}
	
	public void setGageRatio(double gageRatio) {
		this.gageRatio = gageRatio;
	}
	
	public int getQtyUnit() {
		return qtyUnit;
	}
	
	public void setQtyUnit(int qtyUnit) {
		this.qtyUnit = qtyUnit;
	}
	
	public double getPreviousClosingPx() {
		return previousClosingPx;
	}
	
	public void setPreviousClosingPx(double previousClosingPx) {
		this.previousClosingPx = previousClosingPx;
	}
	
	public Integer getSecurityType() {
		return securityType;
	}
	
	public void setSecurityType(Integer securityType) {
		this.securityType = securityType;
	}
	
	public String getStatus() {
		if (status == null) return null;
		return status.toString();
	}
	
	public void setStatus(String status) {
		if (status != null) {
			this.status = new BitSet(status.length());
			for (int i = 0; i < status.length(); i++) {
				if (status.charAt(i) == '1') {
					this.status.set(i);;
				}
			}
		}
	}
	
	public boolean isStartupBoardListing() {
		return (securityType != null) && (securityType == 3);
	}
	
	public boolean isRiskWarningListing() {
		return (status != null) && (status.get(4) || status.get(5));
	}
	
	public boolean isDelistedListing() {
		return (status != null) && status.get(10);
	}
	
	public boolean isSuspendingListing() {
		return (status != null) && status.get(1);
	}
	
	public double getHighLimitPx() {
		return highLimitPx;
	}
	
	public void setHighLimitPx(double highLimitPx) {
		this.highLimitPx = highLimitPx;
	}
	
	public double getLowLimitPx() {
		return lowLimitPx;
	}
	
	public void setLowLimitPx(double lowLimitPx) {
		this.lowLimitPx = lowLimitPx;
	}
	
	public Timestamp getCreated() {
		return created;
	}
	
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	public Timestamp getModified() {
		return modified;
	}
	
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("RefData [symbol=");
		sb.append(symbol);
		sb.append(", chineseName=");
		sb.append(chineseName);
		sb.append(", spellCode=");
		sb.append(spellCode);
		sb.append(", englishName=");
		sb.append(englishName);
		sb.append(", industryClassification=");
		sb.append(industryClassification);
		sb.append(", currency=");
		sb.append(currency);
		sb.append(", parValue=");
		sb.append(parValue);
		sb.append(", outstandingShare=");
		sb.append(outstandingShare);
		sb.append(", publicFloatShareQuantity=");
		sb.append(publicFloatShareQuantity);
		sb.append(", interestAccrualDate=");
		sb.append(interestAccrualDate);
		sb.append(", crdBuyUnderlying=");
		sb.append(crdBuyUnderlying);
		sb.append(", crdSellUnderlying=");
		sb.append(crdSellUnderlying);
		sb.append(", gageRatio=");
		sb.append(gageRatio);
		sb.append(", status=");
		sb.append(status);
		sb.append(", qtyUnit=");
		sb.append(qtyUnit);
		sb.append(", previousClosingPx=");
		sb.append(previousClosingPx);
		sb.append(", securityType=");
		sb.append(securityType);
		sb.append(", highLimitPx=");
		sb.append(highLimitPx);
		sb.append(", lowLimitPx=");
		sb.append(lowLimitPx);
		sb.append("]");
		return sb.toString();
	}
	
}
