import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GradeMe {

	static List <Name> names = new ArrayList<Name>();
	
	@Test
	public void testComparision() 
	{
		Name nameArray[] = {
	    new Name("BUNDY", "TERESSA",88),
	    new Name("SMITH", "ALLAN",70),
	    new Name("KING", "MADISON",88),
	    new Name("SMITH", "FRANCIS",85) };
	
		Name sortedArray[] = {
			    new Name("BUNDY", "TERESSA",88),
			    new Name("KING", "MADISON",88),
			    new Name("SMITH", "FRANCIS",85),
			    new Name("SMITH", "ALLAN",70) };
	
		List <Name> testNames = Arrays.asList(nameArray);
		Collections.sort(testNames, Collections.reverseOrder());
		
		Iterator <Name> itr = names.iterator();
		int count = 0;
		while (itr.hasNext())
		{
			assertEquals(sortedArray[count].toString(),itr.next().toString());
		}
	}

	public static List <Name> readFile (String file)
	{
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
				while ((sCurrentLine = br.readLine()) != null) {
					StringTokenizer strTok = new StringTokenizer(sCurrentLine,",");
					if(strTok.countTokens()==3)
					{
						String firstName = strTok.nextToken().trim();				
						String lastName = strTok.nextToken().trim();
						Integer score= new Integer(strTok.nextToken().trim());
						names.add(new Name(firstName,lastName,score));
					}else 
					{
						br.close();
						return null;
					}
				  }
				br.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
		return names;
	}
	
	public static boolean createFile (String file)
	{
		try {
				FileWriter fout=new FileWriter(file);  
				Iterator <Name> itr = names.iterator();
				while (itr.hasNext())
				{
					fout.write(itr.next().toString());
				}
				fout.close();
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		return true;
	}
	
	public static void main(String[] args) {
		names=readFile (args[0]);
        Collections.sort(names, Collections.reverseOrder());
        String outPutFileName = args[0].substring(0, args[0].lastIndexOf('.')) + "-graded.txt";
        createFile(outPutFileName);
        System.out.println("Finished : created "+ outPutFileName);
	}
}

class Name implements Comparable<Name> {
    private final String firstName, lastName;
    private final Integer score;
    
    public Name(String firstName, String lastName, int value) {
        if (firstName == null || lastName == null)
            throw new NullPointerException();
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = value;
    }

    public String firstName() { return firstName; }
    public String lastName()  { return lastName;  }
    public int score()  { return score;  }

    public boolean equals(Object o) {
        if (!(o instanceof Name))
            return false;
        Name n = (Name) o;
        return n.firstName.equals(firstName) && n.lastName.equals(lastName) && n.score==score;
    }

    public int hashCode() {
        return 31*firstName.hashCode() + lastName.hashCode() + score.hashCode();
    }

    public String toString() {
	return firstName + ", " + lastName + ", " +score + "\r\n";
    }

    public int compareTo(Name n) {
        int lastNameCmp = lastName.compareTo(n.lastName);
        int scoreCmp = score.compareTo(n.score);
        return ( scoreCmp !=0? scoreCmp : lastNameCmp !=0? lastNameCmp: firstName.compareTo(n.firstName));
    }
}
