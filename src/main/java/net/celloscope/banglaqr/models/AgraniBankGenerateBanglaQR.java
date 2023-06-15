package net.celloscope.banglaqr.models;

import com.emv.qrcode.core.isos.Country;
import com.emv.qrcode.core.isos.Currency;
import com.emv.qrcode.model.mpm.AdditionalDataField;
import com.emv.qrcode.model.mpm.AdditionalDataFieldTemplate;
import com.emv.qrcode.model.mpm.MerchantAccountInformationReservedAdditional;
import com.emv.qrcode.model.mpm.MerchantAccountInformationTemplate;
import com.emv.qrcode.model.mpm.MerchantPresentedMode;
import com.emv.qrcode.model.mpm.PaymentSystemSpecific;
import com.emv.qrcode.model.mpm.PaymentSystemSpecificTemplate;

import lombok.Getter;

@Getter
public class AgraniBankGenerateBanglaQR {

	private MerchantPresentedMode mpm = new MerchantPresentedMode();

	public AgraniBankGenerateBanglaQR() {
		mpm.setPayloadFormatIndicator("01");
		mpm.setCountryCode(Country.BN.getAlpha2());
		mpm.setTransactionCurrency(Currency.BDT.getNumber());
		mpm.setMerchantCategoryCode("0000");
	}

	public String output() {
		return mpm.toString();
	}

	public void setNPSBNetworkSpecific(String globallyUniqueIdentifier, String merchantType, String merchantAccount) {
		final NPSBDataField npsb = new NPSBDataField();
		npsb.setGloballyUniqueIdentifier(globallyUniqueIdentifier);
		npsb.setAquirerInstitutionType("00");
		npsb.setAquireID("0010");
		if (merchantType.equals("REGULAR")) {
			npsb.setMerchantPAN("900101" + merchantAccount);
		} else if (merchantType.equals("MICRO")) {
			npsb.setMerchantPAN("900102" + merchantAccount);
		}

		this.setMerchantInformationforNPSB(npsb);

	}

	public void setAgraniBankSpecificStaticQR(String globallyUniqueIdentifier, String transactionType, String branchName,
			String accountTile, String accountNumber, String merchantCity) {
		final AgraniBankDataField agrani = new AgraniBankDataField();
		agrani.setGloballyUniqueIdentifier(globallyUniqueIdentifier);
		agrani.setTransactionType(transactionType);
		agrani.setBranchName(branchName);
		agrani.setAccountTitle(accountTile);
		agrani.setAccountNumber(accountNumber);
		agrani.setMerchantCity(merchantCity);

		this.mpm.setPointOfInitiationMethod("11");
		this.mpm.setMerchantName(accountTile);
		this.mpm.setMerchantCity(merchantCity);

		this.setAdditionalDataForAgraniBank(agrani);
	}

	public void setAgraniBankSpecificDynamicQR(String globallyUniqueIdentifier, String transactionType, String branchName,
			String accountTile, String accountNumber, String merchantCity, String TransactionAmount) {
		final AgraniBankDataField agrani = new AgraniBankDataField();
		agrani.setGloballyUniqueIdentifier(globallyUniqueIdentifier);
		agrani.setTransactionType(transactionType);
		agrani.setBranchName(branchName);
		agrani.setAccountTitle(accountTile);
		agrani.setAccountNumber(accountNumber);
		agrani.setMerchantCity(merchantCity);
		agrani.setTransactionAmount(TransactionAmount);

		this.mpm.setPointOfInitiationMethod("12");
		this.mpm.setMerchantName(accountTile);
		this.mpm.setMerchantCity(merchantCity);
		this.mpm.setTransactionAmount(TransactionAmount);

		this.setAdditionalDataForAgraniBank(agrani);
	}

	private void setMerchantInformationforNPSB(NPSBDataField npsb) {
		final MerchantAccountInformationReservedAdditional data = new MerchantAccountInformationReservedAdditional();
		data.setGloballyUniqueIdentifier(npsb.getGloballyUniqueIdentifier().getValue());
		data.addPaymentNetworkSpecific(npsb.getAquirerInstitutionType());
		data.addPaymentNetworkSpecific(npsb.getAquireID());
		data.addPaymentNetworkSpecific(npsb.getMerchantPAN());

		this.mpm.addMerchantAccountInformation(new MerchantAccountInformationTemplate("26", data));
	}

	private void setAdditionalDataForAgraniBank(AgraniBankDataField agrani) {
		final PaymentSystemSpecific payment = new PaymentSystemSpecific();
		payment.setGloballyUniqueIdentifier(agrani.getGloballyUniqueIdentifier().getValue());
		payment.addPaymentSystemSpecific(agrani.getTransactionType());
		payment.addPaymentSystemSpecific(agrani.getBranchName());
		payment.addPaymentSystemSpecific(agrani.getAccountTitle());
		payment.addPaymentSystemSpecific(agrani.getAccountNumber());

		final PaymentSystemSpecificTemplate template = new PaymentSystemSpecificTemplate();
		template.setTag("50");
		template.setValue(payment);

		final AdditionalDataField additional = new AdditionalDataField();
		additional.addPaymentSystemSpecific(template);

		final AdditionalDataFieldTemplate additionalTemplate = new AdditionalDataFieldTemplate();
		additionalTemplate.setValue(additional);

		this.mpm.setAdditionalDataField(additionalTemplate);
	}

}
