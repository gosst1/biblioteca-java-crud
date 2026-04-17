package datos;

import beans.AutorBeans;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos para la entidad Autor.
 * Provee métodos de consulta para poblar los combos de la interfaz.
 */
public class AutorDatos {

    /**
     * Retorna la lista completa de autores desde la base de datos.
     * Se usa para cargar el JComboBox de autores.
     *
     * @return Lista de {@link AutorBeans}.
     * @throws SQLException si ocurre un error de BD.
     */
    public List<AutorBeans> listarAutores() throws SQLException {
        List<AutorBeans> lista = new ArrayList<>();

        String sql = "SELECT id_autor, nombre, nacionalidad FROM autor ORDER BY nombre";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AutorBeans autor = new AutorBeans(
                        rs.getInt("id_autor"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad")
                );
                lista.add(autor);
            }
        }
        return lista;
    }

    /**
     * Busca un autor por su ID.
     *
     * @param idAutor ID del autor a buscar.
     * @return {@link AutorBeans} encontrado o {@code null} si no existe.
     * @throws SQLException si ocurre un error de BD.
     */
    public AutorBeans buscarPorId(int idAutor) throws SQLException {
        AutorBeans autor = null;

        String sql = "SELECT id_autor, nombre, nacionalidad FROM autor WHERE id_autor = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAutor);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    autor = new AutorBeans(
                            rs.getInt("id_autor"),
                            rs.getString("nombre"),
                            rs.getString("nacionalidad")
                    );
                }
            }
        }
        return autor;
    }
}
