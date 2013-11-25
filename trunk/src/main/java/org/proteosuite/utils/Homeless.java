package org.proteosuite.utils;

public class Homeless {


	/*------------------------------------------------
	 * Get molecular composition from a given peptide
	 * @param sPeptide - Peptide sequence
	 * @return Molecular composition
	 -------------------------------------------------*/
	public static String getResidueComposition(String sPeptide) {
		/*
		 * -------------------------------------------------------------- Name
		 * 3-Sym 1-Sym Mono Avg Residues
		 * --------------------------------------------------------------
		 * Alanine Ala A 71.03711 71.08 C3H5NO Arginine Arg R 156.10111 156.2
		 * C6H12N4O Asparagine Asn N 114.04293 114.1 C4H6N2O2 Aspartic Acid Asp
		 * D 115.02694 115.1 C4H5NO3 Cysteine Cys C 103.00919 103.1 C3H5NOS
		 * Glutamic Acid Glu E 129.04259 129.1 C5H7NO3 Glutamine Gln Q 128.05858
		 * 128.1 C5H8N2O2 Glycine Gly G 57.02146 57.05 C2H3NO Histidine His H
		 * 137.05891 137.1 C6H7N3O Isoleucine Ile I 113.08406 113.2 C6H11NO
		 * Leucine Leu L 113.08406 113.2 C6H11NO Lysine Lys K 128.09496 128.2
		 * C6H12N2O Methionine Met M 131.04049 131.2 C5H9NOS Phenyalanine Phe F
		 * 147.06841 147.2 C9H9NO Proline Pro P 97.05276 97.12 C5H7NO Serine Ser
		 * S 87.03203 87.08 C3H5NO2 Threonine Thr T 101.04768 101.1 C4H7NO2
		 * Tryptophan Trp W 186.07931 186.2 C11H10N2O Tyrosine Tyr Y 163.06333
		 * 163.2 C9H9NO2 Valine Val V 99.06841 99.13 C5H9NO
		 * --------------------------------------------------------------
		 */

		int iCarb = 0, iHydro = 3, iNitro = 0, iOxy = 1, iSulf = 0; // ... Water
																	// molecule
																	// (H2O) +
																	// H1 ...//
		String sPeptideRet = "";
		for (int iI = 0; iI < sPeptide.length(); iI++) {
			switch (sPeptide.toUpperCase().charAt(iI)) {
			case 'A': {
				iCarb += 3;
				iHydro += 5;
				iNitro++;
				iOxy++;
				break;
			}
			case 'R': {
				iCarb += 6;
				iHydro += 12;
				iNitro += 4;
				iOxy++;
				break;
			}
			case 'N': {
				iCarb += 4;
				iHydro += 6;
				iNitro += 2;
				iOxy += 2;
				break;
			}
			case 'D': {
				iCarb += 4;
				iHydro += 5;
				iNitro++;
				iOxy += 3;
				break;
			}
			case 'C': {
				iCarb += 3;
				iHydro += 5;
				iNitro++;
				iOxy++;
				iSulf++;
				break;
			}
			case 'E': {
				iCarb += 5;
				iHydro += 7;
				iNitro++;
				iOxy += 3;
				break;
			}
			case 'Q': {
				iCarb += 5;
				iHydro += 8;
				iNitro += 2;
				iOxy += 2;
				break;
			}
			case 'G': {
				iCarb += 2;
				iHydro += 3;
				iNitro++;
				iOxy++;
				break;
			}
			case 'H': {
				iCarb += 6;
				iHydro += 7;
				iNitro += 3;
				iOxy++;
				break;
			}
			case 'I': {
				iCarb += 6;
				iHydro += 11;
				iNitro++;
				iOxy++;
				break;
			}
			case 'L': {
				iCarb += 6;
				iHydro += 11;
				iNitro++;
				iOxy++;
				break;
			}
			case 'K': {
				iCarb += 6;
				iHydro += 12;
				iNitro += 2;
				iOxy++;
				break;
			}
			case 'M': {
				iCarb += 5;
				iHydro += 9;
				iNitro++;
				iOxy++;
				iSulf++;
				break;
			}
			case 'F': {
				iCarb += 9;
				iHydro += 9;
				iNitro++;
				iOxy++;
				break;
			}
			case 'P': {
				iCarb += 5;
				iHydro += 7;
				iNitro++;
				iOxy++;
				break;
			}
			case 'S': {
				iCarb += 3;
				iHydro += 5;
				iNitro++;
				iOxy += 2;
				break;
			}
			case 'T': {
				iCarb += 4;
				iHydro += 7;
				iNitro++;
				iOxy += 2;
				break;
			}
			case 'W': {
				iCarb += 11;
				iHydro += 10;
				iNitro += 2;
				iOxy++;
				break;
			}
			case 'Y': {
				iCarb += 9;
				iHydro += 9;
				iNitro++;
				iOxy += 2;
				break;
			}
			case 'V': {
				iCarb += 5;
				iHydro += 9;
				iNitro++;
				iOxy++;
				break;
			}
			}
		}
		if (iCarb > 0) {
			sPeptideRet = "C" + iCarb;
		}
		if (iHydro > 0) {
			sPeptideRet = sPeptideRet + "H" + iHydro;
		}
		if (iNitro > 0) {
			sPeptideRet = sPeptideRet + "N" + iNitro;
		}
		if (iOxy > 0) {
			sPeptideRet = sPeptideRet + "O" + iOxy;
		}
		if (iSulf > 0) {
			sPeptideRet = sPeptideRet + "S" + iSulf;
		}
		return sPeptideRet;
	}
}
