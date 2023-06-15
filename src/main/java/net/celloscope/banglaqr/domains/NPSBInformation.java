package net.celloscope.banglaqr.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NPSBInformation {

	private String globallyUniqueIdentifier;
	private String aquirerInstitutionType;
	private String aquireID;
	private String merchantType;
	private String merchantPAN;

}
