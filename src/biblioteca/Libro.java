package biblioteca;

public class Libro 
{
	private int _id;
	private String _title;
	private String _author;
	private int _yearPublication;
	private String _editorial;
	private int _numPages;
	
	public Libro( int id, String title, String author, int yearPublication, String editorial, int numPages )
	{
		_id = id;
		_title = title;
		_author = author;
		_yearPublication = yearPublication;
		_editorial = editorial;
		_numPages = numPages;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public String getTitle() 
	{
		return _title;
	}
	
	public String getAuthor() 
	{
		return _author;
	}
	
	public int getYearPublication()
	{
		return _yearPublication;
	}
	
	public String getEditorial()
	{
		return _editorial;
	}
	
	public int getNumPages()
	{
		return _numPages;
	}
	
	public void setTitle( String title )
	{
		_title = title;
	}
	
	public void setAuthor( String author ) 
	{
		_author = author;
	}
	
	public void setYearPublication( int yearPublication ) 
	{
		_yearPublication = yearPublication;
	}
	
	public void setEditorial( String editorial ) 
	{
		_editorial = editorial;
	}
	
	public void setAuthor( int numPages ) 
	{
		_numPages = numPages;
	}
}
