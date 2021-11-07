package biblioteca;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document; // Para gestionar XML
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Biblioteca {

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner( System.in );
		
		String input = "";
		
		
		while( !input.equals( "6" ) )
		{
			System.out.println( "1. Mostrar todos los titulos de la biblioteca." );
			System.out.println( "2. Mostrar informacion detallada de un libro." );
			System.out.println( "3. Crear nuevo libro." );
			System.out.println( "4. Actualizar libro." );
			System.out.println( "5. Borrar libro." );
			System.out.println( "6. Cerrar la biblioteca." );
			
			input = sc.next();
			
			int id = 0;
			
			switch( input ) 
			{
				case "1" : 
					ArrayList<Libro> biblioteca = RecuperarTodos();
					
					for( Libro libro : biblioteca ) {
						libro.getId();
						libro.getTitle();
					}
					
					break;
				
					
				case "2" :
					System.out.println( "Introduce el id del libro que quieres mostrar : ");
					id = sc.nextInt();
					
					MostrarLibro( RecuperarLibro( id ) );
					break;
				
					
				case "3" :
					AnyadirLibro( CrearLibro( sc ) );
					break;
					
					
				case "4" :
					System.out.println( "Introduce el id del libro a actualizar : ");
					id = sc.nextInt();
					
					ActualizaLibro( id );
					break;
				
					
				case "5" :
					System.out.println( "Introduce el id del libro a borrar : ");
					id = sc.nextInt();
					
					BorrarLibro( id );
					break;
			}
		}
		
		sc.close();	
	}
	
	static Libro CrearLibro( Scanner sc )
	{
		System.out.println( "Introduce el nombre del libro : ");
		String id = sc.next();
		
		System.out.println( "Introduce el titutlo del libro : ");
		String title = sc.next();
		
		System.out.println( "Introduce el autor del libro : ");
		String author = sc.next();
		
		System.out.println( "Introduce el anyo de publicacion del libro : ");
		String yearPublication = sc.next());
		
		System.out.println( "Introduce la editorial del libro : ");
		String editorial = sc.next();
		
		System.out.println( "Introduce el numeor de paginas del libro : ");
		String numPages = sc.next();
		
		
		Libro libro = new Libro( id, title, author, yearPublication, editorial, numPages );
		
		return libro;
	}
	
	static int AnyadirLibro( Libro libro ) // AnyadirLibro = CrearLibro de la actividad
	{
		//anyadir el libro a un XML
		try 
		{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();
		
		Element biblioteca = doc.createElement( "biblioteca" );
		doc.appendChild( biblioteca );
		
		}
		catch( Exception e )
		{
			System.out.println( "error" ); // Modificar
		}
		
		return libro.getId();
	}
	
	static Libro RecuperarLibro( int identificador ) 
	{		
		String id = String.valueOf( identificador );
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();
		
		Element raiz = doc.getDocumentElement(); // obtiene el elemento raiz (biblioteca)
		NodeList nodeList = doc.getElementsByTagName( "libro" ); //lista de objetos que seran los nodos llamados libro
				
		for( int i = 0; i < nodeList.getLength(); i++ ) 
		{
			Node node = nodeList.item( i );
			Element nodeElement = ( Element ) node; // Casting (transforma) el elemento Node a Element
			
			if( id.equals( nodeElement.getAttribute( "id" ) ) )
			{
				Libro libro = new Libro( nodeElement.getAttribute( "id" ),
										 nodeElement.getElementsByTagName( "title" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "author" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "yearPublication" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "editorial" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "numPages" ).item(0).getTextContent()
									   );
				return libro;
			}
			else 
			{
				System.out.println( "Libro no encontrado" );
				
				Libro libro;
				
				return libro;
			}
		}
		
		
		
		
		
		return libroBuscado;
	}

	static void MostrarLibro( Libro libro ) 
	{
		// muestra los atributos del libro que le pasamos por pantalla
	}
	
	static void BorrarLibro( int identificador )
	{
		// borra un objeto libro a partir de un identificador
		
		
	}
	
	static void ActualizaLibro( int identificador ) 
	{
		// modifica la informacion de un libro a partir de su identificador
	}
	
	static ArrayList<Libro> RecuperarTodos()
	{
		// devuelve una lista con todos los libros de la biblioteca
		
		return biblioteca;
	}
}
