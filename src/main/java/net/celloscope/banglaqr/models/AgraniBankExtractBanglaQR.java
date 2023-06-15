package net.celloscope.banglaqr.models;

import java.util.ArrayList;
import java.util.List;

import com.emv.qrcode.core.model.mpm.TagLengthString;
import com.emv.qrcode.decoder.mpm.AdditionalDataFieldTemplateDecoder;
import com.emv.qrcode.decoder.mpm.DecoderMpm;
import com.emv.qrcode.decoder.mpm.MerchantAccountInformationReservedAdditionalDecoder;
import com.emv.qrcode.decoder.mpm.MerchantAccountInformationTemplateDecoder;
import com.emv.qrcode.decoder.mpm.PaymentSystemSpecificDecoder;
import com.emv.qrcode.decoder.mpm.PaymentSystemSpecificTemplateDecoder;
import com.emv.qrcode.decoder.mpm.TagLengthStringDecoder;
import com.emv.qrcode.model.mpm.AdditionalDataFieldTemplate;
import com.emv.qrcode.model.mpm.MerchantAccountInformationReservedAdditional;
import com.emv.qrcode.model.mpm.MerchantAccountInformationTemplate;
import com.emv.qrcode.model.mpm.MerchantPresentedMode;
import com.emv.qrcode.model.mpm.PaymentSystemSpecific;
import com.emv.qrcode.model.mpm.PaymentSystemSpecificTemplate;

import lombok.Data;
import lombok.ToString;
import net.celloscope.banglaqr.domains.AgraniBankInformation;
import net.celloscope.banglaqr.domains.MerchantBasicInformation;
import net.celloscope.banglaqr.domains.NPSBInformation;

@Data
@ToString
public class AgraniBankExtractBanglaQR {

	public MerchantBasicInformation merchant;
	public NPSBInformation npsb;
	public AgraniBankInformation agrani;

	public AgraniBankExtractBanglaQR(String encodedQR) {
		this.merchant = new MerchantBasicInformation();
		this.npsb = new NPSBInformation();
		this.agrani = new AgraniBankInformation();

		MerchantPresentedMode mpm = DecoderMpm.decode(encodedQR, MerchantPresentedMode.class);
		getMerchantBasicInformation(mpm);
		getNPSBInformation(mpm);
		if (mpm.getPointOfInitiationMethod().getValue().equals("11")) {
			getAgraniBankInformation(mpm, "STATIC");
		} else if (mpm.getPointOfInitiationMethod().getValue().equals("12")) {
			getAgraniBankInformation(mpm, "DYNAMIC");
		}
	}

	private void getMerchantBasicInformation(MerchantPresentedMode mpm) {
		this.merchant.setCategory(mpm.getMerchantCategoryCode().getValue());
		this.merchant.setCountry(mpm.getCountryCode().getValue());
		this.merchant.setCity(mpm.getMerchantCity().getValue());
		this.merchant.setCurrency(mpm.getTransactionCurrency().getValue());
		if (mpm.getPointOfInitiationMethod().getValue().equals("11")) {
			this.merchant.setQrType("STATIC");
		} else if (mpm.getPointOfInitiationMethod().getValue().equals("12")) {
			this.merchant.setQrType("DYNAMIC");
		}
	}

	public void getNPSBInformation(MerchantPresentedMode mpm) {

		MerchantAccountInformationTemplate mpmInfoTemplate = MerchantAccountInformationTemplateDecoder.decode(
				mpm.getMerchantAccountInformation().values().toArray()[0].toString(), MerchantAccountInformationTemplate.class);
		MerchantAccountInformationReservedAdditional paymentNetworkSpecific = MerchantAccountInformationReservedAdditionalDecoder
				.decode(mpmInfoTemplate.toString(), MerchantAccountInformationReservedAdditional.class);

		this.npsb.setGloballyUniqueIdentifier(paymentNetworkSpecific.getGloballyUniqueIdentifier().getValue());

		List<TagLengthString> data = new ArrayList<TagLengthString>();
		paymentNetworkSpecific.getPaymentNetworkSpecific().values().stream().forEach(value -> {
			data.add(TagLengthStringDecoder.decode(value.toString(), TagLengthString.class));
		});

		this.npsb.setAquirerInstitutionType(data.get(0).getValue());
		this.npsb.setAquireID(data.get(1).getValue());
		this.npsb.setMerchantPAN(data.get(2).getValue());
		String type = data.get(2).getValue().substring(0, 6);
		if (type.equals("900101")) {
			this.npsb.setMerchantType("REGULAR");
		} else if (type.equals("900102")) {
			this.npsb.setMerchantType("MICRO");
		}

	}

	private void getAgraniBankInformation(MerchantPresentedMode mpm, String qrType) {

		AdditionalDataFieldTemplate additionalData = AdditionalDataFieldTemplateDecoder
				.decode(mpm.getAdditionalDataField().toString(), AdditionalDataFieldTemplate.class);
		PaymentSystemSpecificTemplate paymentTemplate = PaymentSystemSpecificTemplateDecoder.decode(
				additionalData.getValue().toString(),
				PaymentSystemSpecificTemplate.class);
		PaymentSystemSpecific paymentSystemSpecific = PaymentSystemSpecificDecoder.decode(paymentTemplate.toString(),
				PaymentSystemSpecific.class);
		this.agrani.setGloballyUniqueIdentifier(paymentSystemSpecific.getGloballyUniqueIdentifier().getValue());

		List<TagLengthString> data = new ArrayList<TagLengthString>();
		paymentSystemSpecific.getPaymentSystemSpecific().values().stream().forEach(value -> {
			data.add(TagLengthStringDecoder.decode(value.toString(), TagLengthString.class));
		});

		this.agrani.setTransactionType(data.get(0).getValue());
		this.agrani.setBranchName(data.get(1).getValue());
		this.agrani.setAccountTitle(data.get(2).getValue());
		this.agrani.setAccountNumber(data.get(3).getValue());

		if (qrType.equals("DYNAMIC")) {
			this.agrani.setTransactionAmount(mpm.getTransactionAmount().getValue());
		}

	}
}
