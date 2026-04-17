package vista;

import beans.AutorBeans;
import beans.CategoriaBeans;
import beans.LibroBeans;
import datos.AutorDatos;
import datos.CategoriaDatos;
import datos.LibroDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class frmBiblioteca extends JFrame {

    // ── Componentes de la interfaz ────────────────────────────────────────────
    private JTextField  txtTitulo;
    private JTextField  txtAnio;
    private JComboBox<AutorBeans>     cmbAutor;
    private JComboBox<CategoriaBeans> cmbCategoria;
    private JComboBox<Object>         cmbFiltro;      // combo para el tipo de filtro
    private JComboBox<Object>         cmbValorFiltro; // combo para el valor del filtro

    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnFiltrar;
    private JButton btnMostrarTodos;

    private JTable          tablaLibros;
    private DefaultTableModel modeloTabla;

    // ── Capa de datos ─────────────────────────────────────────────────────────
    private final LibroDatos    libroDatos    = new LibroDatos();
    private final AutorDatos    autorDatos    = new AutorDatos();
    private final CategoriaDatos catDatos     = new CategoriaDatos();

    // ── Estado interno ────────────────────────────────────────────────────────
    private int idLibroSeleccionado = -1;   // -1 = sin selección activa

    // ── Constructor ───────────────────────────────────────────────────────────

    public frmBiblioteca() {
        initComponentes();
        cargarCombos();
        cargarTabla();
        configurarEventos();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  1. CONSTRUCCIÓN DE LA INTERFAZ
    // ══════════════════════════════════════════════════════════════════════════

    private void initComponentes() {
        setTitle(" Sistema de Biblioteca Digital — Universidad Don Bosco");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 250));
        setLayout(new BorderLayout(10, 10));

        // ── Panel superior: título ────────────────────────────────────────────
        JLabel lblTituloPrincipal = new JLabel("Biblioteca Digital", SwingConstants.CENTER);
        lblTituloPrincipal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTituloPrincipal.setForeground(new Color(30, 60, 120));
        lblTituloPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        add(lblTituloPrincipal, BorderLayout.NORTH);

        // ── Panel izquierdo: formulario de registro ───────────────────────────
        JPanel pnlFormulario = crearPanelFormulario();
        add(pnlFormulario, BorderLayout.WEST);

        // ── Panel central: tabla y filtros ────────────────────────────────────
        JPanel pnlCentral = crearPanelTabla();
        add(pnlCentral, BorderLayout.CENTER);
    }

    /** Crea el panel izquierdo con los campos del formulario y los botones. */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 5),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(30, 60, 120), 1),
                        " Datos del Libro ",
                        javax.swing.border.TitledBorder.LEFT,
                        javax.swing.border.TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 13),
                        new Color(30, 60, 120))
        ));
        panel.setPreferredSize(new Dimension(280, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 10, 4, 10);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.weightx = 1.0;

        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(etiqueta("Título del libro:"), gbc);
        gbc.gridy = 1;
        txtTitulo = new JTextField();
        estilizarTextField(txtTitulo);
        panel.add(txtTitulo, gbc);

        // Año
        gbc.gridy = 2;
        panel.add(etiqueta("Año de publicación:"), gbc);
        gbc.gridy = 3;
        txtAnio = new JTextField();
        estilizarTextField(txtAnio);
        panel.add(txtAnio, gbc);

        // Autor
        gbc.gridy = 4;
        panel.add(etiqueta("Autor:"), gbc);
        gbc.gridy = 5;
        cmbAutor = new JComboBox<>();
        estilizarCombo(cmbAutor);
        panel.add(cmbAutor, gbc);

        // Categoría
        gbc.gridy = 6;
        panel.add(etiqueta("Categoría:"), gbc);
        gbc.gridy = 7;
        cmbCategoria = new JComboBox<>();
        estilizarCombo(cmbCategoria);
        panel.add(cmbCategoria, gbc);

        // Espacio
        gbc.gridy = 8;
        gbc.insets = new Insets(20, 10, 4, 10);
        panel.add(new JSeparator(), gbc);

        // Botones CRUD
        gbc.insets = new Insets(6, 10, 4, 10);
        gbc.gridy = 9;
        btnGuardar  = crearBoton("Guardar",   new Color(34, 139, 34));
        panel.add(btnGuardar, gbc);

        gbc.gridy = 10;
        btnEditar   = crearBoton("Editar",    new Color(30, 100, 200));
        btnEditar.setEnabled(false);
        panel.add(btnEditar, gbc);

        gbc.gridy = 11;
        btnEliminar = crearBoton("🗑️  Eliminar",  new Color(180, 30, 30));
        btnEliminar.setEnabled(false);
        panel.add(btnEliminar, gbc);

        gbc.gridy = 12;
        btnLimpiar  = crearBoton("Limpiar",   new Color(100, 100, 100));
        panel.add(btnLimpiar, gbc);

        // Relleno inferior
        gbc.gridy   = 13;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        return panel;
    }

    /** Crea el panel central con la tabla y el panel de filtros. */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        // ── Panel de filtros ──────────────────────────────────────────────────
        JPanel pnlFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        pnlFiltros.setBackground(Color.WHITE);
        pnlFiltros.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(30, 60, 120), 1),
                " Filtros ",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(30, 60, 120)));

        JLabel lblFiltrarPor = new JLabel("Filtrar por:");
        lblFiltrarPor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pnlFiltros.add(lblFiltrarPor);

        cmbFiltro = new JComboBox<>(new Object[]{"-- Seleccione --", "Categoría", "Autor"});
        cmbFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbFiltro.setPreferredSize(new Dimension(130, 28));
        pnlFiltros.add(cmbFiltro);

        cmbValorFiltro = new JComboBox<>();
        cmbValorFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbValorFiltro.setPreferredSize(new Dimension(180, 28));
        cmbValorFiltro.setEnabled(false);
        pnlFiltros.add(cmbValorFiltro);

        btnFiltrar = new JButton("🔍 Filtrar");
        btnFiltrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFiltrar.setBackground(new Color(30, 60, 120));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setPreferredSize(new Dimension(100, 28));
        pnlFiltros.add(btnFiltrar);

        btnMostrarTodos = new JButton("📋 Todos");
        btnMostrarTodos.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnMostrarTodos.setBackground(new Color(80, 80, 80));
        btnMostrarTodos.setForeground(Color.WHITE);
        btnMostrarTodos.setFocusPainted(false);
        btnMostrarTodos.setPreferredSize(new Dimension(100, 28));
        pnlFiltros.add(btnMostrarTodos);

        panel.add(pnlFiltros, BorderLayout.NORTH);

        // ── Tabla de libros ───────────────────────────────────────────────────
        String[] columnas = {"ID", "Título", "Año", "Autor", "Categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.setRowHeight(24);
        tablaLibros.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaLibros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaLibros.getTableHeader().setBackground(new Color(30, 60, 120));
        tablaLibros.getTableHeader().setForeground(Color.WHITE);
        tablaLibros.setGridColor(new Color(220, 220, 230));
        tablaLibros.setShowGrid(true);

        // Ocultar columna ID (se conserva en el modelo para uso interno)
        tablaLibros.getColumnModel().getColumn(0).setMinWidth(0);
        tablaLibros.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaLibros.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scroll = new JScrollPane(tablaLibros);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(30, 60, 120), 1));
        panel.add(scroll, BorderLayout.CENTER);

        // Etiqueta de estado
        JLabel lblEstado = new JLabel(" Selecciona un registro de la tabla para editar o eliminar.");
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblEstado.setForeground(Color.GRAY);
        panel.add(lblEstado, BorderLayout.SOUTH);

        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  2. EVENTOS
    // ══════════════════════════════════════════════════════════════════════════

    private void configurarEventos() {

        // ── Guardar ───────────────────────────────────────────────────────────
        btnGuardar.addActionListener(e -> accionGuardar());

        // ── Editar ────────────────────────────────────────────────────────────
        btnEditar.addActionListener(e -> accionEditar());

        // ── Eliminar ──────────────────────────────────────────────────────────
        btnEliminar.addActionListener(e -> accionEliminar());

        // ── Limpiar ───────────────────────────────────────────────────────────
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // ── Selección en la tabla → cargar datos en el formulario ─────────────
        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFilaSeleccionada();
            }
        });

        // ── Cambio en combo de tipo de filtro ─────────────────────────────────
        cmbFiltro.addActionListener(e -> actualizarComboValorFiltro());

        // ── Botón Filtrar ─────────────────────────────────────────────────────
        btnFiltrar.addActionListener(e -> accionFiltrar());

        // ── Botón Mostrar Todos ───────────────────────────────────────────────
        btnMostrarTodos.addActionListener(e -> {
            cmbFiltro.setSelectedIndex(0);
            cargarTabla();
        });
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  3. LÓGICA DE NEGOCIO / CRUD
    // ══════════════════════════════════════════════════════════════════════════

    /** Guarda un nuevo libro en la base de datos. */
    private void accionGuardar() {
        if (!validarCampos()) return;

        try {
            LibroBeans libro = construirLibroDesdeFormulario();
            if (idLibroSeleccionado != -1) {
                libro.setIdLibro(idLibroSeleccionado);

                if (libroDatos.actualizar(libro)) {
                    JOptionPane.showMessageDialog(this,
                            " Libro actualizado exitosamente.",
                            "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            " No se pudo actualizar el libro.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }

            } else {
                if (libroDatos.insertar(libro)) {
                    JOptionPane.showMessageDialog(this,
                            " Libro guardado exitosamente.",
                            "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            " No se pudo guardar el libro.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }

            limpiarFormulario();
            cargarTabla();

        } catch (SQLException ex) {
            mostrarErrorBD("guardar/actualizar el libro", ex);
        }
    }

    /** Actualiza el libro actualmente seleccionado en la tabla. */
    private void accionEditar() {
        if (idLibroSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    " Selecciona un libro de la tabla antes de editar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;

        try {
            LibroBeans libro = construirLibroDesdeFormulario();
            libro.setIdLibro(idLibroSeleccionado);

            if (libroDatos.actualizar(libro)) {
                JOptionPane.showMessageDialog(this,
                        " Libro actualizado exitosamente.",
                        "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this,
                        " No se pudo actualizar el libro. Inténtalo de nuevo.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            mostrarErrorBD("actualizar el libro", ex);
        }
    }

    /** Elimina el libro seleccionado en la tabla, previa confirmación. */
    private void accionEliminar() {
        if (idLibroSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    " Selecciona un libro de la tabla antes de eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar este libro?\nEsta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) return;

        try {
            if (libroDatos.eliminar(idLibroSeleccionado)) {
                JOptionPane.showMessageDialog(this,
                        " Libro eliminado exitosamente.",
                        "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this,
                        " No se pudo eliminar el libro. Inténtalo de nuevo.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            mostrarErrorBD("eliminar el libro", ex);
        }
    }

    /** Aplica el filtro seleccionado en los combos de filtro. */
    private void accionFiltrar() {
        String tipoFiltro = (String) cmbFiltro.getSelectedItem();

        if (tipoFiltro == null || tipoFiltro.equals("-- Seleccione --")) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un tipo de filtro (Categoría o Autor).",
                    "Filtro inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cmbValorFiltro.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    " Selecciona un valor para el filtro.",
                    "Filtro inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<LibroBeans> lista;

            if (tipoFiltro.equals("Categoría")) {
                CategoriaBeans cat = (CategoriaBeans) cmbValorFiltro.getSelectedItem();
                lista = libroDatos.filtrarPorCategoria(cat.getIdCategoria());
            } else {
                AutorBeans autor = (AutorBeans) cmbValorFiltro.getSelectedItem();
                lista = libroDatos.filtrarPorAutor(autor.getIdAutor());
            }

            poblarTabla(lista);

        } catch (SQLException ex) {
            mostrarErrorBD("filtrar los libros", ex);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  4. CARGA DE DATOS
    // ══════════════════════════════════════════════════════════════════════════

    /** Carga los combos de Autor y Categoría desde la BD. */
    private void cargarCombos() {
        try {
            // ── Autores ───────────────────────────────────────────────────────
            DefaultComboBoxModel<AutorBeans> modeloAutor = new DefaultComboBoxModel<>();
            for (AutorBeans a : autorDatos.listarAutores()) {
                modeloAutor.addElement(a);
            }
            cmbAutor.setModel(modeloAutor);

            // ── Categorías ────────────────────────────────────────────────────
            DefaultComboBoxModel<CategoriaBeans> modeloCat = new DefaultComboBoxModel<>();
            for (CategoriaBeans c : catDatos.listarCategorias()) {
                modeloCat.addElement(c);
            }
            cmbCategoria.setModel(modeloCat);

        } catch (SQLException ex) {
            mostrarErrorBD("cargar los datos de los combos", ex);
        }
    }

    /** Carga todos los libros en la tabla. */
    private void cargarTabla() {
        try {
            poblarTabla(libroDatos.listarTodos());
        } catch (SQLException ex) {
            mostrarErrorBD("cargar los libros", ex);
        }
    }

    /** Llena el DefaultTableModel con la lista de libros recibida. */
    private void poblarTabla(List<LibroBeans> lista) {
        modeloTabla.setRowCount(0); // Limpiar filas existentes
        for (LibroBeans l : lista) {
            modeloTabla.addRow(new Object[]{
                    l.getIdLibro(),
                    l.getTitulo(),
                    l.getAnioPublicacion(),
                    l.getNombreAutor(),
                    l.getNombreCategoria()
            });
        }
    }

    /** Cuando el usuario selecciona una fila, carga sus datos en el formulario. */
    private void cargarFilaSeleccionada() {
        int fila = tablaLibros.getSelectedRow();
        if (fila == -1) return;

        idLibroSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
        txtTitulo.setText((String) modeloTabla.getValueAt(fila, 1));
        txtAnio.setText  (String.valueOf(modeloTabla.getValueAt(fila, 2)));

        // Seleccionar autor en el combo
        String autorNombre = (String) modeloTabla.getValueAt(fila, 3);
        for (int i = 0; i < cmbAutor.getItemCount(); i++) {
            if (cmbAutor.getItemAt(i).getNombre().equals(autorNombre)) {
                cmbAutor.setSelectedIndex(i);
                break;
            }
        }

        // Seleccionar categoría en el combo
        String catNombre = (String) modeloTabla.getValueAt(fila, 4);
        for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
            if (cmbCategoria.getItemAt(i).getNombreCategoria().equals(catNombre)) {
                cmbCategoria.setSelectedIndex(i);
                break;
            }
        }

        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    /** Actualiza el combo de valor del filtro según el tipo seleccionado. */
    private void actualizarComboValorFiltro() {
        String tipo = (String) cmbFiltro.getSelectedItem();
        DefaultComboBoxModel<Object> modelo = new DefaultComboBoxModel<>();

        if ("Categoría".equals(tipo)) {
            for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                modelo.addElement(cmbCategoria.getItemAt(i));
            }
            cmbValorFiltro.setModel(modelo);
            cmbValorFiltro.setEnabled(true);
        } else if ("Autor".equals(tipo)) {
            for (int i = 0; i < cmbAutor.getItemCount(); i++) {
                modelo.addElement(cmbAutor.getItemAt(i));
            }
            cmbValorFiltro.setModel(modelo);
            cmbValorFiltro.setEnabled(true);
        } else {
            cmbValorFiltro.setModel(modelo);
            cmbValorFiltro.setEnabled(false);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  5. UTILIDADES DE LA INTERFAZ
    // ══════════════════════════════════════════════════════════════════════════

    /** Limpia el formulario y restablece el estado de los botones. */
    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtAnio.setText("");
        if (cmbAutor.getItemCount()     > 0) cmbAutor.setSelectedIndex(0);
        if (cmbCategoria.getItemCount() > 0) cmbCategoria.setSelectedIndex(0);
        idLibroSeleccionado = -1;
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        tablaLibros.clearSelection();
    }

    /**
     * Valida que los campos obligatorios estén completos y con formato correcto.
     *
     * @return {@code true} si todo es válido.
     */
    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    " El campo 'Título' es obligatorio.",
                    "Validación", JOptionPane.ERROR_MESSAGE);
            txtTitulo.requestFocus();
            return false;
        }
        if (txtAnio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    " El campo 'Año de publicación' es obligatorio.",
                    "Validación", JOptionPane.ERROR_MESSAGE);
            txtAnio.requestFocus();
            return false;
        }
        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            if (anio < 1000 || anio > 2026) {
                JOptionPane.showMessageDialog(this,
                        " El año debe estar entre 1000 y 2100.",
                        "Validación", JOptionPane.ERROR_MESSAGE);
                txtAnio.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    " El año debe ser un número entero válido (ej: 2023).",
                    "Validación", JOptionPane.ERROR_MESSAGE);
            txtAnio.requestFocus();
            return false;
        }
        if (cmbAutor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    " Debes seleccionar un autor.",
                    "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cmbCategoria.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    " Debes seleccionar una categoría.",
                    "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /** Construye un objeto {@link LibroBeans} con los datos actuales del formulario. */
    private LibroBeans construirLibroDesdeFormulario() {
        LibroBeans libro = new LibroBeans();
        libro.setTitulo(txtTitulo.getText().trim());
        libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText().trim()));
        libro.setIdAutor    (((AutorBeans)     cmbAutor.getSelectedItem()).getIdAutor());
        libro.setIdCategoria(((CategoriaBeans) cmbCategoria.getSelectedItem()).getIdCategoria());
        return libro;
    }

    /** Muestra un mensaje de error detallado con la capa y el mensaje de SQL. */
    private void mostrarErrorBD(String accion, SQLException ex) {
        JOptionPane.showMessageDialog(this,
                " Error en la capa de datos al intentar " + accion + ".\n\n" +
                "Detalle técnico (Capa BD):\n" + ex.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }

    // ── Helpers de estilo ─────────────────────────────────────────────────────

    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(50, 50, 80));
        return lbl;
    }

    private void estilizarTextField(JTextField tf) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setPreferredSize(new Dimension(240, 28));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200)),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));
    }

    private void estilizarCombo(JComboBox<?> cmb) {
        cmb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmb.setPreferredSize(new Dimension(240, 28));
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(240, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN – Punto de entrada
    // ══════════════════════════════════════════════════════════════════════════

    public static void main(String[] args) {
        // Aplicar Look & Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new frmBiblioteca().setVisible(true));
    }
}
