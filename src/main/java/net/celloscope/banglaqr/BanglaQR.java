package net.celloscope.banglaqr;

import net.celloscope.banglaqr.models.AgraniBankExtractBanglaQR;
import net.celloscope.banglaqr.models.AgraniBankGenerateBanglaQR;

public class BanglaQR {

	public static void main(String[] args) {

		final AgraniBankGenerateBanglaQR banglaQR = new AgraniBankGenerateBanglaQR();

		banglaQR.setNPSBNetworkSpecific("net.celloscope.api", "REGULAR", "1234567890123");

		// banglaQR.setAgraniBankSpecificStaticQR("com.agrani.banglaqr", "QR2QR",
		// 		"BARISHAL", "MR AHMED", "1234567890123",
		// 		"BARISHAL");

		banglaQR.setAgraniBankSpecificDynamicQR("com.agrani.banglaqr", "QR2QR",
		"BARISHAL", "MR AHMED", "1234567890123",
		"BARISHAL", "123.34");

		// System.out.println(banglaQR.output());

		AgraniBankExtractBanglaQR extractedQR = new AgraniBankExtractBanglaQR(banglaQR.output());
		System.out.println(extractedQR);
	}
}
