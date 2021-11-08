package biblioteca;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class testing {
	/*
	static Libro CrearLibro( Scanner sc )
	{
		System.out.println( "Introduce el nombre del libro : ");
		String id = sc.next();
		
		System.out.println( "Introduce el titutlo del libro : ");
		String title = sc.next();
		
		System.out.println( "Introduce el autor del libro : ");
		String author = sc.next();
		
		System.out.println( "Introduce el anyo de publicacion del libro : ");
		String yearPublication = sc.next();
		
		System.out.println( "Introduce la editorial del libro : ");
		String editorial = sc.next();
		
		System.out.println( "Introduce el numeor de paginas del libro : ");
		String numPages = sc.next();
		
		
		//verificar que este libro no existe ya (el id no tiene que coincidir con ninguno de los que hay en xml)
		
		Libro libro = new Libro( id, title, author, yearPublication, editorial, numPages );
		
		return libro;
	}
	
	static int AnyadirLibro( Libro objetoLibro ) // AnyadirLibro = CrearLibro de la actividad
	{
		//EL NUMERO DEL ATRIBUTO NO TIENE QUE EXISTIR (tiene que estar verificado previamente antes de entrar en este metodo)
		
		String atributo = "0";		
		
		try 
		{
			//PASAR EL DOM A MEMORIA DE EJECUCCION
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument(); // Para escribir en un fichero tenemos que pasar el DOM a memoria de ejecuccion
			
			Element biblioteca = doc.createElement( "biblioteca" );
			doc.appendChild( biblioteca );// Elemento hijo de document (biblioteca)
			
				Element libro = doc.createElement( "libro" );
				libro.setAttribute( "id", atributo );
				biblioteca.appendChild( libro );
			
					Element titulo = doc.createElement( "titulo" );
					titulo.appendChild( doc.createTextNode( String.valueOf( objetoLibro.getTitle() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( titulo ); // Elemento hijo del nodo libro (titulo)
					
					Element author = doc.createElement( "author" );
					titulo.appendChild( doc.createTextNode( String.valueOf( objetoLibro.getAuthor() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( author );
					
					Element yearPublication = doc.createElement( "yearPublication" );
					titulo.appendChild( doc.createTextNode( String.valueOf( objetoLibro.getYearPublication() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( yearPublication );
					
					Element editorial = doc.createElement( "editorial" );
					titulo.appendChild( doc.createTextNode( String.valueOf( objetoLibro.getEditorial() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( editorial );
					
					Element numPages = doc.createElement( "numPages" );
					titulo.appendChild( doc.createTextNode( String.valueOf( objetoLibro.getNumPages() ) ) ); // Se le da el valor al nodo titulo
					libro.appendChild( numPages );		
			
					
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
				FileWriter fw = new FileWriter( "biblioteca.xml" );
				StreamResult result = new StreamResult( fw );
				aTransformer.transform( source, result );
				fw.close();
			}
			catch( IOException e )
			{
				e.printStackTrace();
			}
			
		catch( TransformerException ex )
		{
			System.out.println( "Error escribiendo en el documento" );
		}
		catch( ParserConfigurationException ex )
		{
			System.out.println( "Error construyendo el documento" );
		}
		
			
			
		}
		catch( Exception e )
		{
			System.out.println( "error" ); // Modificar
		}
		
		return objetoLibro.getId(); //usar id como int no como string
	}
	
	static Libro RecuperarLibro( int identificador ) 
	{		
		String id = String.valueOf( identificador );
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( new File( "biblioteca.xml" ) ); // Diferencia de leer a escribir en un fichero XML
			
			Element raiz = doc.getDocumentElement(); // obtiene el elemento hijo de document (biblioteca)
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
				}
			}
		}
		catch( Exception e )
		{
			System.out.println( "error" ); // Modificar
		}
		
		return null;
	}

	static void MostrarLibro( Libro libro ) 
	{
		System.out.println( "\n### Informacion del libro seleccionado : ###\n");
		System.out.println( libro.getId() );
		System.out.println( libro.getTitle() );
		System.out.println( libro.getAuthor() );
		System.out.println( libro.getYearPublication() );
		System.out.println( libro.getEditorial() );
		System.out.println( libro.getNumPages() );
	}
	
	static void BorrarLibro( int identificador )
	{
		// borra un objeto libro a partir de un identificador
		
		
	}
	
	static void ActualizaLibro( int identificador ) 
	{
		// modifica la informacion de un libro a partir de su identificador
	}
	*/
}
