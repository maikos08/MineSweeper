### README

# **Proyecto BuscaMinas**

Este proyecto es una implementación de un juego clásico basado en un enfoque modular y patrones arquitectónicos. A continuación, se explica la organización del código y los conceptos clave detrás de su arquitectura.

---

## **Estructura del Directorio**
La organización del proyecto se basa en una separación de responsabilidades, agrupando componentes relacionados:

```plaintext
mine-sweeper/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── software/
                └── ulpgc/
                    └── MineSweeper/
                        ├── app/                   # Entrada principal y configuración de la UI.
                        ├── arquitecture/          # Lógica principal del juego y gestión del modelo.
                        │   ├── control/           # Comunicación entre la lógica y la presentación.
                        │   ├── io/                # Manejo de imágenes y recursos externos.
                        │   ├── model/             # Modelo de datos del juego.
                        │   ├── services/          # Lógica de negocio y funcionalidades auxiliares.
                        │   └── view/              # Interfaz de usuario y su representación.
                        └── images/                # Imágenes utilizadas en la UI.
```

---

## **Patrones Arquitectónicos**

### **Modelo-Vista-Controlador (MVP)**
La arquitectura general del proyecto sigue este enfoque, separando las responsabilidades en tres capas:

1. **Modelo**:
    - Define los datos y la lógica del juego. Contiene clases para la representación del tablero, celdas, dificultades y el estado del juego.
    - Ejemplo: `Board`, `Cell`, y `Game`.

2. **Vista**:
    - Gestiona la presentación visual. Proporciona componentes para mostrar el tablero, la selección de dificultades y otros elementos de la UI.
    - Ejemplo: `SwingBoardDisplay`, `SwingDifficultyDialog`.

3. **Presenter**:
    - Media entre el modelo y la vista, actuando como coordinador. Gestiona eventos de usuario, actualiza el modelo y notifica a la vista.
    - Ejemplo: `BoardPresenter`, `SelectDifficultyCommand`.

---

### **Patrón Comando**
Se utiliza para encapsular solicitudes como objetos, permitiendo la ejecución de comandos sin conocer sus detalles. Este enfoque simplifica la ejecución y modificación de acciones.

- Implementado en: `Command` y `SelectDifficultyCommand`.

---

### **Patrón Observador**
Permite la comunicación reactiva entre componentes. Se utiliza para que el modelo notifique cambios relevantes, como el estado del juego o las celdas reveladas, a los observadores interesados.

- Implementado en: `Observer`, `GameStatusObserver`.

---

### **Patrón Singleton**
Garantiza que ciertas funcionalidades compartidas solo tengan una instancia global en toda la aplicación, como el conteo de banderas y minas.

- Implementado en: `FlagCounter`.

---

### **Servicios Auxiliares**
Encapsulan funcionalidades específicas para facilitar el mantenimiento y la reutilización del código:

- **Inicialización del tablero**:
    - Generación y colocación de minas con exclusiones.
    - Implementado en: `BoardInitializer`, `MinePlacer`.

- **Gestión de celdas**:
    - Descubrimiento y marcado de celdas.
    - Implementado en: `CellRevealer`, `CellToogler`.

- **Cómputo de minas adyacentes**:
    - Implementado en: `MineCounter`.

---

### **Modularidad**
Cada componente está diseñado con una única responsabilidad. Esto mejora la mantenibilidad y escalabilidad del proyecto. Por ejemplo:

- **Módulo de entrada/salida**:
    - Manejo de recursos externos como imágenes (`FileImageLoader`, `ImageDeserializer`).

- **Módulo del modelo**:
    - Representación de entidades centrales (`Cell`, `Difficulty`, `GameStatus`).

- **Módulo de la vista**:
    - Interfaz visual (`SwingBoardDisplay`, `SwingTimeDisplay`).

---

## **Ejemplo de Flujo**
1. El usuario interactúa con la UI para seleccionar una dificultad.
2. El controlador recibe la acción y crea un nuevo tablero basado en la dificultad seleccionada.
3. La vista se actualiza para reflejar el nuevo estado del modelo.
4. Durante el juego, al hacer clic en una celda:
    - Se consulta y actualiza el modelo.
    - La vista muestra los cambios en tiempo real.
    - Los observadores verifican si el jugador ha ganado o perdido.

---

## **Tecnologías Utilizadas**
- **Java Swing**: Para la interfaz gráfica.
- **Maven**: Gestión de dependencias y configuración del proyecto.
- **Java 23**: Versión requerida para ejecutar el proyecto.

---

## **Configuración y Ejecución**
1. Clonar el repositorio.
2. Compilar el proyecto con Maven:
   ```bash
   mvn clean install
   ```
3. Ejecutar la clase principal:
   ```bash
   java -cp target/BuscaMinas-1.0-SNAPSHOT.jar software.ulpgc.MineSweeper.app.Main
   ```

---

## **Contribuciones**
El diseño del proyecto está pensado para facilitar la ampliación, como añadir nuevas dificultades, estilos de interfaz, o modos de juego.
