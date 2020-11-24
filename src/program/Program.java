package program;

/*
 * Author: Lucas Correia Forte ID: 2020379
 * Lecturer: Ken Healy
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Program 
{
    /* Validates if name is text only.
     * returns true if no number is present in the given string.
     */
    public static boolean isValidName(String name)
    {
        char[] nameArray = name.toCharArray();
        for(int i = 0; i < nameArray.length; i++)
        {
            if (nameArray[i] >= '0' && nameArray[i] <= '9')
            {
                return false;
            }
        }
        return true;
    }
    
    /* Validates gender.
     * returns true if gender is F, M or T only.
     */
    public static boolean isValidGender(String gender)
    {
        char genderType = gender.charAt(0);
        // Making sure theres only one character in the String
        if(gender.length() > 1)
        {
            return false;
        }
        else if(genderType != 'F' && genderType != 'M' && genderType != 'T' )
        {
            return false;
        }    
        return true;
    }
    
    /* 
     * Receives age and returns status.
     */
    public static String getPersonStatus(int age)
    {
        if(age > 0 && age <= 18)
        {
            return "School";
        }
        else if(age > 18 && age <= 25)
        {
            return "College";
        }
        else if(age > 25 && age <= 66)
        {
            return "Worker";
        }
        else if(age > 66 && age < 101)
        {
            return "Retired";
        }
        else
        {
            return "Error: Invalid Age";  
        }
    }
    
    /* 
     * Receives gender and returns title.
     */
    public static String getPersonTitle(String gender)
    {
        switch(gender)
        {
            case "F":
                return "Ms";
            case "M":
                return "Mr";
            case "T":
                return "Mx";
            default:
                return "Error: Invalid Gender";
        }
    }
    
            
    public static void main(String[] args) 
    {
       try
       { 
            // initialyzing the buffers.
            BufferedReader myReader = new BufferedReader (new FileReader ("people2.txt") );
            BufferedWriter myWriter = new BufferedWriter(new FileWriter("status.txt", false));
            // Initialyzing the flag variable that indicate whether the output should be appended to the file or not.
            boolean shouldCreateNewFile = true;
            // Making sure it goes at least once in the loop.
            do
            {
                // Reading data files and storing into respective variables.    
                String personFullName = myReader.readLine();
                // Reached EOF.
                if (personFullName == null) {
                    break;
                }

                // Separating first name and surname.
                String[] nameSplit = personFullName.split(" ", 2);
                String personName = nameSplit[0], personSurname = nameSplit[1];
                // Reading age & gender
                int personAge = Integer.parseInt(myReader.readLine());
                String personGender = myReader.readLine();
                
                /* Generation the String output. Following the format:
                 * <title> <surname>, <first initial>
                 * <status>   
                 */
                String output =  getPersonTitle(personGender) + ". " + personSurname + ", " + personName.charAt(0);
                output += "\n" + getPersonStatus(personAge);
                
                String errorMsg = "";
                // Validation flow.
                // Name validation - task 2) a.
                if(!isValidName(personFullName))
                {
                    errorMsg += "ERROR: Name cannot have numbers. ";
                    System.out.println("ERROR: Name cannot have numbers.");
                }
                // Age validation - task 2) b.
                if(personAge < 0 || personAge > 100)
                {
                    errorMsg += "ERROR: Age must be between 0 and 100. ";
                    System.out.println("ERROR: Age must be between 0 and 100.");
                }
                // Gender validation - task 2) c.
                if(!isValidGender(personGender))
                {
                    errorMsg += "ERROR: Gender must be F, M or T.";
                    System.out.println("ERROR: Gender must be F, M or T.");
                }
                // If there is invalid data, append to file an error message.
                if(!errorMsg.isEmpty())
                {
                    output = errorMsg + "\n";
                }
                
                // First time the file must be initialyzed, or overwritten.
                if(shouldCreateNewFile)
                { 
                    myWriter.write(output);
                    myWriter.close();
                    // Setting the flag to false, because the file has been created.
                    shouldCreateNewFile = false;
                } 
                else
                {
                    // Appending to file.
                    myWriter = new BufferedWriter(new FileWriter("status.txt", true));
                    myWriter.write("\n" + output);
                    myWriter.close();
                    
                }
            } while(true);
       }
       catch(Exception e)
       {
            System.out.println(e);
       }

    }   
}
