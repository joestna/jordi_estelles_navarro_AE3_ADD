package biblioteca;

import java.util.Scanner;

public class App {

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner( System.in );
		String input = "";
		
		Biblioteca biblioteca = new Biblioteca();
		
		while( !input.equals( "6" ) )
		{
			System.out.println( "\n### PANEL DE OPCIONES ###\n" );
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
					
					for( Libro libro : biblioteca.RecuperarTodos() ) {
						System.out.println( "ID : " + libro.getId() + " | Titulo : " + libro.getTitle() );
					}
					
					break;
				
					
				case "2" :		
						System.out.print( "\n> Introduce el *ID* del libro que quieres mostrar : ");
						id = sc.nextInt();
					
					biblioteca.MostrarLibro( biblioteca.RecuperarLibro( id ) );
					break;
				
					
				case "3" :					
					biblioteca.AnyadirLibro( biblioteca.CrearLibro( sc ) );
					break;
					
					
				case "4" :
					System.out.print( "\n> Introduce el id del libro a actualizar : ");
					id = sc.nextInt();
					
					biblioteca.ActualizarLibro( sc, id );
					break;
				
					
				case "5" :
					System.out.print( "\n> Introduce el id del libro a borrar : ");
					id = sc.nextInt();
					
					biblioteca.BorrarLibro( id );
					break;
					
					
				case "6" :
					System.out.println( ">> Gracias por jugar. ");
					break;
				
					
				default : 
					System.out.println( ">> Opcion Inexistente" );
			}
		}
		
		sc.close();	
	}
}
