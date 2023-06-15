package net.celloscope.banglaqr.models;

// import java.util.LinkedHashMap;
// import java.util.Map;
// import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.emv.qrcode.core.model.mpm.TagLengthString;

import lombok.Getter;

@Getter
public class AgraniBankDataField {
	private TagLengthString globallyUniqueIdentifier;
	private TagLengthString transactionType;
	private TagLengthString branchName;
	private TagLengthString accountTitle;
	private TagLengthString accountNumber;
	private String merchantCity;
	private String transactionAmount;

	// private final Map<String, TagLengthString> rFUforAgraniBank = new
	// LinkedHashMap<>();

	public void setGloballyUniqueIdentifier(String globallyUniqueIdentifier) {
		this.globallyUniqueIdentifier = new TagLengthString("00", globallyUniqueIdentifier);
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = new TagLengthString("01", transactionType);
	}

	public void setBranchName(String branchName) {
		this.branchName = new TagLengthString("02", branchName);
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = new TagLengthString("03", accountTitle);
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = new TagLengthString("04", accountNumber);
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();

		Optional.ofNullable(globallyUniqueIdentifier).ifPresent(tlv -> sb.append(tlv.toString()));
		Optional.ofNullable(transactionType).ifPresent(tlv -> sb.append(tlv.toString()));
		Optional.ofNullable(branchName).ifPresent(tlv -> sb.append(tlv.toString()));
		Optional.ofNullable(accountTitle).ifPresent(tlv -> sb.append(tlv.toString()));
		Optional.ofNullable(accountNumber).ifPresent(tlv -> sb.append(tlv.toString()));

		// for (final Entry<String, TagLengthString> entry :
		// rFUforAgraniBank.entrySet()) {
		// Optional.ofNullable(entry.getValue()).ifPresent(tlv ->
		// sb.append(tlv.toString()));
		// }

		final String string = sb.toString();

		if (StringUtils.isBlank(string)) {
			return StringUtils.EMPTY;
		}

		return string;
	}
}
