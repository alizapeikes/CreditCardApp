
public enum USState {AL, AK, AZ, AR, CA, CO, CT, DE,
	FL, GA, HI, ID, IL, IN, IA, KS, KY, LA, ME, MD, MA, MI, 
	MN, MS, MO, MT, NE, NV, NH, NJ, NM, NY, NC, ND, OH, OK,
	OR, PA, RI, SC, SD, TN, TX, UT, VT, VA, WA, WV, WI, WY;

	public static boolean contains(String state) {
		for (USState s : USState.values()) 
			if (s.toString().equals(state)) {
				return true; 
		} 
		return false;
	}
}