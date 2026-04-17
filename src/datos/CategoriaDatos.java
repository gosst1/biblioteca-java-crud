package datos;

import beans.CategoriaBeans;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos para la entidad Categoría.
 * Provee métodos de consulta para poblar los combos de la interfaz.
 */
public class CategoriaDatos {

    /**
     * Retorna la lista completa de categorías desde la base de datos.
     * Se usa para cargar el JComboBox de categorías.
     *
     * @return Lista de {@link CategoriaBeans}.
     * @throws SQLException si ocurre un error de BD.
     */
    public List<CategoriaBeans> listarCategorias() throws SQLException {
        List<CategoriaBeans> lista = new ArrayList<>();

        String sql = "SELECT id_categoria, nombre_categoria FROM categoria ORDER BY nombre_categoria";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaBeans cat = new CategoriaBeans(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria")
                );
                lista.add(cat);
            }
        }
        return lista;
    }
}
