package net.celloscope.banglaqr.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgraniBankInformation {

	private final String bankCode = "010";
	private String globallyUniqueIdentifier;
	private String transactionType;
	private String branchName;
	private String accountTitle;
	private String accountNumber;
	private String transactionAmount;

}
