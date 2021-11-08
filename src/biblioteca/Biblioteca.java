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
		
		ArrayList<Libro> biblioteca = RecuperarTodos(); //Parseamos el documento xml y almacenamos los datos en la lista biblioteca
		
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
					
					for( Libro libro : biblioteca ) {
						System.out.println( "ID : " + libro.getId() + " | Titulo : " + libro.getTitle() );
					}
					
					break;
				
					
				case "2" :		
					do{
						System.out.print( "\nIntroduce el *ID* del libro que quieres mostrar : ");
						id = sc.nextInt();
					}while( id <= 0 || id > biblioteca.size() );
					
					MostrarLibro( RecuperarLibro( id ) ); // RecuperarLibro( id )
					break;
				
					
				case "3" :
					//AnyadirLibro( CrearLibro( sc ) );
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
	
	
}
