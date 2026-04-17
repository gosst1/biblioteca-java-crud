# 📚 Biblioteca Digital — POO404 — Universidad Don Bosco
## Desafío Práctico #02

---

## 🗂️ Estructura del proyecto

```
BibliotecaDigital/
├── sql/
│   └── biblioteca_db.sql          ← Script para crear la BD en WAMP
└── src/
    ├── util/
    │   └── Conexion.java          ← Manejo de conexión JDBC (Singleton)
    ├── beans/
    │   ├── AutorBeans.java        ← Entidad Autor (con getters/setters)
    │   ├── CategoriaBeans.java    ← Entidad Categoría
    │   └── LibroBeans.java        ← Entidad Libro
    ├── datos/
    │   ├── AutorDatos.java        ← Consultas de autor (para combos)
    │   ├── CategoriaDatos.java    ← Consultas de categoría
    │   └── LibroDatos.java        ← CRUD completo de libros
    └── vista/
        └── frmBiblioteca.java     ← Formulario principal (JFrame)
```

---

## ⚙️ PASO 1 — Crear la base de datos en WAMP

1. Abre **WAMP** y asegúrate de que el servidor MySQL esté corriendo (ícono verde).
2. Abre tu navegador y ve a: `http://localhost/phpmyadmin`
3. En phpMyAdmin, haz clic en **"SQL"** (pestaña superior).
4. Abre el archivo `sql/biblioteca_db.sql` con un editor de texto (Bloc de notas, etc.).
5. Copia todo el contenido y pégalo en la ventana SQL de phpMyAdmin.
6. Haz clic en **"Continuar"** o **"Go"**.
7. Verifica que se haya creado la base de datos `biblioteca_db` con sus tablas y datos.

---

## 🛠️ PASO 2 — Configurar el proyecto en IntelliJ IDEA

### 2.1 — Crear el proyecto

1. Abre **IntelliJ IDEA**.
2. Ve a `File → New → Project`.
3. Selecciona **Java** (sin frameworks adicionales).
4. Dale el nombre: `BibliotecaDigital`.
5. Haz clic en **Finish/Create**.

### 2.2 — Copiar el código fuente

1. En el panel izquierdo (Project), busca la carpeta `src`.
2. Crea los paquetes:
   - Clic derecho en `src` → `New → Package` → escribe `util`
   - Repite para: `beans`, `datos`, `vista`
3. Dentro de cada paquete, crea las clases Java correspondientes y copia el código de cada archivo.

   > **Alternativa rápida:** Copia directamente la carpeta `src/` de este proyecto
   > dentro de la carpeta `src` que generó IntelliJ.

### 2.3 — Agregar el conector MySQL (JDBC Driver)

El proyecto necesita el archivo **`mysql-connector-j-X.X.X.jar`**.

#### Opción A — Descargar manualmente
1. Ve a: https://dev.mysql.com/downloads/connector/j/
2. Selecciona **"Platform Independent"** y descarga el ZIP.
3. Extrae el ZIP y copia el archivo `.jar` a una carpeta dentro del proyecto,
   por ejemplo: `BibliotecaDigital/lib/mysql-connector-j.jar`

#### Opción B — Con Maven (si usaste proyecto Maven en IntelliJ)
Agrega esta dependencia en tu `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.3.0</version>
</dependency>
```

#### Agregar el JAR al classpath en IntelliJ (Opción A)
1. Ve a `File → Project Structure` (o `Ctrl+Alt+Shift+S`).
2. Selecciona **"Libraries"** en el panel izquierdo.
3. Haz clic en el botón **"+"** → `Java`.
4. Navega hasta el archivo `.jar` que descargaste y selecciónalo.
5. Haz clic en **OK** → **Apply** → **OK**.

---

## 🔑 PASO 3 — Verificar la configuración de conexión

Abre el archivo `src/util/Conexion.java` y verifica estos valores:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/biblioteca_db?...";
private static final String USUARIO  = "root";
private static final String PASSWORD = "";   // ← Cambia esto si tu root tiene contraseña
```

> En WAMP, por defecto el usuario es `root` y la contraseña está vacía.
> Si tu configuración es diferente, actualiza los valores.

---

## ▶️ PASO 4 — Ejecutar el proyecto

1. Abre el archivo `src/vista/frmBiblioteca.java`.
2. Busca el método `main` al final del archivo.
3. Haz clic en el botón ▶️ verde a la izquierda del método `main`.
4. O usa el atajo: `Shift + F10`.

---

## 🖥️ Funcionalidades del sistema

| Acción          | Cómo usarla |
|-----------------|-------------|
| **Registrar libro** | Llena los campos y haz clic en 💾 Guardar |
| **Editar libro** | Selecciona una fila de la tabla, modifica los campos y haz clic en ✏️ Editar |
| **Eliminar libro** | Selecciona una fila de la tabla y haz clic en 🗑️ Eliminar |
| **Limpiar formulario** | Haz clic en 🔄 Limpiar |
| **Filtrar por categoría** | Selecciona "Categoría" en el combo de filtro, elige la categoría y haz clic en 🔍 Filtrar |
| **Filtrar por autor** | Selecciona "Autor" en el combo de filtro, elige el autor y haz clic en 🔍 Filtrar |
| **Ver todos** | Haz clic en 📋 Todos |

---

## ⚠️ Solución de errores comunes

| Error | Solución |
|-------|----------|
| `Driver MySQL no encontrado` | El archivo `.jar` del conector no está en el classpath. Revisa el Paso 2.3. |
| `Access denied for user 'root'` | La contraseña en `Conexion.java` es incorrecta. Revisa el Paso 3. |
| `Unknown database 'biblioteca_db'` | La base de datos no fue creada. Ejecuta el script SQL (Paso 1). |
| `Communications link failure` | WAMP no está corriendo o MySQL está detenido. Verifica que WAMP tenga el ícono verde. |

---

## 📋 Tecnologías utilizadas

- **Java** con **JFrame** — Interfaz gráfica de escritorio
- **JDBC** — Conexión a base de datos
- **PreparedStatement** — Consultas SQL seguras
- **DefaultTableModel** — Tabla de libros
- **DefaultComboBoxModel** — Combos de autor y categoría
- **MySQL** via **WAMP** — Base de datos
- **JOptionPane** — Mensajes de éxito y error
