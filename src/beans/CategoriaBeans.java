package beans;

/**
 * Bean que representa la entidad Categoría de la base de datos.
 * Aplica encapsulamiento: todos los atributos son privados con getters y setters.
 */
public class CategoriaBeans {

    // ── Atributos privados ────────────────────────────────────────────────────
    private int    idCategoria;
    private String nombreCategoria;

    // ── Constructores ─────────────────────────────────────────────────────────

    public CategoriaBeans() {}

    public CategoriaBeans(int idCategoria, String nombreCategoria) {
        this.idCategoria     = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public int    getIdCategoria()     { return idCategoria; }
    public String getNombreCategoria() { return nombreCategoria; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setIdCategoria(int idCategoria)          { this.idCategoria     = idCategoria; }
    public void setNombreCategoria(String nombreCat)     { this.nombreCategoria = nombreCat; }

    /**
     * Se sobreescribe toString() para visualización correcta en JComboBox.
     */
    @Override
    public String toString() {
        return nombreCategoria;
    }
}
