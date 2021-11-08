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
	
	
	
	public ArrayList<Libro> RecuperarTodos()
	{		
		ArrayList<Libro> bibliotecaLibros = new ArrayList<Libro>();
		
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( new File( "/home/jordi/proyectosJavaEclipse/jordi_estelles_navarro_AE3_ADD/info/biblioteca.xml" ) ); // Diferencia de leer a escribir en un fichero XML
			
			//Element biblioteca = doc.getDocumentElement(); // obtiene el elemento hijo de document (biblioteca)
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
			System.out.println( "error" ); // Modificar
		}
		
		return bibliotecaLibros;
	}
	
	
	
	public void MostrarLibro( Libro libro ) 
	{
		System.out.println( ">> ID : " + libro.getId() );
		System.out.println( ">> Titulo : " + libro.getTitle() );
		System.out.println( ">> Autor : " + libro.getAuthor() );
		System.out.println( ">> Anyo Publicacion : " + libro.getYearPublication() );
		System.out.println( ">> Editorial : " + libro.getEditorial() );
		System.out.println( ">> Numero Paginas : " + libro.getNumPages() );
	}
	
	
	
	public Libro RecuperarLibro( int identificador ) 
	{		
		//ArrayList<Libro> biblioteca = RecuperarTodos();
		boolean libroExiste = false;
		
		for( Libro libro : bibliotecaLibros ) 
		{
			if( libro.getId() == identificador )
			{
				libroExiste = true;				
			}
		}
		
		if( libroExiste ) 
		{
			return bibliotecaLibros.get( identificador -1 ); // Posicion real en el arryalist bibilioteca
			
		}else {
			System.out.println( "Libro no encontrado ");
			return null;
		}		
	}
	
	
	
	public ArrayList<Libro> AnyadirLibro( Libro libroCreado )
	{
		// a partir de la lista que le pasamos tiene que volver a crear un nuevo documento con los valores que ya tenia y los del libro que le pasamos nuevo
		
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
			
		
		return bibliotecaLibros;
	}
	
	
	
	public Libro CrearLibro( Scanner sc )
	{
		
		// FALTAN EXPRESIONES REGULARES
		
		boolean verificador;
		int id = 0;
		
		do {
			System.out.print( "\nIntroduce el id del libro : ");
			id = sc.nextInt();
			
			verificador = true;
			
			for( int i = 0; i < bibliotecaLibros.size(); i++ )
			{
				if( id == Integer.valueOf(bibliotecaLibros.get( i ).getId()) ) 
				{
					verificador = false;
					System.out.println( ">> El ID del libro introducido ya existe ");
				}
			}
		}while( id <= 0 || !verificador );
		
		
		System.out.print( "Introduce el titutlo del libro : ");
		String title = sc.next();
		
		System.out.print( "Introduce el autor del libro : ");
		String author = sc.next();
		
		System.out.print( "Introduce el anyo de publicacion del libro : ");
		int yearPublication = sc.nextInt();
		
		System.out.print( "Introduce la editorial del libro : ");
		String editorial = sc.next();
		
		System.out.print( "Introduce el numero de paginas del libro : ");
		int numPages = sc.nextInt();
		
		Libro libro = new Libro( id, title, author, yearPublication, editorial, numPages );
		System.out.println( ">> Libro creado correctamente. " );
		
		return libro;
	}
	
	
	
	public void ActualizarLibro( Scanner sc, int identificador )
	{
		System.out.println( "Libro seleccionado : " );
		
		MostrarLibro( RecuperarLibro( identificador ) );
		Libro libro = RecuperarLibro( identificador );
		int marcador = 0;
		boolean validador = false;
		
		for( int i = 0; i < bibliotecaLibros.size(); i++ )
		{
			if( i == bibliotecaLibros.get( i ).getId() )
			{
				marcador = i;
			}
		}
		
		System.out.print( "Introduce el nuevo titulo del libro : ");
		libro.setTitle( sc.next() );
		
		System.out.print( "Introduce el nuevo autor del libro : ");
		libro.setAuthor( sc.next() );
		
		System.out.print( "Introduce el nuevo anyo de publicacion del libro : ");
		libro.setYearPublication( sc.nextInt() );
		
		System.out.print( "Introduce la nuevo editorial del libro : ");
		libro.setEditorial( sc.next() );
		
		System.out.print( "Introduce el nuevo numero de paginas del libro : ");
		libro.setAuthor( sc.nextInt() );	
		
		
		bibliotecaLibros.set( marcador, libro );

		
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
	
	
	
	public void BorrarLibro( int identificador )
	{
		System.out.println( "Libro seleccionado : " );
		
		MostrarLibro( RecuperarLibro( identificador ) );
		Libro libro = RecuperarLibro( identificador );
		
		int marcador = 0;
		boolean validador = false;
		
		for( int i = 0; i < bibliotecaLibros.size(); i++ )
		{
			if( i == bibliotecaLibros.get( i ).getId() )
			{
				marcador = i;
			}
		}
		
		bibliotecaLibros.remove( marcador );
		
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
					author.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getAuthor() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( author );
					
					Element yearPublication = doc.createElement( "yearPublication" );
					yearPublication.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getYearPublication() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( yearPublication );
					
					Element editorial = doc.createElement( "editorial" );
					editorial.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getEditorial() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( editorial );
					
					Element numPages = doc.createElement( "numPages" );
					numPages.appendChild( doc.createTextNode( String.valueOf( libroAnyadir.getNumPages() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( numPages );
			}

			// GUARDAR EL DOM DE LA MEMORIA DE EJECUCCION A DISCO
			TransformerFactory tranFactory = TransformerFactory.newInstance(); //las dependencias puede que no sean correctas
			Transformer aTransformer = tranFactory.newTransformer();
			
			// DAR FORMATO AL FICHERO XML QUE ESTAMOS CREANDO
			aTransformer.setOutputProperty( OutputKeys.ENCODING,  "ISO-8859-1)" ); // Formato universal para que interprete bien todo tipo de caracteres
			aTransformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "4" ); // Indentado (sangria) 4 espacios
			aTransformer.setOutputProperty( OutputKeys.INDENT,  "yes" ); // Activar el indentado
			
			
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
}
