package net.celloscope.banglaqr.models;

// import java.util.LinkedHashMap;
// import java.util.Map;
import java.util.Optional;
// import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.emv.qrcode.core.model.mpm.TagLengthString;

import lombok.Getter;

@Getter
public class NPSBDataField {

	private TagLengthString globallyUniqueIdentifier;
	private TagLengthString aquirerInstitutionType;
	private TagLengthString aquireID;
	private TagLengthString merchantPAN;

	// private final Map<String, TagLengthString> rFUforNPSB = new
	// LinkedHashMap<>();

	public final void setGloballyUniqueIdentifier(final String globallyUniqueIdentifier) {
		this.globallyUniqueIdentifier = new TagLengthString("00", globallyUniqueIdentifier);
	}

	public final void setAquirerInstitutionType(final String aquirerInstitutionType) {
		this.aquirerInstitutionType = new TagLengthString("01", aquirerInstitutionType);
	}

	public final void setAquireID(final String aquireID) {
		this.aquireID = new TagLengthString("02", aquireID);
	}

	public final void setMerchantPAN(final String merchantPAN) {
		this.merchantPAN = new TagLengthString("03", merchantPAN);
	}

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder();

		Optional.ofNullable(globallyUniqueIdentifier).ifPresent(tlv -> sb.append(tlv.toString()));
		Optional.ofNullable(aquirerInstitutionType).ifPresent(tlv -> sb.append(tlv.toString()));
		Optional.ofNullable(aquireID).ifPresent(tlv -> sb.append(tlv.toString()));
		Optional.ofNullable(merchantPAN).ifPresent(tlv -> sb.append(tlv.toString()));

		// for (final Entry<String, TagLengthString> entry : rFUforNPSB.entrySet()) {
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
