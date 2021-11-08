package biblioteca;

public class Libro 
{
	private String _id;
	private String _title;
	private String _author;
	private String _yearPublication;
	private String _editorial;
	private String _numPages;
	
	public Libro( String id, String title, String author, String yearPublication, String editorial, String numPages )
	{
		_id = id;
		_title = title;
		_author = author;
		_yearPublication = yearPublication;
		_editorial = editorial;
		_numPages = numPages;
	}
	
	public String getId()
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
	
	public String getYearPublication()
	{
		return _yearPublication;
	}
	
	public String getEditorial()
	{
		return _editorial;
	}
	
	public String getNumPages()
	{
		return _numPages;
	}
}
