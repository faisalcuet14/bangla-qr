package net.celloscope.banglaqr.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MerchantBasicInformation {

	private String qrType;
	private String category;
	private String country;
	private String city;
	private String currency;

}
