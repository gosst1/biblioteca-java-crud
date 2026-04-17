package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL.
 */
public class Conexion {

    // ── Datos de conexión ──────────────────────────────────────────────────────
    private static final String URL      = "jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "";          // Cambia si tu root tiene contraseña en WAMP

    // ── Instancia única (Singleton) ────────────────────────────────────────────
    private static Connection conexion = null;

    /** Constructor privado: no se deben crear instancias externas. */
    private Conexion() {}

    /**
     * Devuelve la conexión activa; si no existe o está cerrada, la crea.
     *
     * @return objeto {@link Connection} listo para usar.
     * @throws SQLException si el driver no se encuentra o los datos son incorrectos.
     */
    public static Connection getConexion() throws SQLException {
        try {
            // Cargar driver (necesario en algunos entornos sin module-path)
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado. Agrega mysql-connector-j al proyecto.", e);
        }
        return conexion;
    }

    /**
     * Cierra la conexión si está abierta.
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
