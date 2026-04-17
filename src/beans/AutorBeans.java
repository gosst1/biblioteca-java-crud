package beans;

/**
 * Bean que representa la entidad Autor de la base de datos.
 * Aplica encapsulamiento: todos los atributos son privados con getters y setters.
 */
public class AutorBeans {

    // ── Atributos privados ────────────────────────────────────────────────────
    private int    idAutor;
    private String nombre;
    private String nacionalidad;

    // ── Constructores ─────────────────────────────────────────────────────────

    /** Constructor vacío requerido para algunas operaciones. */
    public AutorBeans() {}

    /**
     * Constructor completo.
     *
     * @param idAutor      Identificador del autor.
     * @param nombre       Nombre completo del autor.
     * @param nacionalidad Nacionalidad del autor.
     */
    public AutorBeans(int idAutor, String nombre, String nacionalidad) {
        this.idAutor      = idAutor;
        this.nombre       = nombre;
        this.nacionalidad = nacionalidad;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public int    getIdAutor()      { return idAutor; }
    public String getNombre()       { return nombre; }
    public String getNacionalidad() { return nacionalidad; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setIdAutor(int idAutor)           { this.idAutor      = idAutor; }
    public void setNombre(String nombre)           { this.nombre       = nombre; }
    public void setNacionalidad(String nac)        { this.nacionalidad = nac; }

    /**
     * Se sobreescribe toString() para que los JComboBox muestren el nombre
     * en lugar de la referencia del objeto.
     */
    @Override
    public String toString() {
        return nombre;
    }
}
