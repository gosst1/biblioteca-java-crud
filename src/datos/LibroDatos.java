package datos;

import beans.LibroBeans;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos para la entidad Libro.
 * Implementa las operaciones CRUD: Insertar, Leer, Actualizar y Eliminar.
 * Utiliza {@link PreparedStatement} para todas las consultas SQL (obligatorio según rúbrica).
 */
public class LibroDatos {

    // ══════════════════════════════════════════════════════════════════════════
    //  CREATE – Insertar libro
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Inserta un nuevo libro en la base de datos.
     *
     * @param libro objeto {@link LibroBeans} con los datos a guardar.
     * @return {@code true} si la inserción fue exitosa.
     * @throws SQLException si ocurre un error de BD.
     */
    public boolean insertar(LibroBeans libro) throws SQLException {
        String sql = "INSERT INTO libro (titulo, anio_publicacion, id_autor, id_categoria) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt   (2, libro.getAnioPublicacion());
            ps.setInt   (3, libro.getIdAutor());
            ps.setInt   (4, libro.getIdCategoria());

            return ps.executeUpdate() > 0;
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  READ – Consultar libros
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Retorna todos los libros con nombre de autor y categoría (JOIN).
     *
     * @return Lista de {@link LibroBeans}.
     * @throws SQLException si ocurre un error de BD.
     */
    public List<LibroBeans> listarTodos() throws SQLException {
        List<LibroBeans> lista = new ArrayList<>();

        String sql = "SELECT l.id_libro, l.titulo, l.anio_publicacion, " +
                     "       l.id_autor, l.id_categoria, " +
                     "       a.nombre AS nombre_autor, " +
                     "       c.nombre_categoria " +
                     "FROM libro l " +
                     "INNER JOIN autor    a ON l.id_autor     = a.id_autor " +
                     "INNER JOIN categoria c ON l.id_categoria = c.id_categoria " +
                     "ORDER BY l.titulo";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearLibro(rs));
            }
        }
        return lista;
    }

    /**
     * Filtra libros por categoría.
     *
     * @param idCategoria ID de la categoría a filtrar.
     * @return Lista de {@link LibroBeans} filtrada.
     * @throws SQLException si ocurre un error de BD.
     */
    public List<LibroBeans> filtrarPorCategoria(int idCategoria) throws SQLException {
        List<LibroBeans> lista = new ArrayList<>();

        String sql = "SELECT l.id_libro, l.titulo, l.anio_publicacion, " +
                     "       l.id_autor, l.id_categoria, " +
                     "       a.nombre AS nombre_autor, " +
                     "       c.nombre_categoria " +
                     "FROM libro l " +
                     "INNER JOIN autor    a ON l.id_autor     = a.id_autor " +
                     "INNER JOIN categoria c ON l.id_categoria = c.id_categoria " +
                     "WHERE l.id_categoria = ? " +
                     "ORDER BY l.titulo";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLibro(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Filtra libros por autor.
     *
     * @param idAutor ID del autor a filtrar.
     * @return Lista de {@link LibroBeans} filtrada.
     * @throws SQLException si ocurre un error de BD.
     */
    public List<LibroBeans> filtrarPorAutor(int idAutor) throws SQLException {
        List<LibroBeans> lista = new ArrayList<>();

        String sql = "SELECT l.id_libro, l.titulo, l.anio_publicacion, " +
                     "       l.id_autor, l.id_categoria, " +
                     "       a.nombre AS nombre_autor, " +
                     "       c.nombre_categoria " +
                     "FROM libro l " +
                     "INNER JOIN autor    a ON l.id_autor     = a.id_autor " +
                     "INNER JOIN categoria c ON l.id_categoria = c.id_categoria " +
                     "WHERE l.id_autor = ? " +
                     "ORDER BY l.titulo";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAutor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLibro(rs));
                }
            }
        }
        return lista;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  UPDATE – Actualizar libro
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Actualiza los datos de un libro existente.
     *
     * @param libro objeto {@link LibroBeans} con los datos actualizados (debe tener idLibro).
     * @return {@code true} si la actualización fue exitosa.
     * @throws SQLException si ocurre un error de BD.
     */
    public boolean actualizar(LibroBeans libro) throws SQLException {
        String sql = "UPDATE libro SET titulo = ?, anio_publicacion = ?, " +
                     "id_autor = ?, id_categoria = ? WHERE id_libro = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt   (2, libro.getAnioPublicacion());
            ps.setInt   (3, libro.getIdAutor());
            ps.setInt   (4, libro.getIdCategoria());
            ps.setInt   (5, libro.getIdLibro());

            return ps.executeUpdate() > 0;
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  DELETE – Eliminar libro
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Elimina un libro por su ID.
     *
     * @param idLibro ID del libro a eliminar.
     * @return {@code true} si la eliminación fue exitosa.
     * @throws SQLException si ocurre un error de BD.
     */
    public boolean eliminar(int idLibro) throws SQLException {
        String sql = "DELETE FROM libro WHERE id_libro = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idLibro);
            return ps.executeUpdate() > 0;
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Método auxiliar privado
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Mapea una fila del ResultSet a un objeto {@link LibroBeans}.
     */
    private LibroBeans mapearLibro(ResultSet rs) throws SQLException {
        return new LibroBeans(
                rs.getInt   ("id_libro"),
                rs.getString("titulo"),
                rs.getInt   ("anio_publicacion"),
                rs.getInt   ("id_autor"),
                rs.getInt   ("id_categoria"),
                rs.getString("nombre_autor"),
                rs.getString("nombre_categoria")
        );
    }
}
