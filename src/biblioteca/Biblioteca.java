package biblioteca;

import java.util.ArrayList;
import java.util.Scanner;

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
					RecuperarTodos();
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
		int id = sc.nextInt();
		
		System.out.println( "Introduce el titutlo del libro : ");
		String title = sc.next();
		
		System.out.println( "Introduce el autor del libro : ");
		String author = sc.next();
		
		System.out.println( "Introduce el anyo de publicacion del libro : ");
		int yearPublication = sc.nextInt();
		
		System.out.println( "Introduce la editorial del libro : ");
		String editorial = sc.next();
		
		System.out.println( "Introduce el numeor de paginas del libro : ");
		int numPages = sc.nextInt();
		
		
		Libro libro = new Libro( id, title, author, yearPublication, editorial, numPages );
		
		return libro;
	}
	
	static int AnyadirLibro( Libro libro ) // AnyadirLibro = CrearLibro de la actividad
	{
		//anyadir el libro a un XML
		
		return libro.getId();
	}
	
	static Libro RecuperarLibro( int identificador ) 
	{
		// devuelve un objeto libro a partir de un identificador (id)
		// analiza el xml en busca del libro y transoforma los datos del libro en un objeto libro
		
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
