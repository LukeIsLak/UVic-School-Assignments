public class A4Exercises {

	/*
	 * Purpose: gets the number of buildings in the given list
	 * Parameters: List bList - the list of buildings
	 * Returns: int - the number of buildings in the given list
	 */
	public static int buildingsCount(List bList) {
		// TODO: implement this
		return bList.size(); // so it compiles
	}
	
	/*
	 * Purpose: gets the total number of inhabitants living 
	 *          in all the buildings in the given list
	 * Parameters: List bList - the list of buildings
	 * Returns: int - the number of inhabitants across all buildings
	 */
	public static int inhabitantsCount(List bList) {
		int sumInhabitants = 0;
		for(int i = 0;i < bList.size(); i++){
			sumInhabitants += bList.get(i).getNumberOfInhabitants();
		}
		return sumInhabitants; // so it compiles
	}
	
	/*
	 * Purpose: get the number of buildings into the list b is
	 * Parameters: List bList - the list of buildings
	 *             Building b - the building to find
	 * Returns: int - the distance the first occurrence of 
	 *                b is from the start of the list, or 
	 *                -1 if b is not found in bList
	 */
	public static int distanceAway(List bList, Building b) {
		// TODO: implement this
		return bList.find(b); // so it compiles
	}
	
	/*
	 * Purpose: get the distance the tallest building is 
	 *          from the start of the list
	 * Parameters: List bList - the list of buildings
	 * Returns: int - the distance the tallest building
	 *                is from the start of the list
	 */
	public static int distanceToTallest(List bList) {
		Building currTallest = null;
		for (int i = 0; i < bList.size(); i ++) {
			if (currTallest == null) {
				currTallest = bList.get(i);
			}
			else {
				if (currTallest.getNumberOfStories() < bList.get(i).getNumberOfStories()) {
					currTallest = bList.get(i);
				}
			}
		}
		return distanceAway(bList, currTallest); // so it compiles
	}

	/*
	 * Purpose: get the number of buildings visible 
	 *          from the beginning of the list 
	 * Parameters: List bList - the list of buildings
	 * Returns: int - the number of buildings visible
	 * 
	 * Note: Read through the assignment PDF for more information
	 */
	public static int numberVisible(List bList) {
		int numVisible = (bList.size() != 1)?0: 1;
		int tallestVal = -1;
		for (int i = 1; i < bList.size(); i ++) {
			if (tallestVal == -1) {
				tallestVal = bList.get(0).getNumberOfStories();
				numVisible ++;
			}
			if (tallestVal < bList.get(i).getNumberOfStories()) {
				numVisible ++;
				tallestVal = bList.get(i).getNumberOfStories();
			}
		}
		return numVisible; // so it compiles
	}
		


}