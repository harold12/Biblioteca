package biblioteca;

import java.io.Serializable;


public class Libro implements Serializable{
    
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

    /**
     * @param titulo the titulo to set
     */
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
    
     public boolean empiezaPor(String inicio){
        String idd = Integer.toString(id);
        if(inicio.isEmpty() || inicio.length()>idd.length())
            return false;
        for(int i=0; i<inicio.length(); ++i)
            if( inicio.charAt(i) != idd.charAt(i))
                return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "Nombre libro: "+titulo+", "+editorial+
               "\nFecha: "+year+
               "\nGenero: "+tipo;
    }
}
