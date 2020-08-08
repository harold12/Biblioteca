package biblioteca;

/**
 * Es una clase con la cual vamos a crear un objeto, la vamos a usar guardando datos en sus atributos
 * y que a su vez interactua mediante una asociacion con la clase BASEDATOS
 * @author Grupo Biblioteca
 */
public class Libro{
    // Atributos de la clase que vamos a utilizar y enlazar a la base de datos
    protected int id;
    protected String titulo;
    protected String editorial;
    protected String year;
    protected String tipo;

    public Libro(int id, String titulo, String editorial, String year, String tipo){
          this.id = id;
          this.titulo = titulo;     
          this.editorial = editorial;
          this.year = year;
          this.tipo = tipo;
    
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the editorial
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * @param editorial the editorial to set
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
   
}
