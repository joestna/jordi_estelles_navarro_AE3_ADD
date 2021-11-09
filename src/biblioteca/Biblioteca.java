package biblioteca;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document; // Para gestionar XML
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Biblioteca 
{
	private ArrayList<Libro> bibliotecaLibros = RecuperarTodos(); //Parseamos el documento xml y almacenamos los datos en la lista biblioteca
	
	
	// Metodo : RecuperarTodos
	// Parametros : 
	// Devuelve un ArrayList con la lista de libros que se encuentran en el XML selecccionado
	public ArrayList<Libro> RecuperarTodos()
	{		
		ArrayList<Libro> bibliotecaLibros = new ArrayList<Libro>();
		
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( new File( "/home/jordi/proyectosJavaEclipse/jordi_estelles_navarro_AE3_ADD/info/biblioteca.xml" ) ); // Diferencia de leer a escribir en un fichero XML

			NodeList nodeList = doc.getElementsByTagName( "libro" ); //lista de objetos que seran los nodos llamados libro
					
			for( int i = 0; i < nodeList.getLength(); i++ ) 
			{
				Node node = nodeList.item( i ); // Fijmos el nodo que buscamos en un objeto
				Element nodeElement = ( Element ) node; // Casting (transforma) el objeto Node anterior a Element
							
				Libro libro = new Libro( Integer.valueOf(nodeElement.getAttribute( "id" )),
										 nodeElement.getElementsByTagName( "title" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "author" ).item(0).getTextContent(),
										 Integer.valueOf(nodeElement.getElementsByTagName( "yearPublication" ).item(0).getTextContent()),
										 nodeElement.getElementsByTagName( "editorial" ).item(0).getTextContent(),
										 Integer.valueOf(nodeElement.getElementsByTagName( "numPages" ).item(0).getTextContent())
									   );
				
				bibliotecaLibros.add( libro );			
			}
		}
		catch( Exception e )
		{
			System.out.println( e );
		}
		
		return bibliotecaLibros;
	}
	
	
	
	// Metodo : MostrarLibro
	// Parametros : Libro
	// Muestra la informacion de un libro pasado por parametro
	public void MostrarLibro( Libro libro ) 
	{
		try
		{
			System.out.println( ">> ID : " + libro.getId() );
			System.out.println( ">> Titulo : " + libro.getTitle() );
			System.out.println( ">> Autor : " + libro.getAuthor() );
			System.out.println( ">> Anyo Publicacion : " + libro.getYearPublication() );
			System.out.println( ">> Editorial : " + libro.getEditorial() );
			System.out.println( ">> Numero Paginas : " + libro.getNumPages() );
		}
		catch( Exception e )
		{
			System.out.println( e );
		}
		
	}
	
	
	
	// Metodo : ComprobarLibroExiste
	// Parametros : int
	// Comprueba que el libro con el identificador que pasamos por parametro existe en el fichero XML->Lista de libros
	private boolean ComprobarLibroExiste( int identificador )
	{
		ArrayList<Libro> biblioteca = RecuperarTodos();
		
		boolean libroExiste = false;
		
		for( Libro libro : bibliotecaLibros ) 
		{
			if( libro.getId() == identificador )
			{
				libroExiste = true;				
			}
		}
		
		return libroExiste;
	}
	
	

	// Metodo : RecuperarLibro
	// Parametros : int
	// Devuelve un objeto libro de la lista de libros del fichero XML que coincida con el identificador que le pasamos por parametro
	public Libro RecuperarLibro( int identificador ) 
	{		
		boolean libroExiste = ComprobarLibroExiste( identificador );
		
		if( libroExiste ) 
		{
			return bibliotecaLibros.get( IndiceDelLibroEnLista( identificador ) ); 
		}
		else
		{
			System.out.println( ">> Libro no encontrado ");
			return null;
		}		
	}
	
	
	
	// Metodo : AnyadirLibro
	// Parametros : Libro
	// Anyade el objeto libro que le pasamos por parametro al fichero XML y devuelve la lista de libros actualizada
	public ArrayList<Libro> AnyadirLibro( Libro libroCreado )
	{
		try
		{
			bibliotecaLibros.add( libroCreado );
			boolean validador = false;
			
			validador = GuardarInformacionXML();
			
			if( !validador ) 
			{
				System.out.println( ">> Error al anyadir libro. ");
			}
			else 
			{
				System.out.println( ">> Libro anyadido correctamente. ");
			}
		}
		catch( Exception e )
		{
			System.out.println( e );
		}
		
		return bibliotecaLibros;		
	}
	
	
	
	// Metodo : CrearLibro
	// Parametros : Scanner
	// Devuelve un Libro creado
	public Libro CrearLibro( Scanner sc )
	{
		System.out.print( "\nIntroduce el id del libro a crear : ");
		int identificador = sc.nextInt();
		
		boolean libroExiste = ComprobarLibroExiste( identificador );
		
		if( !libroExiste ) 
		{
			Libro libro = DatosLibro( sc, identificador );
			System.out.println( ">> Libro creado correctamente.");
			
			return libro;
		}
		else
		{
			System.out.println( ">> Identificador de libro duplicado (libro ya existe). ");
			return null;
		}	
	}
	
	
	
	// Metodo : IndiceDelLibroEnLista
	// Parametros : int
	// Devuelve la posicion en la lista de libros del libro con el identificador que le pasamos por parametro
	private int IndiceDelLibroEnLista( int identificador )
	{
		int indiceDelLibroEnLista = 0;
		
		for( int i = 0; i < bibliotecaLibros.size(); i++ )
		{
			if( identificador == bibliotecaLibros.get( i ).getId() )
			{
				indiceDelLibroEnLista = i;
			}
		}
		
		return indiceDelLibroEnLista;
	}
	
	
	
	// Metodo : ActualizarLibro
	// Parametros : Scanner, int
	// Actualiza un libro en la lista de libros y guarda el resultado en el fichero XML seleccionado
	public void ActualizarLibro( Scanner sc, int identificador )
	{
		boolean libroExiste = ComprobarLibroExiste( identificador );
		
		if( libroExiste ) {
			System.out.println( "Libro seleccionado : " );
			
			MostrarLibro( RecuperarLibro( identificador ) );
			System.out.println( "" );
			
			
			Libro libro = DatosLibro( sc, identificador );			
			bibliotecaLibros.set( IndiceDelLibroEnLista( identificador ), libro );

			
			boolean validador;			
			validador = GuardarInformacionXML();
			
			if( !validador ) 
			{
				System.out.println( ">> Error al actualizar libro. ");
			}
			else 
			{
				System.out.println( ">> Libro actualizado correctamente. ");
			}	
		}
		else
		{
			System.out.println( ">> El libro seleccionado no existe. ");
		}
	}
	
	
	// Metodo : BorrarLibro
	// Parametros : Scanner, int
	// Borra un libro en la lista de libros y guarda el resultado en el fichero XML seleccionado
	public void BorrarLibro( int identificador )
	{
		boolean libroExiste = ComprobarLibroExiste( identificador );
		
		if( libroExiste ) 
		{
			System.out.println( "Libro seleccionado : " );
			
			MostrarLibro( RecuperarLibro( identificador ) );
			System.out.println( "" );
			
			
			bibliotecaLibros.remove( IndiceDelLibroEnLista( identificador ) );
			
			
			boolean validador;			
			validador = GuardarInformacionXML();
			
			if( !validador ) 
			{
				System.out.println( ">> Error al borrar libro. ");
			}
			else 
			{
				System.out.println( ">> Libro borrado correctamente. ");
			}	
		}
		else
		{
			System.out.println( ">> El libro seleccionado no existe. ");
		}
		
		
	}
	
	
	
	// Metodo : GuardarInformacionXML
	// Parametros : 
	// Guarda la informacion del DOM en memoria de ejecuccion a un fichero XML seleccionado
	private boolean GuardarInformacionXML() 
	{
		try 
		{
			//PASAR EL DOM A MEMORIA DE EJECUCCION
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument(); // Para escribir en un fichero tenemos que pasar el DOM a memoria de ejecuccion
			
			Element biblioteca = doc.createElement( "biblioteca" );
			doc.appendChild( biblioteca );// Elemento hijo de document (biblioteca)
			
			for( Libro libroAnyadir : bibliotecaLibros )
			{
				Element libro = doc.createElement( "libro" );
				libro.setAttribute( "id", String.valueOf( libroAnyadir.getId() ) );
				biblioteca.appendChild( libro );
			
					Element titulo = doc.createElement( "titulo" );
					titulo.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getTitle() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( titulo ); // Elemento hijo del nodo libro (titulo)
					
					Element author = doc.createElement( "author" );
					author.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getAuthor() ) ) ); 
					libro.appendChild( author );
					
					Element yearPublication = doc.createElement( "yearPublication" );
					yearPublication.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getYearPublication() ) ) ); 
					libro.appendChild( yearPublication );
					
					Element editorial = doc.createElement( "editorial" );
					editorial.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getEditorial() ) ) ); 
					libro.appendChild( editorial );
					
					Element numPages = doc.createElement( "numPages" );
					numPages.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getNumPages() ) ) ); 
					libro.appendChild( numPages );
			}

			// GUARDAR EL DOM DE LA MEMORIA DE EJECUCCION A DISCO
			TransformerFactory tranFactory = TransformerFactory.newInstance(); 
			Transformer aTransformer = tranFactory.newTransformer();
			
			// DAR FORMATO AL FICHERO XML QUE ESTAMOS CREANDO
			aTransformer.setOutputProperty( OutputKeys.ENCODING,  "ISO-8859-1)" ); // Formato universal para que interprete bien todo tipo de caracteres
			aTransformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "4" ); // Indentado (sangria) 4 espacios
			aTransformer.setOutputProperty( OutputKeys.INDENT,  "yes" ); // Activar el indentado
			
			// GUARDAR EL DOM DE MEMORIA DE EJECUCCION A FICHERO XML
			DOMSource source = new DOMSource( doc );
			
			try
			{
				FileWriter fw = new FileWriter( "/home/jordi/proyectosJavaEclipse/jordi_estelles_navarro_AE3_ADD/info/biblioteca2.xml" );
				StreamResult result = new StreamResult( fw );
				aTransformer.transform( source, result );
				
				fw.close();
				return true;	
				
			}
			catch( IOException e )
			{
				e.printStackTrace();
			}
			
		
		}
		catch( TransformerException ex )
		{
			System.out.println( "Error escribiendo en el documento" );
		}
		catch( ParserConfigurationException ex ) 
		{
			System.out.println( "Error escribiendo en el documento" );
		}
		
		return false;
	}
	
	
	
	// Metodo : DatosLibro
	// Parametros : Scanner, int
	// Devuelve un libro con la informacion sobre este registrada
	private Libro DatosLibro( Scanner sc, int identificador )
	{
		String titulo, autor, anyoPublicacion, editorial, numPaginas;
		String regexp = "\\d[0-9]*$";
		
		
		System.out.print( "Introduce el titutlo del libro : ");
		titulo = sc.next();
		
		System.out.print( "Introduce el autor del libro : ");
		autor = sc.next();
		
		do{
			System.out.print( "Introduce el anyo de publicacion del libro : ");
			anyoPublicacion = sc.next();
		}while( !anyoPublicacion.matches( regexp ) );
	
		System.out.print( "Introduce la editorial del libro : ");
		editorial = sc.next();
		
		do{
			System.out.print( "Introduce el numero de paginas del libro : ");
			numPaginas = sc.next();
		}while( !numPaginas.matches( regexp ) );		
		
		
		Libro libro = new Libro( identificador, titulo, autor, Integer.valueOf(anyoPublicacion), editorial, Integer.valueOf( numPaginas ) );
		
		return libro;
	}
}
