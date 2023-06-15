package net.celloscope.banglaqr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emv.qrcode.core.model.mpm.TagLengthString;
import com.emv.qrcode.decoder.mpm.AdditionalDataFieldDecoder;
import com.emv.qrcode.decoder.mpm.AdditionalDataFieldTemplateDecoder;
import com.emv.qrcode.decoder.mpm.DecoderMpm;
import com.emv.qrcode.decoder.mpm.MerchantAccountInformationReservedAdditionalDecoder;
import com.emv.qrcode.decoder.mpm.MerchantAccountInformationTemplateDecoder;
import com.emv.qrcode.decoder.mpm.PaymentSystemSpecificDecoder;
import com.emv.qrcode.decoder.mpm.PaymentSystemSpecificTemplateDecoder;
import com.emv.qrcode.decoder.mpm.StringDecoder;
import com.emv.qrcode.decoder.mpm.TagLengthStringDecoder;
import com.emv.qrcode.model.mpm.AdditionalDataFieldTemplate;
import com.emv.qrcode.model.mpm.MerchantAccountInformationReservedAdditional;
import com.emv.qrcode.model.mpm.MerchantAccountInformationTemplate;
import com.emv.qrcode.model.mpm.MerchantPresentedMode;
import com.emv.qrcode.model.mpm.PaymentSystemSpecific;
import com.emv.qrcode.model.mpm.PaymentSystemSpecificTemplate;

import net.celloscope.banglaqr.models.AgraniBankGenerateBanglaQR;

public class App {
	public static void main(String[] args) {
		final AgraniBankGenerateBanglaQR banglaQR = new AgraniBankGenerateBanglaQR();

		banglaQR.setNPSBNetworkSpecific("net.celloscope.api", "REGULAR", "1234567890123");
		banglaQR.setAgraniBankSpecificStaticQR("com.agrani.banglaqr", "QR2QR", "BARISHAL", "MR AHMED", "1234567890123",
				"BARISHAL");

		// System.out.println(banglaQR.output());

		final MerchantPresentedMode mpm = DecoderMpm.decode(banglaQR.output(), MerchantPresentedMode.class);
		MerchantAccountInformationTemplate decode = MerchantAccountInformationTemplateDecoder.decode(
				mpm.getMerchantAccountInformation().values().toArray()[0].toString(), MerchantAccountInformationTemplate.class);
		// System.out.println(decode);
		// System.out.println(decode.getValue());
		MerchantAccountInformationReservedAdditional decode2 = MerchantAccountInformationReservedAdditionalDecoder
				.decode(decode.toString(), MerchantAccountInformationReservedAdditional.class);
		System.out.println(decode2.getGloballyUniqueIdentifier().getValue());
		System.out.println(decode2.getPaymentNetworkSpecific());
		List<TagLengthString> data = new ArrayList<TagLengthString>();
		decode2.getPaymentNetworkSpecific().values().stream().forEach(value -> {
			data.add(TagLengthStringDecoder.decode(value.toString(),
					TagLengthString.class));
		});
		data.forEach(val -> System.out.println(val.getValue()));

		// System.out.println(mpm.getAdditionalDataField());
		// AdditionalDataFieldTemplate decode3 = AdditionalDataFieldTemplateDecoder
		// .decode(mpm.getAdditionalDataField().toString(),
		// AdditionalDataFieldTemplate.class);
		// // System.out.println(decode3.getValue());

		// PaymentSystemSpecificTemplate decode4 =
		// PaymentSystemSpecificTemplateDecoder.decode(decode3.getValue().toString(),
		// PaymentSystemSpecificTemplate.class);
		// // System.out.println(decode4);
		// // System.out.println(decode4.getValue());
		// PaymentSystemSpecific decode5 =
		// PaymentSystemSpecificDecoder.decode(decode4.toString(),
		// PaymentSystemSpecific.class);
		// // System.out.println(decode5.getGloballyUniqueIdentifier().getValue());
		// System.out.println(decode5.getPaymentSystemSpecific());

		// TagLengthString decode6 = TagLengthStringDecoder
		// .decode(decode5.getPaymentSystemSpecific().values().toArray()[0].toString(),
		// TagLengthString.class);
		// // System.out.println(decode6.getValue());
		// List<TagLengthString> data = new ArrayList<TagLengthString>();
		// decode5.getPaymentSystemSpecific().values().stream().forEach(value->{
		// data.add(TagLengthStringDecoder.decode(value.toString(),
		// TagLengthString.class));
		// });
		// data.forEach(val -> System.out.println(val.getValue()));

		// TagLengthString data = mpm.getCountryCode();
		// System.out.println(data);
		// System.out.println("Tag: " + data.getTag() + " Value: " + data.getValue());
	}
}
