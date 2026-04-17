package beans;

/**
 * Bean que representa la entidad Libro de la base de datos.
 * Aplica encapsulamiento: todos los atributos son privados con getters y setters.
 */
public class LibroBeans {

    // ── Atributos privados ────────────────────────────────────────────────────
    private int    idLibro;
    private String titulo;
    private int    anioPublicacion;
    private int    idAutor;
    private int    idCategoria;

    // Atributos adicionales para mostrar nombres en la tabla (JOIN)
    private String nombreAutor;
    private String nombreCategoria;

    // ── Constructores ─────────────────────────────────────────────────────────

    public LibroBeans() {}

    /**
     * Constructor completo con todos los campos incluyendo los nombres de relación.
     */
    public LibroBeans(int idLibro, String titulo, int anioPublicacion,
                      int idAutor, int idCategoria,
                      String nombreAutor, String nombreCategoria) {
        this.idLibro         = idLibro;
        this.titulo          = titulo;
        this.anioPublicacion = anioPublicacion;
        this.idAutor         = idAutor;
        this.idCategoria     = idCategoria;
        this.nombreAutor     = nombreAutor;
        this.nombreCategoria = nombreCategoria;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public int    getIdLibro()          { return idLibro; }
    public String getTitulo()           { return titulo; }
    public int    getAnioPublicacion()  { return anioPublicacion; }
    public int    getIdAutor()          { return idAutor; }
    public int    getIdCategoria()      { return idCategoria; }
    public String getNombreAutor()      { return nombreAutor; }
    public String getNombreCategoria()  { return nombreCategoria; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setIdLibro(int idLibro)                   { this.idLibro         = idLibro; }
    public void setTitulo(String titulo)                  { this.titulo          = titulo; }
    public void setAnioPublicacion(int anio)              { this.anioPublicacion = anio; }
    public void setIdAutor(int idAutor)                   { this.idAutor         = idAutor; }
    public void setIdCategoria(int idCategoria)           { this.idCategoria     = idCategoria; }
    public void setNombreAutor(String nombreAutor)        { this.nombreAutor     = nombreAutor; }
    public void setNombreCategoria(String nombreCat)      { this.nombreCategoria = nombreCat; }
}
