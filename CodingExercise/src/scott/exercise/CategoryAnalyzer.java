package scott.exercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
* The CategoryAnalyzer read given filePath, 
* print out unique Category-SubCategory combination Count,
* and print out first occurrences of Category-SubCategory combination in order
* 
*
* @author  Scott Lee
* @version 1.0
* @since   2016-03-01 
*/
public class CategoryAnalyzer {

	enum CategoryEnum {
		PERSON, PLACE, ANIMAL, COMPUTER, OTHER;
		

		public static CategoryEnum getEnum(String name) {
			
			for (CategoryEnum eachEnum : values()) {
				if (eachEnum.name().equals(name)) {
					return eachEnum;
				}
			}
			
			return null;
		}
	}
	
	private List<String> nonRepeatingFirstOccurrenceList;
	
	private Map<CategoryEnum, Set<String>> countingMap;

	/**
	 * 
	 * Constructor
	 */
	public CategoryAnalyzer() {
		
		countingMap = new HashMap<>();
		
		for (CategoryEnum eachCategory : CategoryEnum.values()) {

			countingMap.put(eachCategory, new HashSet<String>());
		}
		
		nonRepeatingFirstOccurrenceList = new ArrayList<>();
	}

	/**
	 * 
	 * Read file by line and process
	 * 
	 * @param filePath
	 * @throws IOException 
	 */
	public void processFile(String filePath) throws IOException {

		File file = new File(filePath);

		BufferedReader br = new BufferedReader(new FileReader(file));

		String line = null;
		while ((line = br.readLine()) != null) {

			processLine(line);

		}

		br.close();
	}

	/**
	 * 
	 * Given a line, parse Category and SubCategory from it and populate data fields 
	 * based on the business rules
	 * 
	 * This is in protected scope to expose the method for testing. 
	 * 
	 * @param line
	 */
	protected void processLine(String line) {

		final int indexOfDelimiter = line.indexOf(" ");
		String category = line.substring(0, indexOfDelimiter);
		String subCategory = line.substring(indexOfDelimiter + 1);

		CategoryEnum categoryEnum = CategoryEnum.getEnum(category);

		/* If CategoryEnum cannot be found, parse Category is invalid and shall be ignored */
		if (null != categoryEnum) {

			Set<String> subCategorySet = countingMap.get(categoryEnum);
		
			if (!subCategorySet.contains(subCategory)) {
				/* this is the first occurrence of SubCategory */
				nonRepeatingFirstOccurrenceList.add(line);
			}

			/* raise the counter by adding the subCategory to the set */
			subCategorySet.add(subCategory);
		}
	}

	/**
	 * 
	 * Print report using the data fields based on the business rules
	 */
	public void printReport() {

		/* header */
		System.out.println("CATEGORY" + "  " + "COUNT");
		
		/* print Category and its unique SubCategory count in order */
		for (CategoryEnum eachCategory : CategoryEnum.values()) {

			System.out.println(eachCategory + "  " + getCountingMap().get(eachCategory).size());
		}

		/* printed order unique first occurrences */
		for (String eachFirstRecord : getNonRepeatingFirstOccurrenceList()) {
			
			System.out.println(eachFirstRecord);
		}
	}
	
	/**
	 * 
	 * args[0] = filePath
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		CategoryAnalyzer categoryAnalyzer = new CategoryAnalyzer();

		try {
			
			categoryAnalyzer.processFile(args[0]);
			categoryAnalyzer.printReport();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Return the list of first occurrence of lines
	 * 
	 * This is in protected scope to expose the method for testing.
	 * 
	 * @return nonRepeatingFirstOccurrenceList
	 */
	protected List<String> getNonRepeatingFirstOccurrenceList() {
		
		return nonRepeatingFirstOccurrenceList;
	}
	
	/**
	 * 
	 * Return the map of Category and set of SubCategories
	 * The size of set of SubCategories is the counter of occurrences.
	 * 
	 * @return countingMap
	 */
	protected Map<CategoryEnum, Set<String>> getCountingMap() {
		
		return countingMap;
	}
}