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

public class Biblioteca {

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner( System.in );
		
		ArrayList<Libro> bibliotecaLibros = RecuperarTodos(); //Parseamos el documento xml y almacenamos los datos en la lista biblioteca
		
		String input = "";
		
		
		while( !input.equals( "6" ) )
		{
			System.out.println( "\n\n### PANEL DE OPCIONES ###\n" );
			System.out.println( "1. Mostrar todos los titulos de la biblioteca." );
			System.out.println( "2. Mostrar informacion detallada de un libro." );
			System.out.println( "3. Crear nuevo libro." );
			System.out.println( "4. Actualizar libro." );
			System.out.println( "5. Borrar libro." );
			System.out.println( "6. Cerrar la biblioteca.\n" );
			
			System.out.print( "> ");
			input = sc.next();
			
			int id = 0;
			
			switch( input ) 
			{
				case "1" :
					System.out.println( "" );
					
					for( Libro libro : bibliotecaLibros ) {
						System.out.println( "ID : " + libro.getId() + " | Titulo : " + libro.getTitle() );
					}
					
					break;
				
					
				case "2" :		
					do{
						System.out.print( "\nIntroduce el *ID* del libro que quieres mostrar : ");
						id = sc.nextInt();
					}while( id <= 0 || id > bibliotecaLibros.size() );
					
					MostrarLibro( RecuperarLibro( id ) ); // RecuperarLibro( id )
					break;
				
					
				case "3" :
					AnyadirLibro( bibliotecaLibros, bibliotecaLibros.get(0) ); //CrearLibro()
					break;
					
					
				case "4" :
					System.out.println( "\nIntroduce el id del libro a actualizar : ");
					id = sc.nextInt();
					
					//ActualizaLibro( id );
					break;
				
					
				case "5" :
					System.out.println( "\nIntroduce el id del libro a borrar : ");
					id = sc.nextInt();
					
					//BorrarLibro( id );
					break;
					
				default : 
					System.out.println( ">> Opcion Inexistente" );
			}
		}
		
		sc.close();	
	}
	
	
	static ArrayList<Libro> RecuperarTodos()
	{		
		ArrayList<Libro> biblioteca = new ArrayList<Libro>();
		
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( new File( "/home/jordi/proyectosJavaEclipse/jordi_estelles_navarro_AE3_ADD/info/biblioteca.xml" ) ); // Diferencia de leer a escribir en un fichero XML
			
			Element raiz = doc.getDocumentElement(); // obtiene el elemento hijo de document (biblioteca)
			NodeList nodeList = doc.getElementsByTagName( "libro" ); //lista de objetos que seran los nodos llamados libro
					
			for( int i = 0; i < nodeList.getLength(); i++ ) 
			{
				Node node = nodeList.item( i ); // Fijmos el nodo que buscamos en un objeto
				Element nodeElement = ( Element ) node; // Casting (transforma) el objeto Node anterior a Element
							
				Libro libro = new Libro( nodeElement.getAttribute( "id" ),
										 nodeElement.getElementsByTagName( "title" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "author" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "yearPublication" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "editorial" ).item(0).getTextContent(),
										 nodeElement.getElementsByTagName( "numPages" ).item(0).getTextContent()
									   );
				
				biblioteca.add( libro );			
			}
		}
		catch( Exception e )
		{
			System.out.println( "error" ); // Modificar
		}
		
		return biblioteca;
	}
	
	
	static void MostrarLibro( Libro libro ) 
	{
		System.out.println( ">> ID : " + libro.getId() );
		System.out.println( ">> Titulo : " + libro.getTitle() );
		System.out.println( ">> Autor : " + libro.getAuthor() );
		System.out.println( ">> Anyo Publicacion : " + libro.getYearPublication() );
		System.out.println( ">> Editorial : " + libro.getEditorial() );
		System.out.println( ">> Numero Paginas : " + libro.getNumPages() );
	}
	
	
	static Libro RecuperarLibro( int identificador ) 
	{		
		ArrayList<Libro> biblioteca = RecuperarTodos();
		String id = String.valueOf( identificador );
		boolean libroExiste = false;
		
		for( Libro libro : biblioteca ) 
		{
			if( libro.getId().equals( id ) )
			{
				libroExiste = true;				
			}
		}
		
		if( libroExiste ) 
		{
			return biblioteca.get( identificador -1 ); // Posicion real en el arryalist bibilioteca
			
		}else {
			System.out.println( "Libro no encontrado ");
			return null;
		}		
	}
	
	
	static ArrayList<Libro> AnyadirLibro( ArrayList<Libro> bibliotecaLibros, Libro libroCreado )
	{
		// a partir de la lista que le pasamos tiene que volver a crear un nuevo documento con los valores que ya tenia y los del libro que le pasamos nuevo
		
		bibliotecaLibros.add( libroCreado );
		
		String atributo = "0";		
		
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
				libro.setAttribute( "id", atributo );
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
		
		return bibliotecaLibros;
	}
}
