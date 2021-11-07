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
}
