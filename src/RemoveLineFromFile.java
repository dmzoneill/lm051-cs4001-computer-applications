import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public final class RemoveLineFromFile 
{
	private BufferedReader aBufferedReader = null;
	private BufferedWriter aBufferedWriter = null;
	private FileReader aFileReader = null;
	private FileWriter aFileWriter = null;
	private String error = null;
	private boolean status = false;
	
	
	public RemoveLineFromFile( String[] args )
	{
		if( args.length < 2 )
		{		
			File inFile = this.getFile( "Select a file to use" );
			if(inFile==null) return;
			String mystring = this.requestUserInput( "Which line would you like to take out?" );	
			if(mystring==null || mystring.length()==0) return;
			this.status = this.tryReplace( inFile , mystring );
		}
		else
		{		
			this.status = this.tryReplace( new File( args[0] ) , args[1] );	
		}
	}
	
	
	public boolean getStatus()
	{
		return this.status;
	}
	
	
	public String getError()
	{
		return this.error;
	}
	
	
	private final boolean tryReplace( File in , String replace )
	{	
		boolean result = false;
		String lineFromFile = "";
		String contents = "";
		
		if( !in.canRead() || !in.canWrite() )
		{
			this.error = "File is not available for read / write";	
			return result;
		}
		
		if(replace.length()==0 || replace==null)
		{			
			this.error = "User did not supply input";	
			return result;
		}
		
		try
		{
			this.aFileReader = new FileReader( in );		
			this.aBufferedReader = new BufferedReader( this.aFileReader );
						
			while( ( lineFromFile = this.aBufferedReader.readLine() ) != null )
			{
				if( lineFromFile.compareToIgnoreCase( replace ) == 0 )
				{
					continue;
				}
				else
				{
					contents += lineFromFile + System.getProperty( "line.separator" );				
				}
			}
			
			this.aFileReader.close();
			this.aBufferedReader.close();			
			this.aFileWriter = new FileWriter( in );
			this.aBufferedWriter = new BufferedWriter( this.aFileWriter );			
			this.aBufferedWriter.write( contents );
			this.aBufferedWriter.close();
			this.aFileWriter.close();
			
			this.aFileReader = null;
			this.aFileWriter = null;
			this.aBufferedReader = null;
			this.aBufferedWriter = null;
			
			result = true;
			
		}
		catch( FileNotFoundException e )
		{
			this.error =  e.getMessage();
			result = false;
		}
		catch( IOException e )
		{
			this.error = e.getMessage();
			result = false;
		}
		finally
		{
			if( !result )
			{
			    this.error = "Unknown file exception occured";	
			    result = false;
			}		
		}
		
		return result;
	}
	
	
	private final File getFile( String message )
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter( ".txt and .java files" , "txt" , "java" );	
		JFileChooser fc = null;		
		File inFile = null;		
		int retVal = 0;
		
		while( inFile == null )
		{								
			fc = new JFileChooser();
			fc.setDialogTitle( message );
			fc.setFileSelectionMode( JFileChooser.FILES_ONLY );
			fc.setFileFilter( filter );				
			retVal = fc.showOpenDialog( null );
			
			if( retVal == JFileChooser.APPROVE_OPTION )
			{
				try
				{
					inFile = fc.getSelectedFile();
				}
				catch( Exception e )
				{
					this.error = "Error selecting file";
					inFile = null;
				}
			}
			else if( retVal == JFileChooser.CANCEL_OPTION )
			{
				this.error = "User canceled file selection";
				return null;				
			}
			else
			{
				this.error = "User canceled file selection";
				inFile = null;
			}
		}	
		
		return inFile;		
	}
	
	
	private final String requestUserInput( String message )
	{
		String replacement = null;
		
		while( replacement == null )
		{
			replacement = JOptionPane.showInputDialog( null , "Enter some text : " , message , 1 );
			
			if( replacement == null )
			{
				this.error = "User did not input string replacement";
				return null;
			}
			else if( replacement.length() == 0 )
			{
				this.error = "User did not input string replacement";
				return null;
			}	
		}
	
		return replacement;
	}
	
	
	public static void main( String[] args ) 
	{	
		/*
		   RemoveLineFromFile temp = new RemoveLineFromFile( args );
		   if( temp.getStatus() )
			   System.out.println( "Succesfully executed" );
		   else
			   System.out.println( temp.getError() );
	    
		
		int t = 5;
		int y = 9;
		
		t ^= y;
		y ^= t;
		t ^= y;
		
		System.out.println(t);
		System.out.println(y);
		*/
		
		int[] ty = {6,7,3,4,2,1,9};
		int[] tg = {6,2,3,7,3,4,5,2,1,9};
		int[] result = unionOfSets(ty,tg);
		
		for(int j=0; j <result.length; j++)
		{
			System.out.println(result[j]);
		}
		
	}
	
	
	public static boolean contains(int[] set, int val)
	{
		boolean hasValue = false;
		int h = 0;
		
		while(h < set.length && !hasValue)
		{
			if(set[h] == val)
			{
				hasValue = true;
			}
			h++;
		}
		
		return hasValue;		
	}
		
	
	public static int[] unionOfSets(int[] setA, int setB[])
	{
		int[] temp = new int[setA.length + setB.length];
		int[] union;
		int items = 0;
		
		for(int y = 0 ; y < setA.length; y++)
		{
			if(!contains(temp, setA[y]))
			{
				temp[items] = setA[y];
				items++;
			}
		}
		
		for(int y = 0 ; y < setB.length; y++)
		{
			if(!contains(temp, setB[y]))
			{
				temp[items] = setB[y];
				items++;
			}
		}
		
		union = new int[items];
		for(int u = 0; u < items; u++)
		{
			union[u] = temp[u];
		}
		
		return union;
	}
	

}
