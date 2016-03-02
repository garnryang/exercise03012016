package scott.exercise;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
* 
* @author  Scott Lee
* @version 1.0
* @since   2016-03-01 
*/
public class CategoryAnalyzerTest {

	private CategoryAnalyzer categoryAnalyzer;
	
	private final static String NEWLINE = System.getProperty("line.separator");
	
	@Before
	public void setUp() {

		categoryAnalyzer = new CategoryAnalyzer();
	}

	@After
	public void tearDown() {

		categoryAnalyzer = null;
	}

	@Test
	public void testProcessLine() {

		final String person1 = CategoryAnalyzer.CategoryEnum.PERSON + " Leonardo DiCaprio";
		final String person2 = CategoryAnalyzer.CategoryEnum.PERSON + " Chris Rock";
		final String person3 = CategoryAnalyzer.CategoryEnum.PERSON + " Leonardo DiCaprio";
		final String place1 = CategoryAnalyzer.CategoryEnum.PLACE + " Maryland";
		final String place2 = CategoryAnalyzer.CategoryEnum.PLACE + " Maryland";
		final String place3 = CategoryAnalyzer.CategoryEnum.PLACE + " Maryland";

		categoryAnalyzer.processLine(person1);
		categoryAnalyzer.processLine(person2);
		categoryAnalyzer.processLine(person3);
		categoryAnalyzer.processLine(place1);
		categoryAnalyzer.processLine(place2);
		categoryAnalyzer.processLine(place3);

		Assert.assertEquals(5, categoryAnalyzer.getCountingMap().size());
		Assert.assertEquals(2, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PERSON).size());
		Assert.assertEquals(1, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PLACE).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.ANIMAL).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.COMPUTER).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.OTHER).size());
		

		Assert.assertEquals(3, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().size());
		Assert.assertEquals(person1, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(0));
		Assert.assertEquals(person2, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(1));
		Assert.assertEquals(place1, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(2));
	}
	
	@Test
	public void testProcessLine_InvalidCategory() {

		final String person1 = CategoryAnalyzer.CategoryEnum.PERSON + " Leonardo DiCaprio";
		final String person2 = CategoryAnalyzer.CategoryEnum.PERSON + " Chris Rock";
		final String person3 = CategoryAnalyzer.CategoryEnum.PERSON + " Leonardo DiCaprio";
		final String place1 = CategoryAnalyzer.CategoryEnum.PLACE + " Maryland";
		final String place2 = CategoryAnalyzer.CategoryEnum.PLACE + " Maryland";
		final String place3 = CategoryAnalyzer.CategoryEnum.PLACE + " Maryland";
		final String invalidCategory = "POLICE" + " Maryland";

		categoryAnalyzer.processLine(person1);
		categoryAnalyzer.processLine(person2);
		categoryAnalyzer.processLine(person3);
		categoryAnalyzer.processLine(place1);
		categoryAnalyzer.processLine(invalidCategory);
		categoryAnalyzer.processLine(place2);
		categoryAnalyzer.processLine(place3);

		Assert.assertEquals(5, categoryAnalyzer.getCountingMap().size());
		Assert.assertEquals(2, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PERSON).size());
		Assert.assertEquals(1, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PLACE).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.ANIMAL).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.COMPUTER).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.OTHER).size());
		
		Assert.assertEquals(3, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().size());
		Assert.assertEquals(person1, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(0));
		Assert.assertEquals(person2, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(1));
		Assert.assertEquals(place1, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(2));
	}

	@Test
	public void testProcessFile_BaseCase() throws IOException {

		categoryAnalyzer.processFile("resources/input1.txt");

		Assert.assertEquals(5, categoryAnalyzer.getCountingMap().size());
		Assert.assertEquals(2, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PERSON).size());
		Assert.assertEquals(2, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PLACE).size());
		Assert.assertEquals(2, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.ANIMAL).size());
		Assert.assertEquals(1, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.COMPUTER).size());
		Assert.assertEquals(1, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.OTHER).size());
		
		Assert.assertEquals(8, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().size());
		Assert.assertEquals("PERSON Bob Jones", categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(0));
		Assert.assertEquals("PLACE Washington", categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(1));
		Assert.assertEquals("PERSON Mary", categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(2));
		Assert.assertEquals("COMPUTER Mac", categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(3));
		Assert.assertEquals("OTHER Tree", categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(4));
		Assert.assertEquals("ANIMAL Dog", categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(5));
		Assert.assertEquals("PLACE Texas", categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(6));
		Assert.assertEquals("ANIMAL Cat", categoryAnalyzer.getNonRepeatingFirstOccurrenceList().get(7));
	}
	
	@Test
	public void testProcessFile_NoValidCategory() throws IOException {

		categoryAnalyzer.processFile("resources/input2.txt");

		Assert.assertEquals(5, categoryAnalyzer.getCountingMap().size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PERSON).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PLACE).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.ANIMAL).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.COMPUTER).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.OTHER).size());
		
		Assert.assertEquals(0, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().size());
	}
	
	@Test
	public void testProcessFile_EmptyFile() throws IOException {

		categoryAnalyzer.processFile("resources/input3.txt");

		Assert.assertEquals(5, categoryAnalyzer.getCountingMap().size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PERSON).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.PLACE).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.ANIMAL).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.COMPUTER).size());
		Assert.assertEquals(0, categoryAnalyzer.getCountingMap().get(CategoryAnalyzer.CategoryEnum.OTHER).size());
		
		Assert.assertEquals(0, categoryAnalyzer.getNonRepeatingFirstOccurrenceList().size());
	}

	@Test(expected=FileNotFoundException.class)
	public void testProcessFile_NonExistingFile() throws IOException {

		categoryAnalyzer.processFile("resources/non_existing.txt");
	}
	
	@Test
	public void testCategoryEnum_Invalid() {
		
		CategoryAnalyzer.CategoryEnum chairCategory = CategoryAnalyzer.CategoryEnum.getEnum("CHIAR");
		Assert.assertNull(chairCategory);
	}
	
	
	@Test
	public void testPrintReport() throws IOException {
		
		ByteArrayOutputStream interceptedOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(interceptedOutputStream));
		
		categoryAnalyzer.processFile("resources/input1.txt");
		categoryAnalyzer.printReport();
		
		StringBuilder expectedPrintOut = new StringBuilder();
		expectedPrintOut.append("CATEGORY  COUNT" + NEWLINE);
		expectedPrintOut.append("PERSON  2" + NEWLINE);
		expectedPrintOut.append("PLACE  2" + NEWLINE);
		expectedPrintOut.append("ANIMAL  2" + NEWLINE);
		expectedPrintOut.append("COMPUTER  1" + NEWLINE);
		expectedPrintOut.append("OTHER  1" + NEWLINE);
		expectedPrintOut.append("PERSON Bob Jones" + NEWLINE);
		expectedPrintOut.append("PLACE Washington" + NEWLINE);
		expectedPrintOut.append("PERSON Mary" + NEWLINE);
		expectedPrintOut.append("COMPUTER Mac" + NEWLINE);
		expectedPrintOut.append("OTHER Tree" + NEWLINE);
		expectedPrintOut.append("ANIMAL Dog" + NEWLINE);
		expectedPrintOut.append("PLACE Texas" + NEWLINE);
		expectedPrintOut.append("ANIMAL Cat" + NEWLINE);
		
		Assert.assertEquals(expectedPrintOut.toString(), interceptedOutputStream.toString());
		
		System.setOut(null);
	}
	
	@Test
	public void testIntegration() throws IOException {
		
		ByteArrayOutputStream interceptedOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(interceptedOutputStream));
		
		String[] args = {"resources/input1.txt"};
		CategoryAnalyzer.main(args);

		StringBuilder expectedPrintOut = new StringBuilder();
		expectedPrintOut.append("CATEGORY  COUNT" + NEWLINE);
		expectedPrintOut.append("PERSON  2" + NEWLINE);
		expectedPrintOut.append("PLACE  2" + NEWLINE);
		expectedPrintOut.append("ANIMAL  2" + NEWLINE);
		expectedPrintOut.append("COMPUTER  1" + NEWLINE);
		expectedPrintOut.append("OTHER  1" + NEWLINE);
		expectedPrintOut.append("PERSON Bob Jones" + NEWLINE);
		expectedPrintOut.append("PLACE Washington" + NEWLINE);
		expectedPrintOut.append("PERSON Mary" + NEWLINE);
		expectedPrintOut.append("COMPUTER Mac" + NEWLINE);
		expectedPrintOut.append("OTHER Tree" + NEWLINE);
		expectedPrintOut.append("ANIMAL Dog" + NEWLINE);
		expectedPrintOut.append("PLACE Texas" + NEWLINE);
		expectedPrintOut.append("ANIMAL Cat" + NEWLINE);
		
		Assert.assertEquals(expectedPrintOut.toString(), interceptedOutputStream.toString());
		
		System.setOut(null);
	}
}
